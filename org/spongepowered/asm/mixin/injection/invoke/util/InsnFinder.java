package org.spongepowered.asm.mixin.injection.invoke.util;

import org.spongepowered.asm.mixin.injection.struct.*;
import org.spongepowered.asm.lib.tree.*;
import org.apache.logging.log4j.*;
import org.spongepowered.asm.lib.tree.analysis.*;

public class InsnFinder
{
    private static final Logger logger;
    
    public InsnFinder() {
        super();
    }
    
    public AbstractInsnNode findPopInsn(final Target v1, final AbstractInsnNode v2) {
        try {
            new PopAnalyzer(v2).analyze(v1.classNode.name, v1.method);
        }
        catch (AnalyzerException a1) {
            if (a1.getCause() instanceof AnalysisResultException) {
                return ((AnalysisResultException)a1.getCause()).getResult();
            }
            InsnFinder.logger.catching((Throwable)a1);
        }
        return null;
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
    
    static class AnalysisResultException extends RuntimeException
    {
        private static final long serialVersionUID = 1L;
        private AbstractInsnNode result;
        
        public AnalysisResultException(final AbstractInsnNode a1) {
            super();
            this.result = a1;
        }
        
        public AbstractInsnNode getResult() {
            return this.result;
        }
    }
    
    enum AnalyzerState
    {
        SEARCH, 
        ANALYSE, 
        COMPLETE;
        
        private static final /* synthetic */ AnalyzerState[] $VALUES;
        
        public static AnalyzerState[] values() {
            return AnalyzerState.$VALUES.clone();
        }
        
        public static AnalyzerState valueOf(final String a1) {
            return Enum.valueOf(AnalyzerState.class, a1);
        }
        
        static {
            $VALUES = new AnalyzerState[] { AnalyzerState.SEARCH, AnalyzerState.ANALYSE, AnalyzerState.COMPLETE };
        }
    }
    
    static class PopAnalyzer extends Analyzer<BasicValue>
    {
        protected final AbstractInsnNode node;
        
        public PopAnalyzer(final AbstractInsnNode a1) {
            super(new BasicInterpreter());
            this.node = a1;
        }
        
        @Override
        protected Frame<BasicValue> newFrame(final int a1, final int a2) {
            return new PopFrame(a1, a2);
        }
        
        class PopFrame extends Frame<BasicValue>
        {
            private AbstractInsnNode current;
            private AnalyzerState state;
            private int depth;
            final /* synthetic */ PopAnalyzer this$0;
            
            public PopFrame(final PopAnalyzer a1, final int a2, final int a3) {
                this.this$0 = a1;
                super(a2, a3);
                this.state = AnalyzerState.SEARCH;
                this.depth = 0;
            }
            
            @Override
            public void execute(final AbstractInsnNode a1, final Interpreter<BasicValue> a2) throws AnalyzerException {
                super.execute(this.current = a1, a2);
            }
            
            @Override
            public void push(final BasicValue a1) throws IndexOutOfBoundsException {
                if (this.current == this.this$0.node && this.state == AnalyzerState.SEARCH) {
                    this.state = AnalyzerState.ANALYSE;
                    ++this.depth;
                }
                else if (this.state == AnalyzerState.ANALYSE) {
                    ++this.depth;
                }
                super.push((Value)a1);
            }
            
            @Override
            public BasicValue pop() throws IndexOutOfBoundsException {
                if (this.state == AnalyzerState.ANALYSE && --this.depth == 0) {
                    this.state = AnalyzerState.COMPLETE;
                    throw new AnalysisResultException(this.current);
                }
                return (BasicValue)super.pop();
            }
            
            public /* bridge */ void push(final Value value) throws IndexOutOfBoundsException {
                this.push((BasicValue)value);
            }
            
            public /* bridge */ Value pop() throws IndexOutOfBoundsException {
                return this.pop();
            }
        }
    }
}
