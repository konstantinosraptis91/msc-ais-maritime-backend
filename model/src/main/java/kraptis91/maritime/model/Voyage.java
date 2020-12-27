package kraptis91.maritime.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.TimeZone;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 20/12/2020. */
public class Voyage {

  /** Destination of this trip */
  private final String destination;

  private ReceiverMeasurement firstMeasurement;
  private ReceiverMeasurement lastMeasurement;

  private int numberOfMeasurements;
  private long duration;

  private Voyage(String destination) {
    this.destination = destination;
    this.numberOfMeasurements = 0;
  }

  private Voyage(Builder builder) {
    this.destination = builder.destination;
    this.firstMeasurement = builder.firstMeasurement;
    this.lastMeasurement = builder.lastMeasurement;
    this.numberOfMeasurements = builder.numberOfMeasurements;
  }

  public static Voyage createInstance(String destination) {
    return new Voyage(destination);
  }

  public Duration calcDuration() {
    return Duration.between(
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(firstMeasurement.getDate().getTime()),
            TimeZone.getDefault().toZoneId()),
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(lastMeasurement.getDate().getTime()),
            TimeZone.getDefault().toZoneId()));
  }

  @JsonProperty("durationInMs")
  public long getDuration() {
    if (duration == 0) {
      duration = calcDuration().toMillis();
    }
    return duration;
  }

  public void addMeasurement(ReceiverMeasurement measurement) {

    setFirstMeasurement(measurement);
    setLastMeasurement(measurement);
    numberOfMeasurements++;
  }

  private void setFirstMeasurement(ReceiverMeasurement measurement) {

    if (Objects.isNull(firstMeasurement)) {
      firstMeasurement = measurement;
    } else {

      if (measurement.getDate().getTime() < firstMeasurement.getDate().getTime()) {
        firstMeasurement = measurement;
      }
    }
  }

  private void setLastMeasurement(ReceiverMeasurement measurement) {

    if (Objects.isNull(lastMeasurement)) {
      lastMeasurement = measurement;
    } else {

      if (measurement.getDate().getTime() > lastMeasurement.getDate().getTime()) {
        lastMeasurement = measurement;
      }
    }
  }

  public int getNumberOfMeasurements() {
    return numberOfMeasurements;
  }

  public ReceiverMeasurement getFirstMeasurement() {
    return firstMeasurement;
  }

  public ReceiverMeasurement getLastMeasurement() {
    return lastMeasurement;
  }

  public String getDestination() {
    return destination;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + Objects.hashCode(this.destination);
    // hash = 29 * hash + Objects.hashCode(this.eta);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }
    final Voyage other = (Voyage) obj;

    return Objects.equals(this.destination, other.destination);
    // && Objects.equals(this.eta, other.eta);
  }

  @Override
  public String toString() {
    return "Voyage{"
        + "destination='"
        + destination
        + '\''
        + ", firstMeasurement="
        + firstMeasurement
        + ", lastMeasurement="
        + lastMeasurement
        + ", duration(ms)="
        + calcDuration().toMillis()
        + ", numberOfMeasurements="
        + numberOfMeasurements
        + '}';
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Fluent API interfaces
  // -------------------------------------------------------------------------------------------------------------------

  public interface VoyageDestination {
    VoyageFirstMeasurement withDestination(String destination);
  }

  public interface VoyageFirstMeasurement {
    VoyageLastMeasurement withFirstMeasurement(ReceiverMeasurement measurement);
  }

  public interface VoyageLastMeasurement {
    VoyageNumberOfMeasurements withLastMeasurement(ReceiverMeasurement measurement);
  }

  public interface VoyageNumberOfMeasurements {
    VoyageBuild withNumberOfMeasurements(int number);
  }

  public interface VoyageBuild {
    Voyage build();
  }

  // -------------------------------------------------------------------------------------------------------------------
  // Voyage POJO builder
  // -------------------------------------------------------------------------------------------------------------------

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder
      implements VoyageDestination,
          VoyageFirstMeasurement,
          VoyageLastMeasurement,
          VoyageNumberOfMeasurements,
          VoyageBuild {

    private String destination;
    private ReceiverMeasurement firstMeasurement;
    private ReceiverMeasurement lastMeasurement;
    private int numberOfMeasurements;

    public Builder() {}

    @Override
    public VoyageFirstMeasurement withDestination(String destination) {
      this.destination = destination;
      return this;
    }

    @Override
    public VoyageLastMeasurement withFirstMeasurement(ReceiverMeasurement measurement) {
      this.firstMeasurement = measurement;
      return this;
    }

    @Override
    public VoyageNumberOfMeasurements withLastMeasurement(ReceiverMeasurement measurement) {
      this.lastMeasurement = measurement;
      return this;
    }

    @Override
    public VoyageBuild withNumberOfMeasurements(int number) {
      this.numberOfMeasurements = number;
      return this;
    }

    @Override
    public Voyage build() {
      return new Voyage(this);
    }
  }
}
