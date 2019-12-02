package com.google.api.client.auth.oauth2;

import com.google.api.client.util.*;
import java.io.*;

@Deprecated
@Beta
public interface CredentialStore
{
    boolean load(final String p0, final Credential p1) throws IOException;
    
    void store(final String p0, final Credential p1) throws IOException;
    
    void delete(final String p0, final Credential p1) throws IOException;
}
