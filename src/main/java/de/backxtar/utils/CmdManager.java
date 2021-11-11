package de.backxtar.utils;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import java.util.concurrent.ConcurrentHashMap;

public class CmdManager {
    private final ConcurrentHashMap<String, CmdInterface> commands;

    /**
     * Constructor
     */
    public CmdManager() {
        this.commands = new ConcurrentHashMap<>();

        /* Command register */
    }

    /**
     * Run commands
     * @param command arg array
     * @param api api object
     * @param e event object
     * @param c client object
     * @return boolean of execution
     */
    public boolean runCmd(String[] command, TS3ApiAsync api, TextMessageEvent e, Client c) {
        CmdInterface cmdInterface;

        if ((cmdInterface = this.commands.get(command[0].toLowerCase())) != null) {
            cmdInterface.run(command, api, e, c);
            return true;
        }
        return false;
    }

    /**
     * Split command into args
     * @param msg message
     * @param values split after char
     * @return String array
     */
    public static String[] splitArgs(String msg, int values) {
        return msg.split(" ", values);
    }
}
