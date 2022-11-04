package ch.heigvd.amt.team10;

import io.github.cdimascio.dotenv.Dotenv;
import org.apache.log4j.BasicConfigurator;

public class Env {
    private static Env instance;
    private final Dotenv env;

    private Env() {
        BasicConfigurator.configure();
        env = Dotenv.configure().ignoreIfMissing().systemProperties().load();
    }

    private static Env getInstance() {
        if (instance == null) instance = new Env();
        return instance;
    }

    public static String get(String key) {
        return getInstance().env.get(key);
    }

    public static String get(String key, String defaultValue) {
        return getInstance().env.get(key, defaultValue);
    }
}
