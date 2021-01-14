package kraptis91.maritime.codelists;

import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 14/1/2021.
 */
public enum CodelistsOfMap {

    MMSI_COUNTER_MAP("mmsi", "counter", "/codelists/mmsi-counter-map.json");

    private final String codeName;
    private final String valueName;
    private final String filename;

    private volatile GenericMapCode INSTANCE;

    CodelistsOfMap(String codeName, String valueName, String filename) {
        this.codeName = codeName;
        this.valueName = valueName;
        this.filename = filename;
    }

    private GenericMapCode getInstance() {
        if (Objects.isNull(INSTANCE)) {
            makeInstance();
        }
        return INSTANCE;
    }

    protected synchronized void makeInstance() {
        INSTANCE = new GenericMapCode(codeName, valueName, filename);
    }

    public final String getValueForId(String id) {
        return getInstance().getValueForId(id);
    }

    public final boolean containsValue(String value) {
        return getInstance().containsValue(value);
    }

    public final boolean containsKey(String key) {
        return getInstance().containsKey(key);
    }

}
