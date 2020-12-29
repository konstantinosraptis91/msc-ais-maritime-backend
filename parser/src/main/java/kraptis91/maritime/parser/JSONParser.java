package kraptis91.maritime.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import kraptis91.maritime.parser.dto.json.MMSICounterDto;
import kraptis91.maritime.parser.utils.CSVParserUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 26/12/2020. */
public class JSONParser {

  public static Map<String, Integer> createMMSICounterMap(Path path) throws IOException {

    final Map<String, Integer> mmsiCounterMap = new LinkedHashMap<>();

    try (BufferedReader reader = Files.newBufferedReader(path)) {

      String line;
      String mmsi;
      String[] data;
      boolean isFirstLine = true;

      while ((line = reader.readLine()) != null) {

        if (isFirstLine) {
          isFirstLine = false;
          continue;
        }

        data = CSVParserUtils.parseLine(line);
        mmsi = data[0];

        if (mmsiCounterMap.containsKey(mmsi)) {
          mmsiCounterMap.put(mmsi, mmsiCounterMap.get(mmsi) + 1);
        } else {
          mmsiCounterMap.put(mmsi, 1);
        }
      }
    }

    return mmsiCounterMap;
  }

  /**
   * @param map The map
   * @param filename The target file name
   */
  public static void writeMapAsJSON(Map<String, Integer> map, String filename) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    final List<MMSICounterDto> mmsiCounterList = new ArrayList<>();
    map.forEach(
        (key, value) -> mmsiCounterList.add(new MMSICounterDto(Integer.parseInt(key), value)));
    mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), mmsiCounterList);
  }
}
