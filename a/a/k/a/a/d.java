package a.a.k.a.a;

public class d extends a
{
    public d() {
        super();
    }
    
    @Override
    public String b() {
        return "Emphasize";
    }
    
    @Override
    public String b(String replaceAll) {
        replaceAll = replaceAll.replaceAll(" ", "");
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = replaceAll.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            sb.append(Character.toUpperCase(charArray[i])).append(" ");
        }
        return sb.toString();
    }
}
