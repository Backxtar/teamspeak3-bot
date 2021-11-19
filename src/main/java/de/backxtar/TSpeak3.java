package de.backxtar;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.backxtar.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.sql.SQLException;

public class TSpeak3 {
    private static final Logger log = LoggerFactory.getLogger(TSpeak3.class);
    private static TSpeak3 tSpeak3;
    private final TS3ApiAsync api;
    private final ConfigJSON botConfig;
    private final CmdManager cmdManager;
    private final EventManager eventManager;
    private final Tasks tasks;

    /**
     * Constructor
     * @throws IOException when the config was not successful
     * @throws InterruptedException when the reading was interrupted
     * @throws SQLException when an error in the mysql connection appears
     * @throws ClassNotFoundException when the ts3 object got issues
     */
    public TSpeak3() throws IOException, InterruptedException, SQLException, ClassNotFoundException {
        tSpeak3 = this;
        log.info("Initialize start...");

        /* Configure the bot */
        final TS3Config ts3Config = new TS3Config();
        log.info("Loading config & checking TERMS OF USE");
        this.botConfig = new ConfigJSON();
        ts3Config.setHost(botConfig.getConfig().getTs3_Host());
        ts3Config.setEnableCommunicationsLogging(true);
        ts3Config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        log.info("Bot configured.");

        /* Connect to query */
        final TS3Query query = new TS3Query(ts3Config);
        query.connect();
        log.info("Query connected.");

        /* Get api object from query */
        this.api = query.getAsyncApi();
        api.login(botConfig.getConfig().getTs3_User(), botConfig.getConfig().getTs3_Passwd());
        api.selectVirtualServerById(1);
        api.setNickname(botConfig.getConfig().getTs3_Nick());
        log.info("Joined " + api.getServerInfo().get().getName());

        /* Init interfaces & managers */
        this.cmdManager = new CmdManager();
        this.eventManager = new EventManager(api);
        SQLManager.connect(
                botConfig.getConfig().getDb_Host(),
                botConfig.getConfig().getDb_Name(),
                botConfig.getConfig().getDb_User(),
                botConfig.getConfig().getDb_Passwd()
        );

        /* Load tasks */
        this.tasks = new Tasks(query, 5);
    }

    /**
     * main method
     * @param args for init
     */
    public static void main(String[] args) {
        try {
            new TSpeak3();
        } catch (IOException | InterruptedException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return a ts3 object
     */
    public static TSpeak3 get_tSpeak3() {
        return tSpeak3;
    }

    /**
     * @return an api object
     */
    public TS3ApiAsync getApi() {
        return api;
    }

    /**
     * @return a config object
     */
    public ConfigJSON getBotConfig() {
        return botConfig;
    }

    /**
     * @return an commandManager object
     */
    public CmdManager getCmdManager() {
        return cmdManager;
    }

    /**
     * @return an eventManager object
     */
    public EventManager getEventManager() {
        return eventManager;
    }

    /**
     * @return a task object with all period running tasks
     */
    public Tasks getTasks() {
        return tasks;
    }
}
