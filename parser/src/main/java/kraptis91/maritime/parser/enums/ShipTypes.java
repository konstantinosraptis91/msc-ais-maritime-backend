package kraptis91.maritime.parser.enums;

import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.ShipTypeListDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public enum ShipTypes {
  INSTANCE("/csv/shipTypesList.csv");

  public final Logger LOGGER = java.util.logging.Logger.getLogger(ShipTypes.class.getName());
  private final List<ShipTypeListDto> shipTypeListDtoList;

  ShipTypes(String resource) {

    shipTypeListDtoList = new ArrayList<>();

    try {
      LOGGER.info("Initializing Ship Types from " + resource + " START");
      final InputStream is = ShipTypes.class.getResourceAsStream(resource);
      final BufferedReader bufferedReader =
          new BufferedReader(new InputStreamReader(InputStreamUtils.getBufferedInputStream(is)));
      final CSVParser parser = new CSVParser();

      String line;
      ShipTypeListDto dto;
      boolean isFirstLine = true;

      while ((line = bufferedReader.readLine()) != null) {

        // omit first line
        if (isFirstLine) {
          isFirstLine = false;
          continue;
        }

        // parse current line to the dto
        dto = parser.extractShipTypeListDto(line);
        // System.out.println(dto);
        // add ship type id as key to Map and type name as value
        shipTypeListDtoList.add(dto);
      }

    } catch (IOException | CSVParserException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  private Predicate<ShipTypeListDto> isBetween(int shipType) {
    return dto -> shipType >= dto.getShipTypeMin() && shipType <= dto.getShipTypeMax();
  }

  public String getShipType(final int shipType) {

    try {
      return shipTypeListDtoList.stream()
          .filter(isBetween(shipType))
          .findAny()
          .orElseThrow()
          .getTypeName();
    } catch (NoSuchElementException e) {
      return null;
    }
  }
}
