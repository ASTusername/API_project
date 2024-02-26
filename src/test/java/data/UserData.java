package data;

import config.UserConfig;
import org.aeonbits.owner.ConfigFactory;

public class UserData {
    private static final UserConfig config = ConfigFactory.create(UserConfig.class, System.getProperties());
    public static final String USERNAME = config.getUserName();
    public static final String PASSWORD = config.getPassword();
}
