package kraptis91.maritime.codelists;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import kraptis91.maritime.codelists.utils.CodelistsMapDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 14/1/2021.
 */
public class GenericMapCode {

    public final Logger LOGGER = Logger.getLogger(GenericMapCode.class.getName());

    protected Map<String, String> map;

    protected GenericMapCode(String codeName, String valueName, String filename) {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Map.class, new CodelistsMapDeserializer(codeName, valueName));
        mapper.registerModule(module);

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(GenericMapCode.class.getResourceAsStream(filename)))) {

            map = mapper.readValue(reader, new TypeReference<>() {
            });

        } catch (
            JsonParseException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (
            JsonMappingException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

    }

    protected final String getValueForId(String id) {
        return map.get(id);
    }

    protected final boolean containsValue(String value) {
        return map.containsValue(value);
    }

    protected final boolean containsKey(String key) {
        return map.containsKey(key);
    }

}
