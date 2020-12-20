package kraptis91.maritime.db.utils;

import kraptis91.maritime.model.GeoPoint;
import kraptis91.maritime.model.OceanConditions;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import kraptis91.maritime.parser.dto.NariDynamicDto;
import kraptis91.maritime.parser.dto.NariStaticDto;
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

  public static Vessel extractVessel(NariStaticDto dto, String shipType, String country) {

    return Vessel.builder(dto.getMmsi())
        .withIMO(dto.getImo())
        .withVesselName(dto.getShipName())
        .withCallSign(dto.getCallSign())
        .withDraught(dto.getDraught())
        .withShipType(shipType)
        .withDestination(dto.getDestination())
        .withCountry(country)
        .build();
  }

  public static VesselTrajectoryPoint extractVesselTrajectoryPoint(
      NariDynamicDto dto, String vesselId) {
    return VesselTrajectoryPoint.builder()
        .withCoordinates(GeoPoint.of(dto.getLon(), dto.getLat()))
        .withSpeed(dto.getSpeed())
        .withTimestamp(dto.getT())
        .withVesselId(vesselId)
        .build();
  }
}
