package com.base;

import com.base.configuration.HostConfiguration;
import com.base.interceptors.HeadersInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.Map;

public class EndpointFactory {

  private String hostOrigin;
  private Retrofit retrofitOrigin;

  private String hostTarget;
  private Retrofit retrofitTarget;

  public EndpointFactory() {
    this(null);
  }

  public EndpointFactory(Map<String, String> headers) {

    hostOrigin = HostConfiguration.hostURL(System.getProperty("host", null)).get(0).toString();
    hostTarget = HostConfiguration.hostURL(System.getProperty("host", null)).get(1).toString();

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    if (headers != null) {
      builder.addInterceptor(new HeadersInterceptor(headers));
    }

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
    builder.addInterceptor(loggingInterceptor);

    OkHttpClient client = builder.build();

    retrofitOrigin = new Retrofit.Builder()
        .baseUrl(hostOrigin)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build();

    retrofitTarget = new Retrofit.Builder()
            .baseUrl(hostTarget)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();
  }

  public <T> T getEndpointOrigin(Class<T> t) {
    return retrofitOrigin.create(t);
  }

  public <T> T getEndpointTarget(Class<T> t) {
    return retrofitTarget.create(t);
  }
}
