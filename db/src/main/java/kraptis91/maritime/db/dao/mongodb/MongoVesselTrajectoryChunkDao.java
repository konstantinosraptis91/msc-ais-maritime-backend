package kraptis91.maritime.db.dao.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import kraptis91.maritime.db.dao.VesselTrajectoryChunkDao;
import kraptis91.maritime.db.dao.utils.DocumentBuilder;
import kraptis91.maritime.db.dao.utils.VesselBuffer;
import kraptis91.maritime.db.dao.utils.VesselTrajectoryBuffer;
import kraptis91.maritime.db.enums.MongoDB;
import kraptis91.maritime.db.enums.MongoDBCollection;
import kraptis91.maritime.db.exceptions.DataException;
import kraptis91.maritime.model.ModelExtractor;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.VesselTrajectoryPointListChunk;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.csv.NariDynamicDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
        // LOGGER.info("Inserting data to db START.");
        if (!trajectoryChunks.isEmpty()) {
            MongoCollection<VesselTrajectoryPointListChunk> collection = createVesselTrajectoryCollection();
            collection.insertMany(trajectoryChunks);
        } else {
            LOGGER.log(Level.WARNING, "Trying to insert an empty trajectory chunk list to db...");
        }
        // LOGGER.info("Inserting data to db END.");
    }

    @Override
    public List<VesselTrajectoryChunk> findVesselTrajectory(String vesselName) {
        final List<VesselTrajectoryChunk> trajectoryPointList = new ArrayList<>();
        createDocumentCollection()
                .find(Filters.eq("vesselName", vesselName))
                .projection(createVesselTrajectoryChunkDocument())
                .map(ModelExtractor::extractVesselTrajectoryChunk)
                .forEach((Consumer<VesselTrajectoryChunk>) trajectoryPointList::add);
        return trajectoryPointList;
    }

    @Override
    public List<VesselTrajectoryChunk> findVesselTrajectory(int mmsi) {
        final List<VesselTrajectoryChunk> trajectoryPointList = new ArrayList<>();
        createDocumentCollection()
                .find(Filters.eq("mmsi", mmsi))
                .projection(createVesselTrajectoryChunkDocument())
                .map(ModelExtractor::extractVesselTrajectoryChunk)
                .forEach((Consumer<VesselTrajectoryChunk>) trajectoryPointList::add);
        return trajectoryPointList;
    }

}
