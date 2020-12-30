package kraptis91.maritime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.validator.constraints.Range;

import javax.annotation.Nullable;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class Vessel {

  /** The id given by mongo db. */
  private final String id;

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
  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
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

  /** Allowed values: 0.1-25.5 meters */
  @DecimalMin(value = "0.1", message = "Invalid draught value, draught cannot be less than 0.1")
  @DecimalMax(value = "25.5", message = "Invalid draught value, draught cannot be more than 25.5")
  private final double draught;

  /** Type of the ship. */
  @Nullable private final String shipType;

  /** The country in which the vessel belongs. */
  private final String country;

  @JsonIgnore private Map<Integer, Voyage> voyageMap;

  private Vessel(Builder builder) {
    this.mmsi = builder.mmsi;
    this.imo = builder.imo;
    this.vesselName = builder.vesselName;
    this.callSign = builder.callSign;
    this.draught = builder.draught;
    this.shipType = builder.shipType;
    this.country = builder.country;
    this.id = builder.id;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Getters
  // -------------------------------------------------------------------------------------------------------------------

  public List<Voyage> getVoyages() {
    // return ImmutableMap.copyOf(voyageMap).values().asList();
    return new ArrayList<>(voyageMap.values());
  }

  public int getMMSI() {
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

  public double getDraught() {
    return draught;
  }

  @Nullable
  public String getShipType() {
    return shipType;
  }

  public String getCountry() {
    return country;
  }

  public String getId() {
    return id;
  }

  /**
   * Add the voyage if not present in voyage map.
   *
   * @param voyage The voyage
   */
  public void addVoyage(Voyage voyage) {

    // check if voyage already in map
    if (!getVoyageMap().containsKey(voyage.hashCode())) { // add only if not exists
      getVoyageMap().put(voyage.hashCode(), voyage);
    }
  }

  /**
   * Add the voyage if not present in voyage map and apply timestamp.
   *
   * @param voyage The voyage
   * @param measurement The receiver measurement
   */
  public void addVoyageAndApplyMeasurement(Voyage voyage, ReceiverMeasurement measurement) {
    addVoyage(voyage); // add only if not exists
    getVoyageMap().get(voyage.hashCode()).addMeasurement(measurement);
  }

  public Map<Integer, Voyage> getVoyageMap() {
    if (Objects.isNull(voyageMap)) {
      voyageMap = new LinkedHashMap<>();
    }
    return voyageMap;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // toString
  // -------------------------------------------------------------------------------------------------------------------

  @Override
  public String toString() {
    return "Vessel{"
        + "id='"
        + id
        + '\''
        + ", mmsi="
        + mmsi
        + ", imo="
        + imo
        + ", vesselName='"
        + vesselName
        + '\''
        + ", callSign='"
        + callSign
        + '\''
        + ", draught="
        + draught
        + ", shipType='"
        + shipType
        + '\''
        + ", country='"
        + country
        + '\''
        + ", voyageMap="
        + voyageMap
        + '}';
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Fluent API interfaces
  // -------------------------------------------------------------------------------------------------------------------

  public interface VesselIMO {
    VesselName withIMO(int imo);
  }

  public interface VesselName {
    VesselCallSign withVesselName(String vesselName);
  }

  public interface VesselCallSign {
    VesselDraught withCallSign(String callSign);
  }

  public interface VesselDraught {
    VesselShipType withDraught(double draught);
  }

  public interface VesselShipType {
    VesselCountry withShipType(String shipType);
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

  public static Builder fluentBuilder(int mmsi) {
    return new Builder(mmsi);
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Vessel POJO builder
  // -------------------------------------------------------------------------------------------------------------------

  public static class Builder
      implements VesselIMO,
          VesselName,
          VesselCallSign,
          VesselDraught,
          VesselShipType,
          VesselCountry,
          VesselBuild {

    // mandatory fields
    private final int mmsi;

    // optional fields
    private int imo;
    private String vesselName;
    private String callSign;
    private double draught;
    private String shipType;
    private String country;
    private String id;

    public Builder(int mmsi) {
      this.mmsi = mmsi;
    }

    /**
     * Set the IMO ship identification number (7 digits).
     *
     * @param imo The IMO
     * @return The next in chain
     */
    @Override
    public VesselName withIMO(int imo) {
      this.imo = imo;
      return this;
    }

    /**
     * Set the vessel name.
     *
     * @param vesselName The name
     * @return The next in chain
     */
    @Override
    public VesselCallSign withVesselName(String vesselName) {
      this.vesselName = vesselName;
      return this;
    }

    /**
     * Set the International radio call sign, assigned to the vessel by its country of registry.
     *
     * @param callSign The International radio call sign (max 7 characters)
     * @return The Builder
     */
    @Override
    public VesselDraught withCallSign(String callSign) {
      this.callSign = callSign;
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
    public VesselCountry withShipType(String shipType) {
      this.shipType = shipType;
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
