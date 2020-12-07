package kraptis91.maritime.api.endpoint;

import io.javalin.http.Handler;
import kraptis91.maritime.model.Vessel;
import kraptis91.maritime.model.VesselTrajectoryPoint;

import java.math.BigInteger;
import java.time.LocalDateTime;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public class VesselController {

  public static Handler getAllVessels =
      ctx -> {
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

        ctx.json(vessel);
      };
}
