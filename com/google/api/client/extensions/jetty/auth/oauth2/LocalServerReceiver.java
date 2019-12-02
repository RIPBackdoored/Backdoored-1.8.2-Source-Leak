package com.google.api.client.extensions.jetty.auth.oauth2;

import com.google.api.client.extensions.java6.auth.oauth2.*;
import java.util.concurrent.*;
import com.google.api.client.util.*;
import org.mortbay.jetty.handler.*;
import javax.servlet.http.*;
import org.mortbay.jetty.*;
import java.io.*;

public final class LocalServerReceiver implements VerificationCodeReceiver
{
    private static final String LOCALHOST = "localhost";
    private static final String CALLBACK_PATH = "/Callback";
    private Server server;
    String code;
    String error;
    final Semaphore waitUnlessSignaled;
    private int port;
    private final String host;
    private final String callbackPath;
    private String successLandingPageUrl;
    private String failureLandingPageUrl;
    
    public LocalServerReceiver() {
        this("localhost", -1, "/Callback", null, null);
    }
    
    LocalServerReceiver(final String a1, final int a2, final String a3, final String a4) {
        this(a1, a2, "/Callback", a3, a4);
    }
    
    LocalServerReceiver(final String a1, final int a2, final String a3, final String a4, final String a5) {
        super();
        this.waitUnlessSignaled = new Semaphore(0);
        this.host = a1;
        this.port = a2;
        this.callbackPath = a3;
        this.successLandingPageUrl = a4;
        this.failureLandingPageUrl = a5;
    }
    
    public String getRedirectUri() throws IOException {
        this.server = new Server((this.port != -1) ? this.port : 0);
        final Connector v0 = this.server.getConnectors()[0];
        v0.setHost(this.host);
        this.server.addHandler((Handler)new CallbackHandler());
        try {
            this.server.start();
            this.port = v0.getLocalPort();
        }
        catch (Exception v2) {
            Throwables.propagateIfPossible(v2);
            throw new IOException(v2);
        }
        final String value = String.valueOf(String.valueOf(this.host));
        final int port = this.port;
        final String value2 = String.valueOf(String.valueOf(this.callbackPath));
        return new StringBuilder(19 + value.length() + value2.length()).append("http://").append(value).append(":").append(port).append(value2).toString();
    }
    
    public String waitForCode() throws IOException {
        this.waitUnlessSignaled.acquireUninterruptibly();
        if (this.error != null) {
            final String value = String.valueOf(String.valueOf(this.error));
            throw new IOException(new StringBuilder(28 + value.length()).append("User authorization failed (").append(value).append(")").toString());
        }
        return this.code;
    }
    
    public void stop() throws IOException {
        this.waitUnlessSignaled.release();
        if (this.server != null) {
            try {
                this.server.stop();
            }
            catch (Exception v1) {
                Throwables.propagateIfPossible(v1);
                throw new IOException(v1);
            }
            this.server = null;
        }
    }
    
    public String getHost() {
        return this.host;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public String getCallbackPath() {
        return this.callbackPath;
    }
    
    static /* synthetic */ String access$000(final LocalServerReceiver a1) {
        return a1.successLandingPageUrl;
    }
    
    static /* synthetic */ String access$100(final LocalServerReceiver a1) {
        return a1.failureLandingPageUrl;
    }
    
    public static final class Builder
    {
        private String host;
        private int port;
        private String successLandingPageUrl;
        private String failureLandingPageUrl;
        private String callbackPath;
        
        public Builder() {
            super();
            this.host = "localhost";
            this.port = -1;
            this.callbackPath = "/Callback";
        }
        
        public LocalServerReceiver build() {
            return new LocalServerReceiver(this.host, this.port, this.callbackPath, this.successLandingPageUrl, this.failureLandingPageUrl);
        }
        
        public String getHost() {
            return this.host;
        }
        
        public Builder setHost(final String a1) {
            this.host = a1;
            return this;
        }
        
        public int getPort() {
            return this.port;
        }
        
        public Builder setPort(final int a1) {
            this.port = a1;
            return this;
        }
        
        public String getCallbackPath() {
            return this.callbackPath;
        }
        
        public Builder setCallbackPath(final String a1) {
            this.callbackPath = a1;
            return this;
        }
        
        public Builder setLandingPages(final String a1, final String a2) {
            this.successLandingPageUrl = a1;
            this.failureLandingPageUrl = a2;
            return this;
        }
    }
    
    class CallbackHandler extends AbstractHandler
    {
        final /* synthetic */ LocalServerReceiver this$0;
        
        CallbackHandler(final LocalServerReceiver this$0) {
            this.this$0 = this$0;
            super();
        }
        
        public void handle(final String a1, final HttpServletRequest a2, final HttpServletResponse a3, final int a4) throws IOException {
            if (!"/Callback".equals(a1)) {
                return;
            }
            try {
                ((Request)a2).setHandled(true);
                this.this$0.error = a2.getParameter("error");
                this.this$0.code = a2.getParameter("code");
                if (this.this$0.error == null && this.this$0.successLandingPageUrl != null) {
                    a3.sendRedirect(this.this$0.successLandingPageUrl);
                }
                else if (this.this$0.error != null && this.this$0.failureLandingPageUrl != null) {
                    a3.sendRedirect(this.this$0.failureLandingPageUrl);
                }
                else {
                    this.writeLandingHtml(a3);
                }
                a3.flushBuffer();
            }
            finally {
                this.this$0.waitUnlessSignaled.release();
            }
        }
        
        private void writeLandingHtml(final HttpServletResponse a1) throws IOException {
            a1.setStatus(200);
            a1.setContentType("text/html");
            final PrintWriter v1 = a1.getWriter();
            v1.println("<html>");
            v1.println("<head><title>OAuth 2.0 Authentication Token Received</title></head>");
            v1.println("<body>");
            v1.println("Received verification code. You may now close this window.");
            v1.println("</body>");
            v1.println("</html>");
            v1.flush();
        }
    }
}
