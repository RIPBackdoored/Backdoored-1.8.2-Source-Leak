package org.json.simple.parser;

public class Yytoken
{
    public static final int TYPE_VALUE = 0;
    public static final int TYPE_LEFT_BRACE = 1;
    public static final int TYPE_RIGHT_BRACE = 2;
    public static final int TYPE_LEFT_SQUARE = 3;
    public static final int TYPE_RIGHT_SQUARE = 4;
    public static final int TYPE_COMMA = 5;
    public static final int TYPE_COLON = 6;
    public static final int TYPE_EOF = -1;
    public int type;
    public Object value;
    
    public Yytoken(final int a1, final Object a2) {
        super();
        this.type = 0;
        this.value = null;
        this.type = a1;
        this.value = a2;
    }
    
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        switch (this.type) {
            case 0: {
                v1.append("VALUE(").append(this.value).append(")");
                break;
            }
            case 1: {
                v1.append("LEFT BRACE({)");
                break;
            }
            case 2: {
                v1.append("RIGHT BRACE(})");
                break;
            }
            case 3: {
                v1.append("LEFT SQUARE([)");
                break;
            }
            case 4: {
                v1.append("RIGHT SQUARE(])");
                break;
            }
            case 5: {
                v1.append("COMMA(,)");
                break;
            }
            case 6: {
                v1.append("COLON(:)");
                break;
            }
            case -1: {
                v1.append("END OF FILE");
                break;
            }
        }
        return v1.toString();
    }
}
