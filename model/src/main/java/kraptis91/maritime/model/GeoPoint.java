package kraptis91.maritime.model;

import java.util.ArrayList;

/** @author Stavros Lamprinos [stalab at linuxmail.org] on 14/12/2020. */
public class GeoPoint {

  /* geospatial data in geoJSON format */
  private final String type = "Point";

  /* longitude and latitude coordinates */
  private final ArrayList<Double> coordinates = new ArrayList<>();

  public GeoPoint(double longitude, double latitude) {
    coordinates.add(longitude);
    coordinates.add(latitude);
//    this.coordinates[0] = longitude;
//    this.coordinates[1] = latitude;
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

  public String getType() { return this.type; }

  public ArrayList<Double> getCoordinates() {return this.coordinates; }


//  @Override
//  public int hashCode() {
//    int hash = 5;
//    hash = Objects.hashCode(this.coordinates);
////    hash = 29 * hash + Objects.hashCode(this.coordinates[1]);
//    return hash;
//  }

  @Override
  public String toString() {
    return "geoPont{"
        + "type = "
        + this.type
        + ", coordinates = ["
        + this.coordinates.get(0)
        + ", "
        + this.coordinates.get(1)
        + "]}";
  }
}
