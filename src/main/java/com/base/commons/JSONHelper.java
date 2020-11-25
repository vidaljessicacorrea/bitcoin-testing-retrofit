package com.base.commons;

import org.pmw.tinylog.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;

public class JSONHelper {

  public static String readFromFile(String path) {
    String content = null;
    if (path != null) {
      try {
        content = new String(Files.readAllBytes(Paths.get(path)));
      } catch (IOException e) {
        if (path.isEmpty()) {
          path = "empty";
        }
        Logger.error("Schema or Json file could not be found at: {}", path, e);
      }
    } else {
      Logger.error("The path to the schema or Json file is null");
    }
    return content;
  }

  public static boolean validateResponseSchema(String response, String schema) {
    if (response != null && !response.isEmpty() && schema != null && !schema.isEmpty()) {
      assertThat(response, matchesJsonSchema(schema));
    }
    return true;
  }

}
