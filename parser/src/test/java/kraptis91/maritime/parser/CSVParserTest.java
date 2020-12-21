package kraptis91.maritime.parser;

//import com.opencsv.CSVParserBuilder;
//import com.opencsv.CSVReader;
//import com.opencsv.CSVReaderBuilder;
import kraptis91.maritime.parser.dto.NariDynamicDto;
import kraptis91.maritime.parser.dto.NariStaticDto;
import kraptis91.maritime.parser.dto.SeaStateForecastDto;
import kraptis91.maritime.parser.utils.CSVParserUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

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
  public void testBreakAtCommas() throws Exception {
    //        String line =
    //            "636010909,"
    //                + "9179218,"
    //                + "ELVP4 ,"
    //                + "\"MONICA P, \","
    //                + "70,"
    //                + "164,"
    //                + "26,"
    //                + "15,"
    //                + "16,"
    //                + "24-12 12:00 ,"
    //                + "9.9,"
    //                + "\"TARRAGONA,SPAIN \","
    //                + ","
    //                + "1450540725";

    String line =
        "564923000,"
            + "9698290,"
            + "9V2516 ,"
            + "EPIC CALEDONIA ,"
            + "89,"
            + "72,"
            + "22,"
            + "11,"
            + "5,"
            + "28-10 01:30 ,"
            + "4.5,"
            + "\"WANDELAAR,FOR,ORDER \","
            + ""
            + ",1445858617";

    // String line = "211232180,,DGYN,IZAR,36,16,1,1,4,,,,33558785,1443652712";

    long startTime = System.currentTimeMillis();

    String[] data = CSVParserUtils.parseLine(line);

    long endTime = System.currentTimeMillis();

    System.out.println("Total time: " + (endTime - startTime) + " ms");

    System.out.println("length " + data.length);
    // Arrays.stream(data).forEach(System.out::println);

    for (int i = 0; i < data.length; i++) {
      System.out.printf("%d %s\n", i, data[i]);
    }
  }

//  @Test
//  public void testBreakAtCommasWithCSVParser() {
//
//    BufferedReader bufferedReader = Files.newBufferedReader(Paths.get());
//  }

//  @Test
//  public void testBreakAtCommasWithOpencsv() throws Exception {
//
//    String line = "211232180,,DGYN,IZAR,36,16,1,1,4,,,,33558785,1443652712";
//
//    com.opencsv.CSVParser csvParser =
//        new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(false).build();
//
//    StringReader reader = new StringReader(line);
//
//    com.opencsv.CSVReader csvReader =
//        new CSVReaderBuilder(reader).withSkipLines(0).withCSVParser(csvParser).build();
//
//    long startTime = System.currentTimeMillis();
//
//    List<String[]> data = csvReader.readAll();
//
//    long endTime = System.currentTimeMillis();
//
//    System.out.println("Total time: " + (endTime - startTime) + " ms");
//
//    System.out.println(data.get(0).length);
//
//    data.forEach(strings -> System.out.println(Arrays.toString(strings)));
//  }
}
