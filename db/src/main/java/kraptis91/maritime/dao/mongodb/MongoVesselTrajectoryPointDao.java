package kraptis91.maritime.dao.mongodb;

import com.mongodb.client.MongoCollection;
import kraptis91.maritime.dao.VesselTrajectoryPointDao;
import kraptis91.maritime.enums.MongoDB;
import kraptis91.maritime.enums.MongoDBCollection;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.NariDynamicDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;
import kraptis91.maritime.utils.ModelExtractor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public class MongoVesselTrajectoryPointDao implements VesselTrajectoryPointDao {

  public static final Logger LOGGER =
      Logger.getLogger(MongoVesselTrajectoryPointDao.class.getName());

  @Override
  public void insertMany(InputStream csvStream, int chunkSize) throws Exception {

    // LOGGER.info("Inserting " + is.available() + " bytes to db.");

    final BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(InputStreamUtils.getBufferedInputStream(csvStream)));
    final CSVParser parser = new CSVParser();
    final List<VesselTrajectoryPoint> pointList = new ArrayList<>(chunkSize);
    int totalPoints = 0;

    // LOGGER.info("Chunk size is " + chunkSize + ".");

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
        dto = parser.extractDynamicDto(line);
        // add to the list after model obj extraction
        pointList.add(ModelExtractor.extractVesselTrajectoryPoint(dto));

        // when read chunkSize number of lines, insert data to mongoDB
        if (pointList.size() == chunkSize) {
          // LOGGER.info(vesselSet.size() + " lines read, attempting to insert data to db.");
          insertMany(pointList);
          // increase total vessels counter
          totalPoints += pointList.size();
          // clear set
          pointList.clear();
        }

      } catch (CSVParserException e) {
        LOGGER.log(Level.WARNING, "Discarding corrupted line" + e.getMessage());
      }
    }
    // LOGGER.info(vesselSet.size() + " lines left, attempting to insert data to db.");
    // insert any data left
    insertMany(pointList);
    // increase total vessels counter
    totalPoints += pointList.size();
    // clear set
    pointList.clear();

    LOGGER.info("All lines inserted to db successfully.");
    LOGGER.info("Total vessel trajectory points added to db: " + totalPoints);
  }

  @Override
  public void insertMany(List<VesselTrajectoryPoint> points) {
    // LOGGER.info("Inserting data to db START.");

    MongoCollection<VesselTrajectoryPoint> collection =
        MongoDB.MARITIME
            .getDatabase()
            .getCollection(
                MongoDBCollection.VESSEL_TRAJECTORY_POINTS.getCollectionName(),
                VesselTrajectoryPoint.class);

    collection.insertMany(points);

    // LOGGER.info("Inserting data to db END.");
  }
}
