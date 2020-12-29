package kraptis91.maritime.model;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
public interface ModelBuilder {

    default VesselTrajectoryChunk createVesselTrajectoryChunk(int mmsi,
                                                              String vesselName,
                                                              String shipType) {
        return VesselTrajectoryChunk.builder(mmsi)
                .withVesselName(vesselName)
                .withShipType(shipType)
                .build();
    }

}
