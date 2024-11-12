package com.example.secure_log_monitoring.blockchain;

import com.example.secure_log_monitoring.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;

import javax.annotation.PostConstruct;

import java.io.IOException;

@Service
public class BlockchainClient {

    private Web3j web3j;

    @Autowired
    private Config config;

    @PostConstruct
    public void init() {
        this.web3j = Web3j.build(new HttpService(config.BLOCKCHAIN_NODE_URL));
    }

    public void getClientVersion() {
        try {
            Web3ClientVersion clientVersion = web3j.web3ClientVersion().send();
            System.out.println("Client Version: " + clientVersion.getWeb3ClientVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void submitLogHash(String logHash) {
        // Lógica para enviar o hash à blockchain.
        System.out.println("Enviando hash para blockchain: " + logHash);
    }
}
