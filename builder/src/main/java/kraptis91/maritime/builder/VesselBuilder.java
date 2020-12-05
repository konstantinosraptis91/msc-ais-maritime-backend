package kraptis91.maritime.builder;

import jakarta.validation.constraints.*;
import kraptis91.maritime.model.Vessel;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 5/12/2020. */
public class VesselBuilder {

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
  private double speed;

  public VesselBuilder(
      @Size(
              min = 9,
              max = 9,
              message =
                  "Invalid maritime mobile service identity. The MMSI should be a 9 digit number.")
          int mmsi,
      @Size(
              min = 7,
              max = 7,
              message = "Invalid ship identification number. The IMO should be a 7 digit number.")
          int imo,
      @NotNull String vesselName) {
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
  public VesselBuilder withCallSign(String callSign) {
    this.callSign = callSign;
    return this;
  }

  /**
   * Set the estimate time of arrival.
   *
   * @param eta The ETA in format dd-mm hh:mm (day, month, hour, minute) – UTC time
   * @return The Builder
   */
  public VesselBuilder withETA(@NotNull String eta) {
    this.eta = eta;
    return this;
  }

  /**
   * Set the ship type code.
   *
   * @param shipType The ship type code
   * @return The Builder
   */
  public VesselBuilder withShipType(int shipType) {
    this.shipType = shipType;
    return this;
  }

  /**
   * Set the destination.
   *
   * @param destination The destination of the trip
   * @return The Builder
   */
  public VesselBuilder withDestination(@NotNull String destination) {
    this.destination = destination;
    return this;
  }

  /**
   * Set the speed of the vessel.
   *
   * @param speed The speed over ground in knots (Min = 0.0, Max = 102.2)
   * @return The Builder
   */
  public VesselBuilder withSpeed(
      @DecimalMin(
              value = "0.0",
              message = "Invalid speed value, speed cannot be less than 0.0 knots")
          @DecimalMax(
              value = "102.2",
              message = "Invalid speed value, speed cannot be more than 102.2 knots")
          double speed) {
    this.speed = speed;
    return this;
  }

  /**
   * Set the vessel draught.
   *
   * @param draught The vessel draught (Min = 0.1, Max = 25.5)
   * @return The Builder
   */
  public VesselBuilder withDraught(
      @DecimalMin(value = "0.1", message = "Invalid draught value, draught cannot be less than 0.1")
          @DecimalMax(
              value = "25.5",
              message = "Invalid draught value, draught cannot be more than 25.5")
          double draught) {
    this.draught = draught;
    return this;
  }

  public Vessel build() {
    Vessel vessel = new Vessel(mmsi, imo, vesselName);
    vessel.setCallSign(callSign);
    vessel.setEta(eta);
    vessel.setDraught(draught);
    vessel.setDestination(destination);
    vessel.setShipType(shipType);
    vessel.setSpeed(speed);
    return vessel;
  }
}
