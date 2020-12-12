package kraptis91.maritime.parser.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public class DirectoryUtils {

  /**
   * Get current user home directory.
   *
   * @return
   */
  public static Path getHomeDirectory() {
    return Paths.get(System.getProperty("user.home"));
  }

  /**
   * Get current user home directory downloads folder.
   *
   * @return
   */
  public static Path getDefaultDownloadsDirectory() {
    return Paths.get(System.getProperty("user.home"), "Downloads");
  }
}
