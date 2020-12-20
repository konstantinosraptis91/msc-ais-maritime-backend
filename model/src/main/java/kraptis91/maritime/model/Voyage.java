package kraptis91.maritime.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 20/12/2020. */
public class Voyage {

  private String destination;
  private final TreeSet<Long> timestamps;
  private String eta;

  public Voyage() {
    timestamps = new TreeSet<>();
  }

  public Voyage(String eta, String destination) {
    timestamps = new TreeSet<>();
    this.destination = destination;
    this.eta = eta;
  }

  /** @param eta The ETA in format dd-mm hh:mm (day, month, hour, minute) – UTC time */
  public Voyage(String eta) {
    timestamps = new TreeSet<>();
    this.eta = eta;
  }

  private Voyage(Builder builder) {
    timestamps = new TreeSet<>();
    this.eta = builder.eta;
    this.destination = builder.destination;
  }

  public static Voyage createInstance() {
    return new Voyage();
  }

  public static Voyage createInstance(String eta, String destination) {
    return new Voyage(eta, destination);
  }

  public Set<Long> getTimestamps() {
    return timestamps;
  }

  public LocalDateTime getStartDateTime() {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(timestamps.first()), TimeZone.getDefault().toZoneId());
  }

  public LocalDateTime getEndDateTime() {
    return LocalDateTime.ofInstant(
        Instant.ofEpochMilli(timestamps.last()), TimeZone.getDefault().toZoneId());
  }

  public Duration getDuration() {
    return Duration.between(
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamps.last()), TimeZone.getDefault().toZoneId()),
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(timestamps.first()), TimeZone.getDefault().toZoneId()));
  }

  public String getDestination() {
    return destination;
  }

  /**
   * Get the estimate time of arrival.
   *
   * @return The ETA in format dd-mm hh:mm (day, month, hour, minute) – UTC time
   */
  public String getEta() {
    return eta;
  }

  @Override
  public String toString() {
    return "Voyage{"
        + "destination='"
        + destination
        + '\''
        + ", startDateTime="
        + getStartDateTime()
        + ", endDateTime="
        + getEndDateTime()
        + ", duration="
        + getDuration()
        + ", eta="
        + eta
        + '}';
  }

  public interface VoyageETA {
    VoyageDestination withETA(String eta);
  }

  public interface VoyageDestination {
    VoyageBuild withDestination(String destination);
  }

  public interface VoyageBuild {
    Voyage build();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements VoyageETA, VoyageDestination, VoyageBuild {

    private String eta;
    private String destination;

    public Builder() {}

    @Override
    public VoyageDestination withETA(String eta) {
      this.eta = eta;
      return this;
    }

    @Override
    public VoyageBuild withDestination(String destination) {
      this.destination = destination;
      return this;
    }

    @Override
    public Voyage build() {
      return new Voyage(this);
    }
  }
}
