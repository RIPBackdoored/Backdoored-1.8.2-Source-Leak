package org.json;

public class HTTPTokener extends JSONTokener
{
    public HTTPTokener(final String a1) {
        super(a1);
    }
    
    public String nextToken() throws JSONException {
        final StringBuilder v2 = new StringBuilder();
        char v3;
        do {
            v3 = this.next();
        } while (Character.isWhitespace(v3));
        if (v3 != '\"' && v3 != '\'') {
            while (v3 != '\0' && !Character.isWhitespace(v3)) {
                v2.append(v3);
                v3 = this.next();
            }
            return v2.toString();
        }
        final char v4 = v3;
        while (true) {
            v3 = this.next();
            if (v3 < ' ') {
                throw this.syntaxError("Unterminated string.");
            }
            if (v3 == v4) {
                return v2.toString();
            }
            v2.append(v3);
        }
    }
}
