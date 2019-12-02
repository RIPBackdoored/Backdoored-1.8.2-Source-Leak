package javassist.tools.web;

import javassist.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class Webserver
{
    private ServerSocket socket;
    private ClassPool classPool;
    protected Translator translator;
    private static final byte[] endofline;
    private static final int typeHtml = 1;
    private static final int typeClass = 2;
    private static final int typeGif = 3;
    private static final int typeJpeg = 4;
    private static final int typeText = 5;
    public String debugDir;
    public String htmlfileBase;
    
    public static void main(final String[] v1) throws IOException {
        if (v1.length == 1) {
            final Webserver a1 = new Webserver(v1[0]);
            a1.run();
        }
        else {
            System.err.println("Usage: java javassist.tools.web.Webserver <port number>");
        }
    }
    
    public Webserver(final String a1) throws IOException {
        this(Integer.parseInt(a1));
    }
    
    public Webserver(final int a1) throws IOException {
        super();
        this.debugDir = null;
        this.htmlfileBase = null;
        this.socket = new ServerSocket(a1);
        this.classPool = null;
        this.translator = null;
    }
    
    public void setClassPool(final ClassPool a1) {
        this.classPool = a1;
    }
    
    public void addTranslator(final ClassPool a1, final Translator a2) throws NotFoundException, CannotCompileException {
        this.classPool = a1;
        (this.translator = a2).start(this.classPool);
    }
    
    public void end() throws IOException {
        this.socket.close();
    }
    
    public void logging(final String a1) {
        System.out.println(a1);
    }
    
    public void logging(final String a1, final String a2) {
        System.out.print(a1);
        System.out.print(" ");
        System.out.println(a2);
    }
    
    public void logging(final String a1, final String a2, final String a3) {
        System.out.print(a1);
        System.out.print(" ");
        System.out.print(a2);
        System.out.print(" ");
        System.out.println(a3);
    }
    
    public void logging2(final String a1) {
        System.out.print("    ");
        System.out.println(a1);
    }
    
    public void run() {
        System.err.println("ready to service...");
    Label_0008_Outer:
        while (true) {
            while (true) {
                try {
                    while (true) {
                        final ServiceThread v1 = new ServiceThread(this, this.socket.accept());
                        v1.start();
                    }
                }
                catch (IOException v2) {
                    this.logging(v2.toString());
                    continue Label_0008_Outer;
                }
                continue;
            }
        }
    }
    
    final void process(final Socket v2) throws IOException {
        final InputStream v3 = new BufferedInputStream(v2.getInputStream());
        final String v4 = this.readLine(v3);
        this.logging(v2.getInetAddress().getHostName(), new Date().toString(), v4);
        while (this.skipLine(v3) > 0) {}
        final OutputStream v5 = new BufferedOutputStream(v2.getOutputStream());
        try {
            this.doReply(v3, v5, v4);
        }
        catch (BadHttpRequest a1) {
            this.replyError(v5, a1);
        }
        v5.flush();
        v3.close();
        v5.close();
        v2.close();
    }
    
    private String readLine(final InputStream a1) throws IOException {
        final StringBuffer v1 = new StringBuffer();
        int v2;
        while ((v2 = a1.read()) >= 0 && v2 != 13) {
            v1.append((char)v2);
        }
        a1.read();
        return v1.toString();
    }
    
    private int skipLine(final InputStream a1) throws IOException {
        int v2 = 0;
        int v3;
        while ((v3 = a1.read()) >= 0 && v3 != 13) {
            ++v2;
        }
        a1.read();
        return v2;
    }
    
    public void doReply(final InputStream v-3, final OutputStream v-2, final String v-1) throws IOException, BadHttpRequest {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ldc             "GET /"
        //     3: invokevirtual   java/lang/String.startsWith:(Ljava/lang/String;)Z
        //     6: ifeq            29
        //     9: aload_3         /* v-1 */
        //    10: iconst_5       
        //    11: aload_3         /* v-1 */
        //    12: bipush          32
        //    14: iconst_5       
        //    15: invokevirtual   java/lang/String.indexOf:(II)I
        //    18: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    21: dup            
        //    22: astore          a2
        //    24: astore          a1
        //    26: goto            37
        //    29: new             Ljavassist/tools/web/BadHttpRequest;
        //    32: dup            
        //    33: invokespecial   javassist/tools/web/BadHttpRequest.<init>:()V
        //    36: athrow         
        //    37: aload           v2
        //    39: ldc             ".class"
        //    41: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //    44: ifeq            53
        //    47: iconst_2       
        //    48: istore          a3
        //    50: goto            114
        //    53: aload           v2
        //    55: ldc             ".html"
        //    57: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //    60: ifne            73
        //    63: aload           v2
        //    65: ldc             ".htm"
        //    67: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //    70: ifeq            79
        //    73: iconst_1       
        //    74: istore          v1
        //    76: goto            114
        //    79: aload           v2
        //    81: ldc             ".gif"
        //    83: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //    86: ifeq            95
        //    89: iconst_3       
        //    90: istore          v1
        //    92: goto            114
        //    95: aload           v2
        //    97: ldc             ".jpg"
        //    99: invokevirtual   java/lang/String.endsWith:(Ljava/lang/String;)Z
        //   102: ifeq            111
        //   105: iconst_4       
        //   106: istore          v1
        //   108: goto            114
        //   111: iconst_5       
        //   112: istore          v1
        //   114: aload           v2
        //   116: invokevirtual   java/lang/String.length:()I
        //   119: istore          v0
        //   121: iload           v1
        //   123: iconst_2       
        //   124: if_icmpne       140
        //   127: aload_0         /* v-4 */
        //   128: aload_2         /* v-2 */
        //   129: aload           v2
        //   131: iload           v0
        //   133: invokespecial   javassist/tools/web/Webserver.letUsersSendClassfile:(Ljava/io/OutputStream;Ljava/lang/String;I)Z
        //   136: ifeq            140
        //   139: return         
        //   140: aload_0         /* v-4 */
        //   141: aload           v2
        //   143: iload           v0
        //   145: invokespecial   javassist/tools/web/Webserver.checkFilename:(Ljava/lang/String;I)V
        //   148: aload_0         /* v-4 */
        //   149: getfield        javassist/tools/web/Webserver.htmlfileBase:Ljava/lang/String;
        //   152: ifnull          179
        //   155: new             Ljava/lang/StringBuilder;
        //   158: dup            
        //   159: invokespecial   java/lang/StringBuilder.<init>:()V
        //   162: aload_0         /* v-4 */
        //   163: getfield        javassist/tools/web/Webserver.htmlfileBase:Ljava/lang/String;
        //   166: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   169: aload           v2
        //   171: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   174: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   177: astore          v2
        //   179: getstatic       java/io/File.separatorChar:C
        //   182: bipush          47
        //   184: if_icmpeq       199
        //   187: aload           v2
        //   189: bipush          47
        //   191: getstatic       java/io/File.separatorChar:C
        //   194: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //   197: astore          v2
        //   199: new             Ljava/io/File;
        //   202: dup            
        //   203: aload           v2
        //   205: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //   208: astore          v4
        //   210: aload           v4
        //   212: invokevirtual   java/io/File.canRead:()Z
        //   215: ifeq            283
        //   218: aload_0         /* v-4 */
        //   219: aload_2         /* v-2 */
        //   220: aload           v4
        //   222: invokevirtual   java/io/File.length:()J
        //   225: iload           v1
        //   227: invokespecial   javassist/tools/web/Webserver.sendHeader:(Ljava/io/OutputStream;JI)V
        //   230: new             Ljava/io/FileInputStream;
        //   233: dup            
        //   234: aload           v4
        //   236: invokespecial   java/io/FileInputStream.<init>:(Ljava/io/File;)V
        //   239: astore          v5
        //   241: sipush          4096
        //   244: newarray        B
        //   246: astore          v6
        //   248: aload           v5
        //   250: aload           v6
        //   252: invokevirtual   java/io/FileInputStream.read:([B)I
        //   255: istore          v0
        //   257: iload           v0
        //   259: ifgt            265
        //   262: goto            277
        //   265: aload_2         /* v-2 */
        //   266: aload           v6
        //   268: iconst_0       
        //   269: iload           v0
        //   271: invokevirtual   java/io/OutputStream.write:([BII)V
        //   274: goto            248
        //   277: aload           v5
        //   279: invokevirtual   java/io/FileInputStream.close:()V
        //   282: return         
        //   283: iload           v1
        //   285: iconst_2       
        //   286: if_icmpne       399
        //   289: aload_0         /* v-4 */
        //   290: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //   293: new             Ljava/lang/StringBuilder;
        //   296: dup            
        //   297: invokespecial   java/lang/StringBuilder.<init>:()V
        //   300: ldc_w           "/"
        //   303: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   306: aload           v3
        //   308: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   311: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   314: invokevirtual   java/lang/Class.getResourceAsStream:(Ljava/lang/String;)Ljava/io/InputStream;
        //   317: astore          v5
        //   319: aload           v5
        //   321: ifnull          399
        //   324: new             Ljava/io/ByteArrayOutputStream;
        //   327: dup            
        //   328: invokespecial   java/io/ByteArrayOutputStream.<init>:()V
        //   331: astore          v6
        //   333: sipush          4096
        //   336: newarray        B
        //   338: astore          v7
        //   340: aload           v5
        //   342: aload           v7
        //   344: invokevirtual   java/io/InputStream.read:([B)I
        //   347: istore          v0
        //   349: iload           v0
        //   351: ifgt            357
        //   354: goto            370
        //   357: aload           v6
        //   359: aload           v7
        //   361: iconst_0       
        //   362: iload           v0
        //   364: invokevirtual   java/io/ByteArrayOutputStream.write:([BII)V
        //   367: goto            340
        //   370: aload           v6
        //   372: invokevirtual   java/io/ByteArrayOutputStream.toByteArray:()[B
        //   375: astore          v8
        //   377: aload_0         /* v-4 */
        //   378: aload_2         /* v-2 */
        //   379: aload           v8
        //   381: arraylength    
        //   382: i2l            
        //   383: iconst_2       
        //   384: invokespecial   javassist/tools/web/Webserver.sendHeader:(Ljava/io/OutputStream;JI)V
        //   387: aload_2         /* v-2 */
        //   388: aload           v8
        //   390: invokevirtual   java/io/OutputStream.write:([B)V
        //   393: aload           v5
        //   395: invokevirtual   java/io/InputStream.close:()V
        //   398: return         
        //   399: new             Ljavassist/tools/web/BadHttpRequest;
        //   402: dup            
        //   403: invokespecial   javassist/tools/web/BadHttpRequest.<init>:()V
        //   406: athrow         
        //    Exceptions:
        //  throws java.io.IOException
        //  throws javassist.tools.web.BadHttpRequest
        //    StackMapTable: 00 13 1D FF 00 07 00 08 07 00 02 07 00 AC 07 00 C1 07 00 AE 00 00 07 00 AE 07 00 AE 00 00 0F 13 05 0F 0F FF 00 02 00 08 07 00 02 07 00 AC 07 00 C1 07 00 AE 00 01 07 00 AE 07 00 AE 00 00 FF 00 19 00 08 07 00 02 07 00 AC 07 00 C1 07 00 AE 01 01 07 00 AE 07 00 AE 00 00 26 13 FE 00 30 07 01 10 07 01 26 07 01 2A 10 0B F9 00 05 FE 00 38 07 00 AC 07 01 40 07 01 2A 10 0C F8 00 1C
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void checkFilename(final String v2, final int v3) throws BadHttpRequest {
        for (int a2 = 0; a2 < v3; ++a2) {
            final char a3 = v2.charAt(a2);
            if (!Character.isJavaIdentifierPart(a3) && a3 != '.' && a3 != '/') {
                throw new BadHttpRequest();
            }
        }
        if (v2.indexOf("..") >= 0) {
            throw new BadHttpRequest();
        }
    }
    
    private boolean letUsersSendClassfile(final OutputStream v2, final String v3, final int v4) throws IOException, BadHttpRequest {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: getfield        javassist/tools/web/Webserver.classPool:Ljavassist/ClassPool;
        //     4: ifnonnull       9
        //     7: iconst_0       
        //     8: ireturn        
        //     9: aload_2         /* v3 */
        //    10: iconst_0       
        //    11: iload_3         /* v4 */
        //    12: bipush          6
        //    14: isub           
        //    15: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //    18: bipush          47
        //    20: bipush          46
        //    22: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
        //    25: astore          v6
        //    27: aload_0         /* v1 */
        //    28: getfield        javassist/tools/web/Webserver.translator:Ljavassist/Translator;
        //    31: ifnull          49
        //    34: aload_0         /* v1 */
        //    35: getfield        javassist/tools/web/Webserver.translator:Ljavassist/Translator;
        //    38: aload_0         /* v1 */
        //    39: getfield        javassist/tools/web/Webserver.classPool:Ljavassist/ClassPool;
        //    42: aload           v6
        //    44: invokeinterface javassist/Translator.onLoad:(Ljavassist/ClassPool;Ljava/lang/String;)V
        //    49: aload_0         /* v1 */
        //    50: getfield        javassist/tools/web/Webserver.classPool:Ljavassist/ClassPool;
        //    53: aload           v6
        //    55: invokevirtual   javassist/ClassPool.get:(Ljava/lang/String;)Ljavassist/CtClass;
        //    58: astore          a1
        //    60: aload           a1
        //    62: invokevirtual   javassist/CtClass.toBytecode:()[B
        //    65: astore          a2
        //    67: aload_0         /* v1 */
        //    68: getfield        javassist/tools/web/Webserver.debugDir:Ljava/lang/String;
        //    71: ifnull          83
        //    74: aload           a1
        //    76: aload_0         /* v1 */
        //    77: getfield        javassist/tools/web/Webserver.debugDir:Ljava/lang/String;
        //    80: invokevirtual   javassist/CtClass.writeFile:(Ljava/lang/String;)V
        //    83: goto            98
        //    86: astore          a3
        //    88: new             Ljavassist/tools/web/BadHttpRequest;
        //    91: dup            
        //    92: aload           a3
        //    94: invokespecial   javassist/tools/web/BadHttpRequest.<init>:(Ljava/lang/Exception;)V
        //    97: athrow         
        //    98: aload_0         /* v1 */
        //    99: aload_1         /* v2 */
        //   100: aload           v5
        //   102: arraylength    
        //   103: i2l            
        //   104: iconst_2       
        //   105: invokespecial   javassist/tools/web/Webserver.sendHeader:(Ljava/io/OutputStream;JI)V
        //   108: aload_1         /* v2 */
        //   109: aload           v5
        //   111: invokevirtual   java/io/OutputStream.write:([B)V
        //   114: iconst_1       
        //   115: ireturn        
        //    Exceptions:
        //  throws java.io.IOException
        //  throws javassist.tools.web.BadHttpRequest
        //    StackMapTable: 00 05 09 FD 00 27 00 07 00 AE FF 00 21 00 06 07 00 02 07 00 C1 07 00 AE 01 07 01 2A 07 00 AE 00 00 FF 00 02 00 06 07 00 02 07 00 C1 07 00 AE 01 00 07 00 AE 00 01 07 01 64 FF 00 0B 00 06 07 00 02 07 00 C1 07 00 AE 01 07 01 2A 07 00 AE 00 00
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  27     83     86     98     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private void sendHeader(final OutputStream a1, final long a2, final int a3) throws IOException {
        a1.write("HTTP/1.0 200 OK".getBytes());
        a1.write(Webserver.endofline);
        a1.write("Content-Length: ".getBytes());
        a1.write(Long.toString(a2).getBytes());
        a1.write(Webserver.endofline);
        if (a3 == 2) {
            a1.write("Content-Type: application/octet-stream".getBytes());
        }
        else if (a3 == 1) {
            a1.write("Content-Type: text/html".getBytes());
        }
        else if (a3 == 3) {
            a1.write("Content-Type: image/gif".getBytes());
        }
        else if (a3 == 4) {
            a1.write("Content-Type: image/jpg".getBytes());
        }
        else if (a3 == 5) {
            a1.write("Content-Type: text/plain".getBytes());
        }
        a1.write(Webserver.endofline);
        a1.write(Webserver.endofline);
    }
    
    private void replyError(final OutputStream a1, final BadHttpRequest a2) throws IOException {
        this.logging2("bad request: " + a2.toString());
        a1.write("HTTP/1.0 400 Bad Request".getBytes());
        a1.write(Webserver.endofline);
        a1.write(Webserver.endofline);
        a1.write("<H1>Bad Request</H1>".getBytes());
    }
    
    static {
        endofline = new byte[] { 13, 10 };
    }
}
