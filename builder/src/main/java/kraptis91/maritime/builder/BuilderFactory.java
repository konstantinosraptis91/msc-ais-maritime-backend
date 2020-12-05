package kraptis91.maritime.builder;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 5/12/2020. */
public class BuilderFactory {

  public static VesselBuilder createVesselBuilder(int mmsi, int imo, String vesselName) {
    return new VesselBuilder(mmsi, imo, vesselName);
  }

}
