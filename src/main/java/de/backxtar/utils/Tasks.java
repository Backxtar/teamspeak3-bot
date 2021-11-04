package de.backxtar.utils;

import com.github.theholywaffle.teamspeak3.TS3Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Tasks {
    private static final Logger logger = LoggerFactory.getLogger(Tasks.class);
    private final ScheduledExecutorService scheduler;
    private final TS3Query query;

    /* Constructor for instance */
    public Tasks(TS3Query query, int threads) throws IOException {
        this.scheduler = Executors.newScheduledThreadPool(threads);
        this.query = query;
        startTasks();
        loadReader();
    }

    /* Start scheduled tasks */
    private void startTasks() {

    }

    /* Shutdown per console */
    private void loadReader() throws IOException {
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while ((line = reader.readLine()) != null) {
            if (line.equalsIgnoreCase("exit")) {
                if (query.isConnected() && !scheduler.isShutdown()) {
                    scheduler.shutdownNow();
                    query.exit();
                    //SqlManager.disconnect();
                    logger.info("Bot offline.");
                    System.exit(0);
                } else logger.info("Can not shutdown! Please terminate the screen.");
            }
        }
    }

    /* Getter */
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }
}
