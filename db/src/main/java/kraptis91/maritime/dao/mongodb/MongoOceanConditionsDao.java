package kraptis91.maritime.dao.mongodb;

import com.mongodb.client.MongoCollection;
import jakarta.validation.constraints.NotEmpty;
import kraptis91.maritime.dao.OceanConditionsDao;
import kraptis91.maritime.enums.MongoDB;
import kraptis91.maritime.enums.MongoDBCollection;
import kraptis91.maritime.model.OceanConditions;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.SeaStateForecastDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;
import kraptis91.maritime.utils.ModelExtractor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020.
 * @author Stavros Lamprinos [stalab at linuxmail.org]
 */
public class MongoOceanConditionsDao implements OceanConditionsDao {

  public static final Logger LOGGER = Logger.getLogger(MongoOceanConditionsDao.class.getName());

  @Override
  public void insertMany(@NotNull InputStream csvStream, int chunkSize) throws Exception {

    //    LOGGER.info("Inserting " + csvStream.available() + " bytes to db.");

    final BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(InputStreamUtils.getBufferedInputStream(csvStream)));
    final CSVParser parser = new CSVParser();
    final List<OceanConditions> oceanConditionsList = new ArrayList<>(chunkSize);
    int totalOceanConditions = 0;

    //    LOGGER.info("Chunk size csvStream " + chunkSize + ".");

    String line;
    SeaStateForecastDto dto;
    boolean isFirstLine = true;

    while ((line = bufferedReader.readLine()) != null) {

      // omit first line
      if (isFirstLine) {
        isFirstLine = false;
        continue;
      }

      try {
        // parse current line to the dto
        dto = parser.extractSeaStateForecastDto(line);
        // add to the list after model obj extraction
        oceanConditionsList.add(ModelExtractor.extractOceanConditions(dto));

        // when read chunkSize number of lines, insert data to mongoDB
        if (oceanConditionsList.size() == chunkSize) {
          //        LOGGER.info(oceanConditionsList.size() + " lines read, attempting to insert data
          // to db.");
          insertMany(oceanConditionsList);
          // increase total ocean conditions counter
          totalOceanConditions += oceanConditionsList.size();
          // clear list
          oceanConditionsList.clear();
        }

      } catch (CSVParserException e) {
        // LOGGER.log(Level.WARNING, "Discarding corrupted line" + e.getMessage());
      }
    }
    //    LOGGER.info(oceanConditionsList.size() + " lines left, attempting to insert data to db.");
    // insert any data left
    insertMany(oceanConditionsList);
    // increase total ocean conditions counter
    totalOceanConditions += oceanConditionsList.size();
    // clear list
    oceanConditionsList.clear();

    LOGGER.info("All lines inserted to db successfully.");
    LOGGER.info("Total ocean conditions added to db: " + totalOceanConditions);
  }

  @Override
  public void insertMany(@NotEmpty List<OceanConditions> list) {

    //    LOGGER.info("Inserting data to db START.");

    MongoCollection<OceanConditions> collection =
        MongoDB.MARITIME
            .getDatabase()
            .getCollection(
                MongoDBCollection.OCEAN_CONDITIONS.getCollectionName(), OceanConditions.class);

    collection.insertMany(list);
    //    LOGGER.info("Inserting data to db END.");
  }
}
