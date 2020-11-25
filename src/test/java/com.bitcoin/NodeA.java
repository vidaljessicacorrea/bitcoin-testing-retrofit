package com.bitcoin;

import org.junit.Assert;

import java.io.IOException;

public class NodeA {

    public BitCoinService services = new BitCoinService("Basic bm9kZUE6c2VjcmV0cGFzc3dvcmQx");

    public String generateBitCoin(BitCoin bitCoinApi,Integer nblocks) throws IOException {
        String addressId1 = services.getnewaddress(bitCoinApi);
        services.generatetoaddress(bitCoinApi,addressId1,nblocks);
        return addressId1;
    }

    public void sendBitCoin(BitCoin bitCoinApi,String origin, String destiny, Double amount, Integer nblocks) throws IOException {
        services.sendtoaddress(bitCoinApi,destiny,amount);
        services.generatetoaddress(bitCoinApi,origin,nblocks);
    }

}
