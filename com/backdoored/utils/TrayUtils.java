package com.backdoored.utils;

import java.awt.image.*;
import java.awt.*;

public class TrayUtils
{
    private static SystemTray bet;
    private static TrayIcon beu;
    
    public TrayUtils() {
        super();
        try {
            TrayUtils.bet = SystemTray.getSystemTray();
            (TrayUtils.beu = new TrayIcon(new BufferedImage(20, 20, 1), "Tray Demo")).setImageAutoSize(true);
            TrayUtils.beu.setToolTip("Backdoored");
            TrayUtils.bet.add(TrayUtils.beu);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            Utils.printMessage("Could not send notification due to error: " + ex.toString());
        }
    }
    
    public static void sendMessage(final String s) {
        sendMessage("Backdoored", s);
    }
    
    public static void sendMessage(final String s, final String s2) {
        if (TrayUtils.beu == null) {
            new TrayUtils();
        }
        TrayUtils.beu.displayMessage(s, s2, TrayIcon.MessageType.INFO);
    }
}
