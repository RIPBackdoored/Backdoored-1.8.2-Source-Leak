package javassist;

class FieldInitLink
{
    FieldInitLink next;
    CtField field;
    CtField.Initializer init;
    
    FieldInitLink(final CtField a1, final CtField.Initializer a2) {
        super();
        this.next = null;
        this.field = a1;
        this.init = a2;
    }
}
