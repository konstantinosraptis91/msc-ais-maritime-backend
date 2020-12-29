package kraptis91.maritime.parser.dto.csv;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class NariDynamicDto {

  private int mmsi;
  private int status;
  private double turn;
  private double speed;
  private double course;
  private int heading;
  private double lon;
  private double lat;
  private long t;

  public int getMMSI() {
    return mmsi;
  }

  public void setMMSI(int mmsi) {
    this.mmsi = mmsi;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public double getTurn() {
    return turn;
  }

  public void setTurn(double turn) {
    this.turn = turn;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public double getCourse() {
    return course;
  }

  public void setCourse(double course) {
    this.course = course;
  }

  public int getHeading() {
    return heading;
  }

  public void setHeading(int heading) {
    this.heading = heading;
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public long getT() {
    return t;
  }

  public void setT(long t) {
    this.t = t;
  }

  @Override
  public String toString() {
    return "NariDynamicDto{"
        + "mmsi="
        + mmsi
        + ", status="
        + status
        + ", turn="
        + turn
        + ", speed="
        + speed
        + ", course="
        + course
        + ", heading="
        + heading
        + ", lon="
        + lon
        + ", lat="
        + lat
        + ", t="
        + t
        + '}';
  }
}
