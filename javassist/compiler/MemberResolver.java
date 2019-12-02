package javassist.compiler;

import javassist.bytecode.*;
import javassist.*;
import java.lang.ref.*;
import java.util.*;
import javassist.compiler.ast.*;

public class MemberResolver implements TokenId
{
    private ClassPool classPool;
    private static final int YES = 0;
    private static final int NO = -1;
    private static final String INVALID = "<invalid>";
    private static WeakHashMap invalidNamesMap;
    private Hashtable invalidNames;
    
    public MemberResolver(final ClassPool a1) {
        super();
        this.invalidNames = null;
        this.classPool = a1;
    }
    
    public ClassPool getClassPool() {
        return this.classPool;
    }
    
    private static void fatal() throws CompileError {
        throw new CompileError("fatal");
    }
    
    public Method lookupMethod(final CtClass a4, final CtClass a5, final MethodInfo a6, final String a7, final int[] v1, final int[] v2, final String[] v3) throws CompileError {
        Method v4 = null;
        if (a6 != null && a4 == a5 && a6.getName().equals(a7)) {
            final int a8 = this.compareSignature(a6.getDescriptor(), v1, v2, v3);
            if (a8 != -1) {
                final Method a9 = new Method(a4, a6, a8);
                if (a8 == 0) {
                    return a9;
                }
                v4 = a9;
            }
        }
        final Method v5 = this.lookupMethod(a4, a7, v1, v2, v3, v4 != null);
        if (v5 != null) {
            return v5;
        }
        return v4;
    }
    
    private Method lookupMethod(final CtClass v-10, final String v-9, final int[] v-8, final int[] v-7, final String[] v-6, final boolean v-5) throws CompileError {
        Method method = null;
        final ClassFile classFile2 = v-10.getClassFile2();
        if (classFile2 != null) {
            final List a5 = classFile2.getMethods();
            for (int a6 = a5.size(), a7 = 0; a7 < a6; ++a7) {
                final MethodInfo a8 = a5.get(a7);
                if (a8.getName().equals(v-9) && (a8.getAccessFlags() & 0x40) == 0x0) {
                    final int a9 = this.compareSignature(a8.getDescriptor(), v-8, v-7, v-6);
                    if (a9 != -1) {
                        final Method a10 = new Method(v-10, a8, a9);
                        if (a9 == 0) {
                            return a10;
                        }
                        if (method == null || method.notmatch > a9) {
                            method = a10;
                        }
                    }
                }
            }
        }
        if (v-5) {
            method = null;
        }
        else if (method != null) {
            return method;
        }
        final int modifiers = v-10.getModifiers();
        final boolean interface1 = Modifier.isInterface(modifiers);
        try {
            if (!interface1) {
                final CtClass v0 = v-10.getSuperclass();
                if (v0 != null) {
                    final Method v2 = this.lookupMethod(v0, v-9, v-8, v-7, v-6, v-5);
                    if (v2 != null) {
                        return v2;
                    }
                }
            }
        }
        catch (NotFoundException ex) {}
        try {
            final CtClass[] v3 = v-10.getInterfaces();
            for (int v4 = v3.length, v5 = 0; v5 < v4; ++v5) {
                final Method v6 = this.lookupMethod(v3[v5], v-9, v-8, v-7, v-6, v-5);
                if (v6 != null) {
                    return v6;
                }
            }
            if (interface1) {
                final CtClass v7 = v-10.getSuperclass();
                if (v7 != null) {
                    final Method v6 = this.lookupMethod(v7, v-9, v-8, v-7, v-6, v-5);
                    if (v6 != null) {
                        return v6;
                    }
                }
            }
        }
        catch (NotFoundException ex2) {}
        return method;
    }
    
    private int compareSignature(final String v-10, final int[] v-9, final int[] v-8, final String[] v-7) throws CompileError {
        int n = 0;
        int i = 1;
        final int length = v-9.length;
        if (length != Descriptor.numOfParameters(v-10)) {
            return -1;
        }
        final int length2 = v-10.length();
        int n2 = 0;
        while (i < length2) {
            char a7 = v-10.charAt(i++);
            if (a7 == ')') {
                return (n2 == length) ? n : -1;
            }
            if (n2 >= length) {
                return -1;
            }
            int v0 = 0;
            while (a7 == '[') {
                ++v0;
                a7 = v-10.charAt(i++);
            }
            if (v-9[n2] == 412) {
                if (v0 == 0 && a7 != 'L') {
                    return -1;
                }
                if (a7 == 'L') {
                    i = v-10.indexOf(59, i) + 1;
                }
            }
            else if (v-8[n2] != v0) {
                if (v0 != 0 || a7 != 'L' || !v-10.startsWith("java/lang/Object;", i)) {
                    return -1;
                }
                i = v-10.indexOf(59, i) + 1;
                ++n;
                if (i <= 0) {
                    return -1;
                }
            }
            else if (a7 == 'L') {
                final int a3 = v-10.indexOf(59, i);
                if (a3 < 0 || v-9[n2] != 307) {
                    return -1;
                }
                final String a4 = v-10.substring(i, a3);
                if (!a4.equals(v-7[n2])) {
                    final CtClass a5 = this.lookupClassByJvmName(v-7[n2]);
                    try {
                        if (!a5.subtypeOf(this.lookupClassByJvmName(a4))) {
                            return -1;
                        }
                        ++n;
                    }
                    catch (NotFoundException a6) {
                        ++n;
                    }
                }
                i = a3 + 1;
            }
            else {
                final int v2 = descToType(a7);
                final int v3 = v-9[n2];
                if (v2 != v3) {
                    if (v2 != 324 || (v3 != 334 && v3 != 303 && v3 != 306)) {
                        return -1;
                    }
                    ++n;
                }
            }
            ++n2;
        }
        return -1;
    }
    
    public CtField lookupFieldByJvmName2(String v1, final Symbol v2, final ASTree v3) throws NoFieldException {
        final String v4 = v2.get();
        CtClass v5 = null;
        try {
            v5 = this.lookupClass(jvmToJavaName(v1), true);
        }
        catch (CompileError a1) {
            throw new NoFieldException(v1 + "/" + v4, v3);
        }
        try {
            return v5.getField(v4);
        }
        catch (NotFoundException a2) {
            v1 = javaToJvmName(v5.getName());
            throw new NoFieldException(v1 + "$" + v4, v3);
        }
    }
    
    public CtField lookupFieldByJvmName(final String a1, final Symbol a2) throws CompileError {
        return this.lookupField(jvmToJavaName(a1), a2);
    }
    
    public CtField lookupField(final String a1, final Symbol a2) throws CompileError {
        final CtClass v1 = this.lookupClass(a1, false);
        try {
            return v1.getField(a2.get());
        }
        catch (NotFoundException ex) {
            throw new CompileError("no such field: " + a2.get());
        }
    }
    
    public CtClass lookupClassByName(final ASTList a1) throws CompileError {
        return this.lookupClass(Declarator.astToClassName(a1, '.'), false);
    }
    
    public CtClass lookupClassByJvmName(final String a1) throws CompileError {
        return this.lookupClass(jvmToJavaName(a1), false);
    }
    
    public CtClass lookupClass(final Declarator a1) throws CompileError {
        return this.lookupClass(a1.getType(), a1.getArrayDim(), a1.getClassName());
    }
    
    public CtClass lookupClass(final int a3, int v1, final String v2) throws CompileError {
        String v3 = "";
        if (a3 == 307) {
            final CtClass a4 = this.lookupClassByJvmName(v2);
            if (v1 <= 0) {
                return a4;
            }
            v3 = a4.getName();
        }
        else {
            v3 = getTypeName(a3);
        }
        while (v1-- > 0) {
            v3 += "[]";
        }
        return this.lookupClass(v3, false);
    }
    
    static String getTypeName(final int a1) throws CompileError {
        String v1 = "";
        switch (a1) {
            case 301: {
                v1 = "boolean";
                break;
            }
            case 306: {
                v1 = "char";
                break;
            }
            case 303: {
                v1 = "byte";
                break;
            }
            case 334: {
                v1 = "short";
                break;
            }
            case 324: {
                v1 = "int";
                break;
            }
            case 326: {
                v1 = "long";
                break;
            }
            case 317: {
                v1 = "float";
                break;
            }
            case 312: {
                v1 = "double";
                break;
            }
            case 344: {
                v1 = "void";
                break;
            }
            default: {
                fatal();
                break;
            }
        }
        return v1;
    }
    
    public CtClass lookupClass(final String v1, final boolean v2) throws CompileError {
        final Hashtable v3 = this.getInvalidNames();
        final Object v4 = v3.get(v1);
        if (v4 == "<invalid>") {
            throw new CompileError("no such class: " + v1);
        }
        if (v4 != null) {
            try {
                return this.classPool.get((String)v4);
            }
            catch (NotFoundException ex) {}
        }
        CtClass v5 = null;
        try {
            v5 = this.lookupClass0(v1, v2);
        }
        catch (NotFoundException a1) {
            v5 = this.searchImports(v1);
        }
        v3.put(v1, v5.getName());
        return v5;
    }
    
    public static int getInvalidMapSize() {
        return MemberResolver.invalidNamesMap.size();
    }
    
    private Hashtable getInvalidNames() {
        Hashtable invalidNames = this.invalidNames;
        if (invalidNames == null) {
            synchronized (MemberResolver.class) {
                final WeakReference v1 = MemberResolver.invalidNamesMap.get(this.classPool);
                if (v1 != null) {
                    invalidNames = (Hashtable)v1.get();
                }
                if (invalidNames == null) {
                    invalidNames = new Hashtable();
                    MemberResolver.invalidNamesMap.put(this.classPool, new WeakReference<Hashtable>(invalidNames));
                }
            }
            this.invalidNames = invalidNames;
        }
        return invalidNames;
    }
    
    private CtClass searchImports(final String v-1) throws CompileError {
        if (v-1.indexOf(46) < 0) {
            final Iterator v0 = this.classPool.getImportedPackages();
            while (v0.hasNext()) {
                final String v2 = v0.next();
                final String v3 = v2 + '.' + v-1;
                try {
                    return this.classPool.get(v3);
                }
                catch (NotFoundException a1) {
                    try {
                        if (v2.endsWith("." + v-1)) {
                            return this.classPool.get(v2);
                        }
                        continue;
                    }
                    catch (NotFoundException ex) {}
                    continue;
                }
                break;
            }
        }
        this.getInvalidNames().put(v-1, "<invalid>");
        throw new CompileError("no such class: " + v-1);
    }
    
    private CtClass lookupClass0(String v-2, final boolean v-1) throws NotFoundException {
        CtClass v0 = null;
        do {
            try {
                v0 = this.classPool.get(v-2);
            }
            catch (NotFoundException v2) {
                final int a2 = v-2.lastIndexOf(46);
                if (v-1 || a2 < 0) {
                    throw v2;
                }
                final StringBuffer a3 = new StringBuffer(v-2);
                a3.setCharAt(a2, '$');
                v-2 = a3.toString();
            }
        } while (v0 == null);
        return v0;
    }
    
    public String resolveClassName(final ASTList a1) throws CompileError {
        if (a1 == null) {
            return null;
        }
        return javaToJvmName(this.lookupClassByName(a1).getName());
    }
    
    public String resolveJvmClassName(final String a1) throws CompileError {
        if (a1 == null) {
            return null;
        }
        return javaToJvmName(this.lookupClassByJvmName(a1).getName());
    }
    
    public static CtClass getSuperclass(final CtClass v1) throws CompileError {
        try {
            final CtClass a1 = v1.getSuperclass();
            if (a1 != null) {
                return a1;
            }
        }
        catch (NotFoundException ex) {}
        throw new CompileError("cannot find the super class of " + v1.getName());
    }
    
    public static CtClass getSuperInterface(final CtClass v1, final String v2) throws CompileError {
        try {
            final CtClass[] a2 = v1.getInterfaces();
            for (int a3 = 0; a3 < a2.length; ++a3) {
                if (a2[a3].getName().equals(v2)) {
                    return a2[a3];
                }
            }
        }
        catch (NotFoundException ex) {}
        throw new CompileError("cannot find the super inetrface " + v2 + " of " + v1.getName());
    }
    
    public static String javaToJvmName(final String a1) {
        return a1.replace('.', '/');
    }
    
    public static String jvmToJavaName(final String a1) {
        return a1.replace('/', '.');
    }
    
    public static int descToType(final char a1) throws CompileError {
        switch (a1) {
            case 'Z': {
                return 301;
            }
            case 'C': {
                return 306;
            }
            case 'B': {
                return 303;
            }
            case 'S': {
                return 334;
            }
            case 'I': {
                return 324;
            }
            case 'J': {
                return 326;
            }
            case 'F': {
                return 317;
            }
            case 'D': {
                return 312;
            }
            case 'V': {
                return 344;
            }
            case 'L':
            case '[': {
                return 307;
            }
            default: {
                fatal();
                return 344;
            }
        }
    }
    
    public static int getModifiers(ASTList v1) {
        int v2 = 0;
        while (v1 != null) {
            final Keyword a1 = (Keyword)v1.head();
            v1 = v1.tail();
            switch (a1.get()) {
                case 335: {
                    v2 |= 0x8;
                    continue;
                }
                case 315: {
                    v2 |= 0x10;
                    continue;
                }
                case 338: {
                    v2 |= 0x20;
                    continue;
                }
                case 300: {
                    v2 |= 0x400;
                    continue;
                }
                case 332: {
                    v2 |= 0x1;
                    continue;
                }
                case 331: {
                    v2 |= 0x4;
                    continue;
                }
                case 330: {
                    v2 |= 0x2;
                    continue;
                }
                case 345: {
                    v2 |= 0x40;
                    continue;
                }
                case 342: {
                    v2 |= 0x80;
                    continue;
                }
                case 347: {
                    v2 |= 0x800;
                    continue;
                }
            }
        }
        return v2;
    }
    
    static {
        MemberResolver.invalidNamesMap = new WeakHashMap();
    }
    
    public static class Method
    {
        public CtClass declaring;
        public MethodInfo info;
        public int notmatch;
        
        public Method(final CtClass a1, final MethodInfo a2, final int a3) {
            super();
            this.declaring = a1;
            this.info = a2;
            this.notmatch = a3;
        }
        
        public boolean isStatic() {
            final int v1 = this.info.getAccessFlags();
            return (v1 & 0x8) != 0x0;
        }
    }
}
