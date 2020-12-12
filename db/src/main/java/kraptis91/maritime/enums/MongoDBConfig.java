package kraptis91.maritime.enums;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public enum MongoDBConfig {
  INSTANCE;

  private final Config config;

  MongoDBConfig() {
    final String confFile = "mongodb.conf";

    Config config = ConfigFactory.parseResources(confFile);
    Config localDirConfig =
        ConfigFactory.parseFile(Paths.get("C:/Users/konst/Downloads/" + confFile).toFile());

    Path f = Paths.get("./" + confFile);
    this.config =
        ConfigFactory.parseFile(f.toFile())
            .withFallback(localDirConfig)
            .withFallback(config)
            .resolve();
  }

  public String getUser() {
    return getMongoDBConfig().getString("user");
  }

  public String getPassword() {
    return getMongoDBConfig().getString("password");
  }

  public String getHost() {
    return getMongoDBConfig().getString("host");
  }

  public int getPort() {
    return getMongoDBConfig().getInt("port");
  }

  private Config getMongoDBConfig() {
    return config.getConfig("mongodb");
  }
}
