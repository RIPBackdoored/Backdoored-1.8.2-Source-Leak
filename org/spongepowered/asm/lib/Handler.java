package org.spongepowered.asm.lib;

class Handler
{
    Label start;
    Label end;
    Label handler;
    String desc;
    int type;
    Handler next;
    
    Handler() {
        super();
    }
    
    static Handler remove(Handler a2, final Label a3, final Label v1) {
        if (a2 == null) {
            return null;
        }
        a2.next = remove(a2.next, a3, v1);
        final int v2 = a2.start.position;
        final int v3 = a2.end.position;
        final int v4 = a3.position;
        final int v5 = (v1 == null) ? 0 : v1.position;
        if (v4 < v3 && v5 > v2) {
            if (v4 <= v2) {
                if (v5 >= v3) {
                    a2 = a2.next;
                }
                else {
                    a2.start = v1;
                }
            }
            else if (v5 >= v3) {
                a2.end = a3;
            }
            else {
                final Handler a4 = new Handler();
                a4.start = v1;
                a4.end = a2.end;
                a4.handler = a2.handler;
                a4.desc = a2.desc;
                a4.type = a2.type;
                a4.next = a2.next;
                a2.end = a3;
                a2.next = a4;
            }
        }
        return a2;
    }
}
