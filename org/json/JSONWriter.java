package org.json;

import java.io.*;

public class JSONWriter
{
    private static final int maxdepth = 200;
    private boolean comma;
    protected char mode;
    private final JSONObject[] stack;
    private int top;
    protected Appendable writer;
    
    public JSONWriter(final Appendable a1) {
        super();
        this.comma = false;
        this.mode = 'i';
        this.stack = new JSONObject[200];
        this.top = 0;
        this.writer = a1;
    }
    
    private JSONWriter append(final String v2) throws JSONException {
        if (v2 == null) {
            throw new JSONException("Null pointer");
        }
        if (this.mode != 'o') {
            if (this.mode != 'a') {
                throw new JSONException("Value out of sequence.");
            }
        }
        try {
            if (this.comma && this.mode == 'a') {
                this.writer.append(',');
            }
            this.writer.append(v2);
        }
        catch (IOException a1) {
            throw new JSONException(a1);
        }
        if (this.mode == 'o') {
            this.mode = 'k';
        }
        this.comma = true;
        return this;
    }
    
    public JSONWriter array() throws JSONException {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            this.push(null);
            this.append("[");
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }
    
    private JSONWriter end(final char v1, final char v2) throws JSONException {
        if (this.mode != v1) {
            throw new JSONException((v1 == 'a') ? "Misplaced endArray." : "Misplaced endObject.");
        }
        this.pop(v1);
        try {
            this.writer.append(v2);
        }
        catch (IOException a1) {
            throw new JSONException(a1);
        }
        this.comma = true;
        return this;
    }
    
    public JSONWriter endArray() throws JSONException {
        return this.end('a', ']');
    }
    
    public JSONWriter endObject() throws JSONException {
        return this.end('k', '}');
    }
    
    public JSONWriter key(final String v0) throws JSONException {
        if (v0 == null) {
            throw new JSONException("Null key.");
        }
        if (this.mode == 'k') {
            try {
                final JSONObject a1 = this.stack[this.top - 1];
                if (a1.has(v0)) {
                    throw new JSONException("Duplicate key \"" + v0 + "\"");
                }
                a1.put(v0, true);
                if (this.comma) {
                    this.writer.append(',');
                }
                this.writer.append(JSONObject.quote(v0));
                this.writer.append(':');
                this.comma = false;
                this.mode = 'o';
                return this;
            }
            catch (IOException v) {
                throw new JSONException(v);
            }
        }
        throw new JSONException("Misplaced key.");
    }
    
    public JSONWriter object() throws JSONException {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        if (this.mode == 'o' || this.mode == 'a') {
            this.append("{");
            this.push(new JSONObject());
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced object.");
    }
    
    private void pop(final char a1) throws JSONException {
        if (this.top <= 0) {
            throw new JSONException("Nesting error.");
        }
        final char v1 = (this.stack[this.top - 1] == null) ? 'a' : 'k';
        if (v1 != a1) {
            throw new JSONException("Nesting error.");
        }
        --this.top;
        this.mode = ((this.top == 0) ? 'd' : ((this.stack[this.top - 1] == null) ? 'a' : 'k'));
    }
    
    private void push(final JSONObject a1) throws JSONException {
        if (this.top >= 200) {
            throw new JSONException("Nesting too deep.");
        }
        this.stack[this.top] = a1;
        this.mode = ((a1 == null) ? 'a' : 'k');
        ++this.top;
    }
    
    public static String valueToString(final Object v-1) throws JSONException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnull          12
        //     4: aload_0         /* v-1 */
        //     5: aconst_null    
        //     6: invokevirtual   java/lang/Object.equals:(Ljava/lang/Object;)Z
        //     9: ifeq            15
        //    12: ldc             "null"
        //    14: areturn        
        //    15: aload_0         /* v-1 */
        //    16: instanceof      Lorg/json/JSONString;
        //    19: ifeq            78
        //    22: aload_0         /* v-1 */
        //    23: checkcast       Lorg/json/JSONString;
        //    26: invokeinterface org/json/JSONString.toJSONString:()Ljava/lang/String;
        //    31: astore_1        /* a1 */
        //    32: goto            45
        //    35: astore_2        /* v1 */
        //    36: new             Lorg/json/JSONException;
        //    39: dup            
        //    40: aload_2         /* v1 */
        //    41: invokespecial   org/json/JSONException.<init>:(Ljava/lang/Throwable;)V
        //    44: athrow         
        //    45: aload_1         /* v0 */
        //    46: ifnull          51
        //    49: aload_1         /* v0 */
        //    50: areturn        
        //    51: new             Lorg/json/JSONException;
        //    54: dup            
        //    55: new             Ljava/lang/StringBuilder;
        //    58: dup            
        //    59: invokespecial   java/lang/StringBuilder.<init>:()V
        //    62: ldc             "Bad value from toJSONString: "
        //    64: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    67: aload_1         /* v0 */
        //    68: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    71: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    74: invokespecial   org/json/JSONException.<init>:(Ljava/lang/String;)V
        //    77: athrow         
        //    78: aload_0         /* v-1 */
        //    79: instanceof      Ljava/lang/Number;
        //    82: ifeq            113
        //    85: aload_0         /* v-1 */
        //    86: checkcast       Ljava/lang/Number;
        //    89: invokestatic    org/json/JSONObject.numberToString:(Ljava/lang/Number;)Ljava/lang/String;
        //    92: astore_1        /* v0 */
        //    93: getstatic       org/json/JSONObject.NUMBER_PATTERN:Ljava/util/regex/Pattern;
        //    96: aload_1         /* v0 */
        //    97: invokevirtual   java/util/regex/Pattern.matcher:(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
        //   100: invokevirtual   java/util/regex/Matcher.matches:()Z
        //   103: ifeq            108
        //   106: aload_1         /* v0 */
        //   107: areturn        
        //   108: aload_1         /* v0 */
        //   109: invokestatic    org/json/JSONObject.quote:(Ljava/lang/String;)Ljava/lang/String;
        //   112: areturn        
        //   113: aload_0         /* v-1 */
        //   114: instanceof      Ljava/lang/Boolean;
        //   117: ifne            134
        //   120: aload_0         /* v-1 */
        //   121: instanceof      Lorg/json/JSONObject;
        //   124: ifne            134
        //   127: aload_0         /* v-1 */
        //   128: instanceof      Lorg/json/JSONArray;
        //   131: ifeq            139
        //   134: aload_0         /* v-1 */
        //   135: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   138: areturn        
        //   139: aload_0         /* v-1 */
        //   140: instanceof      Ljava/util/Map;
        //   143: ifeq            163
        //   146: aload_0         /* v-1 */
        //   147: checkcast       Ljava/util/Map;
        //   150: astore_1        /* v0 */
        //   151: new             Lorg/json/JSONObject;
        //   154: dup            
        //   155: aload_1         /* v0 */
        //   156: invokespecial   org/json/JSONObject.<init>:(Ljava/util/Map;)V
        //   159: invokevirtual   org/json/JSONObject.toString:()Ljava/lang/String;
        //   162: areturn        
        //   163: aload_0         /* v-1 */
        //   164: instanceof      Ljava/util/Collection;
        //   167: ifeq            187
        //   170: aload_0         /* v-1 */
        //   171: checkcast       Ljava/util/Collection;
        //   174: astore_1        /* v0 */
        //   175: new             Lorg/json/JSONArray;
        //   178: dup            
        //   179: aload_1         /* v0 */
        //   180: invokespecial   org/json/JSONArray.<init>:(Ljava/util/Collection;)V
        //   183: invokevirtual   org/json/JSONArray.toString:()Ljava/lang/String;
        //   186: areturn        
        //   187: aload_0         /* v-1 */
        //   188: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //   191: invokevirtual   java/lang/Class.isArray:()Z
        //   194: ifeq            209
        //   197: new             Lorg/json/JSONArray;
        //   200: dup            
        //   201: aload_0         /* v-1 */
        //   202: invokespecial   org/json/JSONArray.<init>:(Ljava/lang/Object;)V
        //   205: invokevirtual   org/json/JSONArray.toString:()Ljava/lang/String;
        //   208: areturn        
        //   209: aload_0         /* v-1 */
        //   210: instanceof      Ljava/lang/Enum;
        //   213: ifeq            227
        //   216: aload_0         /* v-1 */
        //   217: checkcast       Ljava/lang/Enum;
        //   220: invokevirtual   java/lang/Enum.name:()Ljava/lang/String;
        //   223: invokestatic    org/json/JSONObject.quote:(Ljava/lang/String;)Ljava/lang/String;
        //   226: areturn        
        //   227: aload_0         /* v-1 */
        //   228: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   231: invokestatic    org/json/JSONObject.quote:(Ljava/lang/String;)Ljava/lang/String;
        //   234: areturn        
        //    Exceptions:
        //  throws org.json.JSONException
        //    StackMapTable: 00 0E 0C 02 53 07 00 8E FC 00 09 07 00 55 05 FA 00 1A FC 00 1D 07 00 55 FA 00 04 14 04 17 17 15 11
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  22     32     35     45     Ljava/lang/Exception;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public JSONWriter value(final boolean a1) throws JSONException {
        return this.append(a1 ? "true" : "false");
    }
    
    public JSONWriter value(final double a1) throws JSONException {
        return this.value((Object)a1);
    }
    
    public JSONWriter value(final long a1) throws JSONException {
        return this.append(Long.toString(a1));
    }
    
    public JSONWriter value(final Object a1) throws JSONException {
        return this.append(valueToString(a1));
    }
}
