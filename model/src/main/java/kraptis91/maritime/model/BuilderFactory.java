package kraptis91.maritime.model;

import org.bson.Document;

import java.util.Date;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public class BuilderFactory {

    public static VesselTrajectoryChunkBuilder createVesselTrajectoryChunkBuilder(Document document) {

        final int mmsi = document.getInteger("mmsi");
        final String vesselName = document.getString("vesselName");
        final String shipType = document.getString("shipType");
        final Date startDate = document.getDate("startDate");
        final Date endDate = document.getDate("endDate");
        final GeoPoint avgGeoPoint = ModelExtractor.extractGeoPoint(document.get("avgGeoPoint", Document.class));
        final double avgSpeed = document.getDouble("avgSpeed");
        final int nPoints = document.getInteger("nPoints");

        return new VesselTrajectoryChunkBuilder(mmsi)
            .withVesselName(vesselName)
            .withShipType(shipType)
            .withStartDate(startDate)
            .withEndDate(endDate)
            .withAvgGeoPoint(avgGeoPoint)
            .withAvgSpeed(avgSpeed)
            .withNPoints(nPoints);
    }

}
