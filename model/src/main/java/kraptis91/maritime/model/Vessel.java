package kraptis91.maritime.model;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class Vessel {

  /**
   * MMSI identifier for vessel.
   * <p>
   * The MMSI number (Maritime Mobile Service Identity) is a unique nine-digit number for
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

  private int shipType;

  /**
   * Destination of this trip (manually entered).
   */
  private String destination;

  private double speed;

  private VesselTrajectory trajectory;
}
