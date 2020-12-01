package kraptis91.maritime.model;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class Vessel {

  /** IMO ship identification number (7 digits). */
  private int imo;

  /** Name of the vessel (max 20 characters). */
  private String vesselName;

  /** Allowed values: 0.1-25.5 meters */
  private double draught;
}
