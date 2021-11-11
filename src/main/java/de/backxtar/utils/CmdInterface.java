package de.backxtar.utils;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public interface CmdInterface {
    /**
     * Interface for run method
     * @param cmdValues cmdArgs
     * @param api api object
     * @param e event object
     * @param c client object
     */
    default void run(String[] cmdValues, TS3ApiAsync api, TextMessageEvent e, Client c) {}

    /**
     * Interface for sendHelp
     * @param api api object
     * @param c client object
     */
    default void sendHelp(TS3ApiAsync api, Client c) {}
}
