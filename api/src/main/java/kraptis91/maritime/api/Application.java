package kraptis91.maritime.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.plugin.json.JavalinJackson;
import kraptis91.maritime.api.controller.CountryController;
import kraptis91.maritime.api.controller.PortController;
import kraptis91.maritime.api.controller.VesselController;
import kraptis91.maritime.api.conf.ServerConfig;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020.
 */
public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create(JavalinConfig::enableCorsForAllOrigins)
            .start(ServerConfig.getPort());

        final String baseURL = "/msc/ais/maritime/api";

        app.get(baseURL + "/", ctx -> ctx.result("Server Is Up and Running..."));
        app.get(baseURL + "/vessels", VesselController.getVessels);
        app.get(baseURL + "/vessels/types", VesselController.getShipTypes);
        app.get(baseURL + "/vessels/type/:type", VesselController.getPlainVesselsByShipType);
        app.get(baseURL + "/vessels/country/:code", VesselController.getPlainVesselsByCountryCode);
        app.get(baseURL + "/vessels/type/:type/country/:code", VesselController.getPlainVessels);
        app.get(baseURL + "/vessels/mmsi/:mmsi", VesselController.getVesselByMMSI);
        app.get(baseURL + "/vessels/name/:name", VesselController.getVesselByName);
        app.get(baseURL + "/vessels/trajectory/name/:name", VesselController.getVesselTrajectoryByName);
        app.get(baseURL + "/vessels/trajectory/mmsi/:mmsi", VesselController.getVesselTrajectoryByMMSI);
        app.get(baseURL + "/vessels/trajectory/keplergl/mmsi/:mmsi", VesselController.getKeplerGlVesselTrajectoryByMMSI);
        app.get(baseURL + "/vessels/near/lon/:lon/lat/:lat/dist/:dist", VesselController.getNearVessels);

        app.get(baseURL + "/ports", PortController.getPorts);
        app.get(baseURL + "/ports/country/:code", PortController.getPortsByCountryCode);
        app.get(baseURL + "/ports/near/lon/:lon/lat/:lat/dist/:dist", PortController.getNearPortsByReferencePoint);
        app.get(baseURL + "/ports/near/vessel/mmsi/:mmsi/dist/:dist", PortController.getNearPortsByMMSI);

        app.get(baseURL + "/countries", CountryController.getCountriesWith2ACodes);

        JavalinJackson.configure(
            new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL));
    }
}
