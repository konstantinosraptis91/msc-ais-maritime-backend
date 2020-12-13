package kraptis91.maritime.dao.mongodb;

import com.mongodb.client.MongoCollection;
import jakarta.validation.constraints.NotEmpty;
import kraptis91.maritime.dao.VesselDao;
import kraptis91.maritime.enums.MongoDB;
import kraptis91.maritime.enums.MongoDBCollection;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.NariStaticDto;
import kraptis91.maritime.parser.enums.MMSICountryCode;
import kraptis91.maritime.parser.enums.ShipTypes;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.utils.ModelExtractor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020. */
public class MongoDBVesselDao implements VesselDao {

  public static final Logger LOGGER = Logger.getLogger(MongoDBVesselDao.class.getName());

  @Override
  public void insertMany(@NotNull InputStream is) throws Exception {

    // LOGGER.info("Inserting " + is.available() + " bytes to db.");

    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
    final CSVParser parser = new CSVParser();
    final int chunkSize = 50;
    final Set<Vessel> vesselSet = new LinkedHashSet<>(chunkSize);
    final Set<Integer> totalMMSISet = new LinkedHashSet<>();
    int totalVessels = 0;

    // LOGGER.info("Chunk size is " + chunkSize + ".");

    String line;
    NariStaticDto dto;
    boolean isFirstLine = true;

    while ((line = bufferedReader.readLine()) != null) {

      // omit first line
      if (isFirstLine) {
        isFirstLine = false;
        continue;
      }

      // parse current line to the dto
      try {
        dto = parser.extractNariStaticDto(line);

        // System.out.println(dto);
        // add to the list after model obj extraction
        if (!totalMMSISet.contains(dto.getMmsi())) {
          vesselSet.add(
              ModelExtractor.extractVessel(
                  dto,
                  ShipTypes.INSTANCE.getShipType(dto.getShipType()),
                  MMSICountryCode.INSTANCE.getCountryByMMSI(dto.getMmsi())));
          totalMMSISet.add(dto.getMmsi());
        }

        // when read chunkSize number of lines, insert data to mongoDB
        if (vesselSet.size() == chunkSize) {
          // LOGGER.info(vesselSet.size() + " lines read, attempting to insert data to db.");
          insertMany(vesselSet);
          // increase total vessels counter
          totalVessels += vesselSet.size();
          // clear map
          vesselSet.clear();
        }

      } catch (CSVParserException e) {
        LOGGER.log(Level.WARNING, "Discarding corrupted line" + e.getMessage());
      }
    }
    // LOGGER.info(vesselSet.size() + " lines left, attempting to insert data to db.");
    // insert any data left
    insertMany(vesselSet);
    // increase total vessels counter
    totalVessels += vesselSet.size();
    // clear map
    vesselSet.clear();

    LOGGER.info("All lines inserted to db successfully.");
    LOGGER.info("Total vessels added to db: " + totalVessels);
  }

  @Override
  public void insertMany(@NotEmpty Set<Vessel> set) {

    // LOGGER.info("Inserting data to db START.");

    MongoCollection<Vessel> collection =
        MongoDB.MARITIME
            .getDatabase()
            .getCollection(MongoDBCollection.VESSELS.getCollectionName(), Vessel.class);

    collection.insertMany(new ArrayList<>(set));

    // LOGGER.info("Inserting data to db END.");
  }

  @Override
  public void insertMany(@NotEmpty List<Vessel> list) {

    // LOGGER.info("Inserting data to db START.");

    MongoCollection<Vessel> collection =
        MongoDB.MARITIME
            .getDatabase()
            .getCollection(MongoDBCollection.VESSELS.getCollectionName(), Vessel.class);

    collection.insertMany(list);

    // LOGGER.info("Inserting data to db END.");
  }
}
