package kraptis91.maritime.parser.utils;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 11/12/2020. */
public class CSVParserUtils {

  public static final Logger LOGGER = Logger.getLogger(CSVParserUtils.class.getName());

  public static double parseDouble(@NotNull String value) throws NumberFormatException {
    validateValue("parseDouble", value);
    return Double.parseDouble(value);
  }

  public static int parseInt(@NotNull String value) throws NumberFormatException {
    validateValue("parseInt", value);
    return Integer.parseInt(value);
  }

  public static long parseLong(@NotNull String value) throws NumberFormatException {
    validateValue("parseLong", value);
    return Long.parseLong(value);
  }

  /** @param name The name of the method call this method */
  private static void validateValue(String name, String value) throws NumberFormatException {

    if (Objects.isNull(value)) {
      throw new NumberFormatException("Error... Trying to " + name + " null value");
    } else if (value.isEmpty()) {
      throw new NumberFormatException("Error... Trying to " + name + " empty value");
    } else if (value.isBlank()) {
      throw new NumberFormatException("Error... Trying to " + name + " blank value");
    }
  }
}
