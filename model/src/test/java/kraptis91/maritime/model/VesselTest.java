package kraptis91.maritime.model;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020. */
public class VesselTest {

  private static Logger LOGGER = Logger.getLogger(VesselTest.class.getName());

  @Test
  public void testHowToCreateVesselObject() {

    // create a new full vessel
    Vessel vessel = ModelFactory.createDemoVessel();

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
