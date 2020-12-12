package kraptis91.maritime.parser;

import kraptis91.maritime.parser.dto.SeaStateForecastDto;
import org.junit.Ignore;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020. */
public class CSVParserTest {

  private final InputStream isSeaStateForecastSample =
      CSVParserTest.class.getResourceAsStream("/sample/maritime/oc_january_sample.csv");

  @Ignore
  @Test
  public void testExtractSeaStateForecastDro() throws Exception {

    final BufferedReader bufferedReader =
        new BufferedReader(new InputStreamReader(isSeaStateForecastSample));
    final CSVParser parser = new CSVParser();

    String line;
    int counter = 0;

    while ((line = bufferedReader.readLine()) != null) {

      // omit first line
      if (counter == 0) {
        counter++;
        continue;
      }

      SeaStateForecastDto dto = parser.extractSeaStateForecastDto(line);
      System.out.println(dto);

      counter++;
      // read 10 lines and break loop
      if (counter == 10) {
        break;
      }
    }
  }
}
