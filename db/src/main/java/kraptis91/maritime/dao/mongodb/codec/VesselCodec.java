package kraptis91.maritime.dao.mongodb.codec;

import kraptis91.maritime.model.Vessel;
import org.bson.*;
import org.bson.codecs.*;
import org.bson.types.ObjectId;

import java.util.Objects;

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

    return Vessel.builder(document.getInteger("mmsi"))
        .withIMO(document.getInteger("imo"))
        .withVesselName(document.getString("vesselName"))
        .withCallSign(document.getString("callSign"))
        .withEta(document.getString("eta"))
        .withDraught(document.getDouble("draught"))
        .withShipType(document.getString("shipType"))
        .withDestination(document.getString("destination"))
        .withCountry(document.getString("country"))
        .build(document.getObjectId("_id").toHexString());
  }

  @Override
  public void encode(BsonWriter writer, Vessel vessel, EncoderContext encoderContext) {

    Document document = new Document();

    document.put(
        "_id", Objects.isNull(vessel.getId()) ? new ObjectId() : new ObjectId(vessel.getId()));

    if (!Objects.isNull(vessel.getCallSign())) {
      document.put("callSign", vessel.getCallSign());
    }

    if (!Objects.isNull(vessel.getCountry())) {
      document.put("country", vessel.getCountry());
    }

    if (!Objects.isNull(vessel.getDestination())) {
      document.put("destination", vessel.getDestination());
    }

    if (!Objects.isNull(vessel.getEta())) {
      document.put("eta", vessel.getEta());
    }

    if (!Objects.isNull(vessel.getVesselName())) {
      document.put("vesselName", vessel.getVesselName());
    }

    if (!Objects.isNull(vessel.getShipType())) {
      document.put("shipType", vessel.getShipType());
    }

    document.put("draught", vessel.getDraught());
    document.put("imo", vessel.getImo());
    document.put("mmsi", vessel.getMmsi());

    documentCodec.encode(writer, document, encoderContext);
  }

  @Override
  public Class<Vessel> getEncoderClass() {
    return Vessel.class;
  }
}
