package javassist.expr;

static final class NewOp
{
    NewOp next;
    int pos;
    String type;
    
    NewOp(final NewOp a1, final int a2, final String a3) {
        super();
        this.next = a1;
        this.pos = a2;
        this.type = a3;
    }
}
