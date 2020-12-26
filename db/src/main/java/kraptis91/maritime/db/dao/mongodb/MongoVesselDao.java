package kraptis91.maritime.db.dao.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import jakarta.validation.constraints.NotEmpty;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.db.enums.MongoDB;
import kraptis91.maritime.db.enums.MongoDBCollection;
import kraptis91.maritime.model.ReceiverMeasurement;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.csv.NariStaticDto;
import kraptis91.maritime.parser.enums.MMSICountryCode;
import kraptis91.maritime.parser.enums.ShipTypes;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;
import kraptis91.maritime.db.utils.ModelExtractor;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020. */
public class MongoVesselDao implements VesselDao {

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

  //  @Override
  //  public void insertMany(@NotNull InputStream csvStream, final int chunkSize) throws Exception {
  //
  //    // LOGGER.info("Inserting " + csvStream.available() + " bytes to db.");
  //
  //    final BufferedReader bufferedReader =
  //        new BufferedReader(
  //            new InputStreamReader(InputStreamUtils.getBufferedInputStream(csvStream)));
  //    final CSVParser parser = new CSVParser();
  //    final Map<Integer, Vessel> vesselBufferMap = new LinkedHashMap<>(chunkSize);
  //    final Set<Integer> totalMMSIBufferSet = new LinkedHashSet<>();
  //    int totalVessels = 0;
  //
  //    // LOGGER.info("Chunk size csvStream " + chunkSize + ".");
  //
  //    String line;
  //    NariStaticDto dto;
  //    boolean isFirstLine = true;
  //
  //    while ((line = bufferedReader.readLine()) != null) {
  //
  //      // omit first line
  //      if (isFirstLine) {
  //        isFirstLine = false;
  //        continue;
  //      }
  //
  //      try {
  //        // parse current line to the dto
  //        dto = parser.extractNariStaticDto(line);
  //
  //        // System.out.println(dto);
  //        // check to avoid duplicates
  //        if (!totalMMSIBufferSet.contains(dto.getMmsi())) {
  //          // add to the list after model obj extraction
  //          vesselBufferMap.put(
  //              dto.getMmsi(),
  //              ModelExtractor.extractVessel(
  //                  dto,
  //                  ShipTypes.INSTANCE.getShipType(dto.getShipType()),
  //                  MMSICountryCode.INSTANCE.getCountryByMMSI(dto.getMmsi())));
  //          totalMMSIBufferSet.add(dto.getMmsi());
  //        } else { // not a new mmsi, vessel already in set or db
  //
  //          // search for that vessel in buffer map
  //          if (vesselBufferMap.containsKey(dto.getMmsi())) {
  //
  //            vesselBufferMap
  //                .get(dto.getMmsi()) // get the vessel
  //                .addVoyageAndApplyTimestamp(
  //                    ModelExtractor.extractVoyage(dto), dto.getT()); // add voyage if not
  //            // exists and apply timestamp
  //
  //          } else { // vessel not found in map and it is in db
  //
  //            // search for the vessel in db
  //            final Voyage voyage = ModelExtractor.extractVoyage(dto);
  //            final long t = dto.getT();
  //
  //            findVesselByMMSI(dto.getMmsi())
  //                .ifPresentOrElse(
  //                    vessel -> vessel.addVoyageAndApplyTimestamp(voyage, t),
  //                    () ->
  //                        LOGGER.log(
  //                            Level.SEVERE,
  //                            "Error... Unable to find vessel either in buffer nor in db."));
  //          }
  //        }
  //
  //        // when read chunkSize number of lines, insert data to mongoDB
  //        if (vesselBufferMap.size() == chunkSize) {
  //          // LOGGER.info(vesselSet.size() + " lines read, attempting to insert data to db.");
  //          insertMany(new LinkedHashSet<>(vesselBufferMap.values()));
  //          // increase total vessels counter
  //          totalVessels += vesselBufferMap.size();
  //          // clear set
  //          vesselBufferMap.clear();
  //        }
  //
  //      } catch (CSVParserException e) {
  //        // LOGGER.log(Level.WARNING, "Discarding corrupted line " + e.getMessage());
  //      }
  //    }
  //    // LOGGER.info(vesselSet.size() + " lines left, attempting to insert data to db.");
  //    // insert any data left
  //    insertMany(new LinkedHashSet<>(vesselBufferMap.values()));
  //    // increase total vessels counter
  //    totalVessels += vesselBufferMap.size();
  //    // clear set
  //    vesselBufferMap.clear();
  //    totalMMSIBufferSet.clear();
  //
  //    LOGGER.info("All lines inserted to db successfully.");
  //    LOGGER.info("Total vessels added to db: " + totalVessels);
  //  }

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
    // LOGGER.info("Inserting data to db START.");
    MongoCollection<Vessel> collection = createVesselCollection();
    collection.insertMany(new ArrayList<>(vesselSet));
    // LOGGER.info("Inserting data to db END.");
  }

  @Override
  public void insertMany(@NotEmpty List<Vessel> vesselList) {
    // LOGGER.info("Inserting data to db START.");
    MongoCollection<Vessel> collection = createVesselCollection();
    collection.insertMany(vesselList);
    // LOGGER.info("Inserting data to db END.");
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
  public Optional<String> findVesselDestination(String vesselName) {
    return Optional.ofNullable(
            createDocumentCollection()
                .find(Filters.eq("vesselName", vesselName))
                .projection(new Document().append("destination", 1))
                .first())
        .map(d -> d.getString("destination"));
  }

  @Override
  public Optional<String> findVesselDestination(int mmsi) {
    return Optional.ofNullable(
            createDocumentCollection()
                .find(Filters.eq("mmsi", mmsi))
                .projection(new Document().append("destination", 1))
                .first())
        .map(d -> d.getString("destination"));
  }

  @Override
  public Optional<Vessel> findVesselByMMSI(int mmsi) {
    return Optional.ofNullable(createVesselCollection().find(Filters.eq("mmsi", mmsi)).first());
  }

  @Override
  public List<Vessel> findVesselsByType(String shipType, int skip, int limit) {
    final List<Vessel> vesselList = new ArrayList<>();
    createVesselCollection()
        .find(Filters.eq("shipType", shipType))
        .skip(skip)
        .limit(limit)
        .forEach((Consumer<Vessel>) vesselList::add);
    return vesselList;
  }

  @Override
  public Optional<Vessel> findVesselByName(String vesselName) {
    return Optional.ofNullable(
        createVesselCollection().find(Filters.eq("vesselName", vesselName)).first());
  }

  @Override
  public List<Vessel> findVesselsByDestination(String destination, int skip, int limit) {
    final List<Vessel> vesselList = new ArrayList<>();
    createVesselCollection()
        .find(Filters.eq("destination", destination))
        .skip(skip)
        .limit(limit)
        .forEach((Consumer<Vessel>) vesselList::add);
    return vesselList;
  }
}
