package org.spongepowered.asm.util.perf;

import com.google.common.base.*;
import org.spongepowered.asm.util.*;
import java.text.*;
import java.util.*;

public final class Profiler
{
    public static final int ROOT = 1;
    public static final int FINE = 2;
    private final Map<String, Section> sections;
    private final List<String> phases;
    private final Deque<Section> stack;
    private boolean active;
    
    public Profiler() {
        super();
        this.sections = new TreeMap<String, Section>();
        this.phases = new ArrayList<String>();
        this.stack = new LinkedList<Section>();
        this.phases.add("Initial");
    }
    
    public void setActive(final boolean a1) {
        if ((!this.active && a1) || !a1) {
            this.reset();
        }
        this.active = a1;
    }
    
    public void reset() {
        for (final Section v1 : this.sections.values()) {
            v1.invalidate();
        }
        this.sections.clear();
        this.phases.clear();
        this.phases.add("Initial");
        this.stack.clear();
    }
    
    public Section get(final String a1) {
        Section v1 = this.sections.get(a1);
        if (v1 == null) {
            v1 = (this.active ? new LiveSection(a1, this.phases.size() - 1) : new Section(a1));
            this.sections.put(a1, v1);
        }
        return v1;
    }
    
    private Section getSubSection(final String a1, final String a2, final Section a3) {
        Section v1 = this.sections.get(a1);
        if (v1 == null) {
            v1 = new SubSection(a1, this.phases.size() - 1, a2, a3);
            this.sections.put(a1, v1);
        }
        return v1;
    }
    
    boolean isHead(final Section a1) {
        return this.stack.peek() == a1;
    }
    
    public Section begin(final String... a1) {
        return this.begin(0, a1);
    }
    
    public Section begin(final int a1, final String... a2) {
        return this.begin(a1, Joiner.on('.').join((Object[])a2));
    }
    
    public Section begin(final String a1) {
        return this.begin(0, a1);
    }
    
    public Section begin(final int v1, String v2) {
        boolean v3 = (v1 & 0x1) != 0x0;
        final boolean v4 = (v1 & 0x2) != 0x0;
        String v5 = v2;
        final Section v6 = this.stack.peek();
        if (v6 != null) {
            v5 = v6.getName() + (v3 ? " -> " : ".") + v5;
            if (v6.isRoot() && !v3) {
                final int a1 = v6.getName().lastIndexOf(" -> ");
                v2 = ((a1 > -1) ? v6.getName().substring(a1 + 4) : v6.getName()) + "." + v2;
                v3 = true;
            }
        }
        Section v7 = this.get(v3 ? v2 : v5);
        if (v3 && v6 != null && this.active) {
            v7 = this.getSubSection(v5, v6.getName(), v7);
        }
        v7.setFine(v4).setRoot(v3);
        this.stack.push(v7);
        return v7.start();
    }
    
    void end(final Section v-1) {
        try {
            Section v1;
            final Section a1 = v1 = this.stack.pop();
            while (v1 != v-1) {
                if (v1 == null && this.active) {
                    if (a1 == null) {
                        throw new IllegalStateException("Attempted to pop " + v-1 + " but the stack is empty");
                    }
                    throw new IllegalStateException("Attempted to pop " + v-1 + " which was not in the stack, head was " + a1);
                }
                else {
                    v1 = this.stack.pop();
                }
            }
        }
        catch (NoSuchElementException v2) {
            if (this.active) {
                throw new IllegalStateException("Attempted to pop " + v-1 + " but the stack is empty");
            }
        }
    }
    
    public void mark(final String v-2) {
        long n = 0L;
        for (final Section a1 : this.sections.values()) {
            n += a1.getTime();
        }
        if (n == 0L) {
            final int v1 = this.phases.size();
            this.phases.set(v1 - 1, v-2);
            return;
        }
        this.phases.add(v-2);
        for (final Section v2 : this.sections.values()) {
            v2.mark();
        }
    }
    
    public Collection<Section> getSections() {
        return Collections.unmodifiableCollection((Collection<? extends Section>)this.sections.values());
    }
    
    public PrettyPrinter printer(final boolean v-9, final boolean v-8) {
        final PrettyPrinter prettyPrinter = new PrettyPrinter();
        final int n = this.phases.size() + 4;
        final int[] array = { 0, 1, 2, n - 2, n - 1 };
        final Object[] v-10 = new Object[n * 2];
        int a1 = 0;
        int a2 = 0;
        while (a1 < n) {
            v-10[a2 + 1] = PrettyPrinter.Alignment.RIGHT;
            if (a1 == array[0]) {
                v-10[a2] = (v-8 ? "" : "  ") + "Section";
                v-10[a2 + 1] = PrettyPrinter.Alignment.LEFT;
            }
            else if (a1 == array[1]) {
                v-10[a2] = "    TOTAL";
            }
            else if (a1 == array[3]) {
                v-10[a2] = "    Count";
            }
            else if (a1 == array[4]) {
                v-10[a2] = "Avg. ";
            }
            else if (a1 - array[2] < this.phases.size()) {
                v-10[a2] = this.phases.get(a1 - array[2]);
            }
            else {
                v-10[a2] = "";
            }
            a2 = ++a1 * 2;
        }
        prettyPrinter.table(v-10).th().hr().add();
        for (final Section v3 : this.sections.values()) {
            if (!v3.isFine() || v-9) {
                if (v-8 && v3.getDelegate() != v3) {
                    continue;
                }
                this.printSectionRow(prettyPrinter, n, array, v3, v-8);
                if (!v-8) {
                    continue;
                }
                for (final Section v0 : this.sections.values()) {
                    final Section v2 = v0.getDelegate();
                    if ((!v0.isFine() || v-9) && v2 == v3) {
                        if (v2 == v0) {
                            continue;
                        }
                        this.printSectionRow(prettyPrinter, n, array, v0, v-8);
                    }
                }
            }
        }
        return prettyPrinter.add();
    }
    
    private void printSectionRow(final PrettyPrinter a4, final int a5, final int[] v1, final Section v2, final boolean v3) {
        final boolean v4 = v2.getDelegate() != v2;
        final Object[] v5 = new Object[a5];
        int v6 = 1;
        if (v3) {
            v5[0] = (v4 ? ("  > " + v2.getBaseName()) : v2.getName());
        }
        else {
            v5[0] = (v4 ? "+ " : "  ") + v2.getName();
        }
        final long[] times;
        final long[] v7 = times = v2.getTimes();
        for (final long a6 : times) {
            if (v6 == v1[1]) {
                v5[v6++] = v2.getTotalTime() + " ms";
            }
            if (v6 >= v1[2] && v6 < v5.length) {
                v5[v6++] = a6 + " ms";
            }
        }
        v5[v1[3]] = v2.getTotalCount();
        v5[v1[4]] = new DecimalFormat("   ###0.000 ms").format(v2.getTotalAverageTime());
        for (int a7 = 0; a7 < v5.length; ++a7) {
            if (v5[a7] == null) {
                v5[a7] = "-";
            }
        }
        a4.tr(v5);
    }
    
    public class Section
    {
        static final String SEPARATOR_ROOT = " -> ";
        static final String SEPARATOR_CHILD = ".";
        private final String name;
        private boolean root;
        private boolean fine;
        protected boolean invalidated;
        private String info;
        final /* synthetic */ Profiler this$0;
        
        Section(final Profiler a1, final String a2) {
            this.this$0 = a1;
            super();
            this.name = a2;
            this.info = a2;
        }
        
        Section getDelegate() {
            return this;
        }
        
        Section invalidate() {
            this.invalidated = true;
            return this;
        }
        
        Section setRoot(final boolean a1) {
            this.root = a1;
            return this;
        }
        
        public boolean isRoot() {
            return this.root;
        }
        
        Section setFine(final boolean a1) {
            this.fine = a1;
            return this;
        }
        
        public boolean isFine() {
            return this.fine;
        }
        
        public String getName() {
            return this.name;
        }
        
        public String getBaseName() {
            return this.name;
        }
        
        public void setInfo(final String a1) {
            this.info = a1;
        }
        
        public String getInfo() {
            return this.info;
        }
        
        Section start() {
            return this;
        }
        
        protected Section stop() {
            return this;
        }
        
        public Section end() {
            if (!this.invalidated) {
                this.this$0.end(this);
            }
            return this;
        }
        
        public Section next(final String a1) {
            this.end();
            return this.this$0.begin(a1);
        }
        
        void mark() {
        }
        
        public long getTime() {
            return 0L;
        }
        
        public long getTotalTime() {
            return 0L;
        }
        
        public double getSeconds() {
            return 0.0;
        }
        
        public double getTotalSeconds() {
            return 0.0;
        }
        
        public long[] getTimes() {
            return new long[1];
        }
        
        public int getCount() {
            return 0;
        }
        
        public int getTotalCount() {
            return 0;
        }
        
        public double getAverageTime() {
            return 0.0;
        }
        
        public double getTotalAverageTime() {
            return 0.0;
        }
        
        @Override
        public final String toString() {
            return this.name;
        }
    }
    
    class LiveSection extends Section
    {
        private int cursor;
        private long[] times;
        private long start;
        private long time;
        private long markedTime;
        private int count;
        private int markedCount;
        final /* synthetic */ Profiler this$0;
        
        LiveSection(final Profiler a1, final String a2, final int a3) {
            this.this$0 = a1;
            a1.super(a2);
            this.cursor = 0;
            this.times = new long[0];
            this.start = 0L;
            this.cursor = a3;
        }
        
        @Override
        Section start() {
            this.start = System.currentTimeMillis();
            return this;
        }
        
        @Override
        protected Section stop() {
            if (this.start > 0L) {
                this.time += System.currentTimeMillis() - this.start;
            }
            this.start = 0L;
            ++this.count;
            return this;
        }
        
        @Override
        public Section end() {
            this.stop();
            if (!this.invalidated) {
                this.this$0.end(this);
            }
            return this;
        }
        
        @Override
        void mark() {
            if (this.cursor >= this.times.length) {
                this.times = Arrays.copyOf(this.times, this.cursor + 4);
            }
            this.times[this.cursor] = this.time;
            this.markedTime += this.time;
            this.markedCount += this.count;
            this.time = 0L;
            this.count = 0;
            ++this.cursor;
        }
        
        @Override
        public long getTime() {
            return this.time;
        }
        
        @Override
        public long getTotalTime() {
            return this.time + this.markedTime;
        }
        
        @Override
        public double getSeconds() {
            return this.time * 0.001;
        }
        
        @Override
        public double getTotalSeconds() {
            return (this.time + this.markedTime) * 0.001;
        }
        
        @Override
        public long[] getTimes() {
            final long[] v1 = new long[this.cursor + 1];
            System.arraycopy(this.times, 0, v1, 0, Math.min(this.times.length, this.cursor));
            v1[this.cursor] = this.time;
            return v1;
        }
        
        @Override
        public int getCount() {
            return this.count;
        }
        
        @Override
        public int getTotalCount() {
            return this.count + this.markedCount;
        }
        
        @Override
        public double getAverageTime() {
            return (this.count > 0) ? (this.time / (double)this.count) : 0.0;
        }
        
        @Override
        public double getTotalAverageTime() {
            return (this.count > 0) ? ((this.time + this.markedTime) / (double)(this.count + this.markedCount)) : 0.0;
        }
    }
    
    class SubSection extends LiveSection
    {
        private final String baseName;
        private final Section root;
        final /* synthetic */ Profiler this$0;
        
        SubSection(final Profiler a1, final String a2, final int a3, final String a4, final Section a5) {
            this.this$0 = a1;
            a1.super(a2, a3);
            this.baseName = a4;
            this.root = a5;
        }
        
        @Override
        Section invalidate() {
            this.root.invalidate();
            return super.invalidate();
        }
        
        @Override
        public String getBaseName() {
            return this.baseName;
        }
        
        @Override
        public void setInfo(final String a1) {
            this.root.setInfo(a1);
            super.setInfo(a1);
        }
        
        @Override
        Section getDelegate() {
            return this.root;
        }
        
        @Override
        Section start() {
            this.root.start();
            return super.start();
        }
        
        @Override
        public Section end() {
            this.root.stop();
            return super.end();
        }
        
        @Override
        public Section next(final String a1) {
            super.stop();
            return this.root.next(a1);
        }
    }
}
