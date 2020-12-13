package kraptis91.maritime.dao;

import kraptis91.maritime.model.Vessel;

import java.io.InputStream;
import java.util.List;
import java.util.Set;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 8/12/2020. */
public interface VesselDao {

  void insertMany(InputStream is, int chunkSize) throws Exception;

  default void insertMany(InputStream is) throws Exception {
    insertMany(is, 3000);
  }

  void insertMany(Set<Vessel> vesselSet);

  void insertMany(List<Vessel> vesselList);
}
