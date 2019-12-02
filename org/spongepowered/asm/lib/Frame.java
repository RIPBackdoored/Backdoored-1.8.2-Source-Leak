package org.spongepowered.asm.lib;

class Frame
{
    static final int DIM = -268435456;
    static final int ARRAY_OF = 268435456;
    static final int ELEMENT_OF = -268435456;
    static final int KIND = 251658240;
    static final int TOP_IF_LONG_OR_DOUBLE = 8388608;
    static final int VALUE = 8388607;
    static final int BASE_KIND = 267386880;
    static final int BASE_VALUE = 1048575;
    static final int BASE = 16777216;
    static final int OBJECT = 24117248;
    static final int UNINITIALIZED = 25165824;
    private static final int LOCAL = 33554432;
    private static final int STACK = 50331648;
    static final int TOP = 16777216;
    static final int BOOLEAN = 16777225;
    static final int BYTE = 16777226;
    static final int CHAR = 16777227;
    static final int SHORT = 16777228;
    static final int INTEGER = 16777217;
    static final int FLOAT = 16777218;
    static final int DOUBLE = 16777219;
    static final int LONG = 16777220;
    static final int NULL = 16777221;
    static final int UNINITIALIZED_THIS = 16777222;
    static final int[] SIZE;
    Label owner;
    int[] inputLocals;
    int[] inputStack;
    private int[] outputLocals;
    private int[] outputStack;
    int outputStackTop;
    private int initializationCount;
    private int[] initializations;
    
    Frame() {
        super();
    }
    
    final void set(final ClassWriter a3, final int a4, final Object[] a5, final int v1, final Object[] v2) {
        for (int v3 = convert(a3, a4, a5, this.inputLocals); v3 < a5.length; this.inputLocals[v3++] = 16777216) {}
        int v4 = 0;
        for (int a6 = 0; a6 < v1; ++a6) {
            if (v2[a6] == Opcodes.LONG || v2[a6] == Opcodes.DOUBLE) {
                ++v4;
            }
        }
        convert(a3, v1, v2, this.inputStack = new int[v1 + v4]);
        this.outputStackTop = 0;
        this.initializationCount = 0;
    }
    
    private static int convert(final ClassWriter a2, final int a3, final Object[] a4, final int[] v1) {
        int v2 = 0;
        for (int a5 = 0; a5 < a3; ++a5) {
            if (a4[a5] instanceof Integer) {
                v1[v2++] = (0x1000000 | (int)a4[a5]);
                if (a4[a5] == Opcodes.LONG || a4[a5] == Opcodes.DOUBLE) {
                    v1[v2++] = 16777216;
                }
            }
            else if (a4[a5] instanceof String) {
                v1[v2++] = type(a2, Type.getObjectType((String)a4[a5]).getDescriptor());
            }
            else {
                v1[v2++] = (0x1800000 | a2.addUninitializedType("", ((Label)a4[a5]).position));
            }
        }
        return v2;
    }
    
    final void set(final Frame a1) {
        this.inputLocals = a1.inputLocals;
        this.inputStack = a1.inputStack;
        this.outputLocals = a1.outputLocals;
        this.outputStack = a1.outputStack;
        this.outputStackTop = a1.outputStackTop;
        this.initializationCount = a1.initializationCount;
        this.initializations = a1.initializations;
    }
    
    private int get(final int v2) {
        if (this.outputLocals == null || v2 >= this.outputLocals.length) {
            return 0x2000000 | v2;
        }
        int a1 = this.outputLocals[v2];
        if (a1 == 0) {
            final int[] outputLocals = this.outputLocals;
            final int n = 0x2000000 | v2;
            outputLocals[v2] = n;
            a1 = n;
        }
        return a1;
    }
    
    private void set(final int v1, final int v2) {
        if (this.outputLocals == null) {
            this.outputLocals = new int[10];
        }
        final int v3 = this.outputLocals.length;
        if (v1 >= v3) {
            final int[] a1 = new int[Math.max(v1 + 1, 2 * v3)];
            System.arraycopy(this.outputLocals, 0, a1, 0, v3);
            this.outputLocals = a1;
        }
        this.outputLocals[v1] = v2;
    }
    
    private void push(final int v2) {
        if (this.outputStack == null) {
            this.outputStack = new int[10];
        }
        final int v3 = this.outputStack.length;
        if (this.outputStackTop >= v3) {
            final int[] a1 = new int[Math.max(this.outputStackTop + 1, 2 * v3)];
            System.arraycopy(this.outputStack, 0, a1, 0, v3);
            this.outputStack = a1;
        }
        this.outputStack[this.outputStackTop++] = v2;
        final int v4 = this.owner.inputStackTop + this.outputStackTop;
        if (v4 > this.owner.outputStackMax) {
            this.owner.outputStackMax = v4;
        }
    }
    
    private void push(final ClassWriter a1, final String a2) {
        final int v1 = type(a1, a2);
        if (v1 != 0) {
            this.push(v1);
            if (v1 == 16777220 || v1 == 16777219) {
                this.push(16777216);
            }
        }
    }
    
    private static int type(final ClassWriter v-4, final String v-3) {
        final int n = (v-3.charAt(0) == '(') ? (v-3.indexOf(41) + 1) : 0;
        switch (v-3.charAt(n)) {
            case 'V': {
                return 0;
            }
            case 'B':
            case 'C':
            case 'I':
            case 'S':
            case 'Z': {
                return 16777217;
            }
            case 'F': {
                return 16777218;
            }
            case 'J': {
                return 16777220;
            }
            case 'D': {
                return 16777219;
            }
            case 'L': {
                final String a1 = v-3.substring(n + 1, v-3.length() - 1);
                return 0x1700000 | v-4.addType(a1);
            }
            default: {
                int v0;
                for (v0 = n + 1; v-3.charAt(v0) == '['; ++v0) {}
                int v2 = 0;
                switch (v-3.charAt(v0)) {
                    case 'Z': {
                        final int a2 = 16777225;
                        break;
                    }
                    case 'C': {
                        v2 = 16777227;
                        break;
                    }
                    case 'B': {
                        v2 = 16777226;
                        break;
                    }
                    case 'S': {
                        v2 = 16777228;
                        break;
                    }
                    case 'I': {
                        v2 = 16777217;
                        break;
                    }
                    case 'F': {
                        v2 = 16777218;
                        break;
                    }
                    case 'J': {
                        v2 = 16777220;
                        break;
                    }
                    case 'D': {
                        v2 = 16777219;
                        break;
                    }
                    default: {
                        final String substring = v-3.substring(v0 + 1, v-3.length() - 1);
                        v2 = (0x1700000 | v-4.addType(substring));
                        break;
                    }
                }
                return v0 - n << 28 | v2;
            }
        }
    }
    
    private int pop() {
        if (this.outputStackTop > 0) {
            final int[] outputStack = this.outputStack;
            final int outputStackTop = this.outputStackTop - 1;
            this.outputStackTop = outputStackTop;
            return outputStack[outputStackTop];
        }
        final int n = 50331648;
        final Label owner = this.owner;
        return n | -(--owner.inputStackTop);
    }
    
    private void pop(final int a1) {
        if (this.outputStackTop >= a1) {
            this.outputStackTop -= a1;
        }
        else {
            final Label owner = this.owner;
            owner.inputStackTop -= a1 - this.outputStackTop;
            this.outputStackTop = 0;
        }
    }
    
    private void pop(final String a1) {
        final char v1 = a1.charAt(0);
        if (v1 == '(') {
            this.pop((Type.getArgumentsAndReturnSizes(a1) >> 2) - 1);
        }
        else if (v1 == 'J' || v1 == 'D') {
            this.pop(2);
        }
        else {
            this.pop(1);
        }
    }
    
    private void init(final int v2) {
        if (this.initializations == null) {
            this.initializations = new int[2];
        }
        final int v3 = this.initializations.length;
        if (this.initializationCount >= v3) {
            final int[] a1 = new int[Math.max(this.initializationCount + 1, 2 * v3)];
            System.arraycopy(this.initializations, 0, a1, 0, v3);
            this.initializations = a1;
        }
        this.initializations[this.initializationCount++] = v2;
    }
    
    private int init(final ClassWriter v-1, final int v0) {
        int v = 0;
        if (v0 == 16777222) {
            final int a1 = 0x1700000 | v-1.addType(v-1.thisName);
        }
        else {
            if ((v0 & 0xFFF00000) != 0x1800000) {
                return v0;
            }
            final String a2 = v-1.typeTable[v0 & 0xFFFFF].strVal1;
            v = (0x1700000 | v-1.addType(a2));
        }
        for (int v2 = 0; v2 < this.initializationCount; ++v2) {
            int v3 = this.initializations[v2];
            final int v4 = v3 & 0xF0000000;
            final int v5 = v3 & 0xF000000;
            if (v5 == 33554432) {
                v3 = v4 + this.inputLocals[v3 & 0x7FFFFF];
            }
            else if (v5 == 50331648) {
                v3 = v4 + this.inputStack[this.inputStack.length - (v3 & 0x7FFFFF)];
            }
            if (v0 == v3) {
                return v;
            }
        }
        return v0;
    }
    
    final void initInputFrame(final ClassWriter a4, final int v1, final Type[] v2, final int v3) {
        this.inputLocals = new int[v3];
        this.inputStack = new int[0];
        int v4 = 0;
        if ((v1 & 0x8) == 0x0) {
            if ((v1 & 0x80000) == 0x0) {
                this.inputLocals[v4++] = (0x1700000 | a4.addType(a4.thisName));
            }
            else {
                this.inputLocals[v4++] = 16777222;
            }
        }
        for (int a5 = 0; a5 < v2.length; ++a5) {
            final int a6 = type(a4, v2[a5].getDescriptor());
            this.inputLocals[v4++] = a6;
            if (a6 == 16777220 || a6 == 16777219) {
                this.inputLocals[v4++] = 16777216;
            }
        }
        while (v4 < v3) {
            this.inputLocals[v4++] = 16777216;
        }
    }
    
    void execute(final int v-4, final int v-3, final ClassWriter v-2, final Item v-1) {
        Label_2260: {
            switch (v-4) {
                case 0:
                case 116:
                case 117:
                case 118:
                case 119:
                case 145:
                case 146:
                case 147:
                case 167:
                case 177: {
                    break;
                }
                case 1: {
                    this.push(16777221);
                    break;
                }
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 16:
                case 17:
                case 21: {
                    this.push(16777217);
                    break;
                }
                case 9:
                case 10:
                case 22: {
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 11:
                case 12:
                case 13:
                case 23: {
                    this.push(16777218);
                    break;
                }
                case 14:
                case 15:
                case 24: {
                    this.push(16777219);
                    this.push(16777216);
                    break;
                }
                case 18: {
                    switch (v-1.type) {
                        case 3: {
                            this.push(16777217);
                            break Label_2260;
                        }
                        case 5: {
                            this.push(16777220);
                            this.push(16777216);
                            break Label_2260;
                        }
                        case 4: {
                            this.push(16777218);
                            break Label_2260;
                        }
                        case 6: {
                            this.push(16777219);
                            this.push(16777216);
                            break Label_2260;
                        }
                        case 7: {
                            this.push(0x1700000 | v-2.addType("java/lang/Class"));
                            break Label_2260;
                        }
                        case 8: {
                            this.push(0x1700000 | v-2.addType("java/lang/String"));
                            break Label_2260;
                        }
                        case 16: {
                            this.push(0x1700000 | v-2.addType("java/lang/invoke/MethodType"));
                            break Label_2260;
                        }
                        default: {
                            this.push(0x1700000 | v-2.addType("java/lang/invoke/MethodHandle"));
                            break Label_2260;
                        }
                    }
                    break;
                }
                case 25: {
                    this.push(this.get(v-3));
                    break;
                }
                case 46:
                case 51:
                case 52:
                case 53: {
                    this.pop(2);
                    this.push(16777217);
                    break;
                }
                case 47:
                case 143: {
                    this.pop(2);
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 48: {
                    this.pop(2);
                    this.push(16777218);
                    break;
                }
                case 49:
                case 138: {
                    this.pop(2);
                    this.push(16777219);
                    this.push(16777216);
                    break;
                }
                case 50: {
                    this.pop(1);
                    final int a1 = this.pop();
                    this.push(-268435456 + a1);
                    break;
                }
                case 54:
                case 56:
                case 58: {
                    final int a2 = this.pop();
                    this.set(v-3, a2);
                    if (v-3 <= 0) {
                        break;
                    }
                    final int a3 = this.get(v-3 - 1);
                    if (a3 == 16777220 || a3 == 16777219) {
                        this.set(v-3 - 1, 16777216);
                        break;
                    }
                    if ((a3 & 0xF000000) != 0x1000000) {
                        this.set(v-3 - 1, a3 | 0x800000);
                        break;
                    }
                    break;
                }
                case 55:
                case 57: {
                    this.pop(1);
                    final int a4 = this.pop();
                    this.set(v-3, a4);
                    this.set(v-3 + 1, 16777216);
                    if (v-3 <= 0) {
                        break;
                    }
                    final int v1 = this.get(v-3 - 1);
                    if (v1 == 16777220 || v1 == 16777219) {
                        this.set(v-3 - 1, 16777216);
                        break;
                    }
                    if ((v1 & 0xF000000) != 0x1000000) {
                        this.set(v-3 - 1, v1 | 0x800000);
                        break;
                    }
                    break;
                }
                case 79:
                case 81:
                case 83:
                case 84:
                case 85:
                case 86: {
                    this.pop(3);
                    break;
                }
                case 80:
                case 82: {
                    this.pop(4);
                    break;
                }
                case 87:
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 170:
                case 171:
                case 172:
                case 174:
                case 176:
                case 191:
                case 194:
                case 195:
                case 198:
                case 199: {
                    this.pop(1);
                    break;
                }
                case 88:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 173:
                case 175: {
                    this.pop(2);
                    break;
                }
                case 89: {
                    final int v2 = this.pop();
                    this.push(v2);
                    this.push(v2);
                    break;
                }
                case 90: {
                    final int v2 = this.pop();
                    final int v1 = this.pop();
                    this.push(v2);
                    this.push(v1);
                    this.push(v2);
                    break;
                }
                case 91: {
                    final int v2 = this.pop();
                    final int v1 = this.pop();
                    final int v3 = this.pop();
                    this.push(v2);
                    this.push(v3);
                    this.push(v1);
                    this.push(v2);
                    break;
                }
                case 92: {
                    final int v2 = this.pop();
                    final int v1 = this.pop();
                    this.push(v1);
                    this.push(v2);
                    this.push(v1);
                    this.push(v2);
                    break;
                }
                case 93: {
                    final int v2 = this.pop();
                    final int v1 = this.pop();
                    final int v3 = this.pop();
                    this.push(v1);
                    this.push(v2);
                    this.push(v3);
                    this.push(v1);
                    this.push(v2);
                    break;
                }
                case 94: {
                    final int v2 = this.pop();
                    final int v1 = this.pop();
                    final int v3 = this.pop();
                    final int v4 = this.pop();
                    this.push(v1);
                    this.push(v2);
                    this.push(v4);
                    this.push(v3);
                    this.push(v1);
                    this.push(v2);
                    break;
                }
                case 95: {
                    final int v2 = this.pop();
                    final int v1 = this.pop();
                    this.push(v2);
                    this.push(v1);
                    break;
                }
                case 96:
                case 100:
                case 104:
                case 108:
                case 112:
                case 120:
                case 122:
                case 124:
                case 126:
                case 128:
                case 130:
                case 136:
                case 142:
                case 149:
                case 150: {
                    this.pop(2);
                    this.push(16777217);
                    break;
                }
                case 97:
                case 101:
                case 105:
                case 109:
                case 113:
                case 127:
                case 129:
                case 131: {
                    this.pop(4);
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 98:
                case 102:
                case 106:
                case 110:
                case 114:
                case 137:
                case 144: {
                    this.pop(2);
                    this.push(16777218);
                    break;
                }
                case 99:
                case 103:
                case 107:
                case 111:
                case 115: {
                    this.pop(4);
                    this.push(16777219);
                    this.push(16777216);
                    break;
                }
                case 121:
                case 123:
                case 125: {
                    this.pop(3);
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 132: {
                    this.set(v-3, 16777217);
                    break;
                }
                case 133:
                case 140: {
                    this.pop(1);
                    this.push(16777220);
                    this.push(16777216);
                    break;
                }
                case 134: {
                    this.pop(1);
                    this.push(16777218);
                    break;
                }
                case 135:
                case 141: {
                    this.pop(1);
                    this.push(16777219);
                    this.push(16777216);
                    break;
                }
                case 139:
                case 190:
                case 193: {
                    this.pop(1);
                    this.push(16777217);
                    break;
                }
                case 148:
                case 151:
                case 152: {
                    this.pop(4);
                    this.push(16777217);
                    break;
                }
                case 168:
                case 169: {
                    throw new RuntimeException("JSR/RET are not supported with computeFrames option");
                }
                case 178: {
                    this.push(v-2, v-1.strVal3);
                    break;
                }
                case 179: {
                    this.pop(v-1.strVal3);
                    break;
                }
                case 180: {
                    this.pop(1);
                    this.push(v-2, v-1.strVal3);
                    break;
                }
                case 181: {
                    this.pop(v-1.strVal3);
                    this.pop();
                    break;
                }
                case 182:
                case 183:
                case 184:
                case 185: {
                    this.pop(v-1.strVal3);
                    if (v-4 != 184) {
                        final int v2 = this.pop();
                        if (v-4 == 183 && v-1.strVal2.charAt(0) == '<') {
                            this.init(v2);
                        }
                    }
                    this.push(v-2, v-1.strVal3);
                    break;
                }
                case 186: {
                    this.pop(v-1.strVal2);
                    this.push(v-2, v-1.strVal2);
                    break;
                }
                case 187: {
                    this.push(0x1800000 | v-2.addUninitializedType(v-1.strVal1, v-3));
                    break;
                }
                case 188: {
                    this.pop();
                    switch (v-3) {
                        case 4: {
                            this.push(285212681);
                            break Label_2260;
                        }
                        case 5: {
                            this.push(285212683);
                            break Label_2260;
                        }
                        case 8: {
                            this.push(285212682);
                            break Label_2260;
                        }
                        case 9: {
                            this.push(285212684);
                            break Label_2260;
                        }
                        case 10: {
                            this.push(285212673);
                            break Label_2260;
                        }
                        case 6: {
                            this.push(285212674);
                            break Label_2260;
                        }
                        case 7: {
                            this.push(285212675);
                            break Label_2260;
                        }
                        default: {
                            this.push(285212676);
                            break Label_2260;
                        }
                    }
                    break;
                }
                case 189: {
                    final String v5 = v-1.strVal1;
                    this.pop();
                    if (v5.charAt(0) == '[') {
                        this.push(v-2, '[' + v5);
                        break;
                    }
                    this.push(0x11700000 | v-2.addType(v5));
                    break;
                }
                case 192: {
                    final String v5 = v-1.strVal1;
                    this.pop();
                    if (v5.charAt(0) == '[') {
                        this.push(v-2, v5);
                        break;
                    }
                    this.push(0x1700000 | v-2.addType(v5));
                    break;
                }
                default: {
                    this.pop(v-3);
                    this.push(v-2, v-1.strVal1);
                    break;
                }
            }
        }
    }
    
    final boolean merge(final ClassWriter v-6, final Frame v-5, final int v-4) {
        boolean b = false;
        final int length = this.inputLocals.length;
        final int length2 = this.inputStack.length;
        if (v-5.inputLocals == null) {
            v-5.inputLocals = new int[length];
            b = true;
        }
        for (int v0 = 0; v0 < length; ++v0) {
            int v5 = 0;
            if (this.outputLocals != null && v0 < this.outputLocals.length) {
                final int v2 = this.outputLocals[v0];
                if (v2 == 0) {
                    final int a1 = this.inputLocals[v0];
                }
                else {
                    final int v3 = v2 & 0xF0000000;
                    final int v4 = v2 & 0xF000000;
                    if (v4 == 16777216) {
                        final int a2 = v2;
                    }
                    else {
                        if (v4 == 33554432) {
                            final int a3 = v3 + this.inputLocals[v2 & 0x7FFFFF];
                        }
                        else {
                            v5 = v3 + this.inputStack[length2 - (v2 & 0x7FFFFF)];
                        }
                        if ((v2 & 0x800000) != 0x0 && (v5 == 16777220 || v5 == 16777219)) {
                            v5 = 16777216;
                        }
                    }
                }
            }
            else {
                v5 = this.inputLocals[v0];
            }
            if (this.initializations != null) {
                v5 = this.init(v-6, v5);
            }
            b |= merge(v-6, v5, v-5.inputLocals, v0);
        }
        if (v-4 > 0) {
            for (int v0 = 0; v0 < length; ++v0) {
                final int v5 = this.inputLocals[v0];
                b |= merge(v-6, v5, v-5.inputLocals, v0);
            }
            if (v-5.inputStack == null) {
                v-5.inputStack = new int[1];
                b = true;
            }
            b |= merge(v-6, v-4, v-5.inputStack, 0);
            return b;
        }
        final int v6 = this.inputStack.length + this.owner.inputStackTop;
        if (v-5.inputStack == null) {
            v-5.inputStack = new int[v6 + this.outputStackTop];
            b = true;
        }
        for (int v0 = 0; v0 < v6; ++v0) {
            int v5 = this.inputStack[v0];
            if (this.initializations != null) {
                v5 = this.init(v-6, v5);
            }
            b |= merge(v-6, v5, v-5.inputStack, v0);
        }
        for (int v0 = 0; v0 < this.outputStackTop; ++v0) {
            final int v2 = this.outputStack[v0];
            final int v3 = v2 & 0xF0000000;
            final int v4 = v2 & 0xF000000;
            int v5;
            if (v4 == 16777216) {
                v5 = v2;
            }
            else {
                if (v4 == 33554432) {
                    v5 = v3 + this.inputLocals[v2 & 0x7FFFFF];
                }
                else {
                    v5 = v3 + this.inputStack[length2 - (v2 & 0x7FFFFF)];
                }
                if ((v2 & 0x800000) != 0x0 && (v5 == 16777220 || v5 == 16777219)) {
                    v5 = 16777216;
                }
            }
            if (this.initializations != null) {
                v5 = this.init(v-6, v5);
            }
            b |= merge(v-6, v5, v-5.inputStack, v6 + v0);
        }
        return b;
    }
    
    private static boolean merge(final ClassWriter v-6, int v-5, final int[] v-4, final int v-3) {
        final int n = v-4[v-3];
        if (n == v-5) {
            return false;
        }
        if ((v-5 & 0xFFFFFFF) == 0x1000005) {
            if (n == 16777221) {
                return false;
            }
            v-5 = 16777221;
        }
        if (n == 0) {
            v-4[v-3] = v-5;
            return true;
        }
        int n2 = 0;
        if ((n & 0xFF00000) == 0x1700000 || (n & 0xF0000000) != 0x0) {
            if (v-5 == 16777221) {
                return false;
            }
            if ((v-5 & 0xFFF00000) == (n & 0xFFF00000)) {
                if ((n & 0xFF00000) == 0x1700000) {
                    final int a1 = (v-5 & 0xF0000000) | 0x1700000 | v-6.getMergedType(v-5 & 0xFFFFF, n & 0xFFFFF);
                }
                else {
                    final int a2 = -268435456 + (n & 0xF0000000);
                    final int a3 = a2 | 0x1700000 | v-6.addType("java/lang/Object");
                }
            }
            else if ((v-5 & 0xFF00000) == 0x1700000 || (v-5 & 0xF0000000) != 0x0) {
                final int a4 = (((v-5 & 0xF0000000) == 0x0 || (v-5 & 0xFF00000) == 0x1700000) ? 0 : -268435456) + (v-5 & 0xF0000000);
                final int v1 = (((n & 0xF0000000) == 0x0 || (n & 0xFF00000) == 0x1700000) ? 0 : -268435456) + (n & 0xF0000000);
                n2 = (Math.min(a4, v1) | 0x1700000 | v-6.addType("java/lang/Object"));
            }
            else {
                n2 = 16777216;
            }
        }
        else if (n == 16777221) {
            n2 = (((v-5 & 0xFF00000) == 0x1700000 || (v-5 & 0xF0000000) != 0x0) ? v-5 : 16777216);
        }
        else {
            n2 = 16777216;
        }
        if (n != n2) {
            v-4[v-3] = n2;
            return true;
        }
        return false;
    }
    
    static {
        final int[] size = new int[202];
        final String v0 = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE";
        for (int v2 = 0; v2 < size.length; ++v2) {
            size[v2] = v0.charAt(v2) - 'E';
        }
        SIZE = size;
    }
}
