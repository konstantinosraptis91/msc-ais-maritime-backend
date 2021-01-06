package kraptis91.maritime.model.keplergl.adapters;

import kraptis91.maritime.model.VesselTrajectoryChunk;
import kraptis91.maritime.model.keplergl.KeplerGlFeature;
import kraptis91.maritime.model.keplergl.KeplerGlFeatureGeometryPoint;
import kraptis91.maritime.model.keplergl.KeplerGlFeatureProperties;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 6/1/2021.
 */
public class KeplerGlFeatureAdapter {

    public static KeplerGlFeature convertChunkToFeature(VesselTrajectoryChunk chunk) {
        return KeplerGlFeature.builder()
            .withGeometryPoint(KeplerGlFeatureGeometryPoint.of(chunk.getAvgGeoPoint()))
            .withFeatureProperties(KeplerGlFeatureProperties.builder()
                .withStartDate(chunk.getFormattedStartDate())
                .withEndDate(chunk.getFormattedEndDate())
                .withNPoints(chunk.getNumberOfPoints())
                .withAvgSpeed(chunk.getAvgSpeed())
                .withMMSI(chunk.getMmsi())
                .withVesselName(chunk.getVesselName())
                .withShipType(chunk.getShipType())
                .build())
            .build();
    }

}
