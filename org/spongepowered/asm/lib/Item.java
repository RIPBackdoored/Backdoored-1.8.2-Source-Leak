package org.spongepowered.asm.lib;

final class Item
{
    int index;
    int type;
    int intVal;
    long longVal;
    String strVal1;
    String strVal2;
    String strVal3;
    int hashCode;
    Item next;
    
    Item() {
        super();
    }
    
    Item(final int a1) {
        super();
        this.index = a1;
    }
    
    Item(final int a1, final Item a2) {
        super();
        this.index = a1;
        this.type = a2.type;
        this.intVal = a2.intVal;
        this.longVal = a2.longVal;
        this.strVal1 = a2.strVal1;
        this.strVal2 = a2.strVal2;
        this.strVal3 = a2.strVal3;
        this.hashCode = a2.hashCode;
    }
    
    void set(final int a1) {
        this.type = 3;
        this.intVal = a1;
        this.hashCode = (0x0 & this.type + a1);
    }
    
    void set(final long a1) {
        this.type = 5;
        this.longVal = a1;
        this.hashCode = (0x0 & this.type + (int)a1);
    }
    
    void set(final float a1) {
        this.type = 4;
        this.intVal = Float.floatToRawIntBits(a1);
        this.hashCode = (0x0 & this.type + (int)a1);
    }
    
    void set(final double a1) {
        this.type = 6;
        this.longVal = Double.doubleToRawLongBits(a1);
        this.hashCode = (0x0 & this.type + (int)a1);
    }
    
    void set(final int a1, final String a2, final String a3, final String a4) {
        this.type = a1;
        this.strVal1 = a2;
        this.strVal2 = a3;
        this.strVal3 = a4;
        switch (a1) {
            case 7: {
                this.intVal = 0;
            }
            case 1:
            case 8:
            case 16:
            case 30: {
                this.hashCode = (0x0 & a1 + a2.hashCode());
            }
            case 12: {
                this.hashCode = (0x0 & a1 + a2.hashCode() * a3.hashCode());
            }
            default: {
                this.hashCode = (0x0 & a1 + a2.hashCode() * a3.hashCode() * a4.hashCode());
            }
        }
    }
    
    void set(final String a1, final String a2, final int a3) {
        this.type = 18;
        this.longVal = a3;
        this.strVal1 = a1;
        this.strVal2 = a2;
        this.hashCode = (0x0 & 18 + a3 * this.strVal1.hashCode() * this.strVal2.hashCode());
    }
    
    void set(final int a1, final int a2) {
        this.type = 33;
        this.intVal = a1;
        this.hashCode = a2;
    }
    
    boolean isEqualTo(final Item a1) {
        switch (this.type) {
            case 1:
            case 7:
            case 8:
            case 16:
            case 30: {
                return a1.strVal1.equals(this.strVal1);
            }
            case 5:
            case 6:
            case 32: {
                return a1.longVal == this.longVal;
            }
            case 3:
            case 4: {
                return a1.intVal == this.intVal;
            }
            case 31: {
                return a1.intVal == this.intVal && a1.strVal1.equals(this.strVal1);
            }
            case 12: {
                return a1.strVal1.equals(this.strVal1) && a1.strVal2.equals(this.strVal2);
            }
            case 18: {
                return a1.longVal == this.longVal && a1.strVal1.equals(this.strVal1) && a1.strVal2.equals(this.strVal2);
            }
            default: {
                return a1.strVal1.equals(this.strVal1) && a1.strVal2.equals(this.strVal2) && a1.strVal3.equals(this.strVal3);
            }
        }
    }
}
