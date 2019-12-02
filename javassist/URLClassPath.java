package javassist;

import java.io.*;
import java.net.*;

public class URLClassPath implements ClassPath
{
    protected String hostname;
    protected int port;
    protected String directory;
    protected String packageName;
    
    public URLClassPath(final String a1, final int a2, final String a3, final String a4) {
        super();
        this.hostname = a1;
        this.port = a2;
        this.directory = a3;
        this.packageName = a4;
    }
    
    @Override
    public String toString() {
        return this.hostname + ":" + this.port + this.directory;
    }
    
    @Override
    public InputStream openClassfile(final String v2) {
        try {
            final URLConnection a1 = this.openClassfile0(v2);
            if (a1 != null) {
                return a1.getInputStream();
            }
        }
        catch (IOException ex) {}
        return null;
    }
    
    private URLConnection openClassfile0(final String v2) throws IOException {
        if (this.packageName == null || v2.startsWith(this.packageName)) {
            final String a1 = this.directory + v2.replace('.', '/') + ".class";
            return fetchClass0(this.hostname, this.port, a1);
        }
        return null;
    }
    
    @Override
    public URL find(final String v-1) {
        try {
            final URLConnection a1 = this.openClassfile0(v-1);
            final InputStream v1 = a1.getInputStream();
            if (v1 != null) {
                v1.close();
                return a1.getURL();
            }
        }
        catch (IOException ex) {}
        return null;
    }
    
    @Override
    public void close() {
    }
    
    public static byte[] fetchClass(final String v1, final int v2, final String v3, final String v4) throws IOException {
        final URLConnection v5 = fetchClass0(v1, v2, v3 + v4.replace('.', '/') + ".class");
        final int v6 = v5.getContentLength();
        final InputStream v7 = v5.getInputStream();
        try {
            if (v6 <= 0) {
                final byte[] a1 = ClassPoolTail.readStream(v7);
            }
            else {
                final byte[] a2 = new byte[v6];
                int a3 = 0;
                do {
                    final int a4 = v7.read(a2, a3, v6 - a3);
                    if (a4 < 0) {
                        throw new IOException("the stream was closed: " + v4);
                    }
                    a3 += a4;
                } while (a3 < v6);
            }
        }
        finally {
            v7.close();
        }
        final byte[] v8;
        return v8;
    }
    
    private static URLConnection fetchClass0(final String a3, final int v1, final String v2) throws IOException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: dup            
        //     4: ldc             "http"
        //     6: aload_0         /* a3 */
        //     7: iload_1         /* v1 */
        //     8: aload_2         /* v2 */
        //     9: invokespecial   java/net/URL.<init>:(Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;)V
        //    12: astore_3        /* a1 */
        //    13: goto            28
        //    16: astore          a2
        //    18: new             Ljava/io/IOException;
        //    21: dup            
        //    22: ldc             "invalid URL?"
        //    24: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //    27: athrow         
        //    28: aload_3         /* v3 */
        //    29: invokevirtual   java/net/URL.openConnection:()Ljava/net/URLConnection;
        //    32: astore          v4
        //    34: aload           v4
        //    36: invokevirtual   java/net/URLConnection.connect:()V
        //    39: aload           v4
        //    41: areturn        
        //    Exceptions:
        //  throws java.io.IOException
        //    StackMapTable: 00 02 50 07 00 80 FC 00 0B 07 00 82
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                            
        //  -----  -----  -----  -----  --------------------------------
        //  0      13     16     28     Ljava/net/MalformedURLException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
