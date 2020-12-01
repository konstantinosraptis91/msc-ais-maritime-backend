package kraptis91.maritime.model;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class Vessel {

  /**
   * MMSI identifier for vessel.
   * <p>
   * A Maritime Mobile Service Identity (MMSI) is a series of nine digits which are sent in digital
   * form over a radio frequency channel in order to uniquely identify ship stations, ship earth
   * stations, coast stations, coast earth stations, and group calls. These identities are formed in
   * such a way that the identity or part thereof can be used by telephone and telex subscribers
   * connected to the general telecommunications network to call ships automatically.
   */
  private int mmsi;

  /** IMO ship identification number (7 digits). */
  private int imo;

  /** Name of the vessel (max 20 characters). */
  private String shipName;



}
