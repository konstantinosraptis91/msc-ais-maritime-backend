package kraptis91.maritime.model;

import sun.misc.Contended;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigInteger;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * @author Stavros Lamprinos [stalab at linuxmail.org] on 8/12/2020.
 */
public class OceanConditions {

    /**
     * longitude in degrees (-180.0 to 180.0 range)
     */
    @DecimalMin(value = "-180.0", message = "Invalid value, degrees of longitude cannot be less than -180.0")
    @DecimalMax(value = "180.0", message = "Invalid value, degrees of longitude cannot be more than 180.0")
    private final double longitude;

    /**
     * latitude in degrees (-90.0 to 90.0 range)
     */
    @DecimalMin(value = "-90.0", message = "Invalid value, degrees of latitude cannot be less than -90.0")
    @DecimalMax(value = "90.0", message = "Invalid value, degrees of latitude cannot be more than 90.0")
    private final double latitude;

    /**
     * bottom depth of the oceans point in meters (Undefined value = -16384 -> see tdo)
     */
    private final double bottomDepth;

    /**
     * sea surface height above sea level in meters (=>tidal effect). (Undefined value = -327.67 -> see tdo)
     */
    private final double tidalEffect;

    /**
     * significant height of wind and swell waves (=>see state) (Undefined value = -65.534 -> see tdo)
     */
    private final double seaHeight;

    /**
     * mean wave length in meters. (Undefined value= - 32767 -> see tdo)
     */
    private final int meanWaveLength;

    private final BigInteger timestamp;


    private OceanConditions(Builder builder) {
        this.longitude = builder.longitude;
        this.latitude = builder.latitude;
        this.bottomDepth = builder.bottomDepth;
        this.tidalEffect = builder.tidalEffect;
        this.seaHeight = builder.seaHeight;
        this.meanWaveLength = builder.meanWaveLength;
        this.timestamp = builder.timestamp;
    }

    // -------------------------------------------------------------------------------------------------------------------
    // Getters
    // -------------------------------------------------------------------------------------------------------------------

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getBottomDepth() {
        return bottomDepth;
    }

    public double getTidalEffect() {
        return tidalEffect;
    }

    public double getSeaHeight() {
        return seaHeight;
    }

    public double getMeanWaveLength() {
        return meanWaveLength;
    }

    public BigInteger getTimestamp() {
        return timestamp;
    }


    // -------------------------------------------------------------------------------------------------------------------
    // toString
    // -------------------------------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "OceanConditions{"
                + "longitude = "
                + longitude
                + ", latitude = "
                + latitude
                + ", bottomDepth = "
                + bottomDepth
                + ", tidalEffect = "
                + tidalEffect
                + ", seaHeight = "
                + seaHeight
                + ", meanWaveLength="
                + meanWaveLength
                + ", timestamp ="
                + timestamp
                + "}";
    }

    // -------------------------------------------------------------------------------------------------------------------
    // Fluent API interfaces
    // -------------------------------------------------------------------------------------------------------------------

    public interface OceanBottomDepth {
        OceanTidalEffect withBottomDepth(OptionalDouble bottomDepth);
    }

    public interface OceanTidalEffect {
        OceanSeaHeight withTidalEffect(OptionalDouble tidalEffect);
    }

    public interface OceanSeaHeight {
        OceanMeanWaveLength withSeaHeight(OptionalDouble seaHeight);
    }

    public interface OceanMeanWaveLength {
        OceanBuild withMeanWaveLength(OptionalInt meanWaveLength);
    }

    public interface OceanBuild {
        OceanConditions build();
    }

    // -------------------------------------------------------------------------------------------------------------------
    // Vessel POJO builder
    // -------------------------------------------------------------------------------------------------------------------

    public static class Builder
            implements OceanBottomDepth,
            OceanTidalEffect,
            OceanSeaHeight,
            OceanMeanWaveLength,
            OceanBuild {

        // mandatory fields
        private final double longitude;
        private final double latitude;
        private final BigInteger timestamp;

        // optional fields
        private double bottomDepth;
        private double tidalEffect;
        private double seaHeight;
        private int meanWaveLength;

        public Builder(double longitude, double latitude, BigInteger timestamp) {
            this.longitude = longitude;
            this.latitude = latitude;
            this.timestamp = timestamp;
        }

        /**
         * @param bottomDepth The depth of the oceans point in meters (Undefined value = -16384)
         * @return The Builder
         */
        @Override
        public OceanTidalEffect withBottomDepth(OptionalDouble bottomDepth) {
            this.bottomDepth = bottomDepth.orElse(-16384);
            return this;
        }

        /**
         * @param seaHeight Sea surface height above sea level in meters (Undefined value = -65.534)
         * @return The Builder
         */
        @Override
        public OceanMeanWaveLength withSeaHeight(OptionalDouble seaHeight) {
            this.seaHeight = seaHeight.orElse(-65.53);
            return this;
        }

        /**
         * @param meanWaveLength Mean wave length in meters (Undefined value = -32767)
         * @return The Builder
         */
        @Override
        public OceanBuild withMeanWaveLength(OptionalInt meanWaveLength) {
            this.meanWaveLength = meanWaveLength.orElse(-32767);
            return this;
        }

        /**
         * @param tidalEffect Significant height of wind and swell waves (Undefined value = -327.67)
         * @return The Builder
         */
        @Override
        public OceanSeaHeight withTidalEffect(OptionalDouble tidalEffect) {
            this.tidalEffect = tidalEffect.orElse(-327.67);
            return this;
        }

        @Override
        public OceanConditions build() {
            return new OceanConditions(this);
        }
    }
}
