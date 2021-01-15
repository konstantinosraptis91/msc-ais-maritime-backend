package kraptis91.maritime.db.dao.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.validation.constraints.NotEmpty;
import kraptis91.maritime.codelists.CodelistsOfBiMap;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.db.enums.MongoDB;
import kraptis91.maritime.db.enums.MongoDBCollection;
import kraptis91.maritime.db.enums.TransactionStatus;
import kraptis91.maritime.model.*;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.csv.NariStaticDto;
import kraptis91.maritime.parser.enums.MMSICountryCode;
import kraptis91.maritime.parser.enums.ShipTypes;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020.
 */
public class MongoVesselDao implements VesselDao, DocumentBuilder {

    public static final Logger LOGGER = Logger.getLogger(MongoVesselDao.class.getName());

    public static MongoCollection<Vessel> createVesselCollection() {
        return MongoDB.MARITIME
            .getDatabase()
            .getCollection(MongoDBCollection.VESSELS.getCollectionName(), Vessel.class);
    }

    public static MongoCollection<Document> createDocumentCollection() {
        return MongoDB.MARITIME
            .getDatabase()
            .getCollection(MongoDBCollection.VESSELS.getCollectionName(), Document.class);
    }

    @Override
    public void insertMany(@NotNull InputStream csvStream, final int chunkSize) throws Exception {

        LOGGER.info("Inserting " + csvStream.available() + " bytes to db.");

        final BufferedReader bufferedReader =
            new BufferedReader(
                new InputStreamReader(InputStreamUtils.getBufferedInputStream(csvStream)));
        final CSVParser parser = new CSVParser();
        final Map<Integer, Vessel> vesselMap = new LinkedHashMap<>(chunkSize);

        LOGGER.info("Chunk size csvStream " + chunkSize + ".");

        String line;
        NariStaticDto dto;
        boolean isFirstLine = true;

        while ((line = bufferedReader.readLine()) != null) {

            // omit first line
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            try {
                // parse current line to the dto
                dto = parser.extractNariStaticDto(line);

                // System.out.println(dto);
                // check to avoid duplicates
                if (!vesselMap.containsKey(dto.getMmsi())) {
                    // add to the list after model obj extraction
                    vesselMap.put(
                        dto.getMmsi(),
                        ModelExtractor.extractVessel(
                            dto,
                            ShipTypes.INSTANCE.getShipType(dto.getShipType()),
                            MMSICountryCode.INSTANCE.getCountryByMMSI(dto.getMmsi())));

                } else { // not a new mmsi, vessel already in set or db

                    // search for that vessel in map
                    vesselMap
                        .get(dto.getMmsi()) // get the vessel
                        .addVoyageAndApplyMeasurement(
                            ModelExtractor.extractVoyage(dto),
                            ReceiverMeasurement.builder()
                                .withETA(dto.getEta())
                                .withToPort(dto.getToPort())
                                .withDate(new Date(dto.getT()))
                                .build()); // add voyage if not
                    // exists and apply timestamp

                }

            } catch (CSVParserException e) {
                LOGGER.log(Level.WARNING, "Discarding corrupted line " + e.getMessage());
            }
        }
        // LOGGER.info(vesselSet.size() + " lines left, attempting to insert data to db.");
        // insert any data left
        insertMany(new LinkedHashSet<>(vesselMap.values()));
        // count total vessels added
        int totalVessels = vesselMap.size();
        // clear set
        vesselMap.clear();

        LOGGER.info("All lines inserted to db successfully.");
        LOGGER.info("Total vessels added to db: " + totalVessels);
    }

    @Override
    public void insertMany(@NotEmpty Set<Vessel> vesselSet) {

        LOGGER.info("Inserting " + vesselSet.size() + " vessels to db START.");
        long startTime = System.currentTimeMillis();
        TransactionStatus status = TransactionStatus.FAILED;

        if (!vesselSet.isEmpty()) {
            MongoCollection<Vessel> collection = createVesselCollection();
            collection.insertMany(new ArrayList<>(vesselSet));
            status = TransactionStatus.SUCCESS;
        } else {
            LOGGER.log(Level.WARNING, "Trying to insert an empty vessel set to db...");
        }

        long endTime = System.currentTimeMillis();
        LOGGER.info("Inserting " + vesselSet.size()
            + " vessels to db FINISH at [" + (endTime - startTime) + "]"
            + " with status: " + status.name());
    }

    @Override
    public void insertMany(@NotEmpty List<Vessel> vesselList) {

        LOGGER.info("Inserting " + vesselList.size() + " vessels to db START.");
        long startTime = System.currentTimeMillis();
        TransactionStatus status = TransactionStatus.FAILED;

        if (!vesselList.isEmpty()) {
            MongoCollection<Vessel> collection = createVesselCollection();
            collection.insertMany(vesselList);
            status = TransactionStatus.SUCCESS;
        } else {
            LOGGER.log(Level.WARNING, "Trying to insert an empty vessel list to db...");
        }

        long endTime = System.currentTimeMillis();
        LOGGER.info("Inserting " + vesselList.size()
            + " vessels to db FINISH at [" + (endTime - startTime) + "]"
            + " with status: " + status.name());
    }

    @Override
    public Optional<String> findObjectIdAsString(int mmsi) {
        return Optional.ofNullable(
            createDocumentCollection()
                .find(Filters.eq("mmsi", mmsi))
                .projection(new Document().append("_id", 1))
                .first())
            .map(d -> d.getObjectId("_id").toHexString());
    }

    @Override
    public Optional<Vessel> findVesselByMMSI(int mmsi) {
        return Optional.ofNullable(createVesselCollection()
            .find(Filters.eq("mmsi", mmsi)).first());
    }

    @Override
    public Optional<PlainVessel> findPlainVesselByMMSI(int mmsi) {
        return Optional.ofNullable(
            createDocumentCollection()
                .find(Filters.eq("mmsi", mmsi))
                .projection(createPlainVesselDocument())
                .map(ModelExtractor::extractPlainVessel)
                .first());
    }

    @Override
    public List<Vessel> findVesselsByType(String shipType, int skip, int limit) {

        final List<Vessel> vesselList = new ArrayList<>();

        createVesselCollection()
            .find(Filters.eq("shipType", shipType))
            .skip(skip)
            .limit(limit)
            .forEach((Consumer<Vessel>) vesselList::add);

        LOGGER.info("Found " + vesselList.size()
            + " vessels with ship type: " + shipType);

        return vesselList;
    }

    @Override
    public Optional<Vessel> findVesselByName(String vesselName) {
        return Optional.ofNullable(
            createVesselCollection()
                .find(Filters.eq("vesselName", vesselName))
                .first());
    }

    @Override
    public List<Vessel> findVessels(int skip, int limit) {
        final List<Vessel> vesselList = new ArrayList<>();
        createVesselCollection()
            .find()
            .skip(skip)
            .limit(limit)
            .forEach((Consumer<Vessel>) vesselList::add);

        LOGGER.info("Found " + vesselList.size() + " vessels");

        return vesselList;
    }

    @Override
    public List<PlainVessel> findPlainVesselsByType(String shipType, int skip, int limit) {

        final List<PlainVessel> vesselList = new ArrayList<>();

        createDocumentCollection()
            .find(Filters.eq("shipType", shipType))
            .projection(createPlainVesselDocument())
            .skip(skip)
            .limit(limit)
            .map(ModelExtractor::extractPlainVessel)
            .forEach((Consumer<PlainVessel>) vesselList::add);

        LOGGER.info("Found " + vesselList.size()
            + " plain vessels with ship type: " + shipType);

        return vesselList;
    }

    @Override
    public List<PlainVessel> findPlainVesselByCountryCode(String countryCode, int skip, int limit) {

        final List<PlainVessel> vesselList = new ArrayList<>();
        String codeToUpperCase = countryCode.toUpperCase();

        try {
            createDocumentCollection()
                .find(Filters.eq("country",
                    CodelistsOfBiMap.COUNTRY_CODE_MAP.getOptionalValueForId(codeToUpperCase)
                        .orElseThrow(() -> new NoSuchElementException(
                            "ERROR... Country code bimap does not contain country code " + codeToUpperCase))
                ))
                .projection(createPlainVesselDocument())
                .skip(skip)
                .limit(limit)
                .map(ModelExtractor::extractPlainVessel)
                .forEach((Consumer<PlainVessel>) vesselList::add);
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        LOGGER.info("Found " + vesselList.size()
            + " plain vessels for countryCode: " + countryCode);

        return vesselList;
    }

    @Override
    public List<PlainVessel> findPlainVessels(String shipType, String countryCode, int skip, int limit) {

        final List<PlainVessel> vesselList = new ArrayList<>();
        String codeToUpperCase = countryCode.toUpperCase();

        try {
            createDocumentCollection()
                .find(Filters.and(
                    Filters.eq("country",
                        CodelistsOfBiMap.COUNTRY_CODE_MAP.getOptionalValueForId(codeToUpperCase)
                            .orElseThrow(() -> new NoSuchElementException(
                                "ERROR... Country code bimap does not contain country code " + codeToUpperCase))),
                    Filters.eq("shipType", shipType)
                ))
                .projection(createPlainVesselDocument())
                .skip(skip)
                .limit(limit)
                .map(ModelExtractor::extractPlainVessel)
                .forEach((Consumer<PlainVessel>) vesselList::add);
        } catch (NoSuchElementException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }

        LOGGER.info("Found " + vesselList.size()
            + " plain vessels for countryCode: " + countryCode
            + ", with ship type: " + shipType);

        return vesselList;
    }
}
