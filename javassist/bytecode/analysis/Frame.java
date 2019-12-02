package javassist.bytecode.analysis;

public class Frame
{
    private Type[] locals;
    private Type[] stack;
    private int top;
    private boolean jsrMerged;
    private boolean retMerged;
    
    public Frame(final int a1, final int a2) {
        super();
        this.locals = new Type[a1];
        this.stack = new Type[a2];
    }
    
    public Type getLocal(final int a1) {
        return this.locals[a1];
    }
    
    public void setLocal(final int a1, final Type a2) {
        this.locals[a1] = a2;
    }
    
    public Type getStack(final int a1) {
        return this.stack[a1];
    }
    
    public void setStack(final int a1, final Type a2) {
        this.stack[a1] = a2;
    }
    
    public void clearStack() {
        this.top = 0;
    }
    
    public int getTopIndex() {
        return this.top - 1;
    }
    
    public int localsLength() {
        return this.locals.length;
    }
    
    public Type peek() {
        if (this.top < 1) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        return this.stack[this.top - 1];
    }
    
    public Type pop() {
        if (this.top < 1) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        final Type[] stack = this.stack;
        final int top = this.top - 1;
        this.top = top;
        return stack[top];
    }
    
    public void push(final Type a1) {
        this.stack[this.top++] = a1;
    }
    
    public Frame copy() {
        final Frame v1 = new Frame(this.locals.length, this.stack.length);
        System.arraycopy(this.locals, 0, v1.locals, 0, this.locals.length);
        System.arraycopy(this.stack, 0, v1.stack, 0, this.stack.length);
        v1.top = this.top;
        return v1;
    }
    
    public Frame copyStack() {
        final Frame v1 = new Frame(this.locals.length, this.stack.length);
        System.arraycopy(this.stack, 0, v1.stack, 0, this.stack.length);
        v1.top = this.top;
        return v1;
    }
    
    public boolean mergeStack(final Frame v-3) {
        boolean b = false;
        if (this.top != v-3.top) {
            throw new RuntimeException("Operand stacks could not be merged, they are different sizes!");
        }
        for (int i = 0; i < this.top; ++i) {
            if (this.stack[i] != null) {
                final Type a1 = this.stack[i];
                final Type v1 = a1.merge(v-3.stack[i]);
                if (v1 == Type.BOGUS) {
                    throw new RuntimeException("Operand stacks could not be merged due to differing primitive types: pos = " + i);
                }
                this.stack[i] = v1;
                if (!v1.equals(a1) || v1.popChanged()) {
                    b = true;
                }
            }
        }
        return b;
    }
    
    public boolean merge(final Frame v-3) {
        boolean b = false;
        for (int i = 0; i < this.locals.length; ++i) {
            if (this.locals[i] != null) {
                final Type a1 = this.locals[i];
                final Type v1 = a1.merge(v-3.locals[i]);
                this.locals[i] = v1;
                if (!v1.equals(a1) || v1.popChanged()) {
                    b = true;
                }
            }
            else if (v-3.locals[i] != null) {
                this.locals[i] = v-3.locals[i];
                b = true;
            }
        }
        b |= this.mergeStack(v-3);
        return b;
    }
    
    @Override
    public String toString() {
        final StringBuffer v0 = new StringBuffer();
        v0.append("locals = [");
        for (int v2 = 0; v2 < this.locals.length; ++v2) {
            v0.append((this.locals[v2] == null) ? "empty" : this.locals[v2].toString());
            if (v2 < this.locals.length - 1) {
                v0.append(", ");
            }
        }
        v0.append("] stack = [");
        for (int v2 = 0; v2 < this.top; ++v2) {
            v0.append(this.stack[v2]);
            if (v2 < this.top - 1) {
                v0.append(", ");
            }
        }
        v0.append("]");
        return v0.toString();
    }
    
    boolean isJsrMerged() {
        return this.jsrMerged;
    }
    
    void setJsrMerged(final boolean a1) {
        this.jsrMerged = a1;
    }
    
    boolean isRetMerged() {
        return this.retMerged;
    }
    
    void setRetMerged(final boolean a1) {
        this.retMerged = a1;
    }
}
