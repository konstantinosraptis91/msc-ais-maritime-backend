package kraptis91.maritime.parser.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/12/2020.
 */
public class CountryCodeMapDto {

    private String code;
    private String name;

    public CountryCodeMapDto(@JsonProperty("Code") String code,
                             @JsonProperty("Name") String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
