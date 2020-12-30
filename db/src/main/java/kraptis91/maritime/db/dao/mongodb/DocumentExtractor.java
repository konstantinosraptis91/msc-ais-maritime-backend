package kraptis91.maritime.db.dao.mongodb;

import kraptis91.maritime.model.GeoPoint;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import org.bson.Document;

import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public interface DocumentExtractor {

    default Document extractVesselTrajectoryPointDocument(VesselTrajectoryPoint point) {
        Document pointDoc = new Document();
        if (!Objects.isNull(point.getGeoPoint())) {
            pointDoc.put("geoPoint", extractGeoPointDocument(point.getGeoPoint()));
        }
        pointDoc.put("speed", point.getSpeed());
        pointDoc.put("vesselId", point.getVesselId());
        pointDoc.put("timestamp", point.getTimestamp());
        return pointDoc;
    }

    default Document extractGeoPointDocument(GeoPoint geoPoint) {
        Document geoPointDoc = new Document();
        geoPointDoc.put("coordinates", geoPoint.getCoordinates());
        geoPointDoc.put("type", geoPoint.getType());
        return geoPointDoc;
    }

}
