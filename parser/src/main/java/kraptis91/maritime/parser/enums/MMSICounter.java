package kraptis91.maritime.parser.enums;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kraptis91.maritime.parser.dto.json.MMSICounterDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 26/12/2020. */
public enum MMSICounter {
  INSTANCE(MMSICounter.class.getResourceAsStream("/json/mmsi-counter-map.json"));

  private final Logger LOGGER = Logger.getLogger(MMSICounter.class.getName());
  private final Map<Integer, Integer> mmsiCounterMap;

  MMSICounter(InputStream is) {
    mmsiCounterMap = new LinkedHashMap<>();
    ObjectMapper mapper = new ObjectMapper();

    try {
      List<MMSICounterDto> mmsiCounterDtoList = mapper.readValue(is, new TypeReference<>() {});
      mmsiCounterDtoList.forEach(dto -> mmsiCounterMap.put(dto.getMmsi(), dto.getCounter()));

    } catch (JsonParseException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    } catch (JsonMappingException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, e.getMessage(), e);
    }
  }

  public int getCounterForVessel(int mmsi) {
    return mmsiCounterMap.get(mmsi);
  }
}
