package com.base.configuration.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HostConfig {

  @SerializedName("name")
  @Expose
  public String name;
  @SerializedName("host")
  @Expose
  public String host;

}
