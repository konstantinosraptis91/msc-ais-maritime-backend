package kraptis91.maritime.parser.enums;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.ImmutableList;
import kraptis91.maritime.parser.dto.json.CountryCodeMapDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/12/2020.
 */
public enum CountryCodeMap {

    INSTANCE("/json/country-code-map.json");

    public final Logger LOGGER = Logger.getLogger(CountryCodeMap.class.getName());
    private final BiMap<CountryCode, String> countryCodeBiMap;
    private List<CountryCodeMapDto> countryCodeMapDtoList;

    CountryCodeMap(String filename) {

        countryCodeBiMap = EnumHashBiMap.create(CountryCode.class);
        ObjectMapper mapper = new ObjectMapper();

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(CountryCodeMap.class.getResourceAsStream(filename)))) {

            List<CountryCodeMapDto> tempList = mapper.readValue(reader, new TypeReference<>() {});
            countryCodeMapDtoList = ImmutableList.copyOf(tempList);
            tempList.forEach(dto -> countryCodeBiMap.put(
                CountryCode.valueOf(dto.getCode()),
                dto.getName()));

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

    public String getCountryNameByCode(CountryCode code) {
        return countryCodeBiMap.get(code);
    }

    public CountryCode getCodeByCountryName(String name) {
        return countryCodeBiMap.inverse().get(name);
    }

    public List<CountryCodeMapDto> getCountryCodeMapDtoList() {
        return countryCodeMapDtoList;
    }
}
