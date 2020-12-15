package kraptis91.maritime.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.hibernate.validator.constraints.Range;

import javax.annotation.Nullable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class Vessel {

  /** The id given by mongo db. */
  @BsonId private final String id;

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
  private final int mmsi;

  /** IMO ship identification number (7 digits). */
  @Range(
      min = 1,
      max = 9999999,
      message = "Invalid ship identification number. The IMO should be a 7 digit number.")
  private final int imo;

  /** Name of the vessel (max 20 characters). */
  @NotNull
  @Size(max = 20, message = "Invalid vessel name. The vessel name should be max 20 characters.")
  private final String vesselName;

  /**
   * International radio call sign (max 7 characters), assigned to the vessel by its country of
   * registry.
   */
  @NotNull
  @Size(max = 7, message = "Invalid call sign. The call sign should be max 7 characters.")
  private final String callSign;

  /**
   * ETA (estimated time of arrival) in format dd-mm hh:mm (day, month, hour, minute) – UTC time
   * zone.
   */
  @NotNull private final String eta;

  /** Allowed values: 0.1-25.5 meters */
  @DecimalMin(value = "0.1", message = "Invalid draught value, draught cannot be less than 0.1")
  @DecimalMax(value = "25.5", message = "Invalid draught value, draught cannot be more than 25.5")
  private final double draught;

  /** Type of the ship. */
  @Nullable private final String shipType;

  /** Destination of this trip (manually entered). */
  @NotNull private final String destination;

  /** The country in which the vessel belongs. */
  private final String country;

  private Vessel(Builder builder) {
    this.mmsi = builder.mmsi;
    this.imo = builder.imo;
    this.vesselName = builder.vesselName;
    this.callSign = builder.callSign;
    this.eta = builder.eta;
    this.draught = builder.draught;
    this.shipType = builder.shipType;
    this.destination = builder.destination;
    this.country = builder.country;
    this.id = builder.id;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Getters
  // -------------------------------------------------------------------------------------------------------------------

  public int getMmsi() {
    return mmsi;
  }

  public int getImo() {
    return imo;
  }

  public String getVesselName() {
    return vesselName;
  }

  public String getCallSign() {
    return callSign;
  }

  public String getEta() {
    return eta;
  }

  public double getDraught() {
    return draught;
  }

  public String getShipType() {
    return shipType;
  }

  public String getDestination() {
    return destination;
  }

  public String getCountry() {
    return country;
  }

  public String getId() {
    return id;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // toString
  // -------------------------------------------------------------------------------------------------------------------

  @Override
  public String toString() {
    return "Vessel{"
        + "mmsi="
        + mmsi
        + ", imo="
        + imo
        + ", vesselName='"
        + vesselName
        + '\''
        + ", callSign='"
        + callSign
        + '\''
        + ", eta='"
        + eta
        + '\''
        + ", draught="
        + draught
        + ", shipType='"
        + shipType
        + '\''
        + ", destination='"
        + destination
        + '\''
        + ", country='"
        + country
        + '\''
        + '}';
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Fluent API interfaces
  // -------------------------------------------------------------------------------------------------------------------

  public interface VesselCallSign {
    VesselEta withCallSign(String callSign);
  }

  public interface VesselEta {
    VesselDraught withEta(String eta);
  }

  public interface VesselDraught {
    VesselShipType withDraught(double draught);
  }

  public interface VesselShipType {
    VesselDestination withShipType(String shipType);
  }

  public interface VesselDestination {
    VesselCountry withDestination(String destination);
  }

  public interface VesselCountry {
    VesselBuild withCountry(String country);
  }

  public interface VesselBuild {
    Vessel build();

    Vessel build(String id);
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + Objects.hashCode(this.mmsi);
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
    final Vessel other = (Vessel) obj;

    return Objects.equals(this.mmsi, other.mmsi);
  }
  // -------------------------------------------------------------------------------------------------------------------
  // Vessel POJO builder
  // -------------------------------------------------------------------------------------------------------------------

  public static class Builder
      implements VesselCallSign,
          VesselEta,
          VesselDraught,
          VesselShipType,
          VesselDestination,
          VesselCountry,
          VesselBuild {

    // mandatory fields
    private final int mmsi;
    private final int imo;
    private final String vesselName;

    // optional fields
    private String callSign;
    private String eta;
    private double draught;
    private String shipType;
    private String destination;
    private String country;
    private String id;

    public Builder(int mmsi, int imo, String vesselName) {
      this.mmsi = mmsi;
      this.imo = imo;
      this.vesselName = vesselName;
    }

    /**
     * Set the International radio call sign, assigned to the vessel by its country of registry.
     *
     * @param callSign The International radio call sign (max 7 characters)
     * @return The Builder
     */
    @Override
    public VesselEta withCallSign(String callSign) {
      this.callSign = callSign;
      return this;
    }

    /**
     * Set the estimate time of arrival.
     *
     * @param eta The ETA in format dd-mm hh:mm (day, month, hour, minute) – UTC time
     * @return The Builder
     */
    @Override
    public VesselDraught withEta(String eta) {
      this.eta = eta;
      return this;
    }

    /**
     * Set the vessel draught.
     *
     * @param draught The vessel draught (Min = 0.1, Max = 25.5)
     * @return The Builder
     */
    @Override
    public VesselShipType withDraught(double draught) {
      this.draught = draught;
      return this;
    }

    /**
     * Set the ship type code.
     *
     * @param shipType The ship type code
     * @return The Builder
     */
    @Override
    public VesselDestination withShipType(String shipType) {
      this.shipType = shipType;
      return this;
    }

    /**
     * Set the destination.
     *
     * @param destination The destination of the trip
     * @return The Builder
     */
    @Override
    public VesselCountry withDestination(String destination) {
      this.destination = destination;
      return this;
    }

    /**
     * Set the country in which the vessel belongs.
     *
     * @param country The country
     * @return The Builder
     */
    @Override
    public VesselBuild withCountry(String country) {
      this.country = country;
      return this;
    }

    @Override
    public Vessel build() {
      return new Vessel(this);
    }

    /**
     * @param id The id given by mongo db.
     * @return The Vessel
     */
    @Override
    public Vessel build(String id) {
      this.id = id;
      return new Vessel(this);
    }
  }
}
