package kraptis91.maritime.db.dao;

import kraptis91.maritime.model.Vessel;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 8/12/2020. */
public interface VesselDao {

  void insertMany(InputStream csvStream, int chunkSize) throws Exception;

  default void insertMany(InputStream is) throws Exception {
    insertMany(is, 3000);
  }

  void insertMany(Set<Vessel> vesselSet);

  void insertMany(List<Vessel> vesselList);

  List<Vessel> findVesselsByType(String shipType, int skip, int limit);

  default List<Vessel> findVesselsByType(String shipType) {
    return findVesselsByType(shipType, 0, 30);
  }

  default List<Vessel> findVesselsByDestination(String destination) {
    return findVesselsByDestination(destination, 0, 30);
  }

  List<Vessel> findVesselsByDestination(String destination, int skip, int limit);

  Optional<String> findObjectIdAsString(int mmsi);

  Optional<Vessel> findVesselByMMSI(int mmsi);

  Optional<Vessel> findVesselByName(String vesselName);

  Optional<String> findVesselDestination(String vesselName);

  Optional<String> findVesselDestination(int mmsi);
}
