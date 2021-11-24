package de.backxtar.systems;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import de.backxtar.TSpeak3;
import de.backxtar.utils.ConfigJSON;

import java.util.HashMap;
import java.util.List;

public class MoveAfk {
    private static final HashMap<String, User> userMap = new HashMap<>();

    /**
     * User container
     */
    public static class User {
        private long timeStamp;
        private String channelName;
        private int channelID;

        public User(long timeStamp, String channelName, int channelID) {
            this.timeStamp = timeStamp;
            this.channelName = channelName;
            this.channelID = channelID;
        }

        /**
         * TimeStamp getter
         * @return timestamp as long
         */
        public long getTimeStamp() {
            return timeStamp;
        }

        /**
         * ChannelName getter
         * @return ChannelName as string
         */
        public String getChannelName() {
            return channelName;
        }

        /**
         * ChannelID getter
         * @return ChannelID as int
         */
        public int getChannelID() {
            return channelID;
        }
    }

    /**
     * Check if users are afk
     * @param api object
     * @throws InterruptedException if async failed
     */
    public static void checkAfk(TS3ApiAsync api) throws InterruptedException {
        ConfigJSON.Config config = TSpeak3.get_tSpeak3().getBotConfig().getConfig();
        List<Integer> afkChannels = config.getAfk_CH_ID();
        if (!config.getSystems().get("AFK_MOVER") || afkChannels.isEmpty()) return;

        api.getClients().get()
                .forEach(client -> {

                });
    }
}
