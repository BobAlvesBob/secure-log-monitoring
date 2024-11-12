package com.example.secure_log_monitoring.monitor;

import com.example.secure_log_monitoring.blockchain.BlockchainClient;
import com.example.secure_log_monitoring.config.Config;
import com.example.secure_log_monitoring.utils.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.*;

@Service
public class LogMonitor {

    @Autowired
    private BlockchainClient blockchainClient;

    @Autowired
    private Config config;

    @PostConstruct
    public void startMonitoring() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(config.LOG_FILE_PATH).getParent();
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

            System.out.println("Monitorando o diretório: " + path);

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        processLogUpdate();
                    }
                }
                key.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void processLogUpdate() {
        try {
            String lastLog = Files.readAllLines(Paths.get(config.LOG_FILE_PATH))
                    .get(Files.readAllLines(Paths.get(config.LOG_FILE_PATH)).size() - 1);
            String logHash = HashUtil.generateHash(lastLog);
            System.out.println("Novo log: " + lastLog);
            System.out.println("Hash gerado: " + logHash);
            blockchainClient.submitLogHash(logHash);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
