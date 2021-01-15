package kraptis91.maritime.db.dao.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;
import kraptis91.maritime.db.dao.VesselTrajectoryChunkDao;
import kraptis91.maritime.db.dao.mongodb.query.utils.NearQueryOptions;
import kraptis91.maritime.db.dao.utils.VesselBuffer;
import kraptis91.maritime.db.dao.utils.VesselTrajectoryBuffer;
import kraptis91.maritime.db.enums.MongoDB;
import kraptis91.maritime.db.enums.MongoDBCollection;
import kraptis91.maritime.db.enums.TransactionStatus;
import kraptis91.maritime.db.exceptions.DataException;
import kraptis91.maritime.model.*;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.csv.NariDynamicDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020.
 */
public class MongoVesselTrajectoryChunkDao implements VesselTrajectoryChunkDao, DocumentBuilder {

    public static final Logger LOGGER =
        Logger.getLogger(MongoVesselTrajectoryChunkDao.class.getName());

    public static MongoCollection<VesselTrajectoryPointListChunk> createVesselTrajectoryCollection() {
        return MongoDB.MARITIME
            .getDatabase()
            .getCollection(
                MongoDBCollection.VESSEL_TRAJECTORY.getCollectionName(), VesselTrajectoryPointListChunk.class);
    }

    public static MongoCollection<Document> createDocumentCollection() {
        return MongoDB.MARITIME
            .getDatabase()
            .getCollection(
                MongoDBCollection.VESSEL_TRAJECTORY.getCollectionName(), Document.class);
    }

    @Override
    public void insertMany(InputStream csvStream, int capacity) throws Exception {

        LOGGER.info("Inserting " + csvStream.available() + " bytes to db.");
        LOGGER.info("VesselTrajectoryBuffer capacity " + capacity + ".");

        final BufferedReader bufferedReader =
            new BufferedReader(
                new InputStreamReader(InputStreamUtils.getBufferedInputStream(csvStream)));

        final CSVParser parser = new CSVParser();
        final VesselTrajectoryBuffer trajectoryBuffer = VesselTrajectoryBuffer.createInstance(capacity);
        final VesselBuffer vesselBuffer = VesselBuffer.createInstance();

        String line;
        NariDynamicDto dto;
        boolean isFirstLine = true;

        while ((line = bufferedReader.readLine()) != null) {

            // omit first line
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }

            try {
                // parse current line to the dto
                dto = parser.extractNariDynamicDto(line);
                final Vessel vessel = vesselBuffer.getIfExistsOrGetFromDB(dto.getMMSI());

                if (trajectoryBuffer.isCompletedListFull()) {
                    LOGGER.info("Completed list is full, inserting "
                        + trajectoryBuffer.getCompletedChunkList().size() + " chunks in db.");
                    insertMany(trajectoryBuffer.getCompletedChunkList());
                    trajectoryBuffer.clearCompletedChunkList();
                }

                trajectoryBuffer.addPoint(dto, vessel);

            } catch (CSVParserException e) {
                // LOGGER.log(Level.WARNING, "Discarding corrupted line" + e.getMessage(), e);
            } catch (DataException e) {
                // LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }

        // add all completed chunks in db
        insertMany(trajectoryBuffer.getCompletedChunkList());
        trajectoryBuffer.clearCompletedChunkList();
        // add all incompleted chunks in db
        insertMany(trajectoryBuffer.getIncompletedChunkList());
        trajectoryBuffer.clearIncompletedChunkMap();

        LOGGER.info("All lines inserted to db successfully.");
    }

    public void insertOne(VesselTrajectoryPointListChunk trajectory) {
        MongoCollection<VesselTrajectoryPointListChunk> collection = createVesselTrajectoryCollection();
        collection.insertOne(trajectory);
    }

    @Override
    public void insertMany(List<VesselTrajectoryPointListChunk> trajectoryChunks) {

        LOGGER.info("Inserting " + trajectoryChunks.size() + " chunks to db START.");
        long startTime = System.currentTimeMillis();
        TransactionStatus status = TransactionStatus.FAILED;

        if (!trajectoryChunks.isEmpty()) {
            MongoCollection<VesselTrajectoryPointListChunk> collection = createVesselTrajectoryCollection();
            collection.insertMany(trajectoryChunks);
            status = TransactionStatus.SUCCESS;
        } else {
            LOGGER.log(Level.WARNING, "Trying to insert an empty trajectory chunk list to db...");
        }

        long endTime = System.currentTimeMillis();
        LOGGER.info("Inserting " + trajectoryChunks.size()
            + " chunks to db FINISH at [" + (endTime - startTime) + "]"
            + " with status: " + status.name());
    }

    @Override
    public List<VesselTrajectoryChunk> findVesselTrajectory(String vesselName) {

        final List<VesselTrajectoryChunk> trajectoryChunks = new ArrayList<>();

        createDocumentCollection()
            .find(Filters.eq("vesselName", vesselName))
            .projection(createVesselTrajectoryChunkDocument())
            .map(ModelExtractor::extractVesselTrajectoryChunk)
            .forEach((Consumer<VesselTrajectoryChunk>) trajectoryChunks::add);

        LOGGER.info("Found " + trajectoryChunks.size()
            + " chunks for vessel with vessel name: " + vesselName);

        return trajectoryChunks;
    }

    @Override
    public List<VesselTrajectoryChunk> findVesselTrajectory(int mmsi) {

        final List<VesselTrajectoryChunk> trajectoryChunks = new ArrayList<>();

        createDocumentCollection()
            .find(Filters.eq("mmsi", mmsi))
            .projection(createVesselTrajectoryChunkDocument())
            .map(ModelExtractor::extractVesselTrajectoryChunk)
            .forEach((Consumer<VesselTrajectoryChunk>) trajectoryChunks::add);

        LOGGER.info("Found " + trajectoryChunks.size()
            + " chunks for vessel with mmsi: " + mmsi);

        return trajectoryChunks;
    }

    @Override
    public List<Integer> findNearVesselsMMSIList(double longitude,
                                                 double latitude,
                                                 double maxDistance,
                                                 double minDistance) {

        final List<Integer> nearVesselsMMSIList = new ArrayList<>();
        final Point refPoint = new Point(new Position(longitude, latitude));

        createDocumentCollection()
            .distinct("mmsi", Filters.near("avgGeoPoint", refPoint, maxDistance, minDistance), Integer.class)
            .forEach((Consumer<Integer>) nearVesselsMMSIList::add);

        LOGGER.info("Found " + nearVesselsMMSIList.size() + " vessels mmsi near ["
            + longitude + ", " + latitude + "] at max distance: "
            + maxDistance + " meters, min distance: "
            + minDistance + " meters");

        return nearVesselsMMSIList;
    }

    @Override
    public List<Integer> findNearVesselsMMSIList(NearQueryOptions options) {

        final List<Integer> nearVesselsMMSIList = new ArrayList<>();
        createDocumentCollection()
            .aggregate(
                Arrays.asList(
                    // geoNear spherical geometry for 2d sphere
                    createGeoNearSphericalGeometryDocument(
                        options.getLongitude(), options.getLatitude(), options.getMaxDistance()),
                    // projection
                    Aggregates.project(Projections.fields(
                        Projections.excludeId(),
                        Projections.include("mmsi", "vesselName", "shipType"))),
                    // group
                    Aggregates.group("$mmsi", Accumulators.first("mmsi", "$mmsi")),
                    // skip
                    Aggregates.skip(options.getSkip()),
                    // limit
                    Aggregates.limit(options.getLimit())))
            .map(document -> document.getInteger("mmsi"))
            .forEach((Consumer<Integer>) nearVesselsMMSIList::add);

        LOGGER.info("Found " + nearVesselsMMSIList.size() + " vessels mmsi near ["
            + options.getLongitude() + ", " + options.getLatitude() + "] at max distance: "
            + options.getMaxDistance() + " meters, min distance: "
            + options.getMinDistance() + " meters");

        return nearVesselsMMSIList;
    }

    @Override
    public List<PlainVessel> findNearVessels(NearQueryOptions options) {

        final List<PlainVessel> nearVessels = new ArrayList<>();
        createDocumentCollection()
            .aggregate(
                Arrays.asList(
                    // geoNear spherical geometry for 2d sphere
                    createGeoNearSphericalGeometryDocument(
                        options.getLongitude(), options.getLatitude(), options.getMaxDistance()),
                    // group
                    Aggregates.group("$mmsi",
                        Accumulators.first("mmsi", "$mmsi"),
                        Accumulators.first("vesselName", "$vesselName"),
                        Accumulators.first("country", "$country"),
                        Accumulators.first("shipType", "$shipType")),
                    // projection
                    Aggregates.project(Projections.fields(
                        Projections.excludeId(),
                        Projections.include("mmsi", "vesselName", "country", "shipType"))),
                    // skip
                    Aggregates.skip(options.getSkip()),
                    // limit
                    Aggregates.limit(options.getLimit())))
            .map(ModelExtractor::extractPlainVessel)
            .forEach((Consumer<PlainVessel>) nearVessels::add);

        LOGGER.info("Found " + nearVessels.size() + " vessels near ["
            + options.getLongitude() + ", " + options.getLatitude() + "] at max distance: "
            + options.getMaxDistance() + " meters, min distance: "
            + options.getMinDistance() + " meters");

        return nearVessels;
    }

}
