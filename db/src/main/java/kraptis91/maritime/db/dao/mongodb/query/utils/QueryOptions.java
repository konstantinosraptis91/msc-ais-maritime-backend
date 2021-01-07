package kraptis91.maritime.db.dao.mongodb.query.utils;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 7/1/2021.
 */
public class QueryOptions {

    private final int skip;
    private final int limit;

    protected QueryOptions(Builder builder) {
        this.skip = builder.skip;
        this.limit = builder.limit;
    }

    public int getSkip() {
        return skip;
    }

    public int getLimit() {
        return limit;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        protected int skip;
        protected int limit;

        public Builder skip(int skip) {
            this.skip = skip;
            return this;
        }

        public Builder limit(int limit) {
            this.limit = limit;
            return this;
        }

        public QueryOptions build() {
            return new QueryOptions(this);
        }

    }

    @Override
    public String toString() {
        return "QueryOptions{" +
            "skip=" + skip +
            ", limit=" + limit +
            '}';
    }
}
