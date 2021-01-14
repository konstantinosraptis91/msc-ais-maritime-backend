package kraptis91.maritime.api.service;

import kraptis91.maritime.codelists.CodelistMapDto;
import kraptis91.maritime.retriever.MaritimeDataRetriever;
import kraptis91.maritime.retriever.RetrieverFactory;

import java.util.List;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 31/12/2020.
 */
public class CountryService {

    public List<CodelistMapDto> getCountryCodeMapDtoList() {
        MaritimeDataRetriever dataRetriever = RetrieverFactory.createMaritimeDataRetriever();
        return dataRetriever.getCountryCodeMapList();
    }

}
