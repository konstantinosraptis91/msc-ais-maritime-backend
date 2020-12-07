package kraptis91.maritime.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class Vessel {

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
  @NotNull private final LocalDateTime eta;

  /** Allowed values: 0.1-25.5 meters */
  @DecimalMin(value = "0.1", message = "Invalid draught value, draught cannot be less than 0.1")
  @DecimalMax(value = "25.5", message = "Invalid draught value, draught cannot be more than 25.5")
  private final double draught;

  /** Type of the ship. */
  @NotNull private final String shipType;

  /** Destination of this trip (manually entered). */
  @NotNull private final String destination;

  /** The country in which the vessel belongs. */
  private final String country;

  /** Trajectory of the vessel. */
  private VesselTrajectory vesselTrajectory;

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

  public LocalDateTime getEta() {
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

  public VesselTrajectory getVesselTrajectory() {
    if (vesselTrajectory == null) {
      vesselTrajectory = new VesselTrajectory();
    }
    return vesselTrajectory;
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
        + ", eta="
        + eta
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
        + ", vesselTrajectory="
        + vesselTrajectory
        + '}';
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Fluent API interfaces
  // -------------------------------------------------------------------------------------------------------------------

  public interface VesselCallSign {
    VesselEta withCallSign(String callSign);
  }

  public interface VesselEta {
    VesselDraught withEta(LocalDateTime eta);
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
    private LocalDateTime eta;
    private double draught;
    private String shipType;
    private String destination;
    private String country;

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
    public VesselDraught withEta(LocalDateTime eta) {
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
  }
}
