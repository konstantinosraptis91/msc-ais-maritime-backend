package kraptis91.maritime.parser;

import kraptis91.maritime.parser.dto.NariStaticDto;
import kraptis91.maritime.parser.dto.SeaStateForecastDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.CSVParserUtils;

import javax.validation.constraints.NotNull;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020. */
public class CSVParser {

  public static final Logger LOGGER = Logger.getLogger(CSVParser.class.getName());

  public SeaStateForecastDto extractSeaStateForecastDto(@NotNull String line)
      throws CSVParserException {
    // break the line at commas
    final String[] data = line.split(",");
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
            dto.setDpt(CSVParserUtils.parseDoubleOrReturnDefault(data[i], -16384));
            break;

          case 3:
            dto.setWlv(CSVParserUtils.parseDoubleOrReturnDefault(data[i], -327.67));
            break;

          case 4:
            dto.setHs(CSVParserUtils.parseDoubleOrReturnDefault(data[i], -65.534));
            break;

          case 5:
            dto.setLm(CSVParserUtils.parseIntOrReturnDefault(data[i], -32767));
            break;

            //          case 6:
            //            dto.setDir(CSVParserUtils.parseDouble(data[i]));
            //            break;

          case 7:
            dto.setTs(CSVParserUtils.parseLong(data[i]));
            break;
        }
      }
    } catch (CSVParserException | IllegalArgumentException e) {
      throw new CSVParserException(e);
    }

    return dto;
  }

  public NariStaticDto extractNariStaticDto(@NotNull String line) throws CSVParserException {

    // break the line at commas
    final String[] data = line.split(",");
    // print data after split
    // LOGGER.info("Data extracted: " + Arrays.toString(data));
    // create the dto obj
    NariStaticDto dto = new NariStaticDto();
    // if a number format exception thrown, discard the line
    try {

      // loop through results
      for (int i = 0; i <= 13; i++) { // expecting 14 attributes

        switch (i) {
          case 0:
            dto.setMmsi(CSVParserUtils.parseInt(data[i]));
            break;

          case 1:
            dto.setImo(CSVParserUtils.parseInt(data[i]));
            break;

          case 2:
            dto.setCallSign(CSVParserUtils.parseTextOrReturnNull(data[i]));
            break;

          case 3:
            dto.setShipName(CSVParserUtils.parseTextOrReturnNull(data[i]));
            break;

            //          case 4:
            //            dto.setShipType(CSVParserUtils.parseInt(data[i]));
            //            break;

            //          case 5:
            //            dto.setToBow(CSVParserUtils.parseInt(data[i]));
            //            break;

            //          case 6:
            //            dto.setToStern(CSVParserUtils.parseInt(data[i]));
            //            break;

            //          case 7:
            //            dto.setToStarboard(CSVParserUtils.parseInt(data[i]));
            //            break;

            //          case 8:
            //            dto.setToPort(CSVParserUtils.parseInt(data[i]));
            //            break;

          case 9:
            dto.setEta(CSVParserUtils.parseTextOrReturnNull(data[i]));
            break;

          case 10:
            dto.setDraught(CSVParserUtils.parseDouble(data[i]));
            break;

          case 11:
            dto.setDestination(CSVParserUtils.parseTextOrReturnNull(data[i]));
            break;

            //          case 12:
            //            dto.setMotherShipMmsi(CSVParserUtils.parseInt(data[i]));
            //            break;

            //          case 13:
            //            dto.setT(CSVParserUtils.parseLong(data[i]));
            //            break;
        }
      }
    } catch (CSVParserException | IllegalArgumentException e) {
      throw new CSVParserException(e);
    }

    return dto;
  }
}
