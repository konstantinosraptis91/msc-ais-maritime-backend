package kraptis91.maritime.parser;

import kraptis91.maritime.parser.utils.CSVParserUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 26/12/2020.
 */
public class JSONParserTest {

    @Ignore
    @Test
    public void testCreateMMSICounterJSON() throws Exception {

        Map<String, Integer> mmsiCounterMap = JSONParser.createMMSICounterMap(
                // Paths.get(
                //        "D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_dynamic.csv"));
                Paths.get(ClassLoader.getSystemResource(
                        "sample/maritime/nari_dynamic_sample.csv").toURI()));

        //    mmsiCounterMap
        //            .forEach((key, value) -> System.out.println(key + " : " + value));

        JSONParser.writeMapAsJSON(mmsiCounterMap, "D:/Downloads/mmsi-counter-map-sample.json");
    }

    @Test
    public void testCreateNariDynamicSample2() throws Exception {

        try (BufferedReader reader = Files.newBufferedReader(
                Paths.get(ClassLoader.getSystemResource(
                        "sample/maritime/nari_dynamic_sample.csv").toURI()))) {

            String line;
            String mmsi;
            String[] data;
            boolean isFirstLine = true;
            int counter = 0;

            while ((line = reader.readLine()) != null) {

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                data = CSVParserUtils.parseLine(line);
                mmsi = data[0];

                if (mmsi.equals("245257000")) {
                    System.out.println(line);
                    counter++;
                }

            }

            System.out.println("Counter: " + counter);
        }

    }

}
