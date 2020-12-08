package kraptis91.maritime.model;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020.
 */
public class ModelFactory {

    public static Vessel createDemoVessel() {
        Vessel vessel =
                new Vessel.Builder(999999999, 9999999, "__VESSEL_NAME")
                        .withCallSign("__SIGN")
                        .withEta(LocalDateTime.now())
                        .withDraught(25.5)
                        .withShipType("__SHIP_TYPE_NAME")
                        .withDestination("__VESSEL_DESTINATION")
                        .withCountry("__COUNTRY_NAME")
                        .build();

        // set vessel's trajectory
        vessel
                .getVesselTrajectory()
                .add(VesselTrajectoryPoint.of(5.2, 10.7, 67, BigInteger.ZERO))
                // add a duplicate on purpose to check set's behaviour
                .add(VesselTrajectoryPoint.of(5.2, 10.7, 15.2, BigInteger.ZERO))
                .add(VesselTrajectoryPoint.of(102.56, 278.1, 67, BigInteger.ZERO))
                .add(VesselTrajectoryPoint.of(900.8, 1200.67, 15, BigInteger.ZERO));

        return vessel;
    }

    /**
     *
     * @return new OceanConditions object
     */

    public static OceanConditions createOceanConditionsDemo() {

        return new OceanConditions.Builder(45.55, 50.4, BigInteger.valueOf(1812202345))
                .withBottomDepth(OptionalDouble.of(77.99))
                .withTidalEffect(OptionalDouble.of(99.99))
                // setting null value to test defaults
                .withSeaHeight(OptionalDouble.empty())
                .withMeanWaveLength(OptionalInt.of(33))
                .build();
    }
}
