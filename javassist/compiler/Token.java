package javassist.compiler;

class Token
{
    public Token next;
    public int tokenId;
    public long longValue;
    public double doubleValue;
    public String textValue;
    
    Token() {
        super();
        this.next = null;
    }
}
