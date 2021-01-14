package kraptis91.maritime.retriever.impl;

import kraptis91.maritime.codelists.CodelistMapDto;
import kraptis91.maritime.codelists.CodelistsOfBiMap;
import kraptis91.maritime.db.dao.DaoFactory;
import kraptis91.maritime.db.dao.PortDao;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.db.dao.VesselTrajectoryChunkDao;
import kraptis91.maritime.db.dao.mongodb.query.utils.NearQueryOptions;
import kraptis91.maritime.model.PlainVessel;
import kraptis91.maritime.model.Port;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.keplergl.KeplerGlCollection;
import kraptis91.maritime.model.keplergl.KeplerGlFeature;
import kraptis91.maritime.model.keplergl.adapters.KeplerGlFeatureAdapter;
import kraptis91.maritime.parser.enums.ShipTypes;
import kraptis91.maritime.retriever.MaritimeDataRetriever;

import java.util.*;
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

//    @Override
//    public List<Vessel> getVesselsByDestination(String destination, int skip, int limit) {
//        VesselDao dao = DaoFactory.createMongoVesselDao();
//        return dao.findVesselsByDestination(destination, skip, limit);
//    }

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
    public List<Port> getNearPortsByReferencePoint(double longitude, double latitude,
                                                   double maxDistance, double minDistance,
                                                   int skip, int limit) {
        PortDao dao = DaoFactory.createMongoPortDao();
        return dao.findNearPorts(
            NearQueryOptions.builder()
                .withLongitude(longitude)
                .withLatitude(latitude)
                .withMaxDistance(maxDistance)
                .withMinDistance(minDistance)
                .skip(skip)
                .limit(limit)
                .build());
    }

    @Override
    public List<Port> getNearPortsByMMSI(int mmsi, double maxDistance, int skip, int limit) {

        PortDao dao = DaoFactory.createMongoPortDao();
        final Set<Port> nearPortsSet = new HashSet<>();

        getVesselTrajectory(mmsi).forEach(chunk ->
            nearPortsSet.addAll(
                dao.findNearPorts(
                    NearQueryOptions.builder()
                        .withLongitude(chunk.getAvgGeoPoint().getCoordinates().get(0))
                        .withLatitude(chunk.getAvgGeoPoint().getCoordinates().get(1))
                        .withMaxDistance(maxDistance)
                        .withMinDistance(0)
                        .skip(skip)
                        .limit(limit)
                        .build())));

        return new ArrayList<>(nearPortsSet);
    }

    @Override
    public List<CodelistMapDto> getCountryCodeMapList() {
        return CodelistsOfBiMap.COUNTRY_CODE_MAP.getCodelistMapDtoList();
    }

    @Override
    public List<PlainVessel> getPlainVesselsByType(String shipType, int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findPlainVesselsByType(shipType, skip, limit);
    }

    @Override
    public List<PlainVessel> getPlainVesselByCountryCode(String countryCode, int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findPlainVesselByCountryCode(countryCode, skip, limit);
    }

    @Override
    public List<PlainVessel> getPlainVessels(String shipType, String countryCode, int skip, int limit) {
        VesselDao dao = DaoFactory.createMongoVesselDao();
        return dao.findPlainVessels(shipType, countryCode, skip, limit);
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
    public List<PlainVessel> getNearVessels(double longitude, double latitude, double maxDistance, double minDistance,
                                            int skip, int limit) {
        final VesselTrajectoryChunkDao trajectoryChunkDao = DaoFactory.createMongoVesselTrajectoryChunkDao();
        final VesselDao vesselDao = DaoFactory.createMongoVesselDao();

        return trajectoryChunkDao.findNearVesselsMMSIList(
            NearQueryOptions.builder()
                .withLongitude(longitude)
                .withLatitude(latitude)
                .withMaxDistance(maxDistance)
                .withMinDistance(minDistance)
                .skip(skip)
                .limit(limit)
                .build())
            .stream()
            .map(vesselDao::findPlainVesselByMMSI)
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }
}
