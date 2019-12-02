package org.spongepowered.asm.mixin.transformer.ext.extensions;

import org.spongepowered.asm.mixin.transformer.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import com.google.common.io.*;
import java.text.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.transformer.ext.*;
import org.spongepowered.asm.util.*;
import java.util.*;
import java.io.*;
import org.apache.commons.io.*;
import org.apache.logging.log4j.*;

public class ExtensionCheckInterfaces implements IExtension
{
    private static final String AUDIT_DIR = "audit";
    private static final String IMPL_REPORT_FILENAME = "mixin_implementation_report";
    private static final String IMPL_REPORT_CSV_FILENAME = "mixin_implementation_report.csv";
    private static final String IMPL_REPORT_TXT_FILENAME = "mixin_implementation_report.txt";
    private static final Logger logger;
    private final File csv;
    private final File report;
    private final Multimap<ClassInfo, ClassInfo.Method> interfaceMethods;
    private boolean strict;
    
    public ExtensionCheckInterfaces() {
        super();
        this.interfaceMethods = (Multimap<ClassInfo, ClassInfo.Method>)HashMultimap.create();
        final File v0 = new File(Constants.DEBUG_OUTPUT_DIR, "audit");
        v0.mkdirs();
        this.csv = new File(v0, "mixin_implementation_report.csv");
        this.report = new File(v0, "mixin_implementation_report.txt");
        try {
            Files.write((CharSequence)"Class,Method,Signature,Interface\n", this.csv, Charsets.ISO_8859_1);
        }
        catch (IOException ex) {}
        try {
            final String v2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Files.write((CharSequence)("Mixin Implementation Report generated on " + v2 + "\n"), this.report, Charsets.ISO_8859_1);
        }
        catch (IOException ex2) {}
    }
    
    @Override
    public boolean checkActive(final MixinEnvironment a1) {
        this.strict = a1.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS_STRICT);
        return a1.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS);
    }
    
    @Override
    public void preApply(final ITargetClassContext v2) {
        final ClassInfo v3 = v2.getClassInfo();
        for (final ClassInfo.Method a1 : v3.getInterfaceMethods(false)) {
            this.interfaceMethods.put((Object)v3, (Object)a1);
        }
    }
    
    @Override
    public void postApply(final ITargetClassContext v-9) {
        final ClassInfo classInfo = v-9.getClassInfo();
        if (classInfo.isAbstract() && !this.strict) {
            ExtensionCheckInterfaces.logger.info("{} is skipping abstract target {}", new Object[] { this.getClass().getSimpleName(), v-9 });
            return;
        }
        final String replace = classInfo.getName().replace('/', '.');
        int n = 0;
        final PrettyPrinter v3 = new PrettyPrinter();
        v3.add("Class: %s", replace).hr();
        v3.add("%-32s %-47s  %s", "Return Type", "Missing Method", "From Interface").hr();
        final Set<ClassInfo.Method> interfaceMethods = classInfo.getInterfaceMethods(true);
        final Set<ClassInfo.Method> set = new HashSet<ClassInfo.Method>(classInfo.getSuperClass().getInterfaceMethods(true));
        set.addAll(this.interfaceMethods.removeAll((Object)classInfo));
        for (final ClassInfo.Method a2 : interfaceMethods) {
            final ClassInfo.Method a1 = classInfo.findMethodInHierarchy(a2.getName(), a2.getDesc(), ClassInfo.SearchType.ALL_CLASSES, ClassInfo.Traversal.ALL);
            if (a1 != null && !a1.isAbstract()) {
                continue;
            }
            if (set.contains(a2)) {
                continue;
            }
            if (n > 0) {
                v3.add();
            }
            final SignaturePrinter v1 = new SignaturePrinter(a2.getName(), a2.getDesc()).setModifiers("");
            final String v2 = a2.getOwner().getName().replace('/', '.');
            ++n;
            v3.add("%-32s%s", v1.getReturnType(), v1);
            v3.add("%-80s  %s", "", v2);
            this.appendToCSVReport(replace, a2, v2);
        }
        if (n > 0) {
            v3.hr().add("%82s%s: %d", "", "Total unimplemented", n);
            v3.print(System.err);
            this.appendToTextReport(v3);
        }
    }
    
    @Override
    public void export(final MixinEnvironment a1, final String a2, final boolean a3, final byte[] a4) {
    }
    
    private void appendToCSVReport(final String a1, final ClassInfo.Method a2, final String a3) {
        try {
            Files.append((CharSequence)String.format("%s,%s,%s,%s\n", a1, a2.getName(), a2.getDesc(), a3), this.csv, Charsets.ISO_8859_1);
        }
        catch (IOException ex) {}
    }
    
    private void appendToTextReport(final PrettyPrinter v2) {
        FileOutputStream v3 = null;
        try {
            v3 = new FileOutputStream(this.report, true);
            final PrintStream a1 = new PrintStream(v3);
            a1.print("\n");
            v2.print(a1);
        }
        catch (Exception ex) {}
        finally {
            IOUtils.closeQuietly(v3);
        }
    }
    
    static {
        logger = LogManager.getLogger("mixin");
    }
}
