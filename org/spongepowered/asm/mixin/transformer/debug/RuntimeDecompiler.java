package org.spongepowered.asm.mixin.transformer.debug;

import org.spongepowered.asm.mixin.transformer.ext.*;
import java.util.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import org.apache.commons.io.*;
import java.io.*;
import org.jetbrains.java.decompiler.main.extern.*;
import org.jetbrains.java.decompiler.util.*;
import org.jetbrains.java.decompiler.main.*;
import com.google.common.base.*;
import com.google.common.io.*;
import java.util.jar.*;

public class RuntimeDecompiler extends IFernflowerLogger implements IDecompiler, IResultSaver
{
    private static final Level[] SEVERITY_LEVELS;
    private final Map<String, Object> options;
    private final File outputPath;
    protected final Logger logger;
    
    public RuntimeDecompiler(final File v2) {
        super();
        this.options = (Map<String, Object>)ImmutableMap.builder().put((Object)"din", (Object)"0").put((Object)"rbr", (Object)"0").put((Object)"dgs", (Object)"1").put((Object)"asc", (Object)"1").put((Object)"den", (Object)"1").put((Object)"hdc", (Object)"1").put((Object)"ind", (Object)"    ").build();
        this.logger = LogManager.getLogger("fernflower");
        this.outputPath = v2;
        if (this.outputPath.exists()) {
            try {
                FileUtils.deleteDirectory(this.outputPath);
            }
            catch (IOException a1) {
                this.logger.warn("Error cleaning output directory: {}", new Object[] { a1.getMessage() });
            }
        }
    }
    
    @Override
    public void decompile(final File v0) {
        try {
            final Fernflower a1 = new Fernflower(new IBytecodeProvider() {
                private byte[] byteCode;
                final /* synthetic */ RuntimeDecompiler this$0;
                
                RuntimeDecompiler$1() {
                    this.this$0 = a1;
                    super();
                }
                
                @Override
                public byte[] getBytecode(final String a1, final String a2) throws IOException {
                    if (this.byteCode == null) {
                        this.byteCode = InterpreterUtil.getBytes(new File(a1));
                    }
                    return this.byteCode;
                }
            }, this, this.options, this);
            a1.getStructContext().addSpace(v0, true);
            a1.decompileContext();
        }
        catch (Throwable v) {
            this.logger.warn("Decompilation error while processing {}", new Object[] { v0.getName() });
        }
    }
    
    @Override
    public void saveFolder(final String a1) {
    }
    
    @Override
    public void saveClassFile(final String a3, final String a4, final String a5, final String v1, final int[] v2) {
        final File v3 = new File(this.outputPath, a4 + ".java");
        v3.getParentFile().mkdirs();
        try {
            this.logger.info("Writing {}", new Object[] { v3.getAbsolutePath() });
            Files.write((CharSequence)v1, v3, Charsets.UTF_8);
        }
        catch (IOException a6) {
            this.writeMessage("Cannot write source file " + v3, a6);
        }
    }
    
    @Override
    public void startReadingClass(final String a1) {
        this.logger.info("Decompiling {}", new Object[] { a1 });
    }
    
    @Override
    public void writeMessage(final String a1, final Severity a2) {
        this.logger.log(RuntimeDecompiler.SEVERITY_LEVELS[a2.ordinal()], a1);
    }
    
    @Override
    public void writeMessage(final String a1, final Throwable a2) {
        this.logger.warn("{} {}: {}", new Object[] { a1, a2.getClass().getSimpleName(), a2.getMessage() });
    }
    
    @Override
    public void writeMessage(final String a1, final Severity a2, final Throwable a3) {
        this.logger.log(RuntimeDecompiler.SEVERITY_LEVELS[a2.ordinal()], a1, a3);
    }
    
    @Override
    public void copyFile(final String a1, final String a2, final String a3) {
    }
    
    @Override
    public void createArchive(final String a1, final String a2, final Manifest a3) {
    }
    
    @Override
    public void saveDirEntry(final String a1, final String a2, final String a3) {
    }
    
    @Override
    public void copyEntry(final String a1, final String a2, final String a3, final String a4) {
    }
    
    @Override
    public void saveClassEntry(final String a1, final String a2, final String a3, final String a4, final String a5) {
    }
    
    @Override
    public void closeArchive(final String a1, final String a2) {
    }
    
    static {
        SEVERITY_LEVELS = new Level[] { Level.TRACE, Level.INFO, Level.WARN, Level.ERROR };
    }
}
