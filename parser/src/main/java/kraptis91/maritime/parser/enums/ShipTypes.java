package kraptis91.maritime.parser.enums;

import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.ShipTypeListDto;
import kraptis91.maritime.parser.exception.CSVParserException;
import kraptis91.maritime.parser.utils.InputStreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020. */
public enum ShipTypes {
  INSTANCE("/csv/shipTypesList.csv");

  public final Logger LOGGER = java.util.logging.Logger.getLogger(ShipTypes.class.getName());
  private final Map<String, String> shipTypeMap;

  ShipTypes(String resource) {

    shipTypeMap = new LinkedHashMap<>();

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
        shipTypeMap.put(String.valueOf(dto.getIdShipType()), dto.getTypeName());
      }

    } catch (IOException | CSVParserException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  public String getShipType(int shipType) {
    return shipTypeMap.get(String.valueOf(shipType));
  }
}
