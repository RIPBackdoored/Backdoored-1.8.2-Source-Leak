package a.a.k.a.a;

import org.apache.commons.lang3.*;

public class e extends a
{
    private static final char[] bbi;
    
    public e() {
        super();
    }
    
    @Override
    public String b() {
        return "Fancy";
    }
    
    @Override
    public String b(final String s) {
        final StringBuilder sb = new StringBuilder();
        for (final char valueToFind : s.toCharArray()) {
            if (valueToFind < '!' || valueToFind > '\u0080') {
                sb.append(valueToFind);
            }
            else if (ArrayUtils.contains(e.bbi, valueToFind)) {
                sb.append(valueToFind);
            }
            else {
                sb.append(Character.toChars(valueToFind + '\ufee0'));
            }
        }
        return sb.toString();
    }
    
    static {
        bbi = new char[] { '(', ')', '{', '}', '[', ']', '|' };
    }
}
