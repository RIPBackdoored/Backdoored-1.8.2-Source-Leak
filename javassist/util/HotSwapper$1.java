package javassist.util;

import com.sun.jdi.event.*;

class HotSwapper$1 extends Thread {
    final /* synthetic */ HotSwapper this$0;
    
    HotSwapper$1(final HotSwapper a1) {
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
}