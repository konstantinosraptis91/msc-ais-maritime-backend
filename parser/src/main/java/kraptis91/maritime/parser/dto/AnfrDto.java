package kraptis91.maritime.parser.dto;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class AnfrDto {

  private String maritimeArea;
  private String registrationNumber;
  private String imoNumber;
  private String shipName;
  private String callSign;
  private int mmsi;
  private String shipType;
  private double length;
  private double tonnage;
  private String tonnageUnit;
  private String materialOnboard;
  private String atisCode;
  private String radioLicenseStatus;
  private String dateFirstLicense;
  private String dateInactivityLicense;

  public String getMaritimeArea() {
    return maritimeArea;
  }

  public void setMaritimeArea(String maritimeArea) {
    this.maritimeArea = maritimeArea;
  }

  public String getRegistrationNumber() {
    return registrationNumber;
  }

  public void setRegistrationNumber(String registrationNumber) {
    this.registrationNumber = registrationNumber;
  }

  public String getImoNumber() {
    return imoNumber;
  }

  public void setImoNumber(String imoNumber) {
    this.imoNumber = imoNumber;
  }

  public String getShipName() {
    return shipName;
  }

  public void setShipName(String shipName) {
    this.shipName = shipName;
  }

  public String getCallSign() {
    return callSign;
  }

  public void setCallSign(String callSign) {
    this.callSign = callSign;
  }

  public int getMmsi() {
    return mmsi;
  }

  public void setMmsi(int mmsi) {
    this.mmsi = mmsi;
  }

  public String getShipType() {
    return shipType;
  }

  public void setShipType(String shipType) {
    this.shipType = shipType;
  }

  public double getLength() {
    return length;
  }

  public void setLength(double length) {
    this.length = length;
  }

  public double getTonnage() {
    return tonnage;
  }

  public void setTonnage(double tonnage) {
    this.tonnage = tonnage;
  }

  public String getTonnageUnit() {
    return tonnageUnit;
  }

  public void setTonnageUnit(String tonnageUnit) {
    this.tonnageUnit = tonnageUnit;
  }

  public String getMaterialOnboard() {
    return materialOnboard;
  }

  public void setMaterialOnboard(String materialOnboard) {
    this.materialOnboard = materialOnboard;
  }

  public String getAtisCode() {
    return atisCode;
  }

  public void setAtisCode(String atisCode) {
    this.atisCode = atisCode;
  }

  public String getRadioLicenseStatus() {
    return radioLicenseStatus;
  }

  public void setRadioLicenseStatus(String radioLicenseStatus) {
    this.radioLicenseStatus = radioLicenseStatus;
  }

  public String getDateFirstLicense() {
    return dateFirstLicense;
  }

  public void setDateFirstLicense(String dateFirstLicense) {
    this.dateFirstLicense = dateFirstLicense;
  }

  public String getDateInactivityLicense() {
    return dateInactivityLicense;
  }

  public void setDateInactivityLicense(String dateInactivityLicense) {
    this.dateInactivityLicense = dateInactivityLicense;
  }

  @Override
  public String toString() {
    return "AnfrDto{"
        + "maritimeArea='"
        + maritimeArea
        + '\''
        + ", registrationNumber='"
        + registrationNumber
        + '\''
        + ", imoNumber='"
        + imoNumber
        + '\''
        + ", shipName='"
        + shipName
        + '\''
        + ", callSign='"
        + callSign
        + '\''
        + ", mmsi="
        + mmsi
        + ", shipType='"
        + shipType
        + '\''
        + ", length="
        + length
        + ", tonnage="
        + tonnage
        + ", tonnageUnit='"
        + tonnageUnit
        + '\''
        + ", materialOnboard='"
        + materialOnboard
        + '\''
        + ", atisCode='"
        + atisCode
        + '\''
        + ", radioLicenseStatus='"
        + radioLicenseStatus
        + '\''
        + ", dateFirstLicense='"
        + dateFirstLicense
        + '\''
        + ", dateInactivityLicense='"
        + dateInactivityLicense
        + '\''
        + '}';
  }
}
