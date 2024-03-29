package de.backxtar.utils;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.backxtar.systems.TempChannels;

public class EventManager {
    private final TS3ApiAsync api;
    private final TS3Listener ts3Listener;

    /**
     * Init manager
     * @param api object
     */
    public EventManager(TS3ApiAsync api) {
        this.api = api;
        this.ts3Listener = new TS3Listener() {
            @Override
            public void onTextMessage(TextMessageEvent e) {

            }

            @Override
            public void onClientJoin(ClientJoinEvent e) {

            }

            @Override
            public void onClientLeave(ClientLeaveEvent e) {

            }

            @Override
            public void onServerEdit(ServerEditedEvent e) {

            }

            @Override
            public void onChannelEdit(ChannelEditedEvent e) {

            }

            @Override
            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {

            }

            @Override
            public void onClientMoved(ClientMovedEvent e) {
                int clientID = e.getClientId();

                try {
                    if (!api.isClientOnline(clientID).get() || api.getClientInfo(clientID).get().isServerQueryClient()) return;
                    Client client = api.getClientInfo(clientID).get();
                    TempChannels.tempCreate(e, client, api);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onChannelCreate(ChannelCreateEvent e) {

            }

            @Override
            public void onChannelDeleted(ChannelDeletedEvent e) {

            }

            @Override
            public void onChannelMoved(ChannelMovedEvent e) {

            }

            @Override
            public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {

            }

            @Override
            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {

            }
        };
        loadListeners();
    }

    /**
     * load all events
     */
    private void loadListeners() {
        api.registerAllEvents();
        api.addTS3Listeners(ts3Listener);
    }
}
