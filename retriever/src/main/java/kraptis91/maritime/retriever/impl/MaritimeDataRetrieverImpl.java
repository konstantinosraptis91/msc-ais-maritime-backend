package kraptis91.maritime.retriever.impl;

import kraptis91.maritime.db.dao.DaoFactory;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.retriever.MaritimeDataRetriever;
import kraptis91.maritime.retriever.exception.RetrieverException;

import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 6/12/2020. */
public class MaritimeDataRetrieverImpl implements MaritimeDataRetriever {

  @Override
  public List<VesselTrajectoryPoint> getVesselTrajectory(int mmsi) {
    return null;
  }

  @Override
  public List<VesselTrajectoryPoint> getVesselTrajectory(String vesselName) {

    return null;
  }

  @Override
  public String getVesselDestination(int mmsi) {
    return null;
  }

  @Override
  public String getVesselDestination(String vesselName) throws RetrieverException {
    Vessel vessel = getVesselByName(vesselName);
    return vessel.getDestination();
  }

  @Override
  public List<Vessel> getVesselsByType(String shipType) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselsByType(shipType);
  }

  @Override
  public List<Vessel> getVesselsByType(String shipType, int skip, int limit) {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselsByType(shipType, skip, limit);
  }

  @Override
  public Vessel getVesselByMMSI(int mmsi) throws RetrieverException {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselByMMSI(mmsi).orElseThrow(RetrieverException::new);
  }

  @Override
  public Vessel getVesselByName(String vesselName) throws RetrieverException {
    VesselDao dao = DaoFactory.createMongoVesselDao();
    return dao.findVesselByName(vesselName).orElseThrow(RetrieverException::new);
  }
}
