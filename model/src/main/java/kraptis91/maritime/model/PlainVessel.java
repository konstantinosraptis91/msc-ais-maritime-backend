package kraptis91.maritime.model;

import org.hibernate.validator.constraints.Range;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/12/2020.
 */
public class PlainVessel {

    /**
     * MMSI identifier for vessel.
     *
     * <p>The MMSI number (Maritime Mobile Service Identity) is a unique nine-digit number for
     * identifying a ship. It is programmed into all AIS systems and VHF electronics on board of the
     * vessel and provides an internationally standardized number for contacting the vessel.
     */
    @Range(
        min = 1,
        max = 999999999,
        message = "Invalid maritime mobile service identity. The MMSI should be a 9 digit number.")
    protected int mmsi;

    /**
     * Name of the vessel (max 20 characters).
     */
    @NotNull
    @Size(max = 20, message = "Invalid vessel name. The vessel name should be max 20 characters.")
    protected String vesselName;

    /**
     * Type of the ship.
     */
    @Nullable
    protected String shipType;

    /**
     * The country in which the vessel belongs.
     */
    protected String country;

    protected PlainVessel() {
    }

    protected PlainVessel(Builder builder) {
        this.mmsi = builder.mmsi;
        this.vesselName = builder.vesselName;
        this.shipType = builder.shipType;
        this.country = builder.country;
    }

    public int getMMSI() {
        return mmsi;
    }

    public String getVesselName() {
        return vesselName;
    }

    @Nullable
    public String getShipType() {
        return shipType;
    }

    public String getCountry() {
        return country;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        protected int mmsi;
        protected String vesselName;
        protected String shipType;
        protected String country;

        public Builder withMMSI(int mmsi) {
            this.mmsi = mmsi;
            return this;
        }

        public Builder withVesselName(String vesselName) {
            this.vesselName = vesselName;
            return this;
        }

        public Builder withShipType(String shipType) {
            this.shipType = shipType;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public PlainVessel build() {
            return new PlainVessel(this);
        }

    }

    @Override
    public String toString() {
        return "PlainVessel{" +
            "mmsi=" + mmsi +
            ", vesselName='" + vesselName + '\'' +
            ", shipType='" + shipType + '\'' +
            ", country='" + country + '\'' +
            '}';
    }
}
