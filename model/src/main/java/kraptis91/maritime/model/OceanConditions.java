package kraptis91.maritime.model;

/** @author Stavros Lamprinos [stalab at linuxmail.org] on 8/12/2020. */
public class OceanConditions {

  /**
   * longitude and latitude in geoJSON format replaced longitude and latitude with geoPoint Stavros
   * Lamprinos on 14/12/2020
   */
  private final GeoPoint geoPoint;

  /** bottom depth of the oceans point in meters (Undefined value = -16384) */
  private final double bottomDepth;

  /** sea surface height above sea level in meters (=>tidal effect). (Undefined value = -327.67) */
  private final double tidalEffect;

  /** significant height of wind and swell waves (=>see state) (Undefined value = -65.534) */
  private final double seaHeight;

  /** mean wave length in meters. (Undefined value= - 32767) */
  private final int meanWaveLength;

  private final long timestamp;

  private OceanConditions(Builder builder) {
    this.geoPoint = builder.location;
    this.bottomDepth = builder.bottomDepth;
    this.tidalEffect = builder.tidalEffect;
    this.seaHeight = builder.seaHeight;
    this.meanWaveLength = builder.meanWaveLength;
    this.timestamp = builder.timestamp;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Getters
  // -------------------------------------------------------------------------------------------------------------------

  public GeoPoint getGeoPoint() {
    return this.geoPoint;
  }

  public double getBottomDepth() {
    return bottomDepth;
  }

  public double getTidalEffect() {
    return tidalEffect;
  }

  public double getSeaHeight() {
    return seaHeight;
  }

  public double getMeanWaveLength() {
    return meanWaveLength;
  }

  public long getTimestamp() {
    return timestamp;
  }

  // -------------------------------------------------------------------------------------------------------------------
  // toString
  // -------------------------------------------------------------------------------------------------------------------

  @Override
  public String toString() {
    return "OceanConditions{"
        + "geoPoint="
        + geoPoint
        + ", bottomDepth="
        + bottomDepth
        + ", tidalEffect="
        + tidalEffect
        + ", seaHeight="
        + seaHeight
        + ", meanWaveLength="
        + meanWaveLength
        + ", timestamp="
        + timestamp
        + '}';
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Fluent API interfaces
  // -------------------------------------------------------------------------------------------------------------------

  public interface OceanBottomDepth {
    OceanTidalEffect withBottomDepth(double bottomDepth);
  }

  public interface OceanTidalEffect {
    OceanSeaHeight withTidalEffect(double tidalEffect);
  }

  public interface OceanSeaHeight {
    OceanMeanWaveLength withSeaHeight(double seaHeight);
  }

  public interface OceanMeanWaveLength {
    OceanBuild withMeanWaveLength(int meanWaveLength);
  }

  public interface OceanBuild {
    OceanConditions build();
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Vessel POJO builder
  // -------------------------------------------------------------------------------------------------------------------

  public static class Builder
      implements OceanBottomDepth,
          OceanTidalEffect,
          OceanSeaHeight,
          OceanMeanWaveLength,
          OceanBuild {

    // mandatory fields
    private final GeoPoint location;
    private final long timestamp;

    // optional fields
    private double bottomDepth;
    private double tidalEffect;
    private double seaHeight;
    private int meanWaveLength;

    public Builder(double longitude, double latitude, long timestamp) {
      this.location = GeoPoint.of(longitude, latitude);
      this.timestamp = timestamp;
    }

    /**
     * @param bottomDepth The depth of the oceans point in meters (Undefined value = -16384)
     * @return The Builder
     */
    @Override
    public OceanTidalEffect withBottomDepth(double bottomDepth) {
      this.bottomDepth = bottomDepth;
      return this;
    }

    /**
     * @param seaHeight Sea surface height above sea level in meters (Undefined value = -65.534)
     * @return The Builder
     */
    @Override
    public OceanMeanWaveLength withSeaHeight(double seaHeight) {
      this.seaHeight = seaHeight;
      return this;
    }

    /**
     * @param meanWaveLength Mean wave length in meters (Undefined value = -32767)
     * @return The Builder
     */
    @Override
    public OceanBuild withMeanWaveLength(int meanWaveLength) {
      this.meanWaveLength = meanWaveLength;
      return this;
    }

    /**
     * @param tidalEffect Significant height of wind and swell waves (Undefined value = -327.67)
     * @return The Builder
     */
    @Override
    public OceanSeaHeight withTidalEffect(double tidalEffect) {
      this.tidalEffect = tidalEffect;
      return this;
    }

    @Override
    public OceanConditions build() {
      return new OceanConditions(this);
    }
  }
}
