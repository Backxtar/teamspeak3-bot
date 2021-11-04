package de.backxtar;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.backxtar.utils.CmdManager;
import de.backxtar.utils.ConfigJSON;
import de.backxtar.utils.EventManager;
import de.backxtar.utils.Tasks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class TSpeak3 {
    private static final Logger log = LoggerFactory.getLogger(TSpeak3.class);
    private static TSpeak3 tSpeak3;
    private final TS3ApiAsync api;
    private final ConfigJSON botConfig;
    private final CmdManager cmdManager;
    private final EventManager eventManager;
    private final Tasks tasks;

    public TSpeak3() throws IOException, InterruptedException {
        tSpeak3 = this;
        log.info("Initialize start...");

        /* Configure the bot */
        final TS3Config ts3Config = new TS3Config();
        this.botConfig = new ConfigJSON();
        log.info("Loading " + botConfig.getJson().getName());
        ts3Config.setHost(botConfig.getConfig().ts3_Host);
        ts3Config.setEnableCommunicationsLogging(true);
        ts3Config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        log.info("Bot configured.");

        /* Connect to query */
        final TS3Query query = new TS3Query(ts3Config);
        query.connect();
        log.info("Query connected.");

        /* Get api object from query */
        this.api = query.getAsyncApi();
        api.login(botConfig.getConfig().ts3_User, botConfig.getConfig().ts3_Passwd);
        api.selectVirtualServerById(1);
        api.setNickname(botConfig.getConfig().ts3_Nick);
        log.info("Joined " + api.getServerInfo().get().getName());

        /* Init interfaces & managers */
        this.cmdManager = new CmdManager();
        this.eventManager = new EventManager(api);

        /* Load tasks */
        this.tasks = new Tasks(query, 5);
    }

    /* Main method */
    public static void main(String[] args) {
        try {
            new TSpeak3();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /* Getters & Setters */
    public static TSpeak3 get_tSpeak3() {
        return tSpeak3;
    }

    public TS3ApiAsync getApi() {
        return api;
    }

    public ConfigJSON getBotConfig() {
        return botConfig;
    }

    public CmdManager getCmdManager() {
        return cmdManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public Tasks getTasks() {
        return tasks;
    }
}