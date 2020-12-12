package kraptis91.maritime.dao;

import kraptis91.maritime.model.OceanConditions;
import kraptis91.maritime.model.Vessel;

import java.io.InputStream;
import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 8/12/2020.
 */
public interface VesselDao {

    void insertMany(InputStream is) throws Exception;

    void insertMany(List<Vessel> list);

}
