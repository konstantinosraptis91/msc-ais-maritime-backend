package kraptis91.maritime.db.dao;

import kraptis91.maritime.model.PlainVessel;
import kraptis91.maritime.model.Vessel;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 8/12/2020.
 */
public interface VesselDao {

    void insertMany(InputStream csvStream, int chunkSize) throws Exception;

    default void insertMany(InputStream is) throws Exception {
        insertMany(is, 3000);
    }

    void insertMany(Set<Vessel> vesselSet);

    void insertMany(List<Vessel> vesselList);

    default List<Vessel> findVessels() {
        return findVessels(0, 30);
    }

    List<Vessel> findVessels(int skip, int limit);

    default List<Vessel> findVesselsByType(String shipType) {
        return findVesselsByType(shipType, 0, 30);
    }

    List<Vessel> findVesselsByType(String shipType, int skip, int limit);

    List<PlainVessel> findPlainVesselsByType(String shipType, int skip, int limit);

    List<PlainVessel> findPlainVesselByCountryCode(String countryCode, int skip, int limit);

    List<PlainVessel> findPlainVessels(String shipType, String countryCode, int skip, int limit);

//    default List<Vessel> findVesselsByDestination(String destination) {
//        return findVesselsByDestination(destination, 0, 30);
//    }

//    List<Vessel> findVesselsByDestination(String destination, int skip, int limit);

    Optional<String> findObjectIdAsString(int mmsi);

    Optional<Vessel> findVesselByMMSI(int mmsi);

    Optional<PlainVessel> findPlainVesselByMMSI(int mmsi);

    Optional<Vessel> findVesselByName(String vesselName);

    Optional<String> findVesselDestination(String vesselName);

    Optional<String> findVesselDestination(int mmsi);
}
