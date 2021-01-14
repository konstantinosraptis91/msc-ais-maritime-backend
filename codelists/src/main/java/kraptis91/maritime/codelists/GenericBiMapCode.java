package kraptis91.maritime.codelists;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.BiMap;
import kraptis91.maritime.codelists.utils.CodelistsBiMapDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 13/1/2021.
 */
public class GenericBiMapCode {

    public final Logger LOGGER = Logger.getLogger(GenericBiMapCode.class.getName());

    protected BiMap<String, String> biMap;
    protected List<CodelistMapDto> codelistMapDtoList;

    protected GenericBiMapCode(String codeName, String valueName, String filename) {

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BiMap.class, new CodelistsBiMapDeserializer(codeName, valueName));
        mapper.registerModule(module);

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(GenericBiMapCode.class.getResourceAsStream(filename)))) {

            biMap = mapper.readValue(reader, new TypeReference<>() {
            });

            codelistMapDtoList = biMap.entrySet().stream()
                .map(entry -> new CodelistMapDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

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
        return biMap.get(id);
    }

    protected final String getIdForValue(String value) {
        return biMap.inverse().get(value);
    }

    protected final boolean containsValue(String value) {
        return biMap.containsValue(value);
    }

    protected final boolean containsKey(String key) {
        return biMap.containsKey(key);
    }

    protected final List<CodelistMapDto> getCodelistMapDtoList() {
        return codelistMapDtoList;
    }

}
