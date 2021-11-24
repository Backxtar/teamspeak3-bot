package de.backxtar.systems;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import de.backxtar.TSpeak3;
import de.backxtar.utils.ConfigJSON;

import java.util.Arrays;

public class Unwanted {

    /**
     * Check if guests are in home channel
     * @param api object
     * @throws InterruptedException if async failed
     */
    public static void checkGuests(TS3ApiAsync api) throws InterruptedException {
        ConfigJSON.Config config = TSpeak3.get_tSpeak3().getBotConfig().getConfig();
        int guest = api.getServerInfo().get().getDefaultServerGroup();

        api.getClients().get().stream()
                .filter(client -> !client.isServerQueryClient())
                .filter(client -> Arrays.stream(client.getServerGroups()).anyMatch(id -> id != guest))
                .filter(client -> {
                    try {
                        return api.getClientInfo(client.getId()).get().getTimeConnected() >= 30000 &&
                                client.getChannelId() == config.getDefault_CH_ID();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return false;
                }).forEach(client -> {
                    api.kickClientFromServer("message", client.getChannelId());
                    //TODO MULTI-LANGUAGE
                });
    }
}
