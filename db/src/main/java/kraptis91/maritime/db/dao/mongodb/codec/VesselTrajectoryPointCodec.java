package kraptis91.maritime.db.dao.mongodb.codec;

import kraptis91.maritime.model.GeoPoint;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 15/12/2020. */
public class VesselTrajectoryPointCodec implements Codec<VesselTrajectoryPoint> {

  private final Codec<Document> documentCodec;

  public VesselTrajectoryPointCodec() {
    documentCodec = new DocumentCodec();
  }

  public VesselTrajectoryPointCodec(Codec<Document> codec) {
    documentCodec = codec;
  }

  @Override
  public VesselTrajectoryPoint decode(BsonReader reader, DecoderContext decoderContext) {

    Document document = documentCodec.decode(reader, decoderContext);

    List<Double> coordinates =
        document.get("geoPoint", Document.class).getList("coordinates", Double.class);

    return VesselTrajectoryPoint.builder()
        .withCoordinates(GeoPoint.of(coordinates.get(0), coordinates.get(1)))
        .withSpeed(document.getDouble("speed"))
        .withTimestamp(document.getLong("timestamp"))
        .withVesselId(document.getObjectId("vesselId").toHexString())
        .build();
  }

  @Override
  public void encode(
      BsonWriter writer, VesselTrajectoryPoint point, EncoderContext encoderContext) {

    Document document = new Document();
    document.put("_id", new ObjectId());

    if (!Objects.isNull(point.getGeoPoint())) {

      Document geoPointDoc = new Document();
      geoPointDoc.put("coordinates", point.getGeoPoint().getCoordinates());
      geoPointDoc.put("type", point.getGeoPoint().getType());

      document.put("geoPoint", geoPointDoc);
    }

    document.put("speed", point.getSpeed());
    document.put("timestamp", point.getTimestamp());
    document.put("VesselId", new ObjectId(point.getVesselId()));

    documentCodec.encode(writer, document, encoderContext);
  }

  @Override
  public Class<VesselTrajectoryPoint> getEncoderClass() {
    return VesselTrajectoryPoint.class;
  }
}
