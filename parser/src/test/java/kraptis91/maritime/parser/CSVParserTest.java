package kraptis91.maritime.parser;

import kraptis91.maritime.parser.dto.NariDynamicDto;
import kraptis91.maritime.parser.dto.NariStaticDto;
import kraptis91.maritime.parser.dto.SeaStateForecastDto;
import kraptis91.maritime.parser.utils.CSVParserUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020. */
public class CSVParserTest {

  @Ignore
  @Test
  public void testExtractSeaStateForecastDto() throws Exception {

    final InputStream isSeaStateForecastSample =
        CSVParserTest.class.getResourceAsStream("/sample/maritime/oc_january_sample.csv");

    final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(isSeaStateForecastSample));
    final CSVParser parser = new CSVParser();
    SeaStateForecastDto dto;
    int linesRead = 0;

    String line;
    boolean isFirstLine = true;

    while ((line = bufferedReader.readLine()) != null) {

      // omit first line
      if (isFirstLine) {
        isFirstLine = false;
        continue;
      }

      dto = parser.extractSeaStateForecastDto(line);
      System.out.println(dto);

      linesRead++;
      // read 10 lines and break loop
      if (linesRead == 10) {
        break;
      }
    }
  }

  @Test
  public void testExtractNariStaticDto() throws Exception {

    //    final InputStream isNariStaticSample =
    //        CSVParserTest.class.getResourceAsStream("/sample/maritime/nari_static_sample.csv");

    InputStream isBig =
        new FileInputStream("D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_static.csv");

    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(isBig));
    final CSVParser parser = new CSVParser();
    NariStaticDto dto;
    int linesRead = 0;

    String line;
    boolean isFirstLine = true;

    while ((line = bufferedReader.readLine()) != null) {

      // omit first line
      if (isFirstLine) {
        isFirstLine = false;
        continue;
      }

      dto = parser.extractNariStaticDto(line);
      // System.out.println(dto);

      linesRead++;
      // read 10 lines and break loop
      //      if (linesRead == 10) {
      //        break;
      //      }
    }

    System.out.println("Lines read " + linesRead);
  }

  @Ignore
  @Test
  public void testExtractNariDynamicDto() throws Exception {

    // final InputStream isNariDynamicSample =
    //     CSVParserTest.class.getResourceAsStream("/sample/maritime/nari_dynamic_sample.csv");

    InputStream isBig =
        new FileInputStream("D:/NetbeansProjects/maritime-nosql/data/ais-data/nari_dynamic.csv");

    final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(isBig));
    final CSVParser parser = new CSVParser();
    NariDynamicDto dto;
    int linesRead = 0;

    String line;
    boolean isFirstLine = true;

    while ((line = bufferedReader.readLine()) != null) {

      // omit first line
      if (isFirstLine) {
        isFirstLine = false;
        continue;
      }

      dto = parser.extractNariDynamicDto(line);
      // System.out.println(dto);

      linesRead++;
      // read 10 lines and break loop
      //      if (linesRead == 10) {
      //        break;
      //      }
    }

    System.out.println("Lines read " + linesRead);
  }

  @Test
  public void testBreakAtCommas() {
    String line =
        "636010909,"
            + "9179218,"
            + "ELVP4 ,"
            + "\"MONICA P, \","
            + "70,"
            + "164,"
            + "26,"
            + "15,"
            + "16,"
            + "24-12 12:00 ,"
            + "9.9,"
            + "\"TARRAGONA,SPAIN \","
            + ","
            + "1450540725";

    String line2 = "211232180,,DGYN,IZAR,36,16,1,1,4,,,,33558785,1443652712";

    String data[] = CSVParserUtils.parseLine(line2);

    System.out.println("length " + data.length);
    Arrays.stream(data).forEach(System.out::println);
  }
}
