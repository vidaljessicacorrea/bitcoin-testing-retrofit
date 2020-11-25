package com.base.configuration.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Config {

  @SerializedName("host-url")
  @Expose
  public List<HostConfig> hosts = null;
}
