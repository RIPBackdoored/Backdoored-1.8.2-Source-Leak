package javassist.util;

import java.io.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.request.*;
import com.sun.jdi.*;
import java.util.*;
import com.sun.jdi.event.*;

public class HotSwapper
{
    private VirtualMachine jvm;
    private MethodEntryRequest request;
    private Map newClassFiles;
    private Trigger trigger;
    private static final String HOST_NAME = "localhost";
    private static final String TRIGGER_NAME;
    
    public HotSwapper(final int a1) throws IOException, IllegalConnectorArgumentsException {
        this(Integer.toString(a1));
    }
    
    public HotSwapper(final String a1) throws IOException, IllegalConnectorArgumentsException {
        super();
        this.jvm = null;
        this.request = null;
        this.newClassFiles = null;
        this.trigger = new Trigger();
        final AttachingConnector v1 = (AttachingConnector)this.findConnector("com.sun.jdi.SocketAttach");
        final Map v2 = v1.defaultArguments();
        v2.get("hostname").setValue("localhost");
        v2.get("port").setValue(a1);
        this.jvm = v1.attach(v2);
        final EventRequestManager v3 = this.jvm.eventRequestManager();
        this.request = methodEntryRequests(v3, HotSwapper.TRIGGER_NAME);
    }
    
    private Connector findConnector(final String v2) throws IOException {
        final List v3 = Bootstrap.virtualMachineManager().allConnectors();
        for (final Connector a1 : v3) {
            if (a1.name().equals(v2)) {
                return a1;
            }
        }
        throw new IOException("Not found: " + v2);
    }
    
    private static MethodEntryRequest methodEntryRequests(final EventRequestManager a1, final String a2) {
        final MethodEntryRequest v1 = a1.createMethodEntryRequest();
        v1.addClassFilter(a2);
        v1.setSuspendPolicy(1);
        return v1;
    }
    
    private void deleteEventRequest(final EventRequestManager a1, final MethodEntryRequest a2) {
        a1.deleteEventRequest((EventRequest)a2);
    }
    
    public void reload(final String a1, final byte[] a2) {
        final ReferenceType v1 = this.toRefType(a1);
        final Map v2 = new HashMap();
        v2.put(v1, a2);
        this.reload2(v2, a1);
    }
    
    public void reload(final Map v2) {
        final Set v3 = v2.entrySet();
        final Iterator v4 = v3.iterator();
        final Map v5 = new HashMap();
        String v6 = null;
        while (v4.hasNext()) {
            final Map.Entry a1 = v4.next();
            v6 = a1.getKey();
            v5.put(this.toRefType(v6), a1.getValue());
        }
        if (v6 != null) {
            this.reload2(v5, v6 + " etc.");
        }
    }
    
    private ReferenceType toRefType(final String a1) {
        final List v1 = this.jvm.classesByName(a1);
        if (v1 == null || v1.isEmpty()) {
            throw new RuntimeException("no such class: " + a1);
        }
        return v1.get(0);
    }
    
    private void reload2(final Map v1, final String v2) {
        synchronized (this.trigger) {
            this.startDaemon();
            this.newClassFiles = v1;
            this.request.enable();
            this.trigger.doSwap();
            this.request.disable();
            final Map a1 = this.newClassFiles;
            if (a1 != null) {
                this.newClassFiles = null;
                throw new RuntimeException("failed to reload: " + v2);
            }
        }
    }
    
    private void startDaemon() {
        new Thread() {
            final /* synthetic */ HotSwapper this$0;
            
            HotSwapper$1() {
                this.this$0 = a1;
                super();
            }
            
            private void errorMsg(final Throwable a1) {
                System.err.print("Exception in thread \"HotSwap\" ");
                a1.printStackTrace(System.err);
            }
            
            @Override
            public void run() {
                EventSet waitEvent = null;
                try {
                    waitEvent = this.this$0.waitEvent();
                    final EventIterator v0 = waitEvent.eventIterator();
                    while (v0.hasNext()) {
                        final Event v2 = v0.nextEvent();
                        if (v2 instanceof MethodEntryEvent) {
                            this.this$0.hotswap();
                            break;
                        }
                    }
                }
                catch (Throwable v3) {
                    this.errorMsg(v3);
                }
                try {
                    if (waitEvent != null) {
                        waitEvent.resume();
                    }
                }
                catch (Throwable v3) {
                    this.errorMsg(v3);
                }
            }
        }.start();
    }
    
    EventSet waitEvent() throws InterruptedException {
        final EventQueue v1 = this.jvm.eventQueue();
        return v1.remove();
    }
    
    void hotswap() {
        final Map v1 = this.newClassFiles;
        this.jvm.redefineClasses(v1);
        this.newClassFiles = null;
    }
    
    static {
        TRIGGER_NAME = Trigger.class.getName();
    }
}
