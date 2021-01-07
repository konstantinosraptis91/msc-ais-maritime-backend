package kraptis91.maritime.db.dao.mongodb.query.utils;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 7/1/2021.
 */
public class NearQueryOptions extends QueryOptions {

    private final double longitude;
    private final double latitude;
    private final double maxDistance;
    private final double minDistance;

    private NearQueryOptions(Builder builder) {
        super(builder);
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.maxDistance = builder.maxDistance;
        this.minDistance = builder.minDistance;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends QueryOptions.Builder {

        private double longitude;
        private double latitude;
        private double maxDistance;
        private double minDistance;

        public Builder withLongitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder withLatitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder withMaxDistance(double maxDistance) {
            this.maxDistance = maxDistance;
            return this;
        }

        public Builder withMinDistance(double minDistance) {
            this.minDistance = minDistance;
            return this;
        }

        @Override
        public Builder skip(int skip) {
            this.skip = skip;
            return this;
        }

        @Override
        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        @Override
        public NearQueryOptions build() {
            return new NearQueryOptions(this);
        }
    }

    @Override
    public String toString() {
        return "NearQueryOptions{" +
            "longitude=" + longitude +
            ", latitude=" + latitude +
            ", maxDistance=" + maxDistance +
            ", minDistance=" + minDistance +
            ", skip=" + getSkip() +
            ", limit=" + getLimit() +
            '}';
    }
}
