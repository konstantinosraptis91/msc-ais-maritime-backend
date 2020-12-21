package kraptis91.maritime.db.utils;

import kraptis91.maritime.model.*;
import kraptis91.maritime.parser.dto.NariDynamicDto;
import kraptis91.maritime.parser.dto.NariStaticDto;
import kraptis91.maritime.parser.dto.SeaStateForecastDto;

import java.util.Date;

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

    Vessel vessel =
        Vessel.builder(dto.getMmsi())
            .withIMO(dto.getImo())
            .withVesselName(dto.getShipName())
            .withCallSign(dto.getCallSign())
            .withDraught(dto.getDraught())
            .withShipType(shipType)
            .withCountry(country)
            .build();

    vessel.addVoyageAndApplyMeasurement(
        extractVoyage(dto),
        ReceiverMeasurement.builder()
            .withETA(dto.getEta())
            .withToPort(dto.getToPort())
            .withDate(new Date(dto.getT()))
            .build());

    return vessel;
  }

  public static Voyage extractVoyage(NariStaticDto dto) {
    return Voyage.createInstance(dto.getDestination());
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
