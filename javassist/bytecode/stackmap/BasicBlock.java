package javassist.bytecode.stackmap;

import javassist.bytecode.*;
import java.util.*;

public class BasicBlock
{
    protected int position;
    protected int length;
    protected int incoming;
    protected BasicBlock[] exit;
    protected boolean stop;
    protected Catch toCatch;
    
    protected BasicBlock(final int a1) {
        super();
        this.position = a1;
        this.length = 0;
        this.incoming = 0;
    }
    
    public static BasicBlock find(final BasicBlock[] v1, final int v2) throws BadBytecode {
        for (int a2 = 0; a2 < v1.length; ++a2) {
            final int a3 = v1[a2].position;
            if (a3 <= v2 && v2 < a3 + v1[a2].length) {
                return v1[a2];
            }
        }
        throw new BadBytecode("no basic block at " + v2);
    }
    
    @Override
    public String toString() {
        final StringBuffer v1 = new StringBuffer();
        final String v2 = this.getClass().getName();
        final int v3 = v2.lastIndexOf(46);
        v1.append((v3 < 0) ? v2 : v2.substring(v3 + 1));
        v1.append("[");
        this.toString2(v1);
        v1.append("]");
        return v1.toString();
    }
    
    protected void toString2(final StringBuffer v2) {
        v2.append("pos=").append(this.position).append(", len=").append(this.length).append(", in=").append(this.incoming).append(", exit{");
        if (this.exit != null) {
            for (int a1 = 0; a1 < this.exit.length; ++a1) {
                v2.append(this.exit[a1].position).append(",");
            }
        }
        v2.append("}, {");
        for (Catch v3 = this.toCatch; v3 != null; v3 = v3.next) {
            v2.append("(").append(v3.body.position).append(", ").append(v3.typeIndex).append("), ");
        }
        v2.append("}");
    }
    
    static class JsrBytecode extends BadBytecode
    {
        JsrBytecode() {
            super("JSR");
        }
    }
    
    public static class Catch
    {
        public Catch next;
        public BasicBlock body;
        public int typeIndex;
        
        Catch(final BasicBlock a1, final int a2, final Catch a3) {
            super();
            this.body = a1;
            this.typeIndex = a2;
            this.next = a3;
        }
    }
    
    static class Mark implements Comparable
    {
        int position;
        BasicBlock block;
        BasicBlock[] jump;
        boolean alwaysJmp;
        int size;
        Catch catcher;
        
        Mark(final int a1) {
            super();
            this.position = a1;
            this.block = null;
            this.jump = null;
            this.alwaysJmp = false;
            this.size = 0;
            this.catcher = null;
        }
        
        @Override
        public int compareTo(final Object v2) {
            if (v2 instanceof Mark) {
                final int a1 = ((Mark)v2).position;
                return this.position - a1;
            }
            return -1;
        }
        
        void setJump(final BasicBlock[] a1, final int a2, final boolean a3) {
            this.jump = a1;
            this.size = a2;
            this.alwaysJmp = a3;
        }
    }
    
    public static class Maker
    {
        public Maker() {
            super();
        }
        
        protected BasicBlock makeBlock(final int a1) {
            return new BasicBlock(a1);
        }
        
        protected BasicBlock[] makeArray(final int a1) {
            return new BasicBlock[a1];
        }
        
        private BasicBlock[] makeArray(final BasicBlock a1) {
            final BasicBlock[] v1 = this.makeArray(1);
            v1[0] = a1;
            return v1;
        }
        
        private BasicBlock[] makeArray(final BasicBlock a1, final BasicBlock a2) {
            final BasicBlock[] v1 = this.makeArray(2);
            v1[0] = a1;
            v1[1] = a2;
            return v1;
        }
        
        public BasicBlock[] make(final MethodInfo a1) throws BadBytecode {
            final CodeAttribute v1 = a1.getCodeAttribute();
            if (v1 == null) {
                return null;
            }
            final CodeIterator v2 = v1.iterator();
            return this.make(v2, 0, v2.getCodeLength(), v1.getExceptionTable());
        }
        
        public BasicBlock[] make(final CodeIterator a1, final int a2, final int a3, final ExceptionTable a4) throws BadBytecode {
            final HashMap v1 = this.makeMarks(a1, a2, a3, a4);
            final BasicBlock[] v2 = this.makeBlocks(v1);
            this.addCatchers(v2, a4);
            return v2;
        }
        
        private Mark makeMark(final HashMap a1, final int a2) {
            return this.makeMark0(a1, a2, true, true);
        }
        
        private Mark makeMark(final HashMap a1, final int a2, final BasicBlock[] a3, final int a4, final boolean a5) {
            final Mark v1 = this.makeMark0(a1, a2, false, false);
            v1.setJump(a3, a4, a5);
            return v1;
        }
        
        private Mark makeMark0(final HashMap a1, final int a2, final boolean a3, final boolean a4) {
            final Integer v1 = new Integer(a2);
            Mark v2 = a1.get(v1);
            if (v2 == null) {
                v2 = new Mark(a2);
                a1.put(v1, v2);
            }
            if (a3) {
                if (v2.block == null) {
                    v2.block = this.makeBlock(a2);
                }
                if (a4) {
                    final BasicBlock block = v2.block;
                    ++block.incoming;
                }
            }
            return v2;
        }
        
        private HashMap makeMarks(final CodeIterator v-8, final int v-7, final int v-6, final ExceptionTable v-5) throws BadBytecode {
            v-8.begin();
            v-8.move(v-7);
            final HashMap hashMap = new HashMap();
            while (v-8.hasNext()) {
                final int a5 = v-8.next();
                if (a5 >= v-6) {
                    break;
                }
                final int byte1 = v-8.byteAt(a5);
                if ((153 <= byte1 && byte1 <= 166) || byte1 == 198 || byte1 == 199) {
                    final Mark a1 = this.makeMark(hashMap, a5 + v-8.s16bitAt(a5 + 1));
                    final Mark a2 = this.makeMark(hashMap, a5 + 3);
                    this.makeMark(hashMap, a5, this.makeArray(a1.block, a2.block), 3, false);
                }
                else if (167 <= byte1 && byte1 <= 171) {
                    switch (byte1) {
                        case 167: {
                            this.makeGoto(hashMap, a5, a5 + v-8.s16bitAt(a5 + 1), 3);
                            continue;
                        }
                        case 168: {
                            this.makeJsr(hashMap, a5, a5 + v-8.s16bitAt(a5 + 1), 3);
                            continue;
                        }
                        case 169: {
                            this.makeMark(hashMap, a5, null, 2, true);
                            continue;
                        }
                        case 170: {
                            final int a3 = (a5 & 0xFFFFFFFC) + 4;
                            final int a4 = v-8.s32bitAt(a3 + 4);
                            final int v1 = v-8.s32bitAt(a3 + 8);
                            final int v2 = v1 - a4 + 1;
                            final BasicBlock[] v3 = this.makeArray(v2 + 1);
                            v3[0] = this.makeMark(hashMap, a5 + v-8.s32bitAt(a3)).block;
                            int v4 = a3 + 12;
                            final int v5 = v4 + v2 * 4;
                            int v6 = 1;
                            while (v4 < v5) {
                                v3[v6++] = this.makeMark(hashMap, a5 + v-8.s32bitAt(v4)).block;
                                v4 += 4;
                            }
                            this.makeMark(hashMap, a5, v3, v5 - a5, true);
                            continue;
                        }
                        case 171: {
                            final int a6 = (a5 & 0xFFFFFFFC) + 4;
                            final int v7 = v-8.s32bitAt(a6 + 4);
                            final BasicBlock[] v8 = this.makeArray(v7 + 1);
                            v8[0] = this.makeMark(hashMap, a5 + v-8.s32bitAt(a6)).block;
                            int v2 = a6 + 8 + 4;
                            final int v9 = v2 + v7 * 8 - 4;
                            int v4 = 1;
                            while (v2 < v9) {
                                v8[v4++] = this.makeMark(hashMap, a5 + v-8.s32bitAt(v2)).block;
                                v2 += 8;
                            }
                            this.makeMark(hashMap, a5, v8, v9 - a5, true);
                            continue;
                        }
                    }
                }
                else if ((172 <= byte1 && byte1 <= 177) || byte1 == 191) {
                    this.makeMark(hashMap, a5, null, 1, true);
                }
                else if (byte1 == 200) {
                    this.makeGoto(hashMap, a5, a5 + v-8.s32bitAt(a5 + 1), 5);
                }
                else if (byte1 == 201) {
                    this.makeJsr(hashMap, a5, a5 + v-8.s32bitAt(a5 + 1), 5);
                }
                else {
                    if (byte1 != 196 || v-8.byteAt(a5 + 1) != 169) {
                        continue;
                    }
                    this.makeMark(hashMap, a5, null, 4, true);
                }
            }
            if (v-5 != null) {
                int a5 = v-5.size();
                while (--a5 >= 0) {
                    this.makeMark0(hashMap, v-5.startPc(a5), true, false);
                    this.makeMark(hashMap, v-5.handlerPc(a5));
                }
            }
            return hashMap;
        }
        
        private void makeGoto(final HashMap a1, final int a2, final int a3, final int a4) {
            final Mark v1 = this.makeMark(a1, a3);
            final BasicBlock[] v2 = this.makeArray(v1.block);
            this.makeMark(a1, a2, v2, a4, true);
        }
        
        protected void makeJsr(final HashMap a1, final int a2, final int a3, final int a4) throws BadBytecode {
            throw new JsrBytecode();
        }
        
        private BasicBlock[] makeBlocks(final HashMap v-4) {
            final Mark[] array = (Mark[])v-4.values().toArray(new Mark[v-4.size()]);
            Arrays.sort(array);
            final ArrayList list = new ArrayList();
            int i = 0;
            BasicBlock v0 = null;
            if (array.length > 0 && array[0].position == 0 && array[0].block != null) {
                final BasicBlock a1 = getBBlock(array[i++]);
            }
            else {
                v0 = this.makeBlock(0);
            }
            list.add(v0);
            while (i < array.length) {
                final Mark v2 = array[i++];
                final BasicBlock v3 = getBBlock(v2);
                if (v3 == null) {
                    if (v0.length > 0) {
                        v0 = this.makeBlock(v0.position + v0.length);
                        list.add(v0);
                    }
                    v0.length = v2.position + v2.size - v0.position;
                    v0.exit = v2.jump;
                    v0.stop = v2.alwaysJmp;
                }
                else {
                    if (v0.length == 0) {
                        v0.length = v2.position - v0.position;
                        final BasicBlock basicBlock = v3;
                        ++basicBlock.incoming;
                        v0.exit = this.makeArray(v3);
                    }
                    else if (v0.position + v0.length < v2.position) {
                        v0 = this.makeBlock(v0.position + v0.length);
                        list.add(v0);
                        v0.length = v2.position - v0.position;
                        v0.stop = true;
                        v0.exit = this.makeArray(v3);
                    }
                    list.add(v3);
                    v0 = v3;
                }
            }
            return list.toArray(this.makeArray(list.size()));
        }
        
        private static BasicBlock getBBlock(final Mark a1) {
            final BasicBlock v1 = a1.block;
            if (v1 != null && a1.size > 0) {
                v1.exit = a1.jump;
                v1.length = a1.size;
                v1.stop = a1.alwaysJmp;
            }
            return v1;
        }
        
        private void addCatchers(final BasicBlock[] v-6, final ExceptionTable v-5) throws BadBytecode {
            if (v-5 == null) {
                return;
            }
            int size = v-5.size();
            while (--size >= 0) {
                final BasicBlock find = BasicBlock.find(v-6, v-5.handlerPc(size));
                final int startPc = v-5.startPc(size);
                final int endPc = v-5.endPc(size);
                final int v0 = v-5.catchType(size);
                final BasicBlock basicBlock = find;
                --basicBlock.incoming;
                for (int v2 = 0; v2 < v-6.length; ++v2) {
                    final BasicBlock a1 = v-6[v2];
                    final int a2 = a1.position;
                    if (startPc <= a2 && a2 < endPc) {
                        a1.toCatch = new Catch(find, v0, a1.toCatch);
                        final BasicBlock basicBlock2 = find;
                        ++basicBlock2.incoming;
                    }
                }
            }
        }
    }
}
