package kraptis91.maritime.builder.enums;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 5/12/2020. */
public enum VesselDemoCodeListEnum {
  DEFAULT_MMSI(999999),
  DEFAULT_IMO(999998),
  DEFAULT_SHIP_TYPE(999997);

  private int value;

  VesselDemoCodeListEnum(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
