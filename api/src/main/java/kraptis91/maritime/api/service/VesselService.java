package kraptis91.maritime.api.service;

import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.retriever.MaritimeDataRetriever;
import kraptis91.maritime.retriever.RetrieverFactory;

import java.util.List;
import java.util.Optional;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class VesselService {

  public List<VesselTrajectoryPoint> getVesselTrajectory(int mmsi, int skip, int limit) {
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    return dataRetriever.getVesselTrajectory(mmsi, skip, limit);
  }

  public List<VesselTrajectoryPoint> getVesselTrajectory(String vesselName, int skip, int limit) {
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    return dataRetriever.getVesselTrajectory(vesselName, skip, limit);
  }

  public List<Vessel> getVesselsByDestination(String destination, int skip, int limit) {
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    return dataRetriever.getVesselsByDestination(destination, skip, limit);
  }

  public Optional<String> getVesselDestination(int mmsi) {
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    return dataRetriever.getVesselDestination(mmsi);
  }

  public Optional<String> getVesselDestination(String vesselName) {
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    return dataRetriever.getVesselDestination(vesselName);
  }

  public List<Vessel> getVesselsByShipType(String shipType, int skip, int limit) {
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    return dataRetriever.getVesselsByType(shipType, skip, limit);
  }

  public Optional<Vessel> getVesselByMMSI(int mmsi) {
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    return dataRetriever.getVesselByMMSI(mmsi);
  }

  public Optional<Vessel> getVesselByName(String vesselName) {
    MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
    return dataRetriever.getVesselByName(vesselName);
  }
}
