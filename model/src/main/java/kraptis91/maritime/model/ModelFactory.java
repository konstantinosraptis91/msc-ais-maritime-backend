package kraptis91.maritime.model;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class ModelFactory {

  public static Vessel createDemoVessel() {

    return Vessel.fluentBuilder(999999999)
        .withIMO(9999999)
        .withVesselName("__VESSEL_NAME")
        .withCallSign("__SIGN")
        .withDraught(25.5)
        .withShipType("__SHIP_TYPE_NAME")
        .withCountry("__COUNTRY_NAME")
        .build();
  }

  /** @return new OceanConditions object */
  public static OceanConditions createDemoOceanConditionsDemo() {

    return new OceanConditions.Builder(45.55, 50.4, 122121878)
        .withBottomDepth(77.99)
        .withTidalEffect(99.99)
        .withSeaHeight(15.5)
        .withMeanWaveLength(33)
        .build();
  }
}
