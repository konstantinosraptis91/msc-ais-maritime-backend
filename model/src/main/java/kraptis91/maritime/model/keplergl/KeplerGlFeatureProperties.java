package kraptis91.maritime.model.keplergl;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 6/1/2021.
 */
public class KeplerGlFeatureProperties {

    private final String startDate;
    private final String endDate;
    private final int nPoints;
    private final double avgSpeed;
    private final int mmsi;
    private final String vesselName;
    private final String shipType;

    private KeplerGlFeatureProperties(Builder builder) {
        this.startDate = builder.startDate;
        this.endDate = builder.endDate;
        this.nPoints = builder.nPoints;
        this.avgSpeed = builder.avgSpeed;
        this.mmsi = builder.mmsi;
        this.vesselName = builder.vesselName;
        this.shipType = builder.shipType;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public int getnPoints() {
        return nPoints;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public int getMmsi() {
        return mmsi;
    }

    public String getVesselName() {
        return vesselName;
    }

    public String getShipType() {
        return shipType;
    }

    public interface KeplerGlFeaturePropertiesStartDate {
        KeplerGlFeaturePropertiesEndDate withStartDate(String startDate);
    }

    public interface KeplerGlFeaturePropertiesEndDate {
        KeplerGlFeaturePropertiesNPoints withEndDate(String endDate);
    }

    public interface KeplerGlFeaturePropertiesNPoints {
        KeplerGlFeaturePropertiesAvgSpeed withNPoints(int nPoints);
    }

    public interface KeplerGlFeaturePropertiesAvgSpeed {
        KeplerGlFeaturePropertiesMMSI withAvgSpeed(double avgSpeed);
    }

    public interface KeplerGlFeaturePropertiesMMSI {
        KeplerGlFeaturePropertiesVesselName withMMSI(int mmsi);
    }

    public interface KeplerGlFeaturePropertiesVesselName {
        KeplerGlFeaturePropertiesShipType withVesselName(String vesselName);
    }

    public interface KeplerGlFeaturePropertiesShipType {
        KeplerGlFeaturePropertiesBuild withShipType(String shipType);
    }

    public interface KeplerGlFeaturePropertiesBuild {
        KeplerGlFeatureProperties build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements
        KeplerGlFeaturePropertiesStartDate, KeplerGlFeaturePropertiesEndDate, KeplerGlFeaturePropertiesNPoints,
        KeplerGlFeaturePropertiesAvgSpeed, KeplerGlFeaturePropertiesMMSI, KeplerGlFeaturePropertiesVesselName,
        KeplerGlFeaturePropertiesShipType, KeplerGlFeaturePropertiesBuild {

        private String startDate;
        private String endDate;
        private int nPoints;
        private double avgSpeed;
        private int mmsi;
        private String vesselName;
        private String shipType;

        @Override
        public KeplerGlFeaturePropertiesEndDate withStartDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        @Override
        public KeplerGlFeaturePropertiesNPoints withEndDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        @Override
        public KeplerGlFeaturePropertiesAvgSpeed withNPoints(int nPoints) {
            this.nPoints = nPoints;
            return this;
        }

        @Override
        public KeplerGlFeaturePropertiesMMSI withAvgSpeed(double avgSpeed) {
            this.avgSpeed = avgSpeed;
            return this;
        }

        @Override
        public KeplerGlFeaturePropertiesVesselName withMMSI(int mmsi) {
            this.mmsi = mmsi;
            return this;
        }

        @Override
        public KeplerGlFeaturePropertiesShipType withVesselName(String vesselName) {
            this.vesselName = vesselName;
            return this;
        }

        @Override
        public KeplerGlFeaturePropertiesBuild withShipType(String shipType) {
            this.shipType = shipType;
            return this;
        }

        @Override
        public KeplerGlFeatureProperties build() {
            return new KeplerGlFeatureProperties(this);
        }
    }

}
