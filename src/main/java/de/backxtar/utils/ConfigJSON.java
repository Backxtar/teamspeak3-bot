package de.backxtar.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ConfigJSON {
    private final File json, readMe;
    private final Config config;

    public ConfigJSON() throws IOException {
        this.config = new Config();
        this.json = new File("bot_config.json");
        this.readMe = new File("README.txt");
        loadFromJSON();
        if (!checkREADME()) throw new IOException("Please accept the TERMS OF USE!");
    }

    public class Config {
        /* Style */
        public String main_Color                            = "#806BE3";
        public String second_Color                          = "#49B5CB";

        /* Teamspeak Login */
        public String ts3_Host                              = "IP OR 127.0.0.1";
        public String ts3_User                              = "YOUR_QUERY_USER";
        public String ts3_Passwd                            = "YOUR_QUERY_PASSWORD";
        public String ts3_Nick                              = "Der Geraet (Bot)";

        /* Database Login */
        public String db_Host                               = "IP OR 127.0.0.1";
        public String db_Name                               = "YOUR_DB_NAME";
        public String db_User                               = "YOUR_DB_USER";
        public String db_Passwd                             = "YOUR_DB_PASSWORD";

        /* Bot Setup */
        public String prefix                                = "!";
        public String lang                                  = "GER";
        public String guild_ID                              = "YOUR_GW2_GUILD_KEY";
        public String guild_Leader_Api_Key                  = "A_GUILD_LEADERS_API_KEY";
        public int default_CH_ID                            = 1;

        /* System Status */
        public HashMap<String, Boolean> systems             = new HashMap<>() {{
            put("AFK_MOVER", false);
            put("ARC_DPS", false);
            put("INFO_CHANNEL", false);
            put("SERVER_MESSAGE", false);
            put("CLIENT_DESC", false);
            put("CLIENT_SUPPORT", false);
            put("GW2_DAILIES", false);
            put("GW2_TP_CHECK", false);
            put("GW2_GUILD_INFO", false);
            put("GW2_TS3_SYNC", false);
            put("TEMP_CHANNEL", false);
            put("UNWANTED_GUEST", false);
        }};

        /* System Values */
        public List<String> gw_Ranks                        = Arrays.asList("Leader", "Officer", "Member");
        public HashMap<String, Integer> gwRank_tsRank_ID    = new HashMap<>() {{
            put("YOUR_GW2_RANK_1", 1);
            put("YOUR_GW2_RANK_2", 2);
        }};
        public String gm_Day                                = "SAT";
        public String gm_Mes                                = "YOUR_HOST_MESSAGE";
        public int info_CH_ID                               = 2;
        public int tp_CH_ID                                 = 3;
        public int guild_CH_ID                              = 4;
        public int arcDps_CH_ID                             = 5;
        public int daily_CH_ID                              = 6;
        public List<Integer> afk_CH_ID                      = Arrays.asList(7, 8);
        public List<Integer> support_CH_ID                  = Arrays.asList(9, 10);
        public List<Integer> support_Groups                 = Arrays.asList(11, 12);
        public List<Integer> temp_CH_ID                     = Arrays.asList(13, 14);
    }

    private Config loadFromJSON() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String read = "#README - Teamspeak3-Bot by Backxtar\n" +
                "#Note: This bot is still under development!\n\n" +
                "#Step 1: Make sure Java 17 is installed!\n" +
                "#Step 2: Edit the " + json.getName() + ".\n" +
                "#Step 3: Accept the TERMS OF USE by changing the value from FALSE to TRUE.\n" +
                "#Step 4: Run the bot with terminal ()-> java -jar Teamspeak3Bot.jar\n" +
                "#DANGER: YOU NEED TO CHANGE THE VALUES, NOT THE KEYS! EXCEPTIONS: systems & gwRank_tsRank_ID!\n\n" +
                "TERMS_OF_USE = false";

        if (!json.exists() && !readMe.exists()) {
            if (json.createNewFile() & readMe.createNewFile()) {
                gson.toJson(config, new FileWriter(json));
                BufferedWriter writer = new BufferedWriter(new FileWriter(readMe));
                writer.write(read);
                writer.close();
                throw new IOException(json.getName() + " + " + readMe.getName() + " created!");
            }
        } if (!json.exists() && readMe.exists()) {
            if (json.createNewFile()) {
                gson.toJson(config, new FileWriter(json));
                throw new IOException(json.getName() + " created!");
            }
        } if (json.exists() && !readMe.exists()) {
            if (readMe.createNewFile()) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(readMe));
                writer.write(read);
                writer.close();
                throw new IOException(readMe.getName() + " created!");
            }
        }
        JsonReader reader = new JsonReader(new FileReader(json));
        return gson.fromJson(reader, Config.class);
    }

    private boolean checkREADME() throws IOException {
        Properties properties = new Properties();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(readMe.getName()), StandardCharsets.UTF_8);
        properties.load(reader);
        Enumeration<Object> en = properties.keys();

        while (en.hasMoreElements()) {
            String key = (String) en.nextElement();
            if (key.equalsIgnoreCase("TERMS_OF_USE")) {
                return ((String) properties.get(key)).equalsIgnoreCase("true");
            }
        }
        return false;
    }

    public Config getConfig() {
        return config;
    }

    public File getJson() {
        return json;
    }

    public File getReadMe() {
        return readMe;
    }
}
