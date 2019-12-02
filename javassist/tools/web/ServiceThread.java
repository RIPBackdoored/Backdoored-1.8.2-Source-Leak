package javassist.tools.web;

import java.net.*;
import java.io.*;

class ServiceThread extends Thread
{
    Webserver web;
    Socket sock;
    
    public ServiceThread(final Webserver a1, final Socket a2) {
        super();
        this.web = a1;
        this.sock = a2;
    }
    
    @Override
    public void run() {
        try {
            this.web.process(this.sock);
        }
        catch (IOException ex) {}
    }
}
