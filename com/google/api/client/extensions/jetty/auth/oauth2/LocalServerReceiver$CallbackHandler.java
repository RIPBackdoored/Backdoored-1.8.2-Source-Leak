package com.google.api.client.extensions.jetty.auth.oauth2;

import org.mortbay.jetty.handler.*;
import javax.servlet.http.*;
import org.mortbay.jetty.*;
import java.io.*;

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
            if (this.this$0.error == null && LocalServerReceiver.access$000(this.this$0) != null) {
                a3.sendRedirect(LocalServerReceiver.access$000(this.this$0));
            }
            else if (this.this$0.error != null && LocalServerReceiver.access$100(this.this$0) != null) {
                a3.sendRedirect(LocalServerReceiver.access$100(this.this$0));
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
