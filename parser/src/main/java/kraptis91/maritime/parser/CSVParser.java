package kraptis91.maritime.parser;

import kraptis91.maritime.parser.dto.SeaStateForecastDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.CSVParserUtils;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020. */
public class CSVParser {

  public static final Logger LOGGER = Logger.getLogger(CSVParser.class.getName());

  public SeaStateForecastDto extractSeaStateForecastDro(String line) throws CSVParserException {
    // break the line at commas
    final String data[] = line.split(",");
    // print data after split
    // LOGGER.info("Data extracted: " + Arrays.toString(data));
    // create the dto obj
    SeaStateForecastDto dto = new SeaStateForecastDto();
    // if a number format exception thrown, discard the line
    try {
      // loop through results
      for (int i = 0; i <= 7; i++) { // expecting 8 attributes

        switch (i) {
          case 0:
            dto.setLon(CSVParserUtils.parseDouble(data[i]));
            break;

          case 1:
            dto.setLat(CSVParserUtils.parseDouble(data[i]));
            break;

          case 2:
            dto.setDpt(CSVParserUtils.parseDouble(data[i]));
            break;

          case 3:
            dto.setWlv(CSVParserUtils.parseDouble(data[i]));
            break;

          case 4:
            dto.setHs(CSVParserUtils.parseDouble(data[i]));
            break;

          case 5:
            dto.setLm(CSVParserUtils.parseInt(data[i]));
            break;

          case 6:
            dto.setDir(CSVParserUtils.parseDouble(data[i]));
            break;

          case 7:
            dto.setTs(CSVParserUtils.parseLong(data[i]));
            break;
        }
      }
    } catch (NumberFormatException e) {
      throw new CSVParserException(e);
    }

    return dto;
  }
}
