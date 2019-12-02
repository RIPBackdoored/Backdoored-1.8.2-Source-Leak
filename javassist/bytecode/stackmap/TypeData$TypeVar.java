package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import java.util.*;
import javassist.*;

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
