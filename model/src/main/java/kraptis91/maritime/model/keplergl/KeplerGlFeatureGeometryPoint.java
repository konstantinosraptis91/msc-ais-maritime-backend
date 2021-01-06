package kraptis91.maritime.model.keplergl;

import com.google.common.collect.ImmutableList;
import kraptis91.maritime.model.GeoPoint;

import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 6/1/2021.
 */
public class KeplerGlFeatureGeometryPoint {

    private final String type;
    private final List<Double> coordinates;

    private KeplerGlFeatureGeometryPoint(double longitude, double latitude) {
        this.type = "Point";
        coordinates = ImmutableList.of(longitude, latitude);
    }

    private KeplerGlFeatureGeometryPoint(GeoPoint geoPoint) {
        this.type = "Point";
        coordinates = ImmutableList.of(geoPoint.getCoordinates().get(0), geoPoint.getCoordinates().get(1));
    }

    public static KeplerGlFeatureGeometryPoint of(double longitude, double latitude) {
        return new KeplerGlFeatureGeometryPoint(longitude, latitude);
    }

    public static KeplerGlFeatureGeometryPoint of(GeoPoint geoPoint) {
        return new KeplerGlFeatureGeometryPoint(geoPoint);
    }

    public String getType() {
        return type;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }
}
