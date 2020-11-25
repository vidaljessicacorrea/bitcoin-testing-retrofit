package com.bitcoin;

import org.junit.Assert;

import java.io.IOException;

public class NodeB {

    public BitCoinService services = new BitCoinService("Basic bm9kZUI6c2VjcmV0cGFzc3dvcmQy");

    public String createAddress(BitCoin bitCoinApi) throws IOException {
        String addressId1 = services.getnewaddress(bitCoinApi);
        Double oldbalance = services.getbalance(bitCoinApi,6);

        return addressId1;
    }

    public void verifyBalance(BitCoin bitCoinApi,Double oldBalance)throws IOException {
        Double unConfirm = services.getunconfirmedbalance(bitCoinApi);
        Double balance = services.getbalance(bitCoinApi,6);
        Assert.assertTrue("No Transfer Made",oldBalance<balance);
    }
}
