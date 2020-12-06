package kraptis91.maritime.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

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
      min = 100000000,
      max = 999999999,
      message = "Invalid maritime mobile service identity. The MMSI should be a 9 digit number.")
  private final int mmsi;

  /** IMO ship identification number (7 digits). */
  @Range(
      min = 1000000,
      max = 9999999,
      message = "Invalid ship identification number. The IMO should be a 7 digit number.")
  private final int imo;

  /** Name of the vessel (max 20 characters). */
  @NotNull private final String vesselName;

  /**
   * International radio call sign (max 7 characters), assigned to the vessel by its country of
   * registry.
   */
  @NotNull private final String callSign;

  /**
   * ETA (estimated time of arrival) in format dd-mm hh:mm (day, month, hour, minute) – UTC time
   * zone.
   */
  @NotNull private final String eta;

  /** Allowed values: 0.1-25.5 meters */
  @DecimalMin(value = "0.1", message = "Invalid draught value, draught cannot be less than 0.1")
  @DecimalMax(value = "25.5", message = "Invalid draught value, draught cannot be more than 25.5")
  private final double draught;

  private final int shipType;

  /** Destination of this trip (manually entered). */
  @NotNull private final String destination;

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

  public int getShipType() {
    return shipType;
  }

  public String getDestination() {
    return destination;
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
        + ", eta='"
        + eta
        + '\''
        + ", draught="
        + draught
        + ", shipType="
        + shipType
        + ", destination='"
        + destination
        + '\''
        + ", vesselTrajectory="
        + getVesselTrajectory()
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
    VesselDestination withShipType(int shipType);
  }

  public interface VesselDestination {
    VesselBuild withDestination(String destination);
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
          VesselBuild {

    // mandatory fields
    private final int mmsi;
    private final int imo;
    private final String vesselName;

    // optional fields
    private String callSign;
    private String eta;
    private double draught;
    private int shipType;
    private String destination;

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
    public VesselDestination withShipType(int shipType) {
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
    public VesselBuild withDestination(String destination) {
      this.destination = destination;
      return this;
    }

    @Override
    public Vessel build() {
      return new Vessel(this);
    }
  }
}
