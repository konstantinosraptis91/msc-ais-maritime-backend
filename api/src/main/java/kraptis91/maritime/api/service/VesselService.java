package kraptis91.maritime.api.service;

import kraptis91.maritime.model.PlainVessel;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.keplergl.KeplerGlCollection;
import kraptis91.maritime.retriever.MaritimeDataRetriever;
import kraptis91.maritime.retriever.RetrieverFactory;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020.
 */
public class VesselService {

    public List<VesselTrajectoryChunk> getVesselTrajectory(int mmsi) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getVesselTrajectory(mmsi);
    }

    public List<VesselTrajectoryChunk> getVesselTrajectory(String vesselName) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getVesselTrajectory(vesselName);
    }

    public KeplerGlCollection getKeplerGlVesselTrajectory(int mmsi) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getKeplerGlVesselTrajectoryCollection(mmsi);
    }

    public List<Vessel> getVessels(int skip, int limit) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getVessels(skip, limit);
    }

    public List<Vessel> getVesselsByShipType(String shipType, int skip, int limit) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getVesselsByType(shipType, skip, limit);
    }

    public Optional<Vessel> getVesselByMMSI(int mmsi) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getVesselByMMSI(mmsi);
    }

    public Optional<Vessel> getVesselByName(String vesselName) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getVesselByName(vesselName);
    }

    public List<String> getShipTypes() {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getShipTypes();
    }

    public List<PlainVessel> getPlainVesselsByType(String shipType, int skip, int limit) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getPlainVesselsByType(shipType, skip, limit);
    }

    public List<PlainVessel> getPlainVesselByCountryCode(String countryCode, int skip, int limit) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getPlainVesselByCountryCode(countryCode, skip, limit);
    }

    public List<PlainVessel> getPlainVessels(String shipType, String countryCode, int skip, int limit) {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getPlainVessels(shipType, countryCode, skip, limit);
    }

    public List<PlainVessel> getNearVessels(double longitude, double latitude, double maxDistance,
                                            int skip, int limit) {

        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getNearVessels(longitude, latitude, maxDistance, 0, skip, limit);
    }


}
