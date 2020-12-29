package kraptis91.maritime.parser;

import kraptis91.maritime.parser.dto.csv.*;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.CSVParserUtils;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 9/12/2020.
 */
public class CSVParser {

    public static final Logger LOGGER = Logger.getLogger(CSVParser.class.getName());

    public SeaStateForecastDto extractSeaStateForecastDto(@NotNull String line)
        throws CSVParserException {
        // break the line at commas
        final String[] data = CSVParserUtils.parseLine(line);
        CSVParserUtils.validateNumberOfParsedValues(data.length, 8, "extractSeaStateForecastDto");

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
        final String[] data = CSVParserUtils.parseLine(line);
        CSVParserUtils.validateNumberOfParsedValues(data.length, 14, "extractNariStaticDto");

        // print data after split
        // LOGGER.info("Data extracted: " + Arrays.toString(data));
        // create the dto obj
        NariStaticDto dto = new NariStaticDto();
        // if a number format exception thrown, discard the line
        try {

            // loop through results
            for (int i = 0; i <= 13; i++) { // expecting 14 attributes

                switch (i) {
                    case 0: // mmsi (mandatory)
                        dto.setMmsi(CSVParserUtils.parseInt(data[i]));
                        break;

                    case 1: // imo (optional)
                        // dto.setImo(CSVParserUtils.parseInt(data[i]));
                        dto.setImo(CSVParserUtils.parseIntOrReturnDefault(data[i], 0));
                        break;

                    case 2: // callsign (optional)
                        dto.setCallSign(CSVParserUtils.parseTextOrReturnNull(data[i]));
                        break;

                    case 3: // shipname (optional)
                        dto.setShipName(CSVParserUtils.parseTextOrReturnNull(data[i]));
                        break;

                    case 4: // shiptype (optional)
                        dto.setShipType(CSVParserUtils.parseIntOrReturnDefault(data[i], 0));
                        break;

                    case 8: // toPort (optional)
                        dto.setToPort(CSVParserUtils.parseIntOrReturnDefault(data[i], 0));
                        break;

                    case 9: // eta (optional)
                        dto.setEta(CSVParserUtils.parseTextOrReturnNull(data[i]));
                        break;

                    case 10: // draught (optional)
                        dto.setDraught(CSVParserUtils.parseDoubleOrReturnDefault(data[i], 0));
                        break;

                    case 11: // destination (optional)
                        dto.setDestination(CSVParserUtils.parseTextOrReturnNull(data[i]));
                        break;

                    case 13: // timestamp (mandatory)
                        dto.setT(CSVParserUtils.parseLong(data[i]));
                        break;
                }
            }
        } catch (CSVParserException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            LOGGER.log(Level.WARNING, "Failed to parse [ " + line + " ]");
            LOGGER.log(Level.WARNING, "The line parsed as " + Arrays.toString(data));
            throw new CSVParserException(line, e);
        }

        return dto;
    }

    public MMSICountryCodesDto extractMMSICountryCodesDto(@NotNull String line)
        throws CSVParserException {

        // break the line at commas
        final String[] data = CSVParserUtils.parseLine(line);
        CSVParserUtils.validateNumberOfParsedValues(data.length, 2, "extractMMSICountryCodesDto");

        // print data after split
        // LOGGER.info("Data extracted: " + Arrays.toString(data));
        // create the dto obj
        MMSICountryCodesDto dto = new MMSICountryCodesDto();
        // if a number format exception thrown, discard the line
        try {

            // loop through results
            for (int i = 0; i <= 1; i++) { // expecting 2 attributes

                switch (i) {
                    case 0:
                        dto.setMmsiCountryCode(CSVParserUtils.parseInt(data[i]));
                        break;

                    case 1:
                        dto.setCountry(CSVParserUtils.parseTextOrReturnNull(data[i]));
                        break;
                }
            }
        } catch (CSVParserException | IllegalArgumentException e) {
            throw new CSVParserException(e);
        }

        return dto;
    }

    public ShipTypeListDto extractShipTypeListDto(@NotNull String line) throws CSVParserException {

        // break the line at commas
        final String[] data = CSVParserUtils.parseLine(line);
        CSVParserUtils.validateNumberOfParsedValues(data.length, 5, "extractMMSICountryCodesDto");
        // print data after split
        // LOGGER.info("Data extracted: " + Arrays.toString(data));
        // create the dto obj
        ShipTypeListDto dto = new ShipTypeListDto();
        // if a number format exception thrown, discard the line
        try {

            // loop through results
            for (int i = 0; i <= 4; i++) { // expecting 5 attributes

                switch (i) {
                    case 1:
                        dto.setShipTypeMin(CSVParserUtils.parseInt(data[i]));
                        break;

                    case 2:
                        dto.setShipTypeMax(CSVParserUtils.parseInt(data[i]));
                        break;

                    case 3:
                        dto.setTypeName(CSVParserUtils.parseTextOrReturnNull(data[i]));
                        break;
                }
            }
        } catch (CSVParserException | IllegalArgumentException e) {
            throw new CSVParserException(e);
        }

        return dto;
    }

    public NariDynamicDto extractNariDynamicDto(@NotNull String line) throws CSVParserException {

        // break the line at commas
        final String[] data = CSVParserUtils.parseLine(line);
        CSVParserUtils.validateNumberOfParsedValues(data.length, 9, "extractMMSICountryCodesDto");
        // print data after split
        // LOGGER.info("Data extracted: " + Arrays.toString(data));
        // create the dto obj
        NariDynamicDto dto = new NariDynamicDto();
        // if a number format exception thrown, discard the line
        try {

            // loop through results
            for (int i = 0; i <= 8; i++) { // expecting 9 attributes

                switch (i) {
                    case 0:
                        dto.setMMSI(CSVParserUtils.parseInt(data[i]));
                        break;

                    case 3:
                        dto.setSpeed(CSVParserUtils.parseDouble(data[i]));
                        break;

                    case 6:
                        dto.setLon(CSVParserUtils.parseDouble(data[i]));
                        break;

                    case 7:
                        dto.setLat(CSVParserUtils.parseDouble(data[i]));
                        break;

                    case 8:
                        dto.setT(CSVParserUtils.parseLong(data[i]));
                        break;
                }
            }
        } catch (CSVParserException | IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Failed to parse [ " + line + " ]");
            LOGGER.log(Level.WARNING, "The line parsed as " + Arrays.toString(data));
            throw new CSVParserException(line, e);
        }

        return dto;
    }

    public PortDto extractPortDto(@NotNull String line) throws CSVParserException {

        // break the line at commas
        final String[] data = CSVParserUtils.parseLineAtQuestionMarks(line);
        CSVParserUtils.validateNumberOfParsedValues(data.length, 4, "extractPortDto");
        // print data after split
        // LOGGER.info("Data extracted: " + Arrays.toString(data));
        // create the dto obj
        PortDto dto = new PortDto();
        // if a number format exception thrown, discard the line
        try {

            // loop through results
            for (int i = 0; i <= 3; i++) { // expecting 4 attributes

                switch (i) {
                    case 0:
                        dto.setName(CSVParserUtils.parseText(data[i]));
                        break;

                    case 1:
                        dto.setCountry(CSVParserUtils.parseText(data[i]));
                        break;

                    case 2:
                        dto.setLatitude(CSVParserUtils.parseDouble(data[i]));
                        break;

                    case 3:
                        dto.setLongitude(CSVParserUtils.parseDouble(data[i]));
                        break;
                }
            }
        } catch (CSVParserException | IllegalArgumentException e) {
            throw new CSVParserException(e);
        }

        return dto;
    }

}
