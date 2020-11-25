package com.bitcoin;

import com.base.EndpointFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class TestBitCoin {

    NodeA nodeA;
    NodeB nodeB;
    BitCoin bitCoinApiA;
    BitCoin bitCoinApiB;

    @Before
    public void before() throws Exception {
        nodeA = new NodeA();
        nodeB = new NodeB();
        bitCoinApiA = new EndpointFactory().getEndpointOrigin(BitCoin.class);
        bitCoinApiB = new EndpointFactory().getEndpointTarget(BitCoin.class);
    }

    @Test
    public void transferBitCoins()throws IOException {
        String addressOrigin = nodeA.generateBitCoin(bitCoinApiA,101);
        String addressDest = nodeB.createAddress(bitCoinApiB);
        nodeA.sendBitCoin(bitCoinApiA,addressOrigin,addressDest,39.99999991,1);
        nodeB.verifyBalance(bitCoinApiB,39.999);
    }
}
