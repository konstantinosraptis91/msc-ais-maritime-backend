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
    this(null);
  }

  /** @param eta The ETA in format dd-mm hh:mm (day, month, hour, minute) – UTC time */
  public Voyage(String eta) {
    timestamps = new TreeSet<>();
    this.eta = eta;
  }

  public static Voyage createInstance() {
    return new Voyage();
  }

  public static Voyage createInstance(String eta) {
    return new Voyage();
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
}
