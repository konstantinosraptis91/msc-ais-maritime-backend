package kraptis91.maritime.parser.dto;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 11/12/2020. */
public class SeaStateForecastDto {

  private double lon;
  private double lat;
  private double dpt;
  private double wlv;
  private double hs;
  private int lm;
  private double dir;
  private long ts;

  public SeaStateForecastDto() {}

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

  public double getDpt() {
    return dpt;
  }

  public void setDpt(double dpt) {
    this.dpt = dpt;
  }

  public double getWlv() {
    return wlv;
  }

  public void setWlv(double wlv) {
    this.wlv = wlv;
  }

  public double getHs() {
    return hs;
  }

  public void setHs(double hs) {
    this.hs = hs;
  }

  public int getLm() {
    return lm;
  }

  public void setLm(int lm) {
    this.lm = lm;
  }

  public double getDir() {
    return dir;
  }

  public void setDir(double dir) {
    this.dir = dir;
  }

  public long getTs() {
    return ts;
  }

  public void setTs(long ts) {
    this.ts = ts;
  }

  @Override
  public String toString() {
    return "SeaStateForecastDto{"
        + "lon="
        + lon
        + ", lat="
        + lat
        + ", dpt="
        + dpt
        + ", wlv="
        + wlv
        + ", hs="
        + hs
        + ", lm="
        + lm
        + ", dir="
        + dir
        + ", ts="
        + ts
        + '}';
  }
}
