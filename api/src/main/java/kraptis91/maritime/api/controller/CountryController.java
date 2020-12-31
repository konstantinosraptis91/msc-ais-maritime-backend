package kraptis91.maritime.api.controller;

import io.javalin.http.Handler;
import kraptis91.maritime.api.service.ServiceFactory;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/12/2020.
 */
public class CountryController {

    public static Handler getCountriesWith2ACodes =
        ctx ->
            ctx.json(
                ServiceFactory.createCountryService()
                    .getCountryCodeMapDtoList());

}
