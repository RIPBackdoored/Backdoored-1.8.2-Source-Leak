package javassist.bytecode.stackmap;

import javassist.*;
import javassist.bytecode.*;
import java.util.*;

public abstract class TypeData
{
    public static TypeData[] make(final int v1) {
        final TypeData[] v2 = new TypeData[v1];
        for (int a1 = 0; a1 < v1; ++a1) {
            v2[a1] = TypeTag.TOP;
        }
        return v2;
    }
    
    protected TypeData() {
        super();
    }
    
    private static void setType(final TypeData a1, final String a2, final ClassPool a3) throws BadBytecode {
        a1.setType(a2, a3);
    }
    
    public abstract int getTypeTag();
    
    public abstract int getTypeData(final ConstPool p0);
    
    public TypeData join() {
        return new TypeVar(this);
    }
    
    public abstract BasicType isBasicType();
    
    public abstract boolean is2WordType();
    
    public boolean isNullType() {
        return false;
    }
    
    public boolean isUninit() {
        return false;
    }
    
    public abstract boolean eq(final TypeData p0);
    
    public abstract String getName();
    
    public abstract void setType(final String p0, final ClassPool p1) throws BadBytecode;
    
    public abstract TypeData getArrayType(final int p0) throws NotFoundException;
    
    public int dfs(final ArrayList a1, final int a2, final ClassPool a3) throws NotFoundException {
        return a2;
    }
    
    protected TypeVar toTypeVar(final int a1) {
        return null;
    }
    
    public void constructorCalled(final int a1) {
    }
    
    @Override
    public String toString() {
        return super.toString() + "(" + this.toString2(new HashSet()) + ")";
    }
    
    abstract String toString2(final HashSet p0);
    
    public static CtClass commonSuperClassEx(final CtClass v-3, final CtClass v-2) throws NotFoundException {
        if (v-3 == v-2) {
            return v-3;
        }
        if (v-3.isArray() && v-2.isArray()) {
            final CtClass a1 = v-3.getComponentType();
            final CtClass a2 = v-2.getComponentType();
            final CtClass v1 = commonSuperClassEx(a1, a2);
            if (v1 == a1) {
                return v-3;
            }
            if (v1 == a2) {
                return v-2;
            }
            return v-3.getClassPool().get((v1 == null) ? "java.lang.Object" : (v1.getName() + "[]"));
        }
        else {
            if (v-3.isPrimitive() || v-2.isPrimitive()) {
                return null;
            }
            if (v-3.isArray() || v-2.isArray()) {
                return v-3.getClassPool().get("java.lang.Object");
            }
            return commonSuperClass(v-3, v-2);
        }
    }
    
    public static CtClass commonSuperClass(final CtClass v1, final CtClass v2) throws NotFoundException {
        CtClass v3 = v1;
        CtClass v5;
        CtClass v4 = v5 = v2;
        CtClass v6 = v3;
        while (!eq(v3, v4) || v3.getSuperclass() == null) {
            final CtClass a1 = v3.getSuperclass();
            final CtClass a2 = v4.getSuperclass();
            if (a2 == null) {
                v4 = v5;
            }
            else {
                if (a1 != null) {
                    v3 = a1;
                    v4 = a2;
                    continue;
                }
                v3 = v6;
                v6 = v5;
                v5 = v3;
                v3 = v4;
                v4 = v5;
            }
            while (true) {
                v3 = v3.getSuperclass();
                if (v3 == null) {
                    break;
                }
                v6 = v6.getSuperclass();
            }
            for (v3 = v6; !eq(v3, v4); v3 = v3.getSuperclass(), v4 = v4.getSuperclass()) {}
            return v3;
        }
        return v3;
    }
    
    static boolean eq(final CtClass a1, final CtClass a2) {
        return a1 == a2 || (a1 != null && a2 != null && a1.getName().equals(a2.getName()));
    }
    
    public static void aastore(final TypeData a2, final TypeData a3, final ClassPool v1) throws BadBytecode {
        if (a2 instanceof AbsTypeVar && !a3.isNullType()) {
            ((AbsTypeVar)a2).merge(ArrayType.make(a3));
        }
        if (a3 instanceof AbsTypeVar) {
            if (a2 instanceof AbsTypeVar) {
                ArrayElement.make(a2);
            }
            else {
                if (!(a2 instanceof ClassName)) {
                    throw new BadBytecode("bad AASTORE: " + a2);
                }
                if (!a2.isNullType()) {
                    final String a4 = typeName(a2.getName());
                    a3.setType(a4, v1);
                }
            }
        }
    }
    
    protected static class BasicType extends TypeData
    {
        private String name;
        private int typeTag;
        private char decodedName;
        
        public BasicType(final String a1, final int a2, final char a3) {
            super();
            this.name = a1;
            this.typeTag = a2;
            this.decodedName = a3;
        }
        
        @Override
        public int getTypeTag() {
            return this.typeTag;
        }
        
        @Override
        public int getTypeData(final ConstPool a1) {
            return 0;
        }
        
        @Override
        public TypeData join() {
            if (this == TypeTag.TOP) {
                return this;
            }
            return super.join();
        }
        
        @Override
        public BasicType isBasicType() {
            return this;
        }
        
        @Override
        public boolean is2WordType() {
            return this.typeTag == 4 || this.typeTag == 3;
        }
        
        @Override
        public boolean eq(final TypeData a1) {
            return this == a1;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        public char getDecodedName() {
            return this.decodedName;
        }
        
        @Override
        public void setType(final String a1, final ClassPool a2) throws BadBytecode {
            throw new BadBytecode("conflict: " + this.name + " and " + a1);
        }
        
        @Override
        public TypeData getArrayType(final int v0) throws NotFoundException {
            if (this == TypeTag.TOP) {
                return this;
            }
            if (v0 < 0) {
                throw new NotFoundException("no element type: " + this.name);
            }
            if (v0 == 0) {
                return this;
            }
            final char[] v = new char[v0 + 1];
            for (int a1 = 0; a1 < v0; ++a1) {
                v[a1] = '[';
            }
            v[v0] = this.decodedName;
            return new ClassName(new String(v));
        }
        
        @Override
        String toString2(final HashSet a1) {
            return this.name;
        }
        
        static /* synthetic */ char access$100(final BasicType a1) {
            return a1.decodedName;
        }
    }
    
    public abstract static class AbsTypeVar extends TypeData
    {
        public AbsTypeVar() {
            super();
        }
        
        public abstract void merge(final TypeData p0);
        
        @Override
        public int getTypeTag() {
            return 7;
        }
        
        @Override
        public int getTypeData(final ConstPool a1) {
            return a1.addClassInfo(this.getName());
        }
        
        @Override
        public boolean eq(final TypeData a1) {
            return this.getName().equals(a1.getName());
        }
    }
    
    public static class TypeVar extends AbsTypeVar
    {
        protected ArrayList lowers;
        protected ArrayList usedBy;
        protected ArrayList uppers;
        protected String fixedType;
        private boolean is2WordType;
        private int visited;
        private int smallest;
        private boolean inList;
        private int dimension;
        
        public TypeVar(final TypeData a1) {
            super();
            this.visited = 0;
            this.smallest = 0;
            this.inList = false;
            this.dimension = 0;
            this.uppers = null;
            this.lowers = new ArrayList(2);
            this.usedBy = new ArrayList(2);
            this.merge(a1);
            this.fixedType = null;
            this.is2WordType = a1.is2WordType();
        }
        
        @Override
        public String getName() {
            if (this.fixedType == null) {
                return this.lowers.get(0).getName();
            }
            return this.fixedType;
        }
        
        @Override
        public BasicType isBasicType() {
            if (this.fixedType == null) {
                return this.lowers.get(0).isBasicType();
            }
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return this.fixedType == null && this.is2WordType;
        }
        
        @Override
        public boolean isNullType() {
            return this.fixedType == null && this.lowers.get(0).isNullType();
        }
        
        @Override
        public boolean isUninit() {
            return this.fixedType == null && this.lowers.get(0).isUninit();
        }
        
        @Override
        public void merge(final TypeData a1) {
            this.lowers.add(a1);
            if (a1 instanceof TypeVar) {
                ((TypeVar)a1).usedBy.add(this);
            }
        }
        
        @Override
        public int getTypeTag() {
            if (this.fixedType == null) {
                return this.lowers.get(0).getTypeTag();
            }
            return super.getTypeTag();
        }
        
        @Override
        public int getTypeData(final ConstPool a1) {
            if (this.fixedType == null) {
                return this.lowers.get(0).getTypeData(a1);
            }
            return super.getTypeData(a1);
        }
        
        @Override
        public void setType(final String a1, final ClassPool a2) throws BadBytecode {
            if (this.uppers == null) {
                this.uppers = new ArrayList();
            }
            this.uppers.add(a1);
        }
        
        @Override
        protected TypeVar toTypeVar(final int a1) {
            this.dimension = a1;
            return this;
        }
        
        @Override
        public TypeData getArrayType(final int v2) throws NotFoundException {
            if (v2 == 0) {
                return this;
            }
            final BasicType a1 = this.isBasicType();
            if (a1 != null) {
                return a1.getArrayType(v2);
            }
            if (this.isNullType()) {
                return new NullType();
            }
            return new ClassName(this.getName()).getArrayType(v2);
        }
        
        @Override
        public int dfs(final ArrayList v-4, int v-3, final ClassPool v-2) throws NotFoundException {
            if (this.visited > 0) {
                return v-3;
            }
            final int n = ++v-3;
            this.smallest = n;
            this.visited = n;
            v-4.add(this);
            this.inList = true;
            for (int size = this.lowers.size(), a2 = 0; a2 < size; ++a2) {
                final TypeVar a3 = this.lowers.get(a2).toTypeVar(this.dimension);
                if (a3 != null) {
                    if (a3.visited == 0) {
                        v-3 = a3.dfs(v-4, v-3, v-2);
                        if (a3.smallest < this.smallest) {
                            this.smallest = a3.smallest;
                        }
                    }
                    else if (a3.inList && a3.visited < this.smallest) {
                        this.smallest = a3.visited;
                    }
                }
            }
            if (this.visited == this.smallest) {
                final ArrayList a4 = new ArrayList();
                TypeVar v1;
                do {
                    v1 = v-4.remove(v-4.size() - 1);
                    v1.inList = false;
                    a4.add(v1);
                } while (v1 != this);
                this.fixTypes(a4, v-2);
            }
            return v-3;
        }
        
        private void fixTypes(final ArrayList v-12, final ClassPool v-11) throws NotFoundException {
            final HashSet v2 = new HashSet();
            boolean b = false;
            TypeData top = null;
            for (int size = v-12.size(), i = 0; i < size; ++i) {
                final TypeVar typeVar = v-12.get(i);
                final ArrayList lowers = typeVar.lowers;
                for (int size2 = lowers.size(), j = 0; j < size2; ++j) {
                    final TypeData a1 = lowers.get(j);
                    final TypeData a2 = a1.getArrayType(typeVar.dimension);
                    final BasicType v1 = a2.isBasicType();
                    if (top == null) {
                        if (v1 == null) {
                            b = false;
                            top = a2;
                            if (a2.isUninit()) {
                                break;
                            }
                        }
                        else {
                            b = true;
                            top = v1;
                        }
                    }
                    else if ((v1 == null && b) || (v1 != null && top != v1)) {
                        b = true;
                        top = TypeTag.TOP;
                        break;
                    }
                    if (v1 == null && !a2.isNullType()) {
                        v2.add(a2.getName());
                    }
                }
            }
            if (b) {
                this.is2WordType = top.is2WordType();
                this.fixTypes1(v-12, top);
            }
            else {
                final String fixTypes2 = this.fixTypes2(v-12, v2, v-11);
                this.fixTypes1(v-12, new ClassName(fixTypes2));
            }
        }
        
        private void fixTypes1(final ArrayList v-2, final TypeData v-1) throws NotFoundException {
            for (int v0 = v-2.size(), v2 = 0; v2 < v0; ++v2) {
                final TypeVar a1 = v-2.get(v2);
                final TypeData a2 = v-1.getArrayType(-a1.dimension);
                if (a2.isBasicType() == null) {
                    a1.fixedType = a2.getName();
                }
                else {
                    a1.lowers.clear();
                    a1.lowers.add(a2);
                    a1.is2WordType = a2.is2WordType();
                }
            }
        }
        
        private String fixTypes2(final ArrayList a3, final HashSet v1, final ClassPool v2) throws NotFoundException {
            final Iterator v3 = v1.iterator();
            if (v1.size() == 0) {
                return null;
            }
            if (v1.size() == 1) {
                return v3.next();
            }
            CtClass a4 = v2.get(v3.next());
            while (v3.hasNext()) {
                a4 = TypeData.commonSuperClassEx(a4, v2.get(v3.next()));
            }
            if (a4.getSuperclass() == null || isObjectArray(a4)) {
                a4 = this.fixByUppers(a3, v2, new HashSet(), a4);
            }
            if (a4.isArray()) {
                return Descriptor.toJvmName(a4);
            }
            return a4.getName();
        }
        
        private static boolean isObjectArray(final CtClass a1) throws NotFoundException {
            return a1.isArray() && a1.getComponentType().getSuperclass() == null;
        }
        
        private CtClass fixByUppers(final ArrayList v-4, final ClassPool v-3, final HashSet v-2, CtClass v-1) throws NotFoundException {
            if (v-4 == null) {
                return v-1;
            }
            for (int v0 = v-4.size(), v2 = 0; v2 < v0; ++v2) {
                final TypeVar a4 = v-4.get(v2);
                if (!v-2.add(a4)) {
                    return v-1;
                }
                if (a4.uppers != null) {
                    for (int a5 = a4.uppers.size(), a6 = 0; a6 < a5; ++a6) {
                        final CtClass a7 = v-3.get(a4.uppers.get(a6));
                        if (a7.subtypeOf(v-1)) {
                            v-1 = a7;
                        }
                    }
                }
                v-1 = this.fixByUppers(a4.usedBy, v-3, v-2, v-1);
            }
            return v-1;
        }
        
        @Override
        String toString2(final HashSet v2) {
            v2.add(this);
            if (this.lowers.size() > 0) {
                final TypeData a1 = this.lowers.get(0);
                if (a1 != null && !v2.contains(a1)) {
                    return a1.toString2(v2);
                }
            }
            return "?";
        }
    }
    
    public static class ArrayType extends AbsTypeVar
    {
        private AbsTypeVar element;
        
        private ArrayType(final AbsTypeVar a1) {
            super();
            this.element = a1;
        }
        
        static TypeData make(final TypeData a1) throws BadBytecode {
            if (a1 instanceof ArrayElement) {
                return ((ArrayElement)a1).arrayType();
            }
            if (a1 instanceof AbsTypeVar) {
                return new ArrayType((AbsTypeVar)a1);
            }
            if (a1 instanceof ClassName && !a1.isNullType()) {
                return new ClassName(typeName(a1.getName()));
            }
            throw new BadBytecode("bad AASTORE: " + a1);
        }
        
        @Override
        public void merge(final TypeData v2) {
            try {
                if (!v2.isNullType()) {
                    this.element.merge(ArrayElement.make(v2));
                }
            }
            catch (BadBytecode a1) {
                throw new RuntimeException("fatal: " + a1);
            }
        }
        
        @Override
        public String getName() {
            return typeName(this.element.getName());
        }
        
        public AbsTypeVar elementType() {
            return this.element;
        }
        
        @Override
        public BasicType isBasicType() {
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return false;
        }
        
        public static String typeName(final String a1) {
            if (a1.charAt(0) == '[') {
                return "[" + a1;
            }
            return "[L" + a1.replace('.', '/') + ";";
        }
        
        @Override
        public void setType(final String a1, final ClassPool a2) throws BadBytecode {
            this.element.setType(typeName(a1), a2);
        }
        
        @Override
        protected TypeVar toTypeVar(final int a1) {
            return this.element.toTypeVar(a1 + 1);
        }
        
        @Override
        public TypeData getArrayType(final int a1) throws NotFoundException {
            return this.element.getArrayType(a1 + 1);
        }
        
        @Override
        public int dfs(final ArrayList a1, final int a2, final ClassPool a3) throws NotFoundException {
            return this.element.dfs(a1, a2, a3);
        }
        
        @Override
        String toString2(final HashSet a1) {
            return "[" + this.element.toString2(a1);
        }
    }
    
    public static class ArrayElement extends AbsTypeVar
    {
        private AbsTypeVar array;
        
        private ArrayElement(final AbsTypeVar a1) {
            super();
            this.array = a1;
        }
        
        public static TypeData make(final TypeData a1) throws BadBytecode {
            if (a1 instanceof ArrayType) {
                return ((ArrayType)a1).elementType();
            }
            if (a1 instanceof AbsTypeVar) {
                return new ArrayElement((AbsTypeVar)a1);
            }
            if (a1 instanceof ClassName && !a1.isNullType()) {
                return new ClassName(typeName(a1.getName()));
            }
            throw new BadBytecode("bad AASTORE: " + a1);
        }
        
        @Override
        public void merge(final TypeData v2) {
            try {
                if (!v2.isNullType()) {
                    this.array.merge(ArrayType.make(v2));
                }
            }
            catch (BadBytecode a1) {
                throw new RuntimeException("fatal: " + a1);
            }
        }
        
        @Override
        public String getName() {
            return typeName(this.array.getName());
        }
        
        public AbsTypeVar arrayType() {
            return this.array;
        }
        
        @Override
        public BasicType isBasicType() {
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return false;
        }
        
        private static String typeName(final String v1) {
            if (v1.length() > 1 && v1.charAt(0) == '[') {
                final char a1 = v1.charAt(1);
                if (a1 == 'L') {
                    return v1.substring(2, v1.length() - 1).replace('/', '.');
                }
                if (a1 == '[') {
                    return v1.substring(1);
                }
            }
            return "java.lang.Object";
        }
        
        @Override
        public void setType(final String a1, final ClassPool a2) throws BadBytecode {
            this.array.setType(ArrayType.typeName(a1), a2);
        }
        
        @Override
        protected TypeVar toTypeVar(final int a1) {
            return this.array.toTypeVar(a1 - 1);
        }
        
        @Override
        public TypeData getArrayType(final int a1) throws NotFoundException {
            return this.array.getArrayType(a1 - 1);
        }
        
        @Override
        public int dfs(final ArrayList a1, final int a2, final ClassPool a3) throws NotFoundException {
            return this.array.dfs(a1, a2, a3);
        }
        
        @Override
        String toString2(final HashSet a1) {
            return "*" + this.array.toString2(a1);
        }
        
        static /* bridge */ String access$000(final String a1) {
            return typeName(a1);
        }
    }
    
    public static class UninitTypeVar extends AbsTypeVar
    {
        protected TypeData type;
        
        public UninitTypeVar(final UninitData a1) {
            super();
            this.type = a1;
        }
        
        @Override
        public int getTypeTag() {
            return this.type.getTypeTag();
        }
        
        @Override
        public int getTypeData(final ConstPool a1) {
            return this.type.getTypeData(a1);
        }
        
        @Override
        public BasicType isBasicType() {
            return this.type.isBasicType();
        }
        
        @Override
        public boolean is2WordType() {
            return this.type.is2WordType();
        }
        
        @Override
        public boolean isUninit() {
            return this.type.isUninit();
        }
        
        @Override
        public boolean eq(final TypeData a1) {
            return this.type.eq(a1);
        }
        
        @Override
        public String getName() {
            return this.type.getName();
        }
        
        @Override
        protected TypeVar toTypeVar(final int a1) {
            return null;
        }
        
        @Override
        public TypeData join() {
            return this.type.join();
        }
        
        @Override
        public void setType(final String a1, final ClassPool a2) throws BadBytecode {
            this.type.setType(a1, a2);
        }
        
        @Override
        public void merge(final TypeData a1) {
            if (!a1.eq(this.type)) {
                this.type = TypeTag.TOP;
            }
        }
        
        @Override
        public void constructorCalled(final int a1) {
            this.type.constructorCalled(a1);
        }
        
        public int offset() {
            if (this.type instanceof UninitData) {
                return ((UninitData)this.type).offset;
            }
            throw new RuntimeException("not available");
        }
        
        @Override
        public TypeData getArrayType(final int a1) throws NotFoundException {
            return this.type.getArrayType(a1);
        }
        
        @Override
        String toString2(final HashSet a1) {
            return "";
        }
    }
    
    public static class ClassName extends TypeData
    {
        private String name;
        
        public ClassName(final String a1) {
            super();
            this.name = a1;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public BasicType isBasicType() {
            return null;
        }
        
        @Override
        public boolean is2WordType() {
            return false;
        }
        
        @Override
        public int getTypeTag() {
            return 7;
        }
        
        @Override
        public int getTypeData(final ConstPool a1) {
            return a1.addClassInfo(this.getName());
        }
        
        @Override
        public boolean eq(final TypeData a1) {
            return this.name.equals(a1.getName());
        }
        
        @Override
        public void setType(final String a1, final ClassPool a2) throws BadBytecode {
        }
        
        @Override
        public TypeData getArrayType(final int v0) throws NotFoundException {
            if (v0 == 0) {
                return this;
            }
            if (v0 > 0) {
                final char[] v = new char[v0];
                for (int a1 = 0; a1 < v0; ++a1) {
                    v[a1] = '[';
                }
                String v2 = this.getName();
                if (v2.charAt(0) != '[') {
                    v2 = "L" + v2.replace('.', '/') + ";";
                }
                return new ClassName(new String(v) + v2);
            }
            for (int v3 = 0; v3 < -v0; ++v3) {
                if (this.name.charAt(v3) != '[') {
                    throw new NotFoundException("no " + v0 + " dimensional array type: " + this.getName());
                }
            }
            final char v4 = this.name.charAt(-v0);
            if (v4 == '[') {
                return new ClassName(this.name.substring(-v0));
            }
            if (v4 == 'L') {
                return new ClassName(this.name.substring(-v0 + 1, this.name.length() - 1).replace('/', '.'));
            }
            if (v4 == TypeTag.DOUBLE.decodedName) {
                return TypeTag.DOUBLE;
            }
            if (v4 == TypeTag.FLOAT.decodedName) {
                return TypeTag.FLOAT;
            }
            if (v4 == TypeTag.LONG.decodedName) {
                return TypeTag.LONG;
            }
            return TypeTag.INTEGER;
        }
        
        @Override
        String toString2(final HashSet a1) {
            return this.name;
        }
    }
    
    public static class NullType extends ClassName
    {
        public NullType() {
            super("null-type");
        }
        
        @Override
        public int getTypeTag() {
            return 5;
        }
        
        @Override
        public boolean isNullType() {
            return true;
        }
        
        @Override
        public int getTypeData(final ConstPool a1) {
            return 0;
        }
        
        @Override
        public TypeData getArrayType(final int a1) {
            return this;
        }
    }
    
    public static class UninitData extends ClassName
    {
        int offset;
        boolean initialized;
        
        UninitData(final int a1, final String a2) {
            super(a2);
            this.offset = a1;
            this.initialized = false;
        }
        
        public UninitData copy() {
            return new UninitData(this.offset, this.getName());
        }
        
        @Override
        public int getTypeTag() {
            return 8;
        }
        
        @Override
        public int getTypeData(final ConstPool a1) {
            return this.offset;
        }
        
        @Override
        public TypeData join() {
            if (this.initialized) {
                return new TypeVar(new ClassName(this.getName()));
            }
            return new UninitTypeVar(this.copy());
        }
        
        @Override
        public boolean isUninit() {
            return true;
        }
        
        @Override
        public boolean eq(final TypeData v2) {
            if (v2 instanceof UninitData) {
                final UninitData a1 = (UninitData)v2;
                return this.offset == a1.offset && this.getName().equals(a1.getName());
            }
            return false;
        }
        
        public int offset() {
            return this.offset;
        }
        
        @Override
        public void constructorCalled(final int a1) {
            if (a1 == this.offset) {
                this.initialized = true;
            }
        }
        
        @Override
        String toString2(final HashSet a1) {
            return this.getName() + "," + this.offset;
        }
    }
    
    public static class UninitThis extends UninitData
    {
        UninitThis(final String a1) {
            super(-1, a1);
        }
        
        @Override
        public UninitData copy() {
            return new UninitThis(this.getName());
        }
        
        @Override
        public int getTypeTag() {
            return 6;
        }
        
        @Override
        public int getTypeData(final ConstPool a1) {
            return 0;
        }
        
        @Override
        String toString2(final HashSet a1) {
            return "uninit:this";
        }
    }
}
