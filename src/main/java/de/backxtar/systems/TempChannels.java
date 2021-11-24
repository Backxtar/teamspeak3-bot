package de.backxtar.systems;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.backxtar.TSpeak3;
import de.backxtar.utils.ConfigJSON;

import java.util.HashMap;
import java.util.Map;

public class TempChannels {

    /**
     * Creates a temp channel if joined channel is in the list
     * @param e ClientMovedEvent object
     * @param c Client object
     * @param api Ts3Api object
     * @throws InterruptedException if some async failed
     */
    public static void tempCreate(ClientMovedEvent e, Client c, TS3ApiAsync api) throws InterruptedException {
        ConfigJSON.Config config = TSpeak3.get_tSpeak3().getBotConfig().getConfig();
        int targetChID = e.getTargetChannelId();

        if (!config.getSystems().get("TEMP_CHANNEL") || config.getTemp_CH_ID().isEmpty() ||
                config.getTemp_CH_ID().stream().noneMatch(id -> id == targetChID)) return;

        ChannelInfo chInfo = api.getChannelInfo(targetChID).get();
        int count = 0;
        String chName = chInfo.getName() + " #" + c.getNickname();

        while (api.getChannelByNameExact(chName, true) != null) {
            count++;
            chName = chInfo.getName() + " #" + c.getNickname() + "(" + count + ")";
        }

        final Map<ChannelProperty, String> properties = new HashMap<>() {{
            put(ChannelProperty.CHANNEL_FLAG_TEMPORARY, "1");
            put(ChannelProperty.CPID, String.valueOf(e.getTargetChannelId()));
            put(ChannelProperty.CHANNEL_CODEC_QUALITY, "10");
        }};

        api.createChannel(chName, properties).onSuccess(newCH -> {
            try {
                api.moveClient(c.getId(), newCH);
                api.moveClient(api.whoAmI().get().getId(), config.getDefault_CH_ID());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });
    }
}
