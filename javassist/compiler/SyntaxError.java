package javassist.compiler;

public class SyntaxError extends CompileError
{
    public SyntaxError(final Lex a1) {
        super("syntax error near \"" + a1.getTextAround() + "\"", a1);
    }
}
