package kraptis91.maritime.model;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class Vessel {

  /** IMO ship identification number (7 digits). */
  private int imo;

  /** Name of the vessel (max 20 characters). */
  private String vesselName;

  /** Allowed values: 0.1-25.5 meters */
  private double draught;

  private VesselTrajectory vesselTrajectory;

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
