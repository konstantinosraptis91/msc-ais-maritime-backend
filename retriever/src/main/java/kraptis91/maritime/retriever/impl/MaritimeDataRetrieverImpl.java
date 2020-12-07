package kraptis91.maritime.retriever.impl;

import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectory;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.model.ModelFactory;
import kraptis91.maritime.retriever.MaritimeDataRetriever;
import kraptis91.maritime.retriever.MaritimeDemoDataRetriever;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 6/12/2020. */
public class MaritimeDataRetrieverImpl implements MaritimeDataRetriever, MaritimeDemoDataRetriever {
  @Override
  public Map<String, VesselTrajectoryPoint> getVesselPositionMap(BigInteger timestamp) {
    return null;
  }

  @Override
  public VesselTrajectoryPoint getVesselPosition(String vesselName, BigInteger timestamp) {
    return null;
  }

  @Override
  public String getVesselDestination(String vesselName) {
    return null;
  }

  @Override
  public VesselTrajectory getVesselTrajectory(String vesselName) {
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
  public List<Vessel> getVesselsByType(String shipType) {

    // Maybe a demo DAO or DTO could be used here, in order to add db module in the game

    List<Vessel> vesselList = new ArrayList<>();
    vesselList.add(ModelFactory.createDemoVessel());
    vesselList.add(ModelFactory.createDemoVessel());
    vesselList.add(ModelFactory.createDemoVessel());
    vesselList.add(ModelFactory.createDemoVessel());

    return vesselList.stream()
        .filter(vl -> vl.getShipType().equals(shipType))
        .collect(Collectors.toList());
  }
}
