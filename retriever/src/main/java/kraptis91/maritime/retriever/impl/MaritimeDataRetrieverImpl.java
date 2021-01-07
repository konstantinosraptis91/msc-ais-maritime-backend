package kraptis91.maritime.retriever.impl;

import kraptis91.maritime.db.dao.DaoFactory;
import kraptis91.maritime.db.dao.PortDao;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.db.dao.VesselTrajectoryChunkDao;
import kraptis91.maritime.model.PlainVessel;
import kraptis91.maritime.model.Port;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.keplergl.KeplerGlCollection;
import kraptis91.maritime.model.keplergl.KeplerGlFeature;
import kraptis91.maritime.model.keplergl.adapters.KeplerGlFeatureAdapter;
import kraptis91.maritime.parser.dto.json.CountryCodeMapDto;
import kraptis91.maritime.parser.enums.CountryCode;
import kraptis91.maritime.parser.enums.CountryCodeMap;
import kraptis91.maritime.parser.enums.ShipTypes;
import kraptis91.maritime.retriever.MaritimeDataRetriever;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 6/12/2020.
 */
public class MaritimeDataRetrieverImpl implements MaritimeDataRetriever {

    public static Logger LOGGER = Logger.getLogger(MaritimeDataRetrieverImpl.class.getName());

    @Override
    public List<VesselTrajectoryChunk> getVesselTrajectory(int mmsi) {
        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        return dao.findVesselTrajectory(mmsi);
    }

    @Override
    public List<VesselTrajectoryChunk> getVesselTrajectory(String vesselName) {
        VesselTrajectoryChunkDao dao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        return dao.findVesselTrajectory(vesselName);
    }

    @Override
    public List<Vessel> getVesselsByDestination(String destination, int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findVesselsByDestination(destination, skip, limit);
    }

    @Override
    public Optional<String> getVesselDestination(int mmsi) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findVesselDestination(mmsi);
    }

    @Override
    public Optional<String> getVesselDestination(String vesselName) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findVesselDestination(vesselName);
    }

    @Override
    public List<Vessel> getVesselsByType(String shipType, int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findVesselsByType(shipType, skip, limit);
    }

    @Override
    public Optional<Vessel> getVesselByMMSI(int mmsi) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findVesselByMMSI(mmsi);
    }

    @Override
    public Optional<Vessel> getVesselByName(String vesselName) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findVesselByName(vesselName);
    }

    @Override
    public List<Vessel> getVessels(int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findVessels(skip, limit);
    }

    @Override
    public List<String> getShipTypes() {
        return ShipTypes.INSTANCE.getDistinctShipTypes();
    }

    @Override
    public List<Port> getPorts(int skip, int limit) {
        PortDao dao = DaoFactory.createMongoPortDao();
        return dao.findPorts(skip, limit);
    }

    @Override
    public List<Port> getPortsByCountryCode(String countryCode) {
        PortDao dao = DaoFactory.createMongoPortDao();
        return dao.findPortsByCountryCode(countryCode);
    }

    @Override
    public List<CountryCodeMapDto> getCountryCodeMapDtoList() {
        return CountryCodeMap.INSTANCE.getCountryCodeMapDtoList();
    }

    @Override
    public List<PlainVessel> getPlainVesselsByType(String shipType, int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findPlainVesselsByType(shipType, skip, limit);
    }

    @Override
    public List<PlainVessel> getPlainVesselByCountryCode(CountryCode countryCode, int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findPlainVesselByCountryName(
            CountryCodeMap.INSTANCE.getCountryNameByCode(countryCode), skip, limit);
    }

    @Override
    public List<PlainVessel> getPlainVessels(String shipType, CountryCode countryCode, int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findPlainVessels(
            shipType, CountryCodeMap.INSTANCE.getCountryNameByCode(countryCode), skip, limit);
    }

    @Override
    public KeplerGlCollection getKeplerGlVesselTrajectoryCollection(int mmsi) {
        List<VesselTrajectoryChunk> chunks = getVesselTrajectory(mmsi);

        List<KeplerGlFeature> features = chunks.stream()
            .map(KeplerGlFeatureAdapter::convertChunkToFeature)
            .collect(Collectors.toList());

        KeplerGlCollection keplerGlCollection = KeplerGlCollection.newInstance();
        keplerGlCollection.getFeatureList().addAll(features);

        return keplerGlCollection;
    }

    @Override
    public List<PlainVessel> getNearVessels(double longitude, double latitude, double maxDistance, double minDistance) {
        final VesselTrajectoryChunkDao trajectoryChunkDao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        final VesselDao vesselDao = DaoFactory.createMongoVesselDao();

        return trajectoryChunkDao.findNearVesselsMMSIList(longitude, latitude, maxDistance, minDistance)
            .stream()
            .map(vesselDao::findPlainVesselByMMSI)
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }
}
