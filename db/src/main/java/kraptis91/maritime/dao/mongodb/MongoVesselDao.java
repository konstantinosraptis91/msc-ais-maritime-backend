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
import kraptis91.maritime.parser.utils.InputStreamUtils;
import kraptis91.maritime.utils.ModelExtractor;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020. */
public class MongoVesselDao implements VesselDao {

  public static final Logger LOGGER = Logger.getLogger(MongoVesselDao.class.getName());

  @Override
  public void insertMany(@NotNull InputStream csvStream, final int chunkSize) throws Exception {

    // LOGGER.info("Inserting " + csvStream.available() + " bytes to db.");

    final BufferedReader bufferedReader =
        new BufferedReader(
            new InputStreamReader(InputStreamUtils.getBufferedInputStream(csvStream)));
    final CSVParser parser = new CSVParser();
    final Set<Vessel> vesselSet = new LinkedHashSet<>(chunkSize);
    final Set<Integer> totalMMSISet = new LinkedHashSet<>();
    int totalVessels = 0;

    // LOGGER.info("Chunk size csvStream " + chunkSize + ".");

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
        if (!totalMMSISet.contains(dto.getMmsi())) {
          // add to the list after model obj extraction
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
          // clear set
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
    // clear set
    vesselSet.clear();
    totalMMSISet.clear();

    LOGGER.info("All lines inserted to db successfully.");
    LOGGER.info("Total vessels added to db: " + totalVessels);
  }

  @Override
  public void insertMany(@NotEmpty Set<Vessel> vesselSet) {

    // LOGGER.info("Inserting data to db START.");

    MongoCollection<Vessel> collection =
        MongoDB.MARITIME
            .getDatabase()
            .getCollection(MongoDBCollection.VESSELS.getCollectionName(), Vessel.class);

    collection.insertMany(new ArrayList<>(vesselSet));

    // LOGGER.info("Inserting data to db END.");
  }

  @Override
  public void insertMany(@NotEmpty List<Vessel> vesselList) {

    // LOGGER.info("Inserting data to db START.");

    MongoCollection<Vessel> collection =
        MongoDB.MARITIME
            .getDatabase()
            .getCollection(MongoDBCollection.VESSELS.getCollectionName(), Vessel.class);

    collection.insertMany(vesselList);

    // LOGGER.info("Inserting data to db END.");
  }
}
