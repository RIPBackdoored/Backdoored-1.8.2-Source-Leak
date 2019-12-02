package javassist.bytecode;

import java.io.*;
import java.util.*;
import javassist.*;

public class SignatureAttribute extends AttributeInfo
{
    public static final String tag = "Signature";
    
    SignatureAttribute(final ConstPool a1, final int a2, final DataInputStream a3) throws IOException {
        super(a1, a2, a3);
    }
    
    public SignatureAttribute(final ConstPool a1, final String a2) {
        super(a1, "Signature");
        final int v1 = a1.addUtf8Info(a2);
        final byte[] v2 = { (byte)(v1 >>> 8), (byte)v1 };
        this.set(v2);
    }
    
    public String getSignature() {
        return this.getConstPool().getUtf8Info(ByteArray.readU16bit(this.get(), 0));
    }
    
    public void setSignature(final String a1) {
        final int v1 = this.getConstPool().addUtf8Info(a1);
        ByteArray.write16bit(v1, this.info, 0);
    }
    
    @Override
    public AttributeInfo copy(final ConstPool a1, final Map a2) {
        return new SignatureAttribute(a1, this.getSignature());
    }
    
    @Override
    void renameClass(final String a1, final String a2) {
        final String v1 = renameClass(this.getSignature(), a1, a2);
        this.setSignature(v1);
    }
    
    @Override
    void renameClass(final Map a1) {
        final String v1 = renameClass(this.getSignature(), a1);
        this.setSignature(v1);
    }
    
    static String renameClass(final String a1, final String a2, final String a3) {
        final Map v1 = new HashMap();
        v1.put(a2, a3);
        return renameClass(a1, v1);
    }
    
    static String renameClass(final String v-4, final Map v-3) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: ifnonnull       6
        //     4: aload_0         /* v-4 */
        //     5: areturn        
        //     6: new             Ljava/lang/StringBuilder;
        //     9: dup            
        //    10: invokespecial   java/lang/StringBuilder.<init>:()V
        //    13: astore_2        /* v-2 */
        //    14: iconst_0       
        //    15: istore_3        /* v-1 */
        //    16: iconst_0       
        //    17: istore          v0
        //    19: aload_0         /* v-4 */
        //    20: bipush          76
        //    22: iload           v0
        //    24: invokevirtual   java/lang/String.indexOf:(II)I
        //    27: istore          v1
        //    29: iload           v1
        //    31: ifge            37
        //    34: goto            199
        //    37: new             Ljava/lang/StringBuilder;
        //    40: dup            
        //    41: invokespecial   java/lang/StringBuilder.<init>:()V
        //    44: astore          v2
        //    46: iload           v1
        //    48: istore          v3
        //    50: aload_0         /* v-4 */
        //    51: iinc            v3, 1
        //    54: iload           v3
        //    56: invokevirtual   java/lang/String.charAt:(I)C
        //    59: dup            
        //    60: istore          a1
        //    62: bipush          59
        //    64: if_icmpeq       121
        //    67: aload           v2
        //    69: iload           a1
        //    71: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //    74: pop            
        //    75: iload           a1
        //    77: bipush          60
        //    79: if_icmpne       50
        //    82: aload_0         /* v-4 */
        //    83: iinc            v3, 1
        //    86: iload           v3
        //    88: invokevirtual   java/lang/String.charAt:(I)C
        //    91: dup            
        //    92: istore          a1
        //    94: bipush          62
        //    96: if_icmpeq       110
        //    99: aload           v2
        //   101: iload           a1
        //   103: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   106: pop            
        //   107: goto            82
        //   110: aload           v2
        //   112: iload           a1
        //   114: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   117: pop            
        //   118: goto            50
        //   121: goto            129
        //   124: astore          a2
        //   126: goto            199
        //   129: iload           v3
        //   131: iconst_1       
        //   132: iadd           
        //   133: istore          v0
        //   135: aload           v2
        //   137: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   140: astore          v5
        //   142: aload_1         /* v-3 */
        //   143: aload           v5
        //   145: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   150: checkcast       Ljava/lang/String;
        //   153: astore          v6
        //   155: aload           v6
        //   157: ifnull          196
        //   160: aload_2         /* v-2 */
        //   161: aload_0         /* v-4 */
        //   162: iload_3         /* v-1 */
        //   163: iload           v1
        //   165: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   168: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   171: pop            
        //   172: aload_2         /* v-2 */
        //   173: bipush          76
        //   175: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   178: pop            
        //   179: aload_2         /* v-2 */
        //   180: aload           v6
        //   182: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   185: pop            
        //   186: aload_2         /* v-2 */
        //   187: iload           v4
        //   189: invokevirtual   java/lang/StringBuilder.append:(C)Ljava/lang/StringBuilder;
        //   192: pop            
        //   193: iload           v0
        //   195: istore_3        /* v-1 */
        //   196: goto            19
        //   199: iload_3         /* v-1 */
        //   200: ifne            205
        //   203: aload_0         /* v-4 */
        //   204: areturn        
        //   205: aload_0         /* v-4 */
        //   206: invokevirtual   java/lang/String.length:()I
        //   209: istore          v1
        //   211: iload_3         /* v-1 */
        //   212: iload           v1
        //   214: if_icmpge       229
        //   217: aload_2         /* v-2 */
        //   218: aload_0         /* v-4 */
        //   219: iload_3         /* v-1 */
        //   220: iload           v1
        //   222: invokevirtual   java/lang/String.substring:(II)Ljava/lang/String;
        //   225: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   228: pop            
        //   229: aload_2         /* v-2 */
        //   230: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   233: areturn        
        //    StackMapTable: 00 0D 06 FE 00 0C 07 00 8A 01 01 FC 00 11 01 FD 00 0C 07 00 8A 01 FC 00 1F 01 1B 0A FF 00 02 00 08 07 00 8D 07 00 82 07 00 8A 01 01 01 07 00 8A 01 00 01 07 00 88 FC 00 04 01 FF 00 42 00 05 07 00 8D 07 00 82 07 00 8A 01 01 00 00 02 05 FC 00 17 01
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                 
        //  -----  -----  -----  -----  -------------------------------------
        //  50     121    124    129    Ljava/lang/IndexOutOfBoundsException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private static boolean isNamePart(final int a1) {
        return a1 != 59 && a1 != 60;
    }
    
    public static ClassSignature toClassSignature(final String v1) throws BadBytecode {
        try {
            return parseSig(v1);
        }
        catch (IndexOutOfBoundsException a1) {
            throw error(v1);
        }
    }
    
    public static MethodSignature toMethodSignature(final String v1) throws BadBytecode {
        try {
            return parseMethodSig(v1);
        }
        catch (IndexOutOfBoundsException a1) {
            throw error(v1);
        }
    }
    
    public static ObjectType toFieldSignature(final String v1) throws BadBytecode {
        try {
            return parseObjectType(v1, new Cursor(), false);
        }
        catch (IndexOutOfBoundsException a1) {
            throw error(v1);
        }
    }
    
    public static Type toTypeSignature(final String v1) throws BadBytecode {
        try {
            return parseType(v1, new Cursor());
        }
        catch (IndexOutOfBoundsException a1) {
            throw error(v1);
        }
    }
    
    private static ClassSignature parseSig(final String a1) throws BadBytecode, IndexOutOfBoundsException {
        final Cursor v1 = new Cursor();
        final TypeParameter[] v2 = parseTypeParams(a1, v1);
        final ClassType v3 = parseClassType(a1, v1);
        final int v4 = a1.length();
        final ArrayList v5 = new ArrayList();
        while (v1.position < v4 && a1.charAt(v1.position) == 'L') {
            v5.add(parseClassType(a1, v1));
        }
        final ClassType[] v6 = v5.toArray(new ClassType[v5.size()]);
        return new ClassSignature(v2, v3, v6);
    }
    
    private static MethodSignature parseMethodSig(final String v-6) throws BadBytecode {
        final Cursor cursor = new Cursor();
        final TypeParameter[] typeParams = parseTypeParams(v-6, cursor);
        if (v-6.charAt(cursor.position++) != '(') {
            throw error(v-6);
        }
        final ArrayList list = new ArrayList();
        while (v-6.charAt(cursor.position) != ')') {
            final Type a1 = parseType(v-6, cursor);
            list.add(a1);
        }
        final Cursor cursor2 = cursor;
        ++cursor2.position;
        final Type type = parseType(v-6, cursor);
        final int length = v-6.length();
        final ArrayList v0 = new ArrayList();
        while (cursor.position < length && v-6.charAt(cursor.position) == '^') {
            final Cursor cursor3 = cursor;
            ++cursor3.position;
            final ObjectType v2 = parseObjectType(v-6, cursor, false);
            if (v2 instanceof ArrayType) {
                throw error(v-6);
            }
            v0.add(v2);
        }
        final Type[] v3 = list.toArray(new Type[list.size()]);
        final ObjectType[] v4 = v0.toArray(new ObjectType[v0.size()]);
        return new MethodSignature(typeParams, v3, type, v4);
    }
    
    private static TypeParameter[] parseTypeParams(final String v-3, final Cursor v-2) throws BadBytecode {
        final ArrayList list = new ArrayList();
        if (v-3.charAt(v-2.position) == '<') {
            ++v-2.position;
            while (v-3.charAt(v-2.position) != '>') {
                final int a2 = v-2.position;
                final int v1 = v-2.indexOf(v-3, 58);
                final ObjectType v2 = parseObjectType(v-3, v-2, true);
                final ArrayList v3 = new ArrayList();
                while (v-3.charAt(v-2.position) == ':') {
                    ++v-2.position;
                    final ObjectType a3 = parseObjectType(v-3, v-2, false);
                    v3.add(a3);
                }
                final TypeParameter v4 = new TypeParameter(v-3, a2, v1, v2, v3.toArray(new ObjectType[v3.size()]));
                list.add(v4);
            }
            ++v-2.position;
        }
        return list.toArray(new TypeParameter[list.size()]);
    }
    
    private static ObjectType parseObjectType(final String a2, final Cursor a3, final boolean v1) throws BadBytecode {
        final int v2 = a3.position;
        switch (a2.charAt(v2)) {
            case 'L': {
                return parseClassType2(a2, a3, null);
            }
            case 'T': {
                final int a4 = a3.indexOf(a2, 59);
                return new TypeVariable(a2, v2 + 1, a4);
            }
            case '[': {
                return parseArray(a2, a3);
            }
            default: {
                if (v1) {
                    return null;
                }
                throw error(a2);
            }
        }
    }
    
    private static ClassType parseClassType(final String a1, final Cursor a2) throws BadBytecode {
        if (a1.charAt(a2.position) == 'L') {
            return parseClassType2(a1, a2, null);
        }
        throw error(a1);
    }
    
    private static ClassType parseClassType2(final String a2, final Cursor a3, final ClassType v1) throws BadBytecode {
        final int v2 = ++a3.position;
        char v3;
        do {
            v3 = a2.charAt(a3.position++);
        } while (v3 != '$' && v3 != '<' && v3 != ';');
        final int v4 = a3.position - 1;
        TypeArgument[] v5 = null;
        if (v3 == '<') {
            final TypeArgument[] a4 = parseTypeArgs(a2, a3);
            v3 = a2.charAt(a3.position++);
        }
        else {
            v5 = null;
        }
        final ClassType v6 = ClassType.make(a2, v2, v4, v5, v1);
        if (v3 == '$' || v3 == '.') {
            --a3.position;
            return parseClassType2(a2, a3, v6);
        }
        return v6;
    }
    
    private static TypeArgument[] parseTypeArgs(final String v1, final Cursor v2) throws BadBytecode {
        final ArrayList v3 = new ArrayList();
        char v4;
        while ((v4 = v1.charAt(v2.position++)) != '>') {
            TypeArgument a2 = null;
            if (v4 == '*') {
                final TypeArgument a1 = new TypeArgument(null, '*');
            }
            else {
                if (v4 != '+' && v4 != '-') {
                    v4 = ' ';
                    --v2.position;
                }
                a2 = new TypeArgument(parseObjectType(v1, v2, false), v4);
            }
            v3.add(a2);
        }
        return v3.toArray(new TypeArgument[v3.size()]);
    }
    
    private static ObjectType parseArray(final String a1, final Cursor a2) throws BadBytecode {
        int v1 = 1;
        while (a1.charAt(++a2.position) == '[') {
            ++v1;
        }
        return new ArrayType(v1, parseType(a1, a2));
    }
    
    private static Type parseType(final String a1, final Cursor a2) throws BadBytecode {
        Type v1 = parseObjectType(a1, a2, true);
        if (v1 == null) {
            v1 = new BaseType(a1.charAt(a2.position++));
        }
        return v1;
    }
    
    private static BadBytecode error(final String a1) {
        return new BadBytecode("bad signature: " + a1);
    }
    
    static /* bridge */ BadBytecode access$000(final String a1) {
        return error(a1);
    }
    
    private static class Cursor
    {
        int position;
        
        private Cursor() {
            super();
            this.position = 0;
        }
        
        int indexOf(final String a1, final int a2) throws BadBytecode {
            final int v1 = a1.indexOf(a2, this.position);
            if (v1 < 0) {
                throw error(a1);
            }
            this.position = v1 + 1;
            return v1;
        }
        
        Cursor(final SignatureAttribute$1 a1) {
            this();
        }
    }
    
    public static class ClassSignature
    {
        TypeParameter[] params;
        ClassType superClass;
        ClassType[] interfaces;
        
        public ClassSignature(final TypeParameter[] a1, final ClassType a2, final ClassType[] a3) {
            super();
            this.params = ((a1 == null) ? new TypeParameter[0] : a1);
            this.superClass = ((a2 == null) ? ClassType.OBJECT : a2);
            this.interfaces = ((a3 == null) ? new ClassType[0] : a3);
        }
        
        public ClassSignature(final TypeParameter[] a1) {
            this(a1, null, null);
        }
        
        public TypeParameter[] getParameters() {
            return this.params;
        }
        
        public ClassType getSuperClass() {
            return this.superClass;
        }
        
        public ClassType[] getInterfaces() {
            return this.interfaces;
        }
        
        @Override
        public String toString() {
            final StringBuffer v1 = new StringBuffer();
            TypeParameter.toString(v1, this.params);
            v1.append(" extends ").append(this.superClass);
            if (this.interfaces.length > 0) {
                v1.append(" implements ");
                Type.toString(v1, this.interfaces);
            }
            return v1.toString();
        }
        
        public String encode() {
            final StringBuffer v0 = new StringBuffer();
            if (this.params.length > 0) {
                v0.append('<');
                for (int v2 = 0; v2 < this.params.length; ++v2) {
                    this.params[v2].encode(v0);
                }
                v0.append('>');
            }
            this.superClass.encode(v0);
            for (int v2 = 0; v2 < this.interfaces.length; ++v2) {
                this.interfaces[v2].encode(v0);
            }
            return v0.toString();
        }
    }
    
    public static class MethodSignature
    {
        TypeParameter[] typeParams;
        Type[] params;
        Type retType;
        ObjectType[] exceptions;
        
        public MethodSignature(final TypeParameter[] a1, final Type[] a2, final Type a3, final ObjectType[] a4) {
            super();
            this.typeParams = ((a1 == null) ? new TypeParameter[0] : a1);
            this.params = ((a2 == null) ? new Type[0] : a2);
            this.retType = ((a3 == null) ? new BaseType("void") : a3);
            this.exceptions = ((a4 == null) ? new ObjectType[0] : a4);
        }
        
        public TypeParameter[] getTypeParameters() {
            return this.typeParams;
        }
        
        public Type[] getParameterTypes() {
            return this.params;
        }
        
        public Type getReturnType() {
            return this.retType;
        }
        
        public ObjectType[] getExceptionTypes() {
            return this.exceptions;
        }
        
        @Override
        public String toString() {
            final StringBuffer v1 = new StringBuffer();
            TypeParameter.toString(v1, this.typeParams);
            v1.append(" (");
            Type.toString(v1, this.params);
            v1.append(") ");
            v1.append(this.retType);
            if (this.exceptions.length > 0) {
                v1.append(" throws ");
                Type.toString(v1, this.exceptions);
            }
            return v1.toString();
        }
        
        public String encode() {
            final StringBuffer v0 = new StringBuffer();
            if (this.typeParams.length > 0) {
                v0.append('<');
                for (int v2 = 0; v2 < this.typeParams.length; ++v2) {
                    this.typeParams[v2].encode(v0);
                }
                v0.append('>');
            }
            v0.append('(');
            for (int v2 = 0; v2 < this.params.length; ++v2) {
                this.params[v2].encode(v0);
            }
            v0.append(')');
            this.retType.encode(v0);
            if (this.exceptions.length > 0) {
                for (int v2 = 0; v2 < this.exceptions.length; ++v2) {
                    v0.append('^');
                    this.exceptions[v2].encode(v0);
                }
            }
            return v0.toString();
        }
    }
    
    public static class TypeParameter
    {
        String name;
        ObjectType superClass;
        ObjectType[] superInterfaces;
        
        TypeParameter(final String a1, final int a2, final int a3, final ObjectType a4, final ObjectType[] a5) {
            super();
            this.name = a1.substring(a2, a3);
            this.superClass = a4;
            this.superInterfaces = a5;
        }
        
        public TypeParameter(final String a1, final ObjectType a2, final ObjectType[] a3) {
            super();
            this.name = a1;
            this.superClass = a2;
            if (a3 == null) {
                this.superInterfaces = new ObjectType[0];
            }
            else {
                this.superInterfaces = a3;
            }
        }
        
        public TypeParameter(final String a1) {
            this(a1, null, null);
        }
        
        public String getName() {
            return this.name;
        }
        
        public ObjectType getClassBound() {
            return this.superClass;
        }
        
        public ObjectType[] getInterfaceBound() {
            return this.superInterfaces;
        }
        
        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer(this.getName());
            if (this.superClass != null) {
                sb.append(" extends ").append(this.superClass.toString());
            }
            final int v0 = this.superInterfaces.length;
            if (v0 > 0) {
                for (int v2 = 0; v2 < v0; ++v2) {
                    if (v2 > 0 || this.superClass != null) {
                        sb.append(" & ");
                    }
                    else {
                        sb.append(" extends ");
                    }
                    sb.append(this.superInterfaces[v2].toString());
                }
            }
            return sb.toString();
        }
        
        static void toString(final StringBuffer a2, final TypeParameter[] v1) {
            a2.append('<');
            for (int a3 = 0; a3 < v1.length; ++a3) {
                if (a3 > 0) {
                    a2.append(", ");
                }
                a2.append(v1[a3]);
            }
            a2.append('>');
        }
        
        void encode(final StringBuffer v2) {
            v2.append(this.name);
            if (this.superClass == null) {
                v2.append(":Ljava/lang/Object;");
            }
            else {
                v2.append(':');
                this.superClass.encode(v2);
            }
            for (int a1 = 0; a1 < this.superInterfaces.length; ++a1) {
                v2.append(':');
                this.superInterfaces[a1].encode(v2);
            }
        }
    }
    
    public static class TypeArgument
    {
        ObjectType arg;
        char wildcard;
        
        TypeArgument(final ObjectType a1, final char a2) {
            super();
            this.arg = a1;
            this.wildcard = a2;
        }
        
        public TypeArgument(final ObjectType a1) {
            this(a1, ' ');
        }
        
        public TypeArgument() {
            this(null, '*');
        }
        
        public static TypeArgument subclassOf(final ObjectType a1) {
            return new TypeArgument(a1, '+');
        }
        
        public static TypeArgument superOf(final ObjectType a1) {
            return new TypeArgument(a1, '-');
        }
        
        public char getKind() {
            return this.wildcard;
        }
        
        public boolean isWildcard() {
            return this.wildcard != ' ';
        }
        
        public ObjectType getType() {
            return this.arg;
        }
        
        @Override
        public String toString() {
            if (this.wildcard == '*') {
                return "?";
            }
            final String v1 = this.arg.toString();
            if (this.wildcard == ' ') {
                return v1;
            }
            if (this.wildcard == '+') {
                return "? extends " + v1;
            }
            return "? super " + v1;
        }
        
        static void encode(final StringBuffer v1, final TypeArgument[] v2) {
            v1.append('<');
            for (int a2 = 0; a2 < v2.length; ++a2) {
                final TypeArgument a3 = v2[a2];
                if (a3.isWildcard()) {
                    v1.append(a3.wildcard);
                }
                if (a3.getType() != null) {
                    a3.getType().encode(v1);
                }
            }
            v1.append('>');
        }
    }
    
    public abstract static class Type
    {
        public Type() {
            super();
        }
        
        abstract void encode(final StringBuffer p0);
        
        static void toString(final StringBuffer a2, final Type[] v1) {
            for (int a3 = 0; a3 < v1.length; ++a3) {
                if (a3 > 0) {
                    a2.append(", ");
                }
                a2.append(v1[a3]);
            }
        }
        
        public String jvmTypeName() {
            return this.toString();
        }
    }
    
    public static class BaseType extends Type
    {
        char descriptor;
        
        BaseType(final char a1) {
            super();
            this.descriptor = a1;
        }
        
        public BaseType(final String a1) {
            this(Descriptor.of(a1).charAt(0));
        }
        
        public char getDescriptor() {
            return this.descriptor;
        }
        
        public CtClass getCtlass() {
            return Descriptor.toPrimitiveClass(this.descriptor);
        }
        
        @Override
        public String toString() {
            return Descriptor.toClassName(Character.toString(this.descriptor));
        }
        
        @Override
        void encode(final StringBuffer a1) {
            a1.append(this.descriptor);
        }
    }
    
    public abstract static class ObjectType extends Type
    {
        public ObjectType() {
            super();
        }
        
        public String encode() {
            final StringBuffer v1 = new StringBuffer();
            this.encode(v1);
            return v1.toString();
        }
    }
    
    public static class ClassType extends ObjectType
    {
        String name;
        TypeArgument[] arguments;
        public static ClassType OBJECT;
        
        static ClassType make(final String a1, final int a2, final int a3, final TypeArgument[] a4, final ClassType a5) {
            if (a5 == null) {
                return new ClassType(a1, a2, a3, a4);
            }
            return new NestedClassType(a1, a2, a3, a4, a5);
        }
        
        ClassType(final String a1, final int a2, final int a3, final TypeArgument[] a4) {
            super();
            this.name = a1.substring(a2, a3).replace('/', '.');
            this.arguments = a4;
        }
        
        public ClassType(final String a1, final TypeArgument[] a2) {
            super();
            this.name = a1;
            this.arguments = a2;
        }
        
        public ClassType(final String a1) {
            this(a1, null);
        }
        
        public String getName() {
            return this.name;
        }
        
        public TypeArgument[] getTypeArguments() {
            return this.arguments;
        }
        
        public ClassType getDeclaringClass() {
            return null;
        }
        
        @Override
        public String toString() {
            final StringBuffer v1 = new StringBuffer();
            final ClassType v2 = this.getDeclaringClass();
            if (v2 != null) {
                v1.append(v2.toString()).append('.');
            }
            return this.toString2(v1);
        }
        
        private String toString2(final StringBuffer v0) {
            v0.append(this.name);
            if (this.arguments != null) {
                v0.append('<');
                for (int v = this.arguments.length, a1 = 0; a1 < v; ++a1) {
                    if (a1 > 0) {
                        v0.append(", ");
                    }
                    v0.append(this.arguments[a1].toString());
                }
                v0.append('>');
            }
            return v0.toString();
        }
        
        @Override
        public String jvmTypeName() {
            final StringBuffer v1 = new StringBuffer();
            final ClassType v2 = this.getDeclaringClass();
            if (v2 != null) {
                v1.append(v2.jvmTypeName()).append('$');
            }
            return this.toString2(v1);
        }
        
        @Override
        void encode(final StringBuffer a1) {
            a1.append('L');
            this.encode2(a1);
            a1.append(';');
        }
        
        void encode2(final StringBuffer a1) {
            final ClassType v1 = this.getDeclaringClass();
            if (v1 != null) {
                v1.encode2(a1);
                a1.append('$');
            }
            a1.append(this.name.replace('.', '/'));
            if (this.arguments != null) {
                TypeArgument.encode(a1, this.arguments);
            }
        }
        
        static {
            ClassType.OBJECT = new ClassType("java.lang.Object", null);
        }
    }
    
    public static class NestedClassType extends ClassType
    {
        ClassType parent;
        
        NestedClassType(final String a1, final int a2, final int a3, final TypeArgument[] a4, final ClassType a5) {
            super(a1, a2, a3, a4);
            this.parent = a5;
        }
        
        public NestedClassType(final ClassType a1, final String a2, final TypeArgument[] a3) {
            super(a2, a3);
            this.parent = a1;
        }
        
        @Override
        public ClassType getDeclaringClass() {
            return this.parent;
        }
    }
    
    public static class ArrayType extends ObjectType
    {
        int dim;
        Type componentType;
        
        public ArrayType(final int a1, final Type a2) {
            super();
            this.dim = a1;
            this.componentType = a2;
        }
        
        public int getDimension() {
            return this.dim;
        }
        
        public Type getComponentType() {
            return this.componentType;
        }
        
        @Override
        public String toString() {
            final StringBuffer v0 = new StringBuffer(this.componentType.toString());
            for (int v2 = 0; v2 < this.dim; ++v2) {
                v0.append("[]");
            }
            return v0.toString();
        }
        
        @Override
        void encode(final StringBuffer v2) {
            for (int a1 = 0; a1 < this.dim; ++a1) {
                v2.append('[');
            }
            this.componentType.encode(v2);
        }
    }
    
    public static class TypeVariable extends ObjectType
    {
        String name;
        
        TypeVariable(final String a1, final int a2, final int a3) {
            super();
            this.name = a1.substring(a2, a3);
        }
        
        public TypeVariable(final String a1) {
            super();
            this.name = a1;
        }
        
        public String getName() {
            return this.name;
        }
        
        @Override
        public String toString() {
            return this.name;
        }
        
        @Override
        void encode(final StringBuffer a1) {
            a1.append('T').append(this.name).append(';');
        }
    }
}
