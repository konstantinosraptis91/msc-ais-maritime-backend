package kraptis91.maritime.parser.dto;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class NariStaticDto {

  private int mmsi;
  private int imo;
  private String callSign;
  private String shipName;
  private int shipType;
  private int toBow;
  private int toStern;
  private int toStarboard;
  private int toPort;
  private String eta;
  private double draught;
  private String destination;
  private int motherShipMmsi;
  private long t;

  public int getMmsi() {
    return mmsi;
  }

  public void setMmsi(int mmsi) {
    this.mmsi = mmsi;
  }

  public int getImo() {
    return imo;
  }

  public void setImo(int imo) {
    this.imo = imo;
  }

  public String getCallSign() {
    return callSign;
  }

  public void setCallSign(String callSign) {
    this.callSign = callSign;
  }

  public String getShipName() {
    return shipName;
  }

  public void setShipName(String shipName) {
    this.shipName = shipName;
  }

  public int getShipType() {
    return shipType;
  }

  public void setShipType(int shipType) {
    this.shipType = shipType;
  }

  public int getToBow() {
    return toBow;
  }

  public void setToBow(int toBow) {
    this.toBow = toBow;
  }

  public int getToStern() {
    return toStern;
  }

  public void setToStern(int toStern) {
    this.toStern = toStern;
  }

  public int getToStarboard() {
    return toStarboard;
  }

  public void setToStarboard(int toStarboard) {
    this.toStarboard = toStarboard;
  }

  public int getToPort() {
    return toPort;
  }

  public void setToPort(int toPort) {
    this.toPort = toPort;
  }

  public String getEta() {
    return eta;
  }

  public void setEta(String eta) {
    this.eta = eta;
  }

  public double getDraught() {
    return draught;
  }

  public void setDraught(double draught) {
    this.draught = draught;
  }

  public String getDestination() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination = destination;
  }

  public int getMotherShipMmsi() {
    return motherShipMmsi;
  }

  public void setMotherShipMmsi(int motherShipMmsi) {
    this.motherShipMmsi = motherShipMmsi;
  }

  public long getT() {
    return t;
  }

  public void setT(long t) {
    this.t = t;
  }

  @Override
  public String toString() {
    return "NariStaticDto{"
        + "mmsi="
        + mmsi
        + ", imo="
        + imo
        + ", callSign='"
        + callSign
        + '\''
        + ", shipName='"
        + shipName
        + '\''
        + ", shipType="
        + shipType
        + ", toBow="
        + toBow
        + ", toStern="
        + toStern
        + ", toStarboard="
        + toStarboard
        + ", toPort="
        + toPort
        + ", eta='"
        + eta
        + '\''
        + ", draught="
        + draught
        + ", destination='"
        + destination
        + '\''
        + ", motherShipMmsi="
        + motherShipMmsi
        + ", t="
        + t
        + '}';
  }
}
