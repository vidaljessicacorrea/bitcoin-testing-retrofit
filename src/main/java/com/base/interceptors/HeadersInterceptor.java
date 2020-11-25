package com.base.interceptors;

import java.io.IOException;
import java.util.Map;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeadersInterceptor implements Interceptor {

  private Map<String, String> headers;

  public HeadersInterceptor(Map<String, String> headers) {
    this.headers = headers;
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request.Builder builder = chain.request().newBuilder();
    if (this.headers != null && !this.headers.isEmpty()) {
      for (Map.Entry<String, String> header : this.headers.entrySet()) {
        builder.addHeader(header.getKey(), header.getValue());
      }
    }

    return chain.proceed(builder.build());
  }
}