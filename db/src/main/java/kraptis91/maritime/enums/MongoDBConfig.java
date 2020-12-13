package kraptis91.maritime.enums;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

/** @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020. */
enum MongoDBConfig {
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
    return useRemote() ? getRemoteConfig().getString("user") : getLocalConfig().getString("user");
  }

  public char[] getPassword() {
    return useRemote()
        ? getRemoteConfig().getString("password").toCharArray()
        : getLocalConfig().getString("password").toCharArray();
  }

  public String getHost() {
    return useRemote() ? getRemoteConfig().getString("host") : getLocalConfig().getString("host");
  }

  public int getPort() {
    return useRemote() ? getRemoteConfig().getInt("port") : getLocalConfig().getInt("port");
  }

  public boolean useRemote() {
    return config.getConfig("mongodb").getBoolean("use-remote");
  }

  private Config getRemoteConfig() {
    return getMongoDBConfig().getConfig("remote");
  }

  private Config getLocalConfig() {
    return getMongoDBConfig().getConfig("local");
  }

  private Config getMongoDBConfig() {
    return config.getConfig("mongodb");
  }
}
