package com.base.configuration;

import com.google.gson.Gson;
import org.pmw.tinylog.Logger;
import com.base.commons.JSONHelper;
import com.base.configuration.dao.Config;
import com.base.configuration.dao.HostConfig;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HostConfiguration {

  private static final String FILE_NAME = "host.json";
  private static Config config = null;

  private static void readConfiguration() {
    String filePath = String.format("%s/%s/%s", System.getProperty("user.dir"), System.getProperty("host.config","src/test/resources"), HostConfiguration.FILE_NAME);
    if (Files.notExists(Paths.get(filePath))) {
      Logger.error("Host configuration file does not exist: {}", filePath);
    } else {
      config = new Gson().fromJson(JSONHelper.readFromFile(filePath), Config.class);
    }
  }

  public static List<URL> hostURL() {
    return HostConfiguration.hostURL(null);
  }

  public static List<URL> hostURL(String name) {
    if (config == null) {
      readConfiguration();
    }
    if (!config.hosts.isEmpty()) {
      List<HostConfig> hostConfig;
      hostConfig = config.hosts;
      try {
        if (hostConfig == null) {
          throw new Exception("Host config was not found by name: " + name);
        }
        List<URL> listURL = new ArrayList<>();
        hostConfig.forEach(h -> {
          try {
            listURL.add(new URL(h.host));
          } catch (MalformedURLException e) {
            e.printStackTrace();
          }
        });
        return listURL;
      } catch (Exception e) {
        Logger.error(e, "There was a problem initializing the host URL provided", e);
      }
    } else {
      Logger.error("No host configuration available in host file");
    }
    return null;
  }
}
