package kraptis91.maritime.dao.mongodb;

import kraptis91.maritime.dao.OceanConditionsDao;
import kraptis91.maritime.model.OceanConditions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedHashSet;
import java.util.Set;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020. */
public class MongoDBOceanConditionsDao implements OceanConditionsDao {

  public MongoDBOceanConditionsDao() throws FileNotFoundException {}

  /** @author Stavros Lamprinos [stalab at linuxmail.org] on 11/12/2020. */
  //  test inserting data from csv until parser module is up
  public static Set<OceanConditions> getOceanFileData(String filePath) throws IOException {

    Set<OceanConditions> OceanConditions = new LinkedHashSet<>();;
    //  Absolut path here, needs to be changed
    BufferedReader scvReader = new BufferedReader(new FileReader(filePath));

    String row;
    while ((row = scvReader.readLine()) != null) {
      //  temp headers skip
      if (row.startsWith("lon")) continue;

      String[] lineValues = row.split(",");

      double lon = Double.parseDouble(lineValues[0]);
      double lat = Double.parseDouble(lineValues[1]);
      double dpt = Double.parseDouble(lineValues[2]);
      double wlv = Double.parseDouble(lineValues[3]);
      double hs = Double.parseDouble(lineValues[4]);
      int lm = Integer.parseInt(lineValues[5]);
      BigInteger ts = BigInteger.valueOf(Long.parseLong(lineValues[7]));

      OceanConditions oc =
          new OceanConditions.Builder(lon, lat, ts)
              .withBottomDepth(dpt)
              .withTidalEffect(wlv)
              .withSeaHeight(hs)
              .withMeanWaveLength(lm)
              .build();

      OceanConditions.add(oc);
    }

    return OceanConditions;
  }



  /** @return Set of OceanConditions obj */
  public static Set<OceanConditions> createOceanConditionsSetDemo() throws IOException {

    //  Database contains ocean data from six (6) files, one for every month
    String[] ocFiles = {
            "/Users/steve_lab/IdeaProjects/bucketFiles/src/maritime_sample_data/oc_january_sample.csv",
            "/Users/steve_lab/IdeaProjects/bucketFiles/src/maritime_sample_data/oc_november_sample.csv"
    };

    Set<OceanConditions> totalOceanConditions= new LinkedHashSet<>();

    for (String file : ocFiles) {
      totalOceanConditions.addAll(getOceanFileData(file));
    }

    return totalOceanConditions;
  }
}

// "/Users/steve_lab/IdeaProjects/bucketFiles/src/maritime_sample_data/oc_january_sample.csv"
