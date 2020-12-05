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
  private int mmsi;

  /** IMO ship identification number (7 digits). */
  private int imo;

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

  /** Name of the vessel (max 20 characters). */
  private String vesselName;

  /** Allowed values: 0.1-25.5 meters */
  private double draught;

  private VesselTrajectory vesselTrajectory;

  private int shipType;

  /** Destination of this trip (manually entered). */
  private String destination;

  private double speed;

  private VesselTrajectory trajectory;

  private Vessel(Builder builder) {
    this.imo = builder.imo;
    this.vesselName = builder.vesselName;
    this.draught = builder.draught;
    this.vesselTrajectory = builder.vesselTrajectory;
  }

  public int getImo() {
    return imo;
  }

  public String getVesselName() {
    return vesselName;
  }

  public double getDraught() {
    return draught;
  }

  public void setDraught(double draught) {
    this.draught = draught;
  }

  public VesselTrajectory getVesselTrajectory() {
    if (vesselTrajectory == null) {
      vesselTrajectory = new VesselTrajectory();
    }
    return vesselTrajectory;
  }

  public static class Builder {

    // mandatory fields
    private int imo;
    private String vesselName;

    // non mandatory fields
    private double draught;
    private VesselTrajectory vesselTrajectory;

    public Builder(int imo, String vesselName) {
      this.imo = imo;
      this.vesselName = vesselName;
    }

    public Builder withDraught(double draught) {
      this.draught = draught;
      return Builder.this;
    }

    public Builder withVesselTrajectory(VesselTrajectory vesselTrajectory) {
      this.vesselTrajectory = vesselTrajectory;
      return Builder.this;
    }

    public Vessel build() {
      return new Vessel(Builder.this);
    }
  }
}
