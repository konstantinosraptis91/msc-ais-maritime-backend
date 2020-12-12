package kraptis91.maritime.utils;

import kraptis91.maritime.model.OceanConditions;
import kraptis91.maritime.parser.dto.SeaStateForecastDto;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 11/12/2020. */
public class ModelExtractor {

  public static OceanConditions extractOceanConditions(SeaStateForecastDto dto) {
    return new OceanConditions.Builder(dto.getLon(), dto.getLat(), dto.getTs())
        .withBottomDepth(dto.getDpt())
        .withTidalEffect(dto.getWlv())
        .withSeaHeight(dto.getHs())
        .withMeanWaveLength(dto.getLm())
        .build();
  }
}