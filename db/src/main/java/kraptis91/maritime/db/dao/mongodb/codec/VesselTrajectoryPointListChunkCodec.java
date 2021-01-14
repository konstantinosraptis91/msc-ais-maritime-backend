package kraptis91.maritime.db.dao.mongodb.codec;

import kraptis91.maritime.db.dao.mongodb.DocumentExtractor;
import kraptis91.maritime.model.ModelExtractor;
import kraptis91.maritime.model.VesselTrajectoryPointListChunk;
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
import java.util.stream.Collectors;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 15/12/2020.
 */
public class VesselTrajectoryPointListChunkCodec implements Codec<VesselTrajectoryPointListChunk>, DocumentExtractor {

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

        VesselTrajectoryPointListChunk trajectory = ModelExtractor.extractVesselTrajectoryPointListChunk(document);
        List<Document> pointDocs = document.getList("points", Document.class);

        trajectory
            .getPointList()
            .addAll(
                pointDocs.stream()
                    .map(ModelExtractor::extractVesselTrajectoryPoint)
                    .collect(Collectors.toList()));

        return trajectory;
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

        if (!Objects.isNull(trajectory.getCountry())) {
            document.put("country", trajectory.getCountry());
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
                .map(this::extractVesselTrajectoryPointDocument)
                .collect(Collectors.toList());

        document.put("points", pointList);
        document.put("nPoints", trajectory.getNumberOfPoints());
        document.put("avgGeoPoint", extractGeoPointDocument(trajectory.getAvgGeoPoint()));
        document.put("avgSpeed", trajectory.getAvgSpeed());

        documentCodec.encode(writer, document, encoderContext);
    }

    @Override
    public Class<VesselTrajectoryPointListChunk> getEncoderClass() {
        return VesselTrajectoryPointListChunk.class;
    }
}
