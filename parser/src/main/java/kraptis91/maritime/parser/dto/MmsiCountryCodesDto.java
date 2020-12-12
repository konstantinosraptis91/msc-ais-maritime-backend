package kraptis91.maritime.parser.dto;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class MmsiCountryCodesDto {

  /** first 3 digits of ship's identifier (MMSI) */
  private int mmsiCountryCode;
  private String country;

  public int getMmsiCountryCode() {
    return mmsiCountryCode;
  }

  public void setMmsiCountryCode(int mmsiCountryCode) {
    this.mmsiCountryCode = mmsiCountryCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public String toString() {
    return "MmsiCountryCodesDto{"
        + "mmsiCountryCode="
        + mmsiCountryCode
        + ", country='"
        + country
        + '\''
        + '}';
  }
}
