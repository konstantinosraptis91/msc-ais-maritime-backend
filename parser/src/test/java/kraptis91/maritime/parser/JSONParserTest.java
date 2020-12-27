package kraptis91.maritime.parser;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 26/12/2020. */
public class JSONParserTest {

  @Ignore
  @Test
  public void testCreateMMSICounterJSON() throws Exception {

    Map<String, Integer> mmsiCounterMap =
        JSONParser.createMMSICounterMap(
            "D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_dynamic.csv");

    //    mmsiCounterMap
    //            .forEach((key, value) -> System.out.println(key + " : " + value));

    JSONParser.writeMapAsJSON(mmsiCounterMap, "D:/Downloads/mmsi-counter-map.json");
  }

}
