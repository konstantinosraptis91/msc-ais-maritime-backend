package kraptis91.maritime.parser;

import kraptis91.maritime.parser.dto.NariStaticDto;
import kraptis91.maritime.parser.dto.SeaStateForecastDto;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    final InputStream isNariStaticSample =
        CSVParserTest.class.getResourceAsStream("/sample/maritime/nari_static_sample.csv");

    final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(isNariStaticSample));
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
      System.out.println(dto);

      linesRead++;
      // read 10 lines and break loop
      if (linesRead == 10) {
        break;
      }
    }
  }
}
