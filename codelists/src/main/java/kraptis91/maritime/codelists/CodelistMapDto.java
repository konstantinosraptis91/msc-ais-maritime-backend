package kraptis91.maritime.codelists;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 13/1/2021.
 */
public class CodelistMapDto {

    private String code;
    private String value;

    public CodelistMapDto(@JsonProperty("code") String code,
                          @JsonProperty("value") String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
