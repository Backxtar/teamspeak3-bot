package de.backxtar.utils;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public interface CmdInterface {
    /* Default methods for commands */
    default void run(String[] cmdValues, TS3ApiAsync api, TextMessageEvent e, Client c) {}
    default void sendHelp(TS3ApiAsync api, Client c) {}
}
