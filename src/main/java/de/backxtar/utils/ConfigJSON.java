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

    /**
     * Constructor for init an instance of config
     * @throws IOException if reading fails
     */
    public ConfigJSON() throws IOException {
        this.config = new Config();
        this.json = new File("bot_config.json");
        this.readMe = new File("README.txt");
        loadFromJSON();
        if (!checkREADME()) throw new IOException("Please accept the TERMS OF USE!");
    }

    /**
     * Config class
     */
    public class Config {

        /* Style */
        private String main_Color                            = "#806BE3";
        private String second_Color                          = "#49B5CB";

        /* Teamspeak Login */
        private String ts3_Host                              = "IP OR 127.0.0.1";
        private String ts3_User                              = "YOUR_QUERY_USER";
        private String ts3_Passwd                            = "YOUR_QUERY_PASSWORD";
        private String ts3_Nick                              = "Der Geraet (Bot)";

        /* Database Login */
        private String db_Host                               = "IP OR 127.0.0.1";
        private String db_Name                               = "YOUR_DB_NAME";
        private String db_User                               = "YOUR_DB_USER";
        private String db_Passwd                             = "YOUR_DB_PASSWORD";

        /* Bot Setup */
        private String prefix                                = "!";
        private String lang                                  = "GER";
        private String guild_ID                              = "YOUR_GW2_GUILD_KEY";
        private String guild_Leader_Api_Key                  = "A_GUILD_LEADERS_API_KEY";
        private int default_CH_ID                            = 1;

        /* System Status */
        private HashMap<String, Boolean> systems             = new HashMap<>() {{
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
        private List<String> gw_Ranks                        = Arrays.asList("Leader", "Officer", "Member");
        private HashMap<String, Integer> gwRank_tsRank_ID    = new HashMap<>() {{
            put("YOUR_GW2_RANK_1", 1);
            put("YOUR_GW2_RANK_2", 2);
        }};
        private String gm_Day                                = "SAT";
        private String gm_Mes                                = "YOUR_HOST_MESSAGE";
        private int info_CH_ID                               = 2;
        private int tp_CH_ID                                 = 3;
        private int guild_CH_ID                              = 4;
        private int arcDps_CH_ID                             = 5;
        private int daily_CH_ID                              = 6;
        private List<Integer> afk_CH_ID                      = Arrays.asList(7, 8);
        private List<Integer> support_CH_ID                  = Arrays.asList(9, 10);
        private List<Integer> support_Groups                 = Arrays.asList(11, 12);
        private List<Integer> temp_CH_ID                     = Arrays.asList(13, 14);

        /**
         * Color getter
         * @return color as string
         */
        public String getMain_Color() {
            return main_Color;
        }

        /**
         * SecondColor getter
         * @return color as string
         */
        public String getSecond_Color() {
            return second_Color;
        }

        /**
         * Ts3 IP
         * @return IP as string
         */
        public String getTs3_Host() {
            return ts3_Host;
        }

        /**
         * Ts3 USER
         * @return user as string
         */
        public String getTs3_User() {
            return ts3_User;
        }

        /**
         * Ts3 password
         * @return password as string
         */
        public String getTs3_Passwd() {
            return ts3_Passwd;
        }

        /**
         * Ts3 NICKNAME
         * @return nickname as string
         */
        public String getTs3_Nick() {
            return ts3_Nick;
        }

        /**
         * DB HOST
         * @return db host as string
         */
        public String getDb_Host() {
            return db_Host;
        }

        /**
         * DB NAME
         * @return db name as string
         */
        public String getDb_Name() {
            return db_Name;
        }

        /**
         * DB USER
         * @return db user as string
         */
        public String getDb_User() {
            return db_User;
        }

        /**
         * DB PASSWORD
         * @return db password as string
         */
        public String getDb_Passwd() {
            return db_Passwd;
        }

        /**
         * Bot prefix
         * @return bots prefix as string
         */
        public String getPrefix() {
            return prefix;
        }

        /**
         * Bot language
         * @return lang as string
         */
        public String getLang() {
            return lang;
        }

        /**
         * GW2 GuildID
         * @return guildID as string
         */
        public String getGuild_ID() {
            return guild_ID;
        }

        /**
         * GW2 GuildLeaderAPI_Key
         * @return Leaders API_Key as string
         */
        public String getGuild_Leader_Api_Key() {
            return guild_Leader_Api_Key;
        }

        /**
         * Default ChannelID
         * @return channelID as int
         */
        public int getDefault_CH_ID() {
            return default_CH_ID;
        }

        /**
         * Active Systems
         * @return a hashmap with system as key and boolean as value
         */
        public HashMap<String, Boolean> getSystems() {
            return systems;
        }

        /**
         * GW2 GuildRanks
         * @return guildRanks as List<String>
         */
        public List<String> getGw_Ranks() {
            return gw_Ranks;
        }

        /**
         * Match GW2 Rank with Ts3 Rank
         * @return hashmap with GW2 rank as key and Ts3 rank as value
         */
        public HashMap<String, Integer> getGwRank_tsRank_ID() {
            return gwRank_tsRank_ID;
        }

        /**
         * GuildMission Day
         * @return the day of the guild mission as string
         */
        public String getGm_Day() {
            return gm_Day;
        }

        /**
         * GuildMission Message
         * @return the guildMissions message as string
         */
        public String getGm_Mes() {
            return gm_Mes;
        }

        /**
         * Info ChannelID
         * @return infos channelID as int
         */
        public int getInfo_CH_ID() {
            return info_CH_ID;
        }

        /**
         * Trading Post ChannelID
         * @return tp channelID as int
         */
        public int getTp_CH_ID() {
            return tp_CH_ID;
        }

        /**
         * Guild Stats ChannelID
         * @return gs channelID as string
         */
        public int getGuild_CH_ID() {
            return guild_CH_ID;
        }

        /**
         * ArcDps ChannelID
         * @return arcDps channelID as int
         */
        public int getArcDps_CH_ID() {
            return arcDps_CH_ID;
        }

        /**
         * Dailies ChannelID
         * @return dailies channelID as int
         */
        public int getDaily_CH_ID() {
            return daily_CH_ID;
        }

        /**
         * AFK ChannelIDs
         * @return a list of afkChannelIDs as List<Integer>
         */
        public List<Integer> getAfk_CH_ID() {
            return afk_CH_ID;
        }

        /**
         * Support ChannelIDs
         * @return a list of the support channelIDs as List<Integer>
         */
        public List<Integer> getSupport_CH_ID() {
            return support_CH_ID;
        }

        /**
         * Support GroupIDs
         * @return a list of support groupIDs as List<Integer>
         */
        public List<Integer> getSupport_Groups() {
            return support_Groups;
        }

        /**
         * TempChannelIDs
         * @return a list of all tempChannelIDs as List<Integer>
         */
        public List<Integer> getTemp_CH_ID() {
            return temp_CH_ID;
        }
    }

    /**
     * Load config from JSON
     * @return Config object
     * @throws IOException if reading or creating fails
     */
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

    /**
     * Check readme for accepting terms
     * @return boolean
     * @throws IOException if reading fails
     */
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

    /**
     * @return Config object
     */
    public Config getConfig() {
        return config;
    }

    /**
     * @return File object of JSON
     */
    public File getJson() {
        return json;
    }

    /**
     * @return File object of readme
     */
    public File getReadMe() {
        return readMe;
    }
}
