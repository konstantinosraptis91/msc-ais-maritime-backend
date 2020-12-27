package kraptis91.maritime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 21/12/2020. */
public class ReceiverMeasurement {

  /** eta The ETA in format dd-mm hh:mm (day, month, hour, minute) – UTC time */
  private String eta;

  private int toPort;
  @JsonIgnore private Date date;
  private String dateAsString;

  private ReceiverMeasurement(Builder builder) {
    this.eta = builder.eta;
    this.toPort = builder.toPort;
    this.date = builder.date;
  }

  public String getEta() {
    return eta;
  }

  public void setEta(String eta) {
    this.eta = eta;
  }

  public int getToPort() {
    return toPort;
  }

  public void setToPort(int toPort) {
    this.toPort = toPort;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  @JsonProperty("date")
  public String getDateInSimpleDateFormat() {
    if (Objects.isNull(dateAsString)) {
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
      dateAsString = formatter.format(date);
    }
    return dateAsString;
  }

  @Override
  public String toString() {
    return "ReceiverMeasurement{"
        + "eta='"
        + eta
        + '\''
        + ", toPort="
        + toPort
        + ", date="
        + date
        + '}';
  }

  public static Builder builder() {
    return new Builder();
  }

  public interface ReceiverMeasurementETA {
    ReceiverMeasurementToPort withETA(String eta);
  }

  public interface ReceiverMeasurementToPort {
    ReceiverMeasurementDate withToPort(int toPort);
  }

  public interface ReceiverMeasurementDate {
    ReceiverMeasurementBuild withDate(Date date);
  }

  public interface ReceiverMeasurementBuild {
    ReceiverMeasurement build();
  }

  public static class Builder
      implements ReceiverMeasurementETA,
          ReceiverMeasurementToPort,
          ReceiverMeasurementDate,
          ReceiverMeasurementBuild {

    private String eta;
    private int toPort;
    private Date date;

    public Builder() {}

    @Override
    public ReceiverMeasurementToPort withETA(String eta) {
      this.eta = eta;
      return this;
    }

    @Override
    public ReceiverMeasurementDate withToPort(int toPort) {
      this.toPort = toPort;
      return this;
    }

    @Override
    public ReceiverMeasurementBuild withDate(Date date) {
      this.date = date;
      return this;
    }

    @Override
    public ReceiverMeasurement build() {
      return new ReceiverMeasurement(this);
    }
  }
}
