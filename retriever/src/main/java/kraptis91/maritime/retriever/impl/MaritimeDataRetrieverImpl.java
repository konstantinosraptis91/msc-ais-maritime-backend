package kraptis91.maritime.retriever.impl;

import kraptis91.maritime.db.dao.DaoFactory;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.db.dao.VesselTrajectoryPointDao;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.retriever.MaritimeDataRetriever;

import java.util.List;
import java.util.Optional;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 6/12/2020. */
public class MaritimeDataRetrieverImpl implements MaritimeDataRetriever {

  @Override
  public List<VesselTrajectoryPoint> getVesselTrajectory(int mmsi, int skip, int limit) {
    VesselTrajectoryPointDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    return dao.findVesselTrajectory(mmsi, skip, limit);
  }

  @Override
  public List<VesselTrajectoryPoint> getVesselTrajectory(String vesselName, int skip, int limit) {
    VesselTrajectoryPointDao dao = DaoFactory.createMongoVesselTrajectoryPointDao();
    return dao.findVesselTrajectory(vesselName, skip, limit);
  }

  @Override
  public List<Vessel> getVesselsByDestination(String destination, int skip, int limit) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselsByDestination(destination, skip, limit);
  }

  @Override
  public Optional<String> getVesselDestination(int mmsi) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselDestination(mmsi);
  }

  @Override
  public Optional<String> getVesselDestination(String vesselName) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselDestination(vesselName);
  }

  @Override
  public List<Vessel> getVesselsByType(String shipType, int skip, int limit) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselsByType(shipType, skip, limit);
  }

  @Override
  public Optional<Vessel> getVesselByMMSI(int mmsi) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselByMMSI(mmsi);
  }

  @Override
  public Optional<Vessel> getVesselByName(String vesselName) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselByName(vesselName);
  }
}
