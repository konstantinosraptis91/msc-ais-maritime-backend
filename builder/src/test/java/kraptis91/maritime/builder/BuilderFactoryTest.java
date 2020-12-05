package kraptis91.maritime.builder;

import kraptis91.maritime.builder.enums.VesselDemoCodeListEnum;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryPoint;
import org.junit.Assert;
import org.junit.Test;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 5/12/2020. */
public class BuilderFactoryTest {

  @Test
  public void testHowToCreateThroughFactory() {

    // create a new full vessel
    Vessel vessel =
        BuilderFactory.createVesselBuilder(
                VesselDemoCodeListEnum.DEFAULT_MMSI.getValue(),
                VesselDemoCodeListEnum.DEFAULT_IMO.getValue(),
                "__VESSEL_NAME")
            .withCallSign("__CALL_SIGN")
            .withDestination("__DESTINATION")
            .withDraught(15.2)
            .withSpeed(6.8)
            .withETA("__ETA")
            .withShipType(VesselDemoCodeListEnum.DEFAULT_SHIP_TYPE.getValue())
            .build();

    // set vessel's trajectory
    vessel
        .getVesselTrajectory()
        .add(VesselTrajectoryPoint.of(5.2, 10.7))
        // add a duplicate on purpose to check set's behaviour
        .add(VesselTrajectoryPoint.of(5.2, 10.7))
        .add(VesselTrajectoryPoint.of(102.56, 278.1))
        .add(VesselTrajectoryPoint.of(900.8, 1200.67));

    // check set's behaviour
    Assert.assertEquals(3, vessel.getVesselTrajectory().size());

    System.out.println(vessel);
  }
}
