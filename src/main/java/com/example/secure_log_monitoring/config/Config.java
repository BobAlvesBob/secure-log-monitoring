package com.example.secure_log_monitoring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Value("${blockchain.node.url:http://localhost:8545}")
    public String BLOCKCHAIN_NODE_URL;

    @Value("${log.file.path:/var/log/syslog}")
    public String LOG_FILE_PATH;
}
