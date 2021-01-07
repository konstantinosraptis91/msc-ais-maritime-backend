package kraptis91.maritime.model;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public class Port {

    private final String name;
    private String country;
    private final GeoPoint geoPoint;

    private Port(Builder builder) {
        this.name = builder.name;
        this.country = builder.country;
        this.geoPoint = builder.geoPoint;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String name;
        private String country;
        private GeoPoint geoPoint;

        public Builder() {

        }

        /**
         * @param name The port name
         * @return
         */
        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withCoordinates(GeoPoint geoPoint) {
            this.geoPoint = geoPoint;
            return this;
        }

        public Port build() {
            return new Port(this);
        }

    }

    @Override
    public String toString() {
        return "Port{" +
            "name='" + name + '\'' +
            ", country='" + country + '\'' +
            ", geoPoint=" + geoPoint +
            '}';
    }
}
