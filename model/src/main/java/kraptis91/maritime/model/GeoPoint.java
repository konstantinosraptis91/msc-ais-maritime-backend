package kraptis91.maritime.model;

import com.google.common.collect.ImmutableList;
import kraptis91.maritime.model.enums.GeoJsonType;
import org.bson.codecs.pojo.annotations.BsonIgnore;

import java.util.List;
import java.util.Objects;

/** @author Stavros Lamprinos [stalab at linuxmail.org] on 14/12/2020. */
public class GeoPoint {

  // longitude and latitude coordinates
  @BsonIgnore private final double longitude;
  @BsonIgnore private final double latitude;
  // the GeoJson type
  private final String type;
  // coordinates as an array of [lon, lat]
  private final List<Double> coordinates;

  private GeoPoint(double longitude, double latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.type = GeoJsonType.POINT.getValue();
    coordinates = ImmutableList.of(longitude, latitude);
  }

  /**
   * Create a new geospatial point.
   *
   * @param longitude The longitude
   * @param latitude The latitude
   */
  public static GeoPoint of(double longitude, double latitude) {
    return new GeoPoint(longitude, latitude);
  }

  public double getLongitude() {
    return longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public String getType() {
    return type;
  }

  public List<Double> getCoordinates() {
    return coordinates;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + Objects.hashCode(this.longitude);
    hash = 29 * hash + Objects.hashCode(this.latitude);
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
    final GeoPoint other = (GeoPoint) obj;

    return Objects.equals(this.longitude, other.longitude)
        && Objects.equals(this.latitude, other.latitude);
  }

  @Override
  public String toString() {
    return "GeoPoint{" + "longitude=" + longitude + ", latitude=" + latitude + '}';
  }
}
