package com.team1.animalproject.blockchain.utils;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

public class Notification {

    public static void pushNotification(String title, String message, String toolTip, MessageType messageType) throws AWTException {
        // Do a check to ensure SystemTray is supporteed for this OS
        if (SystemTray.isSupported())
        {
            //Obtain only one instance of the SystemTray object
            SystemTray tray = SystemTray.getSystemTray();

            //If the icon is a file
            Image image = Toolkit.getDefaultToolkit().createImage("myImage.png");

            //Alternative (if the icon is on the classpath):
//            Image image = Toolkit.getDefaultToolkit().createImage(Main.class.getClassLoader().getResource("img/xlm.png"));

            TrayIcon trayIcon = new TrayIcon( image,toolTip);

            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);

            //Set tooltip text for the tray icon
            trayIcon.setToolTip(toolTip);

            tray.add(trayIcon);

            // Create our message and what type of Notification this is.
            trayIcon.displayMessage( title,message, messageType);

            // If you want to add a timer for it to go away
            // trayIcon.wait(10000);
        } else {
            //Handle your errors accordingly
            System.err.println("SystemTray is not supported on this OS");
        }
    }
}
