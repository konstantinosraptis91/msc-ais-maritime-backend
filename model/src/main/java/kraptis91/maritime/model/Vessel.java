package kraptis91.maritime.model;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class Vessel {

  /**
   * MMSI identifier for vessel.
   *
   * <p>The MMSI number (Maritime Mobile Service Identity) is a unique nine-digit number for
   * identifying a ship. It is programmed into all AIS systems and VHF electronics on board of the
   * vessel and provides an internationally standardized number for contacting the vessel.
   */
  private final int mmsi;

  /** IMO ship identification number (7 digits). */
  private final int imo;

  /** Name of the vessel (max 20 characters). */
  private final String vesselName;

  /**
   * International radio call sign (max 7 characters), assigned to the vessel by its country of
   * registry.
   */
  private String callSign;

  /**
   * ETA (estimated time of arrival) in format dd-mm hh:mm (day, month, hour, minute) – UTC time
   * zone.
   */
  private String eta;

  /** Allowed values: 0.1-25.5 meters */
  private double draught;

  private int shipType;

  /** Destination of this trip (manually entered). */
  private String destination;

  private double speed;

  private VesselTrajectory vesselTrajectory;

  public Vessel(int mmsi, int imo, String vesselName) {
    this.mmsi = mmsi;
    this.imo = imo;
    this.vesselName = vesselName;
  }

  public void setCallSign(String callSign) {
    this.callSign = callSign;
  }

  public void setEta(String eta) {
    this.eta = eta;
  }

  public void setDraught(double draught) {
    this.draught = draught;
  }

  public void setShipType(int shipType) {
    this.shipType = shipType;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

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

  public double getSpeed() {
    return speed;
  }

  public VesselTrajectory getVesselTrajectory() {
    if (vesselTrajectory == null) {
      vesselTrajectory = new VesselTrajectory();
    }
    return vesselTrajectory;
  }

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
        + ", speed="
        + speed
        + ", vesselTrajectory="
        + getVesselTrajectory()
        + '}';
  }
}
