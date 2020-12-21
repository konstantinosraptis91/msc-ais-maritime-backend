package kraptis91.maritime.db.dao.mongodb.codec;

import kraptis91.maritime.model.ReceiverMeasurement;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.Voyage;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 14/12/2020. */
public class VesselCodec implements Codec<Vessel> {

  private final Codec<Document> documentCodec;

  public VesselCodec() {
    documentCodec = new DocumentCodec();
  }

  public VesselCodec(Codec<Document> codec) {
    documentCodec = codec;
  }

  @Override
  public Vessel decode(BsonReader reader, DecoderContext decoderContext) {

    Document document = documentCodec.decode(reader, decoderContext);

    List<Voyage> voyageList =
        document.getList("voyages", Document.class).stream()
            .map(
                voyageDoc -> {
                  Document firstMeasureDoc = voyageDoc.get("firstMeasurement", Document.class);
                  Document lastMeasureDoc = voyageDoc.get("lastMeasurement", Document.class);

                  return Voyage.builder()
                      .withDestination(voyageDoc.getString("destination"))
                      .withFirstMeasurement(
                          ReceiverMeasurement.builder()
                              .withETA(firstMeasureDoc.getString("eta"))
                              .withToPort(
                                  Optional.ofNullable(firstMeasureDoc.getInteger("toPort"))
                                      .orElse(0))
                              .withDate(new Date(firstMeasureDoc.getLong("t")))
                              .build())
                      .withLastMeasurement(
                          ReceiverMeasurement.builder()
                              .withETA(lastMeasureDoc.getString("eta"))
                              .withToPort(
                                  Optional.ofNullable(lastMeasureDoc.getInteger("toPort"))
                                      .orElse(0))
                              .withDate(new Date(lastMeasureDoc.getLong("t")))
                              .build())
                      .withNumberOfMeasurements(voyageDoc.getInteger("numberOfMeasurements"))
                      .build();
                })
            .collect(Collectors.toList());

    Vessel vessel =
        Vessel.builder(document.getInteger("mmsi"))
            .withIMO(Optional.ofNullable(document.getInteger("imo")).orElse(0))
            .withVesselName(document.getString("vesselName"))
            .withCallSign(document.getString("callSign"))
            .withDraught(Optional.ofNullable(document.getDouble("draught")).orElse(0d))
            .withShipType(document.getString("shipType"))
            .withCountry(document.getString("country"))
            .build(document.getObjectId("_id").toHexString());

    // Add all (distinct) voyages
    voyageList.forEach(vessel::addVoyage);

    return vessel;
  }

  @Override
  public void encode(BsonWriter writer, Vessel vessel, EncoderContext encoderContext) {

    final Document document = new Document();

    document.put(
        "_id", Objects.isNull(vessel.getId()) ? new ObjectId() : new ObjectId(vessel.getId()));

    if (!Objects.isNull(vessel.getCallSign())) {
      document.put("callSign", vessel.getCallSign());
    }

    if (!Objects.isNull(vessel.getCountry())) {
      document.put("country", vessel.getCountry());
    }

    if (!Objects.isNull(vessel.getVesselName())) {
      document.put("vesselName", vessel.getVesselName());
    }

    if (!Objects.isNull(vessel.getShipType())) {
      document.put("shipType", vessel.getShipType());
    }

    if (vessel.getDraught() != 0) {
      document.put("draught", vessel.getDraught());
    }

    if (vessel.getImo() != 0) {
      document.put("imo", vessel.getImo());
    }

    document.put("mmsi", vessel.getMmsi());

    // add voyages document
    List<Document> voyages =
        vessel.getVoyageMap().values().stream()
            .map(this::extractVoyageDocument)
            .collect(Collectors.toList());

    document.put("voyages", voyages);

    documentCodec.encode(writer, document, encoderContext);
  }

  public Document extractVoyageDocument(Voyage voyage) {
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
    voyageDoc.put("duration", voyage.getDuration().toMillis());
    voyageDoc.put("numberOfMeasurements", voyage.getNumberOfMeasurements());
    return voyageDoc;
  }

  public Document extractReceiverMeasurementDocument(ReceiverMeasurement measurement) {
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

  @Override
  public Class<Vessel> getEncoderClass() {
    return Vessel.class;
  }
}
