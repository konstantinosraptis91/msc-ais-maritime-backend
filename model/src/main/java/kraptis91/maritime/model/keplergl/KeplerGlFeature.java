package kraptis91.maritime.model.keplergl;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 6/1/2021.
 */
public class KeplerGlFeature {

    private final String type;
    @JsonProperty("geometry")
    private final KeplerGlFeatureGeometryPoint geometryPoint;
    @JsonProperty("properties")
    private final KeplerGlFeatureProperties featureProperties;

    private KeplerGlFeature(Builder builder) {
        this.type = "Feature";
        this.geometryPoint = builder.geometryPoint;
        this.featureProperties = builder.featureProperties;
    }

    public String getType() {
        return type;
    }

    public KeplerGlFeatureGeometryPoint getGeometryPoint() {
        return geometryPoint;
    }

    public KeplerGlFeatureProperties getFeatureProperties() {
        return featureProperties;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private KeplerGlFeatureGeometryPoint geometryPoint;
        private KeplerGlFeatureProperties featureProperties;

        public Builder withGeometryPoint(KeplerGlFeatureGeometryPoint geometryPoint) {
            this.geometryPoint = geometryPoint;
            return this;
        }

        public Builder withFeatureProperties(KeplerGlFeatureProperties featureProperties) {
            this.featureProperties = featureProperties;
            return this;
        }

        public KeplerGlFeature build() {
            return new KeplerGlFeature(this);
        }

    }

}
