package kraptis91.maritime.model;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class VesselTest {

  private static Logger LOGGER = Logger.getLogger(VesselTest.class.getName());

  @Test
  public void testHowToCreateVesselObject() {

    // create a new full vessel
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

    // check set's behaviour
    Assert.assertEquals(3, vessel.getVesselTrajectory().size());

    System.out.println(vessel);

    // perform validation
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<Vessel>> vesselViolations = validator.validate(vessel);

    for (ConstraintViolation<Vessel> violation : vesselViolations) {
      // System.out.println(violation.getMessage());
      LOGGER.log(Level.SEVERE, violation.getMessage());
    }

    Assert.assertEquals(0, vesselViolations.size());

    for (VesselTrajectoryPoint vesselTrajectoryPoint : vessel.getVesselTrajectory().getPointSet()) {
      Set<ConstraintViolation<VesselTrajectoryPoint>> vesselTrajectoryPointViolations =
          validator.validate(vesselTrajectoryPoint);

      Assert.assertEquals(0, vesselTrajectoryPointViolations.size());

      for (ConstraintViolation<VesselTrajectoryPoint> violation : vesselTrajectoryPointViolations) {
        LOGGER.log(Level.SEVERE, violation.getMessage());
      }
    }
  }
}
