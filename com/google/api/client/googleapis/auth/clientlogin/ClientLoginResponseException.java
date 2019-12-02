package com.google.api.client.googleapis.auth.clientlogin;

import com.google.api.client.http.*;
import com.google.api.client.util.*;

@Beta
public class ClientLoginResponseException extends HttpResponseException
{
    private static final long serialVersionUID = 4974317674023010928L;
    private final transient ClientLogin.ErrorInfo details;
    
    ClientLoginResponseException(final Builder a1, final ClientLogin.ErrorInfo a2) {
        super(a1);
        this.details = a2;
    }
    
    public final ClientLogin.ErrorInfo getDetails() {
        return this.details;
    }
}
