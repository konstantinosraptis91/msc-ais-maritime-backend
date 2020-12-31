package kraptis91.maritime.retriever.impl;

import com.google.common.collect.BiMap;
import kraptis91.maritime.db.dao.DaoFactory;
import kraptis91.maritime.db.dao.PortDao;
import kraptis91.maritime.db.dao.VesselDao;
import kraptis91.maritime.db.dao.VesselTrajectoryChunkDao;
import kraptis91.maritime.model.Port;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.parser.dto.json.CountryCodeMapDto;
import kraptis91.maritime.parser.enums.CountryCode;
import kraptis91.maritime.parser.enums.CountryCodeMap;
import kraptis91.maritime.parser.enums.ShipTypes;
import kraptis91.maritime.retriever.MaritimeDataRetriever;

import java.util.List;
import java.util.Optional;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 6/12/2020.
 */
public class MaritimeDataRetrieverImpl implements MaritimeDataRetriever {

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
        return ShipTypes.INSTANCE.getShipTypes();
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
}
