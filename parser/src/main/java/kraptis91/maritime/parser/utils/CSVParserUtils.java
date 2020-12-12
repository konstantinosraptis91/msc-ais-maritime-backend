package kraptis91.maritime.parser.utils;

import kraptis91.maritime.parser.exception.CSVParserException;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.logging.Logger;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 11/12/2020. */
public class CSVParserUtils {

  public static final Logger LOGGER = Logger.getLogger(CSVParserUtils.class.getName());

  public static double parseDouble(@NotNull String value)
      throws CSVParserException, IllegalArgumentException {
    validateValue("parseDouble", value);

    try {
      return Double.parseDouble(value);
    } catch (NumberFormatException e) {
      throw new CSVParserException(e);
    }
  }

  public static int parseInt(@NotNull String value)
      throws CSVParserException, IllegalArgumentException {
    validateValue("parseInt", value);

    try {
      return Integer.parseInt(value);
    } catch (NumberFormatException e) {
      throw new CSVParserException(e);
    }
  }

  public static long parseLong(@NotNull String value)
      throws CSVParserException, IllegalArgumentException {
    validateValue("parseLong", value);

    try {
      return Long.parseLong(value);
    } catch (NumberFormatException e) {
      throw new CSVParserException(e);
    }
  }

  public static String parseText(@NotNull String value) throws IllegalArgumentException {
    validateValue("parseText", value);
    return value;
  }

  @Nullable
  public static String parseTextOrApplyNull(@Nullable String value) {

    if (Objects.isNull(value)) {
      return null;
    } else if (value.isEmpty() || value.isBlank()) {
      value = null;
    }

    return value;
  }

  /** @param name The name of the method call this method */
  private static void validateValue(String name, String value) throws IllegalArgumentException {

    if (Objects.isNull(value)) {
      throw new IllegalArgumentException("Error... Trying to " + name + " null value");
    } else if (value.isEmpty()) {
      throw new IllegalArgumentException("Error... Trying to " + name + " empty value");
    } else if (value.isBlank()) {
      throw new IllegalArgumentException("Error... Trying to " + name + " blank value");
    }
  }
}
