package kraptis91.maritime.parser.dto;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class ShipTypeDetailedListDto {

  private int idDetailedType;
  private String detailedType;
  private int idShipType;

  public int getIdDetailedType() {
    return idDetailedType;
  }

  public void setIdDetailedType(int idDetailedType) {
    this.idDetailedType = idDetailedType;
  }

  public String getDetailedType() {
    return detailedType;
  }

  public void setDetailedType(String detailedType) {
    this.detailedType = detailedType;
  }

  public int getIdShipType() {
    return idShipType;
  }

  public void setIdShipType(int idShipType) {
    this.idShipType = idShipType;
  }

  @Override
  public String toString() {
    return "ShipTypeDetailedListDto{"
        + "idDetailedType="
        + idDetailedType
        + ", detailedType='"
        + detailedType
        + '\''
        + ", idShipType="
        + idShipType
        + '}';
  }
}
