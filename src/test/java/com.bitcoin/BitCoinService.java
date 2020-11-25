package com.bitcoin;

import com.base.commons.JSONHelper;
import com.google.gson.Gson;
import org.junit.Assert;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;

public class BitCoinService {

    private static final String GETNEWADDRESS_FILE = "getnewaddress.json";
    private static final String GENERATETOADDRESS_FILE = "generatetoaddress.json";
    private static final String GETBALANCE_FILE = "getbalance.json";
    private static final String SENDTOADDRESS_FILE = "sendtoaddress.json";
    private static final String GETUNCONFIRMBALANCE_FILE = "getunconfirmedbalance.json";

    private String authorization;

    public BitCoinService(String authorization) {
        this.authorization = authorization;
    }

    private String readFileFrom(String fileName){
        String filePath = String.format("%s/%s/%s", System.getProperty("user.dir"), System.getProperty("host.config","src/test/resources"), fileName);
        return filePath;
    }

    private BodyJRPC convertFileJson(String filePath){
        BodyJRPC body = new Gson().fromJson(JSONHelper.readFromFile(filePath),BodyJRPC.class);
        return body;
    }

    public String getnewaddress(BitCoin bitCoinApi) throws IOException {
        String filePath = readFileFrom(GETNEWADDRESS_FILE);
        BodyJRPC body = convertFileJson(filePath);
        Response<ResponseBitCoin> response = bitCoinApi.getnewaddress(authorization,body).execute();
        Assert.assertEquals("Response code should be 200.",200,response.code());
        String addressId1 = response.body().getResult();
        return addressId1;

    }

    public void generatetoaddress(BitCoin bitCoinApi,String addressId, Integer nblocks) throws IOException {
        String filePath = readFileFrom(GENERATETOADDRESS_FILE);
        BodyJRPC body = convertFileJson(filePath);
        Integer newNblock = Integer.parseInt(body.getParams().get(0).toString().replace("N_BLOCK",nblocks.toString()));
        String newAddress = body.getParams().get(1).toString().replace("ADDRESS_ID_NODE_A",addressId);
        ArrayList<Object> newParams = new ArrayList<>();
        newParams.add(newNblock);
        newParams.add(newAddress);
        body.setParams(newParams);
        Response<ResponseCreateAddressBitCoin> response = bitCoinApi.generatetoaddress(authorization,body).execute();
        Assert.assertEquals("Response code should be 200.",200,response.code());

    }

    public Double getbalance(BitCoin bitCoinApi, Integer nblocks) throws IOException {
        String filePath = readFileFrom(GETBALANCE_FILE);
        BodyJRPC body = convertFileJson(filePath);
        String actualValue = body.getParams().get(0).toString();
        Integer newNblock = Integer.parseInt(body.getParams().get(1).toString().replace("N_BLOCKS",nblocks.toString()));
        ArrayList<Object> newParams = new ArrayList<>();
        newParams.add(actualValue);
        newParams.add(newNblock);
        body.setParams(newParams);
        Response<ResponseBitCoin> response = bitCoinApi.getbalance(authorization,body).execute();
        Assert.assertEquals("Response code should be 200.",200,response.code());
        Double balance = Double.parseDouble(response.body().getResult());
        return balance;

    }

    public void sendtoaddress(BitCoin bitCoinApi,String addressId, Double amount) throws IOException {
        String filePath = readFileFrom(SENDTOADDRESS_FILE);
        BodyJRPC body = convertFileJson(filePath);
        String newAddress = body.getParams().get(0).toString().replace("ADDRESS_ID_NODE_B",addressId);
        Double newAmount = Double.parseDouble(body.getParams().get(1).toString().replace("AMOUNT",amount.toString()));
        ArrayList<Object> newParams = new ArrayList<>();
        newParams.add(newAddress);
        newParams.add(newAmount);
        body.setParams(newParams);
        Response<ResponseBitCoin> response = bitCoinApi.sendtoaddress(authorization,body).execute();
        Assert.assertEquals("Response code should be 200.",200,response.code());

    }

    public Double getunconfirmedbalance(BitCoin bitCoinApi) throws IOException {
        String filePath = readFileFrom(GETUNCONFIRMBALANCE_FILE);
        BodyJRPC body = convertFileJson(filePath);
        Response<ResponseBitCoin> response = bitCoinApi.getunconfirmedbalance(authorization,body).execute();
        Assert.assertEquals("Response code should be 200.",200,response.code());
        Double unconfirmedbalance = Double.parseDouble(response.body().getResult());
        return unconfirmedbalance;
    }
}
