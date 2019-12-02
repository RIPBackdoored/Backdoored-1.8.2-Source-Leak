package org.spongepowered.asm.mixin.transformer.debug;

import java.io.*;
import java.util.concurrent.*;

public class RuntimeDecompilerAsync extends RuntimeDecompiler implements Runnable, Thread.UncaughtExceptionHandler
{
    private final BlockingQueue<File> queue;
    private final Thread thread;
    private boolean run;
    
    public RuntimeDecompilerAsync(final File a1) {
        super(a1);
        this.queue = new LinkedBlockingQueue<File>();
        this.run = true;
        (this.thread = new Thread(this, "Decompiler thread")).setDaemon(true);
        this.thread.setPriority(1);
        this.thread.setUncaughtExceptionHandler(this);
        this.thread.start();
    }
    
    @Override
    public void decompile(final File a1) {
        if (this.run) {
            this.queue.offer(a1);
        }
        else {
            super.decompile(a1);
        }
    }
    
    @Override
    public void run() {
        while (this.run) {
            try {
                final File v1 = this.queue.take();
                super.decompile(v1);
            }
            catch (InterruptedException v3) {
                this.run = false;
            }
            catch (Exception v2) {
                v2.printStackTrace();
            }
        }
    }
    
    @Override
    public void uncaughtException(final Thread a1, final Throwable a2) {
        this.logger.error("Async decompiler encountered an error and will terminate. Further decompile requests will be handled synchronously. {} {}", new Object[] { a2.getClass().getName(), a2.getMessage() });
        this.flush();
    }
    
    private void flush() {
        this.run = false;
        File v1;
        while ((v1 = this.queue.poll()) != null) {
            this.decompile(v1);
        }
    }
}
