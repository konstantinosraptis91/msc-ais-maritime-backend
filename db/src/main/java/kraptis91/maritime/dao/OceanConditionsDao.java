package kraptis91.maritime.dao;

import kraptis91.maritime.model.OceanConditions;

import java.io.InputStream;
import java.util.List;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 8/12/2020. */
public interface OceanConditionsDao {

  void insertMany(InputStream is) throws Exception;

  void insertMany(List<OceanConditions> list);
}
