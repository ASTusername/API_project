package config;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({
        "classpath:user.properties",
})
public interface UserConfig extends Config {
    @Key("userName")
    String getUserName();@Config.Sources({"classpath:${env}.properties"})
    public interface WebConfig extends Config {
        @Key("baseUrl")
        @DefaultValue("https://www.rusmuseum.ru/")
        String baseUrl();

        @Key("browser")
        @DefaultValue("chrome")
        String browser();

        @Key("browserVersion")
        @DefaultValue("100.0")
        String browserVersion();

        @Key("browserSize")
        @DefaultValue("1920x1080")
        String browserSize();

        @Key("isRemote")
        @DefaultValue("false")
        boolean isRemote();

        @Key("remoteUrl")
        @DefaultValue("https://user1:1234@selenoid.autotests.cloud/wd/hub")
        URL remoteUrl();
    }
    @Key("password")
    String getPassword();
    @Key("userId")
    String getUserId();

}
