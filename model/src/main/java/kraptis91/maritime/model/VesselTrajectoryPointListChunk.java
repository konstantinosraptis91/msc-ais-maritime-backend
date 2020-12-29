package kraptis91.maritime.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 21/12/2020.
 */
public class VesselTrajectoryPointListChunk extends VesselTrajectoryChunk {

    private List<VesselTrajectoryPoint> pointList;

    VesselTrajectoryPointListChunk(VesselTrajectoryChunkBuilder builder) {
        super(builder);
    }

    public List<VesselTrajectoryPoint> getPointList() {
        if (Objects.isNull(pointList)) {
            pointList = new ArrayList<>();
        }
        return pointList;
    }

    public static GeoPoint calcAvgGeoPoint(List<GeoPoint> geoPointList) {
        return GeoPoint.of(
                geoPointList.stream()
                        .mapToDouble(p -> p.getCoordinates().get(0)) // longitude
                        .average()
                        .orElse(Double.NaN),
                geoPointList.stream()
                        .mapToDouble(p -> p.getCoordinates().get(1)) // latitude
                        .average()
                        .orElse(Double.NaN));
    }

    public GeoPoint calcAvgGeoPoint() {
        return calcAvgGeoPoint(
                pointList.stream().map(VesselTrajectoryPoint::getGeoPoint).collect(Collectors.toList()));
    }

    public double calcAvgSpeed() {
        return pointList.stream()
                .mapToDouble(VesselTrajectoryPoint::getSpeed)
                .average()
                .orElse(Double.NaN);
    }

    public long calcStartDateTimestamp() {
        return pointList.stream()
                .mapToLong(VesselTrajectoryPoint::getTimestamp)
                .min()
                .orElse(-1);
    }

    public long calcEndDateTimestamp() {
        return pointList.stream()
                .mapToLong(VesselTrajectoryPoint::getTimestamp)
                .max()
                .orElse(-1);
    }

    @Override
    public int getNumberOfPoints() {
        return getPointList().size();
    }

    @Override
    public String toString() {
        return "VesselTrajectoryPointListChunk{" +
                "mmsi=" + mmsi +
                ", vesselName='" + vesselName + '\'' +
                ", shipType='" + shipType + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", avgGeoPoint=" + avgGeoPoint +
                ", avgSpeed=" + avgSpeed +
                ", chunkFixedSize=" + chunkFixedSize +
                ", nPoints=" + nPoints +
                ", pointList=" + pointList +
                '}';
    }
}
