package kraptis91.maritime.parser.enums;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kraptis91.maritime.parser.dto.json.MMSICounterDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 26/12/2020.
 */
public enum MMSICounter {

    INSTANCE("/json/mmsi-counter-map.json");

    public final Logger LOGGER = Logger.getLogger(MMSICounter.class.getName());
    private final Map<Integer, Integer> mmsiCounterMap;
    private int maxMMSICounter = 0;

    MMSICounter(String filename) {

        LOGGER.info("Initializing MMSI counter map from internal resource: " + filename);

        mmsiCounterMap = new LinkedHashMap<>();
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(MMSICounter.class.getResourceAsStream(filename)))) {

            List<MMSICounterDto> mmsiCounterDtoList = mapper.readValue(reader, new TypeReference<>() {
            });
            mmsiCounterDtoList.forEach(dto -> mmsiCounterMap.put(dto.getMmsi(), dto.getCounter()));

        } catch (JsonParseException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (JsonMappingException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public int getMMSICounterForVessel(int mmsi) {
        return mmsiCounterMap.get(mmsi);
    }

    public int getMaxMMSICounter() {
        if (maxMMSICounter == 0) {
            maxMMSICounter = mmsiCounterMap.values().stream().max(Integer::compareTo).orElse(-1);
        }
        return maxMMSICounter;
    }
}
