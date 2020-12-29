package kraptis91.maritime.db.dao.utils;

import kraptis91.maritime.db.dao.DaoFactory;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.db.exceptions.DataException;
import kraptis91.maritime.model.Vessel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 27/12/2020.
 */
public class VesselBuffer {

    // key = mmsi, value = vessel
    public Map<Integer, Vessel> vesselMap;
    public VesselDao vesselDao;

    public VesselBuffer() {
        vesselMap = new LinkedHashMap<>();
        vesselDao = DaoFactory.createMongoVesselDao();
    }

    public static VesselBuffer createInstance() {
        return new VesselBuffer();
    }

    public Vessel getIfExistsOrGetFromDB(int mmsi) throws DataException {

        if (vesselMap.containsKey(mmsi)) {
            return vesselMap.get(mmsi);
        } else {
            Optional<Vessel> oVessel = vesselDao.findVesselByMMSI(mmsi);

            if (oVessel.isPresent()) {
                vesselMap.put(mmsi, oVessel.get());
                return oVessel.get();
            } else {
                throw new DataException("Error... cannot find vessel in db with mmsi " + mmsi);
            }
        }
    }

}
