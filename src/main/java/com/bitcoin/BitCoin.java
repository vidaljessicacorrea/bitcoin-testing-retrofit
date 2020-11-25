package com.bitcoin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface BitCoin {

    @Headers({
            "Accept: application/json",
            "User-Agent: Retrofit-Sample-App",
            "Content-Type: application/json",
            "Connection: keep-alive"
    })

    @POST("/")
    public Call<ResponseBitCoin> getnewaddress(@Header("Authorization") String authorization, @Body BodyJRPC jsonrpc);

    @POST("/")
    public Call<ResponseCreateAddressBitCoin> generatetoaddress(@Header("Authorization") String authorization,@Body BodyJRPC jsonrpc);

    @POST("/")
    public Call<ResponseBitCoin> getbalance(@Header("Authorization") String authorization,@Body BodyJRPC jsonrpc);

    @POST("/")
    public Call<ResponseBitCoin> sendtoaddress(@Header("Authorization") String authorization,@Body BodyJRPC jsonrpc);

    @POST("/")
    public Call<ResponseBitCoin> getunconfirmedbalance(@Header("Authorization") String authorization,@Body BodyJRPC jsonrpc);

}
