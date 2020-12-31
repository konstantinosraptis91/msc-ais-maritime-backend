package kraptis91.maritime.model;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 1/12/2020.
 */
public class VesselTest {

    private static Logger LOGGER = Logger.getLogger(VesselTest.class.getName());

    @Test
    public void testHowToCreateVesselObject() {

        // create a new full vessel
        Vessel vessel = ModelFactory.createDemoVessel();
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
    }

    @Test
    public void testHowToCreateVesselObjectWithBuilder() {

        // create a new full vessel
        Vessel vessel = Vessel.builder()
            .withMMSI(999999999)
            .withIMO(9999999)
            .withVesselName("__VESSEL_NAME")
            .withCallSign("__SIGN")
            .withDraught(25.5)
            .withShipType("__SHIP_TYPE_NAME")
            .withCountry("__COUNTRY_NAME")
            .withObjectIdHexString(UUID.randomUUID().toString())
            .build();

        System.out.println(vessel);
    }

    @Test
    public void testHowToCreatePlainVesselObjectWithBuilder() {

        // create a new full vessel
        PlainVessel vessel = PlainVessel.builder()
            .withMMSI(999999999)
            .withVesselName("__VESSEL_NAME")
            .withShipType("__SHIP_TYPE_NAME")
            .withCountry("__COUNTRY_NAME")
            .build();

        System.out.println(vessel);
    }

}
