package kraptis91.maritime.api.conf;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.SystemUtils;

import java.io.File;
import java.util.logging.Logger;

/**
 * @author Konstantinos Raptis [kraptis at unipi.gr] on 7/12/2020.
 */
public class ServerConfig {

    private static final Logger LOGGER = Logger.getLogger(ServerConfig.class.getName());
    private static final Config SERVER_CONFIG;

    static {
        final String confFileName = "server.conf";
        final String confFilePath = SystemUtils.getUserDir()
            // .getParentFile().getParent()
            + "/" + confFileName;

        LOGGER.info("Mongo DB conf file name: " + confFileName);
        LOGGER.info("Mongo DB conf file path: " + confFilePath);

        SERVER_CONFIG = ConfigFactory
            .parseFile(new File(confFilePath))
            .resolve();
    }

    private ServerConfig() {}

    public static int getPort() {
        return getServerConfig().getInt("port");
    }

    private static Config getServerConfig() {
        return SERVER_CONFIG.getConfig("server");
    }
}
