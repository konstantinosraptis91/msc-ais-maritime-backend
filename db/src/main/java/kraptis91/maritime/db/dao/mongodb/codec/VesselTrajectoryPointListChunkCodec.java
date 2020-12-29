package kraptis91.maritime.db.dao.mongodb.codec;

import kraptis91.maritime.model.GeoPoint;
import kraptis91.maritime.model.VesselTrajectoryChunkBuilder;
import kraptis91.maritime.model.VesselTrajectoryPointListChunk;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 15/12/2020.
 */
public class VesselTrajectoryPointListChunkCodec implements Codec<VesselTrajectoryPointListChunk> {

    private final Codec<Document> documentCodec;

    public VesselTrajectoryPointListChunkCodec() {
        documentCodec = new DocumentCodec();
    }

    public VesselTrajectoryPointListChunkCodec(Codec<Document> codec) {
        documentCodec = codec;
    }

    @Override
    public VesselTrajectoryPointListChunk decode(BsonReader reader, DecoderContext decoderContext) {

        Document document = documentCodec.decode(reader, decoderContext);

        final int mmsi = document.getInteger("mmsi");
        final String vesselName = document.getString("vesselName");
        final String shipType = document.getString("shipType");
        final Date startDate = document.getDate("startDate");
        final Date endDate = document.getDate("endDate");
        final int nPoints = document.getInteger("nPoints");
        final GeoPoint avgGeoPoint = extractGeoPoint(document.get("avgGeoPoint", Document.class));
        final double avgSpeed = document.getDouble("avgSpeed");

        VesselTrajectoryPointListChunk trajectory =
                new VesselTrajectoryChunkBuilder(mmsi)
                        .withVesselName(vesselName)
                        .withShipType(shipType)
                        .withStartDate(startDate)
                        .withEndDate(endDate)
                        .withAvgGeoPoint(avgGeoPoint)
                        .withAvgSpeed(avgSpeed)
                        .withNPoints(nPoints)
                        .buildPointListChunk();

        List<Document> pointDocs = document.getList("points", Document.class);

        trajectory
                .getPointList()
                .addAll(
                        pointDocs.stream()
                                .map(VesselTrajectoryPointListChunkCodec::extractVesselTrajectoryPoint)
                                .collect(Collectors.toList()));

        return trajectory;
    }

    public static VesselTrajectoryPoint extractVesselTrajectoryPoint(Document pointDoc) {
        return VesselTrajectoryPoint.builder()
                .withCoordinates(extractGeoPoint(pointDoc.get("geoPoint", Document.class)))
                .withSpeed(pointDoc.getDouble("speed"))
                .withTimestamp(pointDoc.getLong("timestamp"))
                .withVesselId(pointDoc.getObjectId("vesselId").toHexString())
                .build();
    }

    public static GeoPoint extractGeoPoint(Document geoPointDoc) {
        List<Double> coordinates = geoPointDoc.getList("coordinates", Double.class);
        return GeoPoint.of(coordinates.get(0), coordinates.get(1));
    }

    @Override
    public void encode(
            BsonWriter writer, VesselTrajectoryPointListChunk trajectory, EncoderContext encoderContext) {

        final Document document = new Document();
        document.put("_id", new ObjectId());

        document.put("mmsi", trajectory.getMmsi());

        if (!Objects.isNull(trajectory.getVesselName())) {
            document.put("vesselName", trajectory.getVesselName());
        }

        if (!Objects.isNull(trajectory.getShipType())) {
            document.put("shipType", trajectory.getShipType());
        }

        if (!Objects.isNull(trajectory.getStartDate())) {
            document.put("startDate", trajectory.getStartDate());
        }

        if (!Objects.isNull(trajectory.getEndDate())) {
            document.put("endDate", trajectory.getEndDate());
        }

        List<Document> pointList =
                trajectory.getPointList().stream()
                        .map(VesselTrajectoryPointListChunkCodec::extractVesselTrajectoryPointDocument)
                        .collect(Collectors.toList());

        document.put("points", pointList);
        document.put("nPoints", trajectory.getNumberOfPoints());
        document.put("avgGeoPoint", extractGeoPointDocument(trajectory.getAvgGeoPoint()));
        document.put("avgSpeed", trajectory.getAvgSpeed());

        documentCodec.encode(writer, document, encoderContext);
    }

    public static Document extractVesselTrajectoryPointDocument(VesselTrajectoryPoint point) {
        Document pointDoc = new Document();
        if (!Objects.isNull(point.getGeoPoint())) {
            pointDoc.put("geoPoint", extractGeoPointDocument(point.getGeoPoint()));
        }
        pointDoc.put("speed", point.getSpeed());
        pointDoc.put("vesselId", point.getVesselId());
        pointDoc.put("timestamp", point.getTimestamp());
        return pointDoc;
    }

    public static Document extractGeoPointDocument(GeoPoint geoPoint) {
        Document geoPointDoc = new Document();
        geoPointDoc.put("coordinates", geoPoint.getCoordinates());
        geoPointDoc.put("type", geoPoint.getType());
        return geoPointDoc;
    }

    @Override
    public Class<VesselTrajectoryPointListChunk> getEncoderClass() {
        return VesselTrajectoryPointListChunk.class;
    }
}
