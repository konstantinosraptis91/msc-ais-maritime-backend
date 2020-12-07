package kraptis91.maritime.api.enums;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020. */
public enum ServerConfig {
  INSTANCE("server.conf");

  private final Config config;

  ServerConfig(String filename) {
    Config config = ConfigFactory.parseResources(filename);
    Path f = Paths.get("./" + filename);
    this.config = ConfigFactory.parseFile(f.toFile()).withFallback(config).resolve();
  }

  public int getPort() {
    return getServerConfig().getInt("port");
  }

  private Config getServerConfig() {
    return config.getConfig("server");
  }
}
