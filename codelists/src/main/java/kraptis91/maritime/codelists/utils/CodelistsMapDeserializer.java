package kraptis91.maritime.codelists.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 14/1/2021.
 */
public class CodelistsMapDeserializer extends JsonDeserializer<Map<String, String>> {

    private final String codeName;
    private final String valueName;

    public CodelistsMapDeserializer(String codeName, String valueName) {
        this.codeName = codeName;
        this.valueName = valueName;
    }

    @Override
    public Map<String, String> deserialize(JsonParser jsonParser,
                                           DeserializationContext context) throws IOException, JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper();
        final JsonNode codelistMapJsonArray = mapper.readTree(jsonParser);
        final Map<String, String> map = new HashMap<>();

        for (JsonNode codelistMapNode : codelistMapJsonArray) {

            String code = codelistMapNode.path(codeName).asText();
            String value = codelistMapNode.path(valueName).asText();

            map.put(code, value);
        }

        return map;
    }

}
