package kraptis91.maritime.db.dao.mongodb;

import kraptis91.maritime.model.GeoPoint;
import kraptis91.maritime.model.ReceiverMeasurement;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.model.Voyage;
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

    default Document extractVoyageDocument(Voyage voyage) {
        Document voyageDoc = new Document();
        voyageDoc.put("destination", voyage.getDestination());
        if (!Objects.isNull(voyage.getFirstMeasurement())) {
            voyageDoc.put(
                "firstMeasurement", extractReceiverMeasurementDocument(voyage.getFirstMeasurement()));
        }
        if (!Objects.isNull(voyage.getLastMeasurement())) {
            voyageDoc.put(
                "lastMeasurement", extractReceiverMeasurementDocument(voyage.getLastMeasurement()));
        }
        voyageDoc.put("duration", voyage.calcDuration().toMillis());
        voyageDoc.put("numberOfMeasurements", voyage.getNumberOfMeasurements());
        return voyageDoc;
    }

    default Document extractReceiverMeasurementDocument(ReceiverMeasurement measurement) {
        Document measurementDoc = new Document();
        if (!Objects.isNull(measurement.getEta())) {
            measurementDoc.put("eta", measurement.getEta());
        }
        if (measurement.getToPort() != 0) {
            measurementDoc.put("toPort", measurement.getToPort());
        }
        measurementDoc.put("t", measurement.getDate().getTime());
        return measurementDoc;
    }

}
