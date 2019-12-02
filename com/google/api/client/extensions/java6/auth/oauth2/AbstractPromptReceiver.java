package com.google.api.client.extensions.java6.auth.oauth2;

import java.util.*;

public abstract class AbstractPromptReceiver implements VerificationCodeReceiver
{
    public AbstractPromptReceiver() {
        super();
    }
    
    public String waitForCode() {
        String v1;
        do {
            System.out.print("Please enter code: ");
            v1 = new Scanner(System.in).nextLine();
        } while (v1.isEmpty());
        return v1;
    }
    
    public void stop() {
    }
}
