package kraptis91.maritime.api.service;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 17/12/2020. */
public class ServiceFactory {

  public static VesselService createVesselService() {
    return new VesselService();
  }
}
