package javassist.bytecode;

static class Pointers
{
    int cursor;
    int mark0;
    int mark;
    ExceptionTable etable;
    LineNumberAttribute line;
    LocalVariableAttribute vars;
    LocalVariableAttribute types;
    StackMapTable stack;
    StackMap stack2;
    
    Pointers(final int a1, final int a2, final int a3, final ExceptionTable a4, final CodeAttribute a5) {
        super();
        this.cursor = a1;
        this.mark = a2;
        this.mark0 = a3;
        this.etable = a4;
        this.line = (LineNumberAttribute)a5.getAttribute("LineNumberTable");
        this.vars = (LocalVariableAttribute)a5.getAttribute("LocalVariableTable");
        this.types = (LocalVariableAttribute)a5.getAttribute("LocalVariableTypeTable");
        this.stack = (StackMapTable)a5.getAttribute("StackMapTable");
        this.stack2 = (StackMap)a5.getAttribute("StackMap");
    }
    
    void shiftPc(final int a1, final int a2, final boolean a3) throws BadBytecode {
        if (a1 < this.cursor || (a1 == this.cursor && a3)) {
            this.cursor += a2;
        }
        if (a1 < this.mark || (a1 == this.mark && a3)) {
            this.mark += a2;
        }
        if (a1 < this.mark0 || (a1 == this.mark0 && a3)) {
            this.mark0 += a2;
        }
        this.etable.shiftPc(a1, a2, a3);
        if (this.line != null) {
            this.line.shiftPc(a1, a2, a3);
        }
        if (this.vars != null) {
            this.vars.shiftPc(a1, a2, a3);
        }
        if (this.types != null) {
            this.types.shiftPc(a1, a2, a3);
        }
        if (this.stack != null) {
            this.stack.shiftPc(a1, a2, a3);
        }
        if (this.stack2 != null) {
            this.stack2.shiftPc(a1, a2, a3);
        }
    }
    
    void shiftForSwitch(final int a1, final int a2) throws BadBytecode {
        if (this.stack != null) {
            this.stack.shiftForSwitch(a1, a2);
        }
        if (this.stack2 != null) {
            this.stack2.shiftForSwitch(a1, a2);
        }
    }
}
