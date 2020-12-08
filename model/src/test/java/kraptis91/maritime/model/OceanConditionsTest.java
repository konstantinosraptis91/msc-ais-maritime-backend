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

/**
 * @author Stavros Lamprinos [stalab at linuxmail.org] on 8/12/2020.
 */
public class OceanConditionsTest {

    private static final Logger LOGGER = Logger.getLogger(OceanConditionsTest.class.getName());

    @Test
    public void testHowToCreateVesselObject() {

        // create new OceanConditions demo object via model factory
        OceanConditions oceanConditionsDemo = ModelFactory.createOceanConditionsDemo();


        System.out.println(oceanConditionsDemo);

        //  perform validation for geometry coordinates
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<OceanConditions>> vesselViolations = validator.validate(oceanConditionsDemo);

        vesselViolations.forEach(v -> LOGGER.log(Level.SEVERE, v.getMessage()));
        //  test that no validations occur
        Assert.assertEquals(0, vesselViolations.size());
    }
}
