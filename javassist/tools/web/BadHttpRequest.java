package javassist.tools.web;

public class BadHttpRequest extends Exception
{
    private Exception e;
    
    public BadHttpRequest() {
        super();
        this.e = null;
    }
    
    public BadHttpRequest(final Exception a1) {
        super();
        this.e = a1;
    }
    
    @Override
    public String toString() {
        if (this.e == null) {
            return super.toString();
        }
        return this.e.toString();
    }
}
