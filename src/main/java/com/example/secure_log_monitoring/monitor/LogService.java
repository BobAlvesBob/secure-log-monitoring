package com.example.secure_log_monitoring.monitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final LogMonitor logMonitor;

    @Autowired
    public LogService(LogMonitor logMonitor) {
        this.logMonitor = logMonitor;
    }

    public void startMonitoring() {
        logMonitor.startMonitoring();
    }
}
