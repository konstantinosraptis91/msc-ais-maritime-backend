package kraptis91.maritime.db.dao.utils;


import org.bson.Document;

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

}
