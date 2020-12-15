package kraptis91.maritime.retriever.impl;

import kraptis91.maritime.dao.DaoFactory;
import kraptis91.maritime.dao.VesselDao;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.retriever.MaritimeDataRetriever;

import java.util.List;
import java.util.Map;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 6/12/2020. */
public class MaritimeDataRetrieverImpl implements MaritimeDataRetriever {
  @Override
  public Map<String, VesselTrajectoryPoint> getVesselPositionMap(long timestamp) {
    return null;
  }

  @Override
  public VesselTrajectoryPoint getVesselPosition(String vesselName, long timestamp) {
    return null;
  }

  @Override
  public String getVesselDestination(String vesselName) {
    return null;
  }

  @Override
  public List<VesselTrajectoryPoint> getVesselTrajectory(String vesselName) {
    return null;
  }

  @Override
  public List<Vessel> getVesselsByTrajectoryPoint(double longitude, double latitude) {
    return null;
  }

  @Override
  public List<Vessel> getVesselsBySpeed(double speed) {
    return null;
  }

  @Override
  public List<Vessel> getVesselsByDraught(double min, double max) {
    return null;
  }

  @Override
  public List<Vessel> getVesselsByType(int shipType) {
    return null;
  }

  @Override
  public List<Vessel> getVesselsByType(String shipType, int skip, int limit) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselsByType(shipType, skip, limit);
  }

  @Override
  public Vessel getVesselByMMSI(int mmsi) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselByMMSI(mmsi);
  }

  @Override
  public Vessel getVesselByName(String vesselName) {
    return null;
  }
}
