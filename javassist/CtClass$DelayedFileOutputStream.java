package javassist;

import java.io.*;

static class DelayedFileOutputStream extends OutputStream
{
    private FileOutputStream file;
    private String filename;
    
    DelayedFileOutputStream(final String a1) {
        super();
        this.file = null;
        this.filename = a1;
    }
    
    private void init() throws IOException {
        if (this.file == null) {
            this.file = new FileOutputStream(this.filename);
        }
    }
    
    @Override
    public void write(final int a1) throws IOException {
        this.init();
        this.file.write(a1);
    }
    
    @Override
    public void write(final byte[] a1) throws IOException {
        this.init();
        this.file.write(a1);
    }
    
    @Override
    public void write(final byte[] a1, final int a2, final int a3) throws IOException {
        this.init();
        this.file.write(a1, a2, a3);
    }
    
    @Override
    public void flush() throws IOException {
        this.init();
        this.file.flush();
    }
    
    @Override
    public void close() throws IOException {
        this.init();
        this.file.close();
    }
}
