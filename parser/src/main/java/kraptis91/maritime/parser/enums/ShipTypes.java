package kraptis91.maritime.parser.enums;

import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.csv.ShipTypeListDto;
import kraptis91.maritime.parser.exception.CSVParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 13/12/2020.
 */
public enum ShipTypes {
    INSTANCE("/csv/shipTypesList.csv");

    public final Logger LOGGER = java.util.logging.Logger.getLogger(ShipTypes.class.getName());
    private final List<ShipTypeListDto> shipTypeListDtoList;
    private final List<String> shipTypeList;
    private List<String> distinctShipTypesList;

    ShipTypes(String resource) {

        LOGGER.info("Initializing Ship Types from internal resource: " + resource);

        shipTypeListDtoList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(ShipTypes.class.getResourceAsStream(resource)))) {

            final CSVParser parser = new CSVParser();

            String line;
            ShipTypeListDto dto;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {

                // omit first line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // parse current line to the dto
                dto = parser.extractShipTypeListDto(line);
                // System.out.println(dto);
                // add ship type id as key to Map and type name as value
                shipTypeListDtoList.add(dto);
            }

        } catch (IOException | CSVParserException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        shipTypeList = shipTypeListDtoList.stream()
            .map(ShipTypeListDto::getTypeName)
            .collect(Collectors.toList());
    }

    private Predicate<ShipTypeListDto> isBetween(int shipType) {
        return dto -> shipType >= dto.getShipTypeMin() && shipType <= dto.getShipTypeMax();
    }

    public List<String> getDistinctShipTypes() {
        if (Objects.isNull(distinctShipTypesList)) {
            distinctShipTypesList = shipTypeList.stream()
                .distinct()
                .collect(Collectors.toList());
        }
        return distinctShipTypesList;
    }

    public String getShipType(final int shipType) {

        try {
            return shipTypeListDtoList.stream()
                .filter(isBetween(shipType))
                .findAny()
                .orElseThrow()
                .getTypeName();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
