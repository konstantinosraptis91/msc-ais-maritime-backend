package kraptis91.maritime.db.dao.mongodb.codec;

import kraptis91.maritime.model.OceanConditions;
import kraptis91.maritime.model.Port;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 15/12/2020.
 */
public class OceanConditionsCodec implements Codec<OceanConditions> {
    @Override
    public OceanConditions decode(BsonReader reader, DecoderContext decoderContext) {
        return null;
    }

    @Override
    public void encode(BsonWriter writer, OceanConditions value, EncoderContext encoderContext) {

    }

    @Override
    public Class<OceanConditions> getEncoderClass() {
        return null;
    }
}
