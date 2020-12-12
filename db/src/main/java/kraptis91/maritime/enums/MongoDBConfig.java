package kraptis91.maritime.enums;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
public enum MongoDBConfig {
  INSTANCE("C:\\Users\\konst\\Downloads\\mongodb.conf");

  private final Config config;

  MongoDBConfig(String filename) {
    Config config = ConfigFactory.parseResources(filename);
    Path f = Paths.get("./" + filename);
    this.config = ConfigFactory.parseFile(f.toFile()).withFallback(config).resolve();
  }

  public String getUser() {
    return getMongoDBConfig().getString("user");
  }

  public String getPassword() {
    return getMongoDBConfig().getString("password");
  }

  public String getSource() {
    return getMongoDBConfig().getString("source");
  }

  private Config getMongoDBConfig() {
    return config.getConfig("mongodb");
  }
}
