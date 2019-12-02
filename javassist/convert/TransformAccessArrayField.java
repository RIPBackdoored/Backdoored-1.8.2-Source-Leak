package javassist.convert;

import javassist.*;
import javassist.bytecode.analysis.*;
import javassist.bytecode.*;

public final class TransformAccessArrayField extends Transformer
{
    private final String methodClassname;
    private final CodeConverter.ArrayAccessReplacementMethodNames names;
    private Frame[] frames;
    private int offset;
    
    public TransformAccessArrayField(final Transformer a1, final String a2, final CodeConverter.ArrayAccessReplacementMethodNames a3) throws NotFoundException {
        super(a1);
        this.methodClassname = a2;
        this.names = a3;
    }
    
    @Override
    public void initialize(final ConstPool v2, final CtClass v3, final MethodInfo v4) throws CannotCompileException {
        final CodeIterator v5 = v4.getCodeAttribute().iterator();
        while (v5.hasNext()) {
            try {
                int a1 = v5.next();
                final int a2 = v5.byteAt(a1);
                if (a2 == 50) {
                    this.initFrames(v3, v4);
                }
                if (a2 == 50 || a2 == 51 || a2 == 52 || a2 == 49 || a2 == 48 || a2 == 46 || a2 == 47 || a2 == 53) {
                    a1 = this.replace(v2, v5, a1, a2, this.getLoadReplacementSignature(a2));
                }
                else {
                    if (a2 != 83 && a2 != 84 && a2 != 85 && a2 != 82 && a2 != 81 && a2 != 79 && a2 != 80 && a2 != 86) {
                        continue;
                    }
                    a1 = this.replace(v2, v5, a1, a2, this.getStoreReplacementSignature(a2));
                }
                continue;
            }
            catch (Exception a3) {
                throw new CannotCompileException(a3);
            }
            break;
        }
    }
    
    @Override
    public void clean() {
        this.frames = null;
        this.offset = -1;
    }
    
    @Override
    public int transform(final CtClass a1, final int a2, final CodeIterator a3, final ConstPool a4) throws BadBytecode {
        return a2;
    }
    
    private Frame getFrame(final int a1) throws BadBytecode {
        return this.frames[a1 - this.offset];
    }
    
    private void initFrames(final CtClass a1, final MethodInfo a2) throws BadBytecode {
        if (this.frames == null) {
            this.frames = new Analyzer().analyze(a1, a2);
            this.offset = 0;
        }
    }
    
    private int updatePos(final int a1, final int a2) {
        if (this.offset > -1) {
            this.offset += a2;
        }
        return a1 + a2;
    }
    
    private String getTopType(final int a1) throws BadBytecode {
        final Frame v1 = this.getFrame(a1);
        if (v1 == null) {
            return null;
        }
        final CtClass v2 = v1.peek().getCtClass();
        return (v2 != null) ? Descriptor.toJvmName(v2) : null;
    }
    
    private int replace(final ConstPool v1, final CodeIterator v2, int v3, final int v4, final String v5) throws BadBytecode {
        String v6 = null;
        final String v7 = this.getMethodName(v4);
        if (v7 != null) {
            if (v4 == 50) {
                v6 = this.getTopType(v2.lookAhead());
                if (v6 == null) {
                    return v3;
                }
                if ("java/lang/Object".equals(v6)) {
                    v6 = null;
                }
            }
            v2.writeByte(0, v3);
            final CodeIterator.Gap a2 = v2.insertGapAt(v3, (v6 != null) ? 5 : 2, false);
            v3 = a2.position;
            final int a3 = v1.addClassInfo(this.methodClassname);
            final int a4 = v1.addMethodrefInfo(a3, v7, v5);
            v2.writeByte(184, v3);
            v2.write16bit(a4, v3 + 1);
            if (v6 != null) {
                final int a5 = v1.addClassInfo(v6);
                v2.writeByte(192, v3 + 3);
                v2.write16bit(a5, v3 + 4);
            }
            v3 = this.updatePos(v3, a2.length);
        }
        return v3;
    }
    
    private String getMethodName(final int a1) {
        String v1 = null;
        switch (a1) {
            case 50: {
                v1 = this.names.objectRead();
                break;
            }
            case 51: {
                v1 = this.names.byteOrBooleanRead();
                break;
            }
            case 52: {
                v1 = this.names.charRead();
                break;
            }
            case 49: {
                v1 = this.names.doubleRead();
                break;
            }
            case 48: {
                v1 = this.names.floatRead();
                break;
            }
            case 46: {
                v1 = this.names.intRead();
                break;
            }
            case 53: {
                v1 = this.names.shortRead();
                break;
            }
            case 47: {
                v1 = this.names.longRead();
                break;
            }
            case 83: {
                v1 = this.names.objectWrite();
                break;
            }
            case 84: {
                v1 = this.names.byteOrBooleanWrite();
                break;
            }
            case 85: {
                v1 = this.names.charWrite();
                break;
            }
            case 82: {
                v1 = this.names.doubleWrite();
                break;
            }
            case 81: {
                v1 = this.names.floatWrite();
                break;
            }
            case 79: {
                v1 = this.names.intWrite();
                break;
            }
            case 86: {
                v1 = this.names.shortWrite();
                break;
            }
            case 80: {
                v1 = this.names.longWrite();
                break;
            }
        }
        if (v1.equals("")) {
            v1 = null;
        }
        return v1;
    }
    
    private String getLoadReplacementSignature(final int a1) throws BadBytecode {
        switch (a1) {
            case 50: {
                return "(Ljava/lang/Object;I)Ljava/lang/Object;";
            }
            case 51: {
                return "(Ljava/lang/Object;I)B";
            }
            case 52: {
                return "(Ljava/lang/Object;I)C";
            }
            case 49: {
                return "(Ljava/lang/Object;I)D";
            }
            case 48: {
                return "(Ljava/lang/Object;I)F";
            }
            case 46: {
                return "(Ljava/lang/Object;I)I";
            }
            case 53: {
                return "(Ljava/lang/Object;I)S";
            }
            case 47: {
                return "(Ljava/lang/Object;I)J";
            }
            default: {
                throw new BadBytecode(a1);
            }
        }
    }
    
    private String getStoreReplacementSignature(final int a1) throws BadBytecode {
        switch (a1) {
            case 83: {
                return "(Ljava/lang/Object;ILjava/lang/Object;)V";
            }
            case 84: {
                return "(Ljava/lang/Object;IB)V";
            }
            case 85: {
                return "(Ljava/lang/Object;IC)V";
            }
            case 82: {
                return "(Ljava/lang/Object;ID)V";
            }
            case 81: {
                return "(Ljava/lang/Object;IF)V";
            }
            case 79: {
                return "(Ljava/lang/Object;II)V";
            }
            case 86: {
                return "(Ljava/lang/Object;IS)V";
            }
            case 80: {
                return "(Ljava/lang/Object;IJ)V";
            }
            default: {
                throw new BadBytecode(a1);
            }
        }
    }
}
