package kraptis91.maritime.model;

import com.google.common.collect.ImmutableList;
import kraptis91.maritime.model.enums.GeoJsonType;

import java.util.List;
import java.util.Objects;

/**
 * @author Stavros Lamprinos [stalab at linuxmail.org] on 14/12/2020.
 */
public class GeoPoint {

    // the GeoJson type
    private final String type;
    // coordinates as an array of [lon, lat]
    private final List<Double> coordinates;

    private GeoPoint(double longitude, double latitude) {
        this.type = GeoJsonType.POINT.getValue();
        coordinates = ImmutableList.of(longitude, latitude);
    }

    /**
     * Create a new geospatial point.
     *
     * @param longitude The longitude
     * @param latitude  The latitude
     */
    public static GeoPoint of(double longitude, double latitude) {
        return new GeoPoint(longitude, latitude);
    }

    public String getType() {
        return type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.coordinates.get(0));
        hash = 29 * hash + Objects.hashCode(this.coordinates.get(1));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeoPoint other = (GeoPoint) obj;

        return Objects.equals(this.coordinates.get(0), other.coordinates.get(0))
            && Objects.equals(this.coordinates.get(1), other.coordinates.get(1));
    }

    @Override
    public String toString() {
        return "GeoPoint{" + "type='" + type + '\'' + ", coordinates=" + coordinates + '}';
    }
}
