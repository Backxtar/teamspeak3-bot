package de.backxtar.utils;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import java.util.concurrent.ConcurrentHashMap;

public class CmdManager {
    private final ConcurrentHashMap<String, CmdInterface> commands;

    public CmdManager() {
        this.commands = new ConcurrentHashMap<>();

        /* Command register */
    }

    /* Run command */
    public boolean runCmd(String[] command, TS3ApiAsync api, TextMessageEvent e, Client c) {
        CmdInterface cmdInterface;

        if ((cmdInterface = this.commands.get(command[0].toLowerCase())) != null) {
            cmdInterface.run(command, api, e, c);
            return true;
        }
        return false;
    }

    /* Split command args */
    public static String[] splitArgs(String msg, int values) {
        return msg.split(" ", values);
    }
}
