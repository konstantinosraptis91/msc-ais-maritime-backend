package kraptis91.maritime.retriever;

import kraptis91.maritime.codelists.CodelistMapDto;
import kraptis91.maritime.model.PlainVessel;
import kraptis91.maritime.model.Port;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.keplergl.KeplerGlCollection;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020.
 */
public interface MaritimeDataRetriever {

    List<VesselTrajectoryChunk> getVesselTrajectory(int mmsi);

    List<VesselTrajectoryChunk> getVesselTrajectory(String vesselName);

    Optional<Vessel> getVesselByMMSI(int mmsi);

    Optional<Vessel> getVesselByName(String vesselName);

    /**
     * Get vessels by type.
     *
     * @param shipType The ship type
     * @param skip     Number of docs to skip in db
     * @param limit    Limit the number of results
     * @return The vessel list
     */
    List<Vessel> getVesselsByType(String shipType, int skip, int limit);

    List<Vessel> getVessels(int skip, int limit);

    List<String> getShipTypes();

    List<Port> getPorts(int skip, int limit);

    List<Port> getPortsByCountryCode(String countryCode);

    List<Port> getNearPortsByReferencePoint(double longitude, double latitude,
                                            double maxDistance, double minDistance,
                                            int skip, int limit);

    List<Port> getNearPortsByMMSI(int mmsi, double maxDistance, int skip, int limit);

    List<CodelistMapDto> getCountryCodeMapList();

    List<PlainVessel> getPlainVesselsByType(String shipType, int skip, int limit);

    List<PlainVessel> getPlainVesselByCountryCode(String countryCode, int skip, int limit);

    List<PlainVessel> getPlainVessels(String shipType, String countryCode, int skip, int limit);

    KeplerGlCollection getKeplerGlVesselTrajectoryCollection(int mmsi);

    List<PlainVessel> getNearVessels(double longitude, double latitude,
                                     double maxDistance, double minDistance,
                                     int skip, int limit);
}
