package com.backdoored.commands;

import com.backdoored.utils.*;

public class Friend extends CommandBase
{
    private String[] bc;
    
    public Friend() {
        super(new String[] { "friend", "friends", "f" });
        this.bc = new String[] { "add", "del", "list" };
    }
    
    @Override
    public boolean a(final String[] array) {
        if (!this.a(array, this.bc, "name")) {
            return false;
        }
        if (array[0].equals("add") && !array[array.length - 1].equals("add")) {
            if (!FriendUtils.c(array[1])) {
                FriendUtils.a(array[1]);
                Utils.printMessage("Added '" + array[1] + "' to your friends!", "green");
            }
            else {
                Utils.printMessage("'" + array[1] + "' was already a friend", "red");
            }
            return true;
        }
        if (array[0].equals("del") && !array[array.length - 1].equals("del")) {
            if (FriendUtils.c(array[1])) {
                FriendUtils.b(array[1]);
                Utils.printMessage("Removed '" + array[1] + "' from your friends!", "green");
            }
            else {
                Utils.printMessage("'" + array[1] + "' wasnt a friend", "red");
            }
            return true;
        }
        if (array[0].equals("list")) {
            final StringBuilder sb = new StringBuilder("Friends: ");
            for (int i = 0; i <= FriendUtils.c().size() - 1; ++i) {
                if (i == FriendUtils.c().size() - 1) {
                    sb.append(FriendUtils.c().get(i));
                    break;
                }
                sb.append(FriendUtils.c().get(i)).append(", ");
            }
            Utils.printMessage(sb.toString(), "red");
            return true;
        }
        return false;
    }
    
    @Override
    public String a() {
        return "-friend add cookiedragon234\n-friend del 2b2tnews\n-friend list";
    }
}
