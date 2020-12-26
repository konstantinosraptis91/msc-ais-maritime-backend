package kraptis91.maritime.parser.dto.csv;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class ShipTypeListDto {

  private int idShipType;
  private int shipTypeMin;
  private int shipTypeMax;
  private String typeName;
  private String aisTypeSummary;

  public int getIdShipType() {
    return idShipType;
  }

  public void setIdShipType(int idShipType) {
    this.idShipType = idShipType;
  }

  public int getShipTypeMin() {
    return shipTypeMin;
  }

  public void setShipTypeMin(int shipTypeMin) {
    this.shipTypeMin = shipTypeMin;
  }

  public int getShipTypeMax() {
    return shipTypeMax;
  }

  public void setShipTypeMax(int shipTypeMax) {
    this.shipTypeMax = shipTypeMax;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getAisTypeSummary() {
    return aisTypeSummary;
  }

  public void setAisTypeSummary(String aisTypeSummary) {
    this.aisTypeSummary = aisTypeSummary;
  }

  @Override
  public String toString() {
    return "ShipTypeListDto{"
        + "idShipType="
        + idShipType
        + ", shipTypeMin="
        + shipTypeMin
        + ", shipTypeMax="
        + shipTypeMax
        + ", typeName='"
        + typeName
        + '\''
        + ", aisTypeSummary='"
        + aisTypeSummary
        + '\''
        + '}';
  }
}
