package kraptis91.maritime.model;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 20/12/2020. */
public class Voyage {

  private final String destination;
  private final TreeSet<Long> timestamps;
  /** eta The ETA in format dd-mm hh:mm (day, month, hour, minute) – UTC time */
  private final String eta;

  private Voyage(String destination, String eta) {
    timestamps = new TreeSet<>();
    this.destination = destination;
    this.eta = eta;
  }

  public static Voyage createInstance(String destination, String eta) {
    return new Voyage(destination, eta);
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
  public int hashCode() {
    int hash = 5;
    hash = 29 * hash + Objects.hashCode(this.destination);
    hash = 29 * hash + Objects.hashCode(this.eta);
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

    return Objects.equals(this.destination, other.destination)
        && Objects.equals(this.eta, other.eta);
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
}
