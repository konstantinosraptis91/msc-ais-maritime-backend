package kraptis91.maritime.dao.mongodb;

import com.mongodb.client.MongoCollection;
import jakarta.validation.constraints.NotEmpty;
import kraptis91.maritime.dao.OceanConditionsDao;
import kraptis91.maritime.enums.MongoDB;
import kraptis91.maritime.enums.MongoDBCollection;
import kraptis91.maritime.model.OceanConditions;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.SeaStateForecastDto;
import kraptis91.maritime.utils.ModelExtractor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020.
 * @author Stavros Lamprinos [stalab at linuxmail.org]
 */
public class MongoDBOceanConditionsDao implements OceanConditionsDao {

  public static final Logger LOGGER = Logger.getLogger(MongoDBOceanConditionsDao.class.getName());

  @Override
  public void insertMany(@NotNull InputStream is) throws Exception {

    LOGGER.info("Inserting " + is.available() + " bytes to db.");

    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
    final CSVParser parser = new CSVParser();
    final int chunkSize = 3000;
    final List<OceanConditions> oceanConditionsList = new ArrayList<>(chunkSize);

    LOGGER.info("Chunk size is " + chunkSize + ".");

    String line;
    SeaStateForecastDto dto;
    boolean isFirstLine = true;

    while ((line = bufferedReader.readLine()) != null) {

      // omit first line
      if (isFirstLine) {
        isFirstLine = false;
        continue;
      }

      // parse current line to the dto
      dto = parser.extractSeaStateForecastDto(line);
      // System.out.println(dto);
      // add to the list after model obj extraction
      oceanConditionsList.add(ModelExtractor.extractOceanConditions(dto));

      // when read chunkSize number of lines, insert data to mongoDB
      if (oceanConditionsList.size() == chunkSize) {
        LOGGER.info(oceanConditionsList.size() + " lines read, attempting to insert data to db.");
        insertMany(oceanConditionsList);
        // clear list
        oceanConditionsList.clear();
      }
    }
    LOGGER.info(oceanConditionsList.size() + " lines left, attempting to insert data to db.");
    // insert any data left
    insertMany(oceanConditionsList);
    LOGGER.info("All lines inserted to db successfully.");
  }

  @Override
  public void insertMany(@NotEmpty List<OceanConditions> list) {

    LOGGER.info("Inserting data to db START.");

    MongoCollection<OceanConditions> collection =
        MongoDB.MARITIME
            .getDatabase()
            .getCollection(
                MongoDBCollection.OCEAN_CONDITIONS.getCollectionName(), OceanConditions.class);

    collection.insertMany(list);
    LOGGER.info("Inserting data to db END.");
  }
}
