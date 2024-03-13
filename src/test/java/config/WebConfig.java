package config;

import org.aeonbits.owner.Config;

import java.net.URL;

@Config.Sources({"classpath:${env}.properties"})
public interface WebConfig extends Config {
    @Key("baseUrl")
    @DefaultValue("https://demoqa.com")
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
/*
@Config.Sources({"classpath:${env}.properties"})
public interface BrowserDriverConfig extends Config {
    @Key("browser")
    @DefaultValue("chrome")
    String getBrowserName();
    @Key("version")
    @DefaultValue("100.0")
    String getBrowserVersion();
    @Key("size")
    @DefaultValue("1920x1280")
    String getBrowserSize();
    @Key("base_url")
    @DefaultValue("https://demoqa.com")
    String getBaseUrl();
    @Key("isRemote")
    @DefaultValue("false")
    boolean isRemote();
    @Key("remoteUrl")
    @DefaultValue("https://user1:1234@selenoid.autotests.cloud/wd/hub")
    URL remoteUrl();
}*/
