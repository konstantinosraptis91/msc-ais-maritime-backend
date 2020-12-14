package kraptis91.maritime.model.enums;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 14/12/2020.
 */
public enum GeoJsonType {

    POINT("Point");

    private String value;

    GeoJsonType(String value) {
        this.value = value;
    }

    public String getValue() {
    return value;
    }
}
