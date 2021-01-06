package kraptis91.maritime.model.keplergl;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 6/1/2021.
 */
public class KeplerGlCollection {

    private final String type;
    @JsonProperty("features")
    private List<KeplerGlFeature> featureList;

    private KeplerGlCollection() {
        this.type = "FeatureCollection";
    }

    public static KeplerGlCollection newInstance() {
        return new KeplerGlCollection();
    }

    public KeplerGlCollection addFeature(KeplerGlFeature feature) {
        getFeatureList().add(feature);
        return this;
    }

    public List<KeplerGlFeature> getFeatureList() {
        if (Objects.isNull(featureList)) {
            featureList = new ArrayList<>();
        }
        return featureList;
    }

    public String getType() {
        return type;
    }
}
