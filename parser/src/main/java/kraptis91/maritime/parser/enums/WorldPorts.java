package kraptis91.maritime.parser.enums;

import kraptis91.maritime.parser.CSVParser;
import kraptis91.maritime.parser.dto.csv.PortDto;
import kraptis91.maritime.parser.exception.CSVParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 29/12/2020.
 */
public enum WorldPorts {

    INSTANCE("/csv/world-ports.csv");

    public final Logger LOGGER = java.util.logging.Logger.getLogger(WorldPorts.class.getName());
    private final List<PortDto> portDtoList;
    private final List<String> portNameList;

    WorldPorts(String filename) {

        portDtoList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(WorldPorts.class.getResourceAsStream(filename)))) {

            LOGGER.info("Initializing World Ports from " + filename + " START");

            final CSVParser parser = new CSVParser();

            String line;
            PortDto dto;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {

                // omit first line
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // parse current line to the dto
                dto = parser.extractPortDto(line);
                // System.out.println(dto);
                // add ship type id as key to Map and type name as value
                portDtoList.add(dto);
            }

        } catch (IOException | CSVParserException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        portNameList = portDtoList.stream()
            .map(PortDto::getName)
            .collect(Collectors.toList());
    }

    public List<String> getPortNames() {
        return portNameList;
    }

    public List<PortDto> getPortDtoList() {
        return portDtoList;
    }
}
