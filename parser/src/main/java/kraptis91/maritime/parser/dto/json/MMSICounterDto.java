package kraptis91.maritime.parser.dto.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 26/12/2020. */
public class MMSICounterDto {

  private int mmsi;
  private int counter;

  public MMSICounterDto(@JsonProperty("mmsi") int mmsi, @JsonProperty("counter") int counter) {
    this.mmsi = mmsi;
    this.counter = counter;
  }

  public int getMmsi() {
    return mmsi;
  }

  public void setMmsi(int mmsi) {
    this.mmsi = mmsi;
  }

  public int getCounter() {
    return counter;
  }

  public void setCounter(int counter) {
    this.counter = counter;
  }
}
