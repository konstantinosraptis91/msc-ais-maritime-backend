package kraptis91.maritime.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 20/12/2020. */
public class Voyage {

  private final String destination;
  private final TreeSet<Long> timestamps;
  /** @param eta The ETA in format dd-mm hh:mm (day, month, hour, minute) – UTC time */
  private String eta;

  private Voyage(String destination) {
    timestamps = new TreeSet<>();
    this.destination = destination;
  }

  private Voyage(String destination, String eta) {
    timestamps = new TreeSet<>();
    this.destination = destination;
    this.eta = eta;
  }

  private Voyage(Builder builder) {
    timestamps = new TreeSet<>();
    this.eta = builder.eta;
    this.destination = builder.destination;
  }

  public static Voyage createInstance(String destination) {
    return new Voyage(destination);
  }

  public static Voyage createInstance(String destination, String eta) {
    return new Voyage(destination, eta);
  }

  public void setEta(String eta) {
    this.eta = eta;
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

  public static Builder builder(String destination) {
    return new Builder(destination);
  }

  public static class Builder {

    private String eta;
    private final String destination;

    public Builder(String destination) {
      this.destination = destination;
    }

    public Builder withETA(String eta) {
      this.eta = eta;
      return this;
    }

    public Voyage build() {
      return new Voyage(this);
    }
  }
}
