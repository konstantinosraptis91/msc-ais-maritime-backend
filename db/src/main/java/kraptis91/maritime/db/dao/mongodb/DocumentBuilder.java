package kraptis91.maritime.db.dao.mongodb;


import org.bson.Document;

import java.util.Arrays;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
public interface DocumentBuilder {

    default Document createVesselTrajectoryChunkDocument() {
        return new Document().append("mmsi", 1)
            .append("vesselName", 1)
            .append("shipType", 1)
            .append("startDate", 1)
            .append("endDate", 1)
            .append("nPoints", 1)
            .append("avgGeoPoint", new Document()
                .append("coordinates", 2))
            .append("avgSpeed", 1);
    }

    default Document createPlainVesselDocument() {
        return new Document().append("mmsi", 1)
            .append("vesselName", 1)
            .append("shipType", 1)
            .append("country", 1)
            .append("_id", 0);
    }

    default Document createMMSIDocument() {
        return new Document().append("mmsi", 1)
            .append("_id", 0);
    }

    default Document createPortDocument() {
        return new Document().append("mmsi", 1)
            .append("_id", 0);
    }

    default Document createGeoNearSphericalGeometryDocument(double longitude, double latitude, double maxDistance) {
        return new Document("$geoNear", new Document()
            .append("near", new Document()
                .append("$geometry", new Document()
                    .append("type", "Point")
                    .append("coordinates", Arrays.asList(longitude, latitude)))
                .append("$maxDistance", maxDistance))
            .append("key", "avgGeoPoint")
            .append("distanceField", "dist.calculated"));
    }

}
