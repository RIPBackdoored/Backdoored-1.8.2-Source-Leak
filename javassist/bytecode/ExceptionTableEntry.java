package javassist.bytecode;

class ExceptionTableEntry
{
    int startPc;
    int endPc;
    int handlerPc;
    int catchType;
    
    ExceptionTableEntry(final int a1, final int a2, final int a3, final int a4) {
        super();
        this.startPc = a1;
        this.endPc = a2;
        this.handlerPc = a3;
        this.catchType = a4;
    }
}
