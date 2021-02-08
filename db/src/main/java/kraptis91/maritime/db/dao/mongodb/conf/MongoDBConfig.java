package kraptis91.maritime.db.dao.mongodb.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 12/12/2020.
 */
public class MongoDBConfig {

    private static final Logger LOGGER = Logger.getLogger(MongoDBConfig.class.getName());
    private static final Config MONGO_CONFIG;

    static {
        final String confFileName = "mongodb.conf";
        final String confFilePath = SystemUtils.getUserDir().getParentFile().getParent()
            + "/" + confFileName;

        LOGGER.info("Mongo DB conf file name: " + confFileName);
        LOGGER.info("Mongo DB conf file path: " + confFilePath);

        MONGO_CONFIG = ConfigFactory
            .parseFile(new File(confFilePath))
            .resolve();
    }

    private MongoDBConfig() {

    }

    public static String getUser() {
        return useRemote() ? getRemoteConfig().getString("user") : getLocalConfig().getString("user");
    }

    public static char[] getPassword() {
        return useRemote()
            ? getRemoteConfig().getString("password").toCharArray()
            : getLocalConfig().getString("password").toCharArray();
    }

    public static String getHost() {
        return useRemote() ? getRemoteConfig().getString("host") : getLocalConfig().getString("host");
    }

    public static int getPort() {
        return useRemote() ? getRemoteConfig().getInt("port") : getLocalConfig().getInt("port");
    }

    public static boolean useRemote() {
        return MONGO_CONFIG.getConfig("mongodb").getBoolean("use-remote");
    }

    private static Config getRemoteConfig() {
        return getMongoDBConfig().getConfig("remote");
    }

    private static Config getLocalConfig() {
        return getMongoDBConfig().getConfig("local");
    }

    private static Config getMongoDBConfig() {
        return MONGO_CONFIG.getConfig("mongodb");
    }
}
