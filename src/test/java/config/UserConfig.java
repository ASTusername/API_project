package config;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({
        "classpath:user.properties",
})
public interface UserConfig extends Config {
    @Key("userName")
    String getUserName();
    @Key("password")
    String getPassword();
    @Key("userId")
    String getUserId();

}
