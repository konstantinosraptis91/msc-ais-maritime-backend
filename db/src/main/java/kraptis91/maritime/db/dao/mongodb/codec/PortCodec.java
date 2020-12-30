package kraptis91.maritime.db.dao.mongodb.codec;

import kraptis91.maritime.db.dao.mongodb.DocumentExtractor;
import kraptis91.maritime.model.GeoPoint;
import kraptis91.maritime.model.ModelExtractor;
import kraptis91.maritime.model.Port;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import java.util.Objects;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 30/12/2020.
 */
public class PortCodec implements Codec<Port>, DocumentExtractor {

    private final Codec<Document> documentCodec;

    public PortCodec() {
        documentCodec = new DocumentCodec();
    }

    public PortCodec(Codec<Document> codec) {
        documentCodec = codec;
    }

    @Override
    public Port decode(BsonReader reader, DecoderContext decoderContext) {
        Document portDoc = documentCodec.decode(reader, decoderContext);
        return ModelExtractor.extractPort(portDoc);
    }

    @Override
    public void encode(BsonWriter writer, Port port, EncoderContext encoderContext) {

        final Document portDoc = new Document();
        portDoc.put("_id", new ObjectId());
        putIfValueNotNull("name", port.getName(), portDoc);
        putIfValueNotNull("country", port.getCountry(), portDoc);
        putGeoDocIfValueNotNull("geoPoint", port.getGeoPoint(), portDoc);

        documentCodec.encode(writer, portDoc, encoderContext);
    }

    private void putIfValueNotNull(String key, Object value, Document document) {
        if (!Objects.isNull(value)) {
            document.put(key, value);
        }
    }

    private void putGeoDocIfValueNotNull(String key, GeoPoint value, Document document) {
        if (!Objects.isNull(value)) {
            document.put(key, extractGeoPointDocument(value));
        }
    }

    @Override
    public Class<Port> getEncoderClass() {
        return Port.class;
    }

}
