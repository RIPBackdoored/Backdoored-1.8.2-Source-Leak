package com.backdoored.commands;

import com.backdoored.utils.*;
import java.io.*;
import java.nio.charset.*;
import org.apache.commons.io.*;
import org.json.*;

public class Import extends CommandBase
{
    public Import() {
        super(new String[] { "import", "importfriends" });
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length == 1) {
            try {
                if (array[0].equalsIgnoreCase("impact")) {
                    final BufferedReader bufferedReader = new BufferedReader(new FileReader("Impact/friends.cfg"));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        final String s = line.split(":")[0];
                        if (!FriendUtils.c(s)) {
                            FriendUtils.a(s);
                            Utils.printMessage("Added '" + s + "' to your friends!", "green");
                        }
                        else {
                            Utils.printMessage("'" + s + "' was already a friend", "red");
                        }
                    }
                    bufferedReader.close();
                    System.out.println("Successfully imported friends");
                }
                else if (array[0].equalsIgnoreCase("wwe")) {
                    final BufferedReader bufferedReader2 = new BufferedReader(new FileReader("WWE/friends.txt"));
                    String line2;
                    while ((line2 = bufferedReader2.readLine()) != null) {
                        final String s2 = line2.split(" ")[0];
                        if (!FriendUtils.c(s2)) {
                            FriendUtils.a(s2);
                            Utils.printMessage("Added '" + s2 + "' to your friends!", "green");
                        }
                        else {
                            Utils.printMessage("'" + s2 + "' was already a friend", "red");
                        }
                    }
                    bufferedReader2.close();
                    System.out.println("Successfully imported friends");
                }
                else if (array[0].equalsIgnoreCase("future")) {
                    final Object[] array2 = new JSONObject(FileUtils.readFileToString(new File(System.getProperty("user.home") + "/Future/friends.json"), Charset.defaultCharset())).getJSONObject("friend-label").keySet().toArray();
                    for (int length = array2.length, i = 0; i < length; ++i) {
                        final String string = array2[i].toString();
                        if (!FriendUtils.c(string)) {
                            FriendUtils.a(string);
                            Utils.printMessage("Added '" + string + "' to your friends!", "green");
                        }
                        else {
                            Utils.printMessage("'" + string + "' was already a friend", "red");
                        }
                    }
                    System.out.println("Successfully imported friends");
                }
            }
            catch (Exception ex) {
                System.out.println("Could not import to friends.txt: " + ex.toString());
                ex.printStackTrace();
                System.out.println(FriendUtils.c());
            }
            return true;
        }
        return false;
    }
    
    @Override
    public String a() {
        return "-import <Impact/WWE only ones supported now>";
    }
}
