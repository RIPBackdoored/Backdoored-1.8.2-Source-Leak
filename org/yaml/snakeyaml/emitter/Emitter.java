package org.yaml.snakeyaml.emitter;

import org.yaml.snakeyaml.util.*;
import java.util.regex.*;
import org.yaml.snakeyaml.*;
import java.util.concurrent.*;
import java.io.*;
import org.yaml.snakeyaml.error.*;
import org.yaml.snakeyaml.scanner.*;
import org.yaml.snakeyaml.reader.*;
import org.yaml.snakeyaml.events.*;
import java.util.*;

public final class Emitter implements Emitable
{
    private static final Map<Character, String> ESCAPE_REPLACEMENTS;
    private static final char[] SPACE;
    private static final Map<String, String> DEFAULT_TAG_PREFIXES;
    private final Writer stream;
    private final ArrayStack<EmitterState> states;
    private EmitterState state;
    private final Queue<Event> events;
    private Event event;
    private final ArrayStack<Integer> indents;
    private Integer indent;
    private int flowLevel;
    private boolean rootContext;
    private boolean mappingContext;
    private boolean simpleKeyContext;
    private int column;
    private boolean whitespace;
    private boolean indention;
    private boolean openEnded;
    private Boolean canonical;
    private Boolean prettyFlow;
    private boolean allowUnicode;
    private int bestIndent;
    private int indicatorIndent;
    private int bestWidth;
    private char[] bestLineBreak;
    private boolean splitLines;
    private Map<String, String> tagPrefixes;
    private String preparedAnchor;
    private String preparedTag;
    private ScalarAnalysis analysis;
    private Character style;
    private static final Pattern HANDLE_FORMAT;
    private static final Pattern ANCHOR_FORMAT;
    
    public Emitter(final Writer stream, final DumperOptions opts) {
        super();
        this.stream = stream;
        this.states = new ArrayStack<EmitterState>(100);
        this.state = new ExpectStreamStart(this);
        this.events = new ArrayBlockingQueue<Event>(100);
        this.event = null;
        this.indents = new ArrayStack<Integer>(10);
        this.indent = null;
        this.flowLevel = 0;
        this.mappingContext = false;
        this.simpleKeyContext = false;
        this.column = 0;
        this.whitespace = true;
        this.indention = true;
        this.openEnded = false;
        this.canonical = opts.isCanonical();
        this.prettyFlow = opts.isPrettyFlow();
        this.allowUnicode = opts.isAllowUnicode();
        this.bestIndent = 2;
        if (opts.getIndent() > 1 && opts.getIndent() < 10) {
            this.bestIndent = opts.getIndent();
        }
        this.indicatorIndent = opts.getIndicatorIndent();
        this.bestWidth = 80;
        if (opts.getWidth() > this.bestIndent * 2) {
            this.bestWidth = opts.getWidth();
        }
        this.bestLineBreak = opts.getLineBreak().getString().toCharArray();
        this.splitLines = opts.getSplitLines();
        this.tagPrefixes = new LinkedHashMap<String, String>();
        this.preparedAnchor = null;
        this.preparedTag = null;
        this.analysis = null;
        this.style = null;
    }
    
    @Override
    public void emit(final Event event) throws IOException {
        this.events.add(event);
        while (!this.needMoreEvents()) {
            this.event = this.events.poll();
            this.state.expect();
            this.event = null;
        }
    }
    
    private boolean needMoreEvents() {
        if (this.events.isEmpty()) {
            return true;
        }
        final Event event = this.events.peek();
        if (event instanceof DocumentStartEvent) {
            return this.needEvents(1);
        }
        if (event instanceof SequenceStartEvent) {
            return this.needEvents(2);
        }
        return event instanceof MappingStartEvent && this.needEvents(3);
    }
    
    private boolean needEvents(final int count) {
        int level = 0;
        final Iterator<Event> iter = this.events.iterator();
        iter.next();
        while (iter.hasNext()) {
            final Event event = iter.next();
            if (event instanceof DocumentStartEvent || event instanceof CollectionStartEvent) {
                ++level;
            }
            else if (event instanceof DocumentEndEvent || event instanceof CollectionEndEvent) {
                --level;
            }
            else if (event instanceof StreamEndEvent) {
                level = -1;
            }
            if (level < 0) {
                return false;
            }
        }
        return this.events.size() < count + 1;
    }
    
    private void increaseIndent(final boolean flow, final boolean indentless) {
        this.indents.push(this.indent);
        if (this.indent == null) {
            if (flow) {
                this.indent = this.bestIndent;
            }
            else {
                this.indent = 0;
            }
        }
        else if (!indentless) {
            this.indent += this.bestIndent;
        }
    }
    
    private void expectNode(final boolean root, final boolean mapping, final boolean simpleKey) throws IOException {
        this.rootContext = root;
        this.mappingContext = mapping;
        this.simpleKeyContext = simpleKey;
        if (this.event instanceof AliasEvent) {
            this.expectAlias();
        }
        else {
            if (!(this.event instanceof ScalarEvent) && !(this.event instanceof CollectionStartEvent)) {
                throw new EmitterException("expected NodeEvent, but got " + this.event);
            }
            this.processAnchor("&");
            this.processTag();
            if (this.event instanceof ScalarEvent) {
                this.expectScalar();
            }
            else if (this.event instanceof SequenceStartEvent) {
                if (this.flowLevel != 0 || this.canonical || ((SequenceStartEvent)this.event).getFlowStyle() || this.checkEmptySequence()) {
                    this.expectFlowSequence();
                }
                else {
                    this.expectBlockSequence();
                }
            }
            else if (this.flowLevel != 0 || this.canonical || ((MappingStartEvent)this.event).getFlowStyle() || this.checkEmptyMapping()) {
                this.expectFlowMapping();
            }
            else {
                this.expectBlockMapping();
            }
        }
    }
    
    private void expectAlias() throws IOException {
        if (((NodeEvent)this.event).getAnchor() == null) {
            throw new EmitterException("anchor is not specified for alias");
        }
        this.processAnchor("*");
        this.state = this.states.pop();
    }
    
    private void expectScalar() throws IOException {
        this.increaseIndent(true, false);
        this.processScalar();
        this.indent = this.indents.pop();
        this.state = this.states.pop();
    }
    
    private void expectFlowSequence() throws IOException {
        this.writeIndicator("[", true, true, false);
        ++this.flowLevel;
        this.increaseIndent(true, false);
        if (this.prettyFlow) {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowSequenceItem(this);
    }
    
    private void expectFlowMapping() throws IOException {
        this.writeIndicator("{", true, true, false);
        ++this.flowLevel;
        this.increaseIndent(true, false);
        if (this.prettyFlow) {
            this.writeIndent();
        }
        this.state = new ExpectFirstFlowMappingKey(this);
    }
    
    private void expectBlockSequence() throws IOException {
        final boolean indentless = this.mappingContext && !this.indention;
        this.increaseIndent(false, indentless);
        this.state = new ExpectFirstBlockSequenceItem(this);
    }
    
    private void expectBlockMapping() throws IOException {
        this.increaseIndent(false, false);
        this.state = new ExpectFirstBlockMappingKey(this);
    }
    
    private boolean checkEmptySequence() {
        return this.event instanceof SequenceStartEvent && !this.events.isEmpty() && this.events.peek() instanceof SequenceEndEvent;
    }
    
    private boolean checkEmptyMapping() {
        return this.event instanceof MappingStartEvent && !this.events.isEmpty() && this.events.peek() instanceof MappingEndEvent;
    }
    
    private boolean checkEmptyDocument() {
        if (!(this.event instanceof DocumentStartEvent) || this.events.isEmpty()) {
            return false;
        }
        final Event event = this.events.peek();
        if (event instanceof ScalarEvent) {
            final ScalarEvent e = (ScalarEvent)event;
            return e.getAnchor() == null && e.getTag() == null && e.getImplicit() != null && e.getValue().length() == 0;
        }
        return false;
    }
    
    private boolean checkSimpleKey() {
        int length = 0;
        if (this.event instanceof NodeEvent && ((NodeEvent)this.event).getAnchor() != null) {
            if (this.preparedAnchor == null) {
                this.preparedAnchor = prepareAnchor(((NodeEvent)this.event).getAnchor());
            }
            length += this.preparedAnchor.length();
        }
        String tag = null;
        if (this.event instanceof ScalarEvent) {
            tag = ((ScalarEvent)this.event).getTag();
        }
        else if (this.event instanceof CollectionStartEvent) {
            tag = ((CollectionStartEvent)this.event).getTag();
        }
        if (tag != null) {
            if (this.preparedTag == null) {
                this.preparedTag = this.prepareTag(tag);
            }
            length += this.preparedTag.length();
        }
        if (this.event instanceof ScalarEvent) {
            if (this.analysis == null) {
                this.analysis = this.analyzeScalar(((ScalarEvent)this.event).getValue());
            }
            length += this.analysis.scalar.length();
        }
        return length < 128 && (this.event instanceof AliasEvent || (this.event instanceof ScalarEvent && !this.analysis.empty && !this.analysis.multiline) || this.checkEmptySequence() || this.checkEmptyMapping());
    }
    
    private void processAnchor(final String indicator) throws IOException {
        final NodeEvent ev = (NodeEvent)this.event;
        if (ev.getAnchor() == null) {
            this.preparedAnchor = null;
            return;
        }
        if (this.preparedAnchor == null) {
            this.preparedAnchor = prepareAnchor(ev.getAnchor());
        }
        this.writeIndicator(indicator + this.preparedAnchor, true, false, false);
        this.preparedAnchor = null;
    }
    
    private void processTag() throws IOException {
        String tag = null;
        if (this.event instanceof ScalarEvent) {
            final ScalarEvent ev = (ScalarEvent)this.event;
            tag = ev.getTag();
            if (this.style == null) {
                this.style = this.chooseScalarStyle();
            }
            if ((!this.canonical || tag == null) && ((this.style == null && ev.getImplicit().canOmitTagInPlainScalar()) || (this.style != null && ev.getImplicit().canOmitTagInNonPlainScalar()))) {
                this.preparedTag = null;
                return;
            }
            if (ev.getImplicit().canOmitTagInPlainScalar() && tag == null) {
                tag = "!";
                this.preparedTag = null;
            }
        }
        else {
            final CollectionStartEvent ev2 = (CollectionStartEvent)this.event;
            tag = ev2.getTag();
            if ((!this.canonical || tag == null) && ev2.getImplicit()) {
                this.preparedTag = null;
                return;
            }
        }
        if (tag == null) {
            throw new EmitterException("tag is not specified");
        }
        if (this.preparedTag == null) {
            this.preparedTag = this.prepareTag(tag);
        }
        this.writeIndicator(this.preparedTag, true, false, false);
        this.preparedTag = null;
    }
    
    private Character chooseScalarStyle() {
        final ScalarEvent ev = (ScalarEvent)this.event;
        if (this.analysis == null) {
            this.analysis = this.analyzeScalar(ev.getValue());
        }
        if ((ev.getStyle() != null && ev.getStyle() == '\"') || this.canonical) {
            return '\"';
        }
        if (ev.getStyle() == null && ev.getImplicit().canOmitTagInPlainScalar() && (!this.simpleKeyContext || (!this.analysis.empty && !this.analysis.multiline)) && ((this.flowLevel != 0 && this.analysis.allowFlowPlain) || (this.flowLevel == 0 && this.analysis.allowBlockPlain))) {
            return null;
        }
        if (ev.getStyle() != null && (ev.getStyle() == '|' || ev.getStyle() == '>') && this.flowLevel == 0 && !this.simpleKeyContext && this.analysis.allowBlock) {
            return ev.getStyle();
        }
        if ((ev.getStyle() == null || ev.getStyle() == '\'') && this.analysis.allowSingleQuoted && (!this.simpleKeyContext || !this.analysis.multiline)) {
            return '\'';
        }
        return '\"';
    }
    
    private void processScalar() throws IOException {
        final ScalarEvent ev = (ScalarEvent)this.event;
        if (this.analysis == null) {
            this.analysis = this.analyzeScalar(ev.getValue());
        }
        if (this.style == null) {
            this.style = this.chooseScalarStyle();
        }
        final boolean split = !this.simpleKeyContext && this.splitLines;
        if (this.style == null) {
            this.writePlain(this.analysis.scalar, split);
        }
        else {
            switch ((char)this.style) {
                case '\"': {
                    this.writeDoubleQuoted(this.analysis.scalar, split);
                    break;
                }
                case '\'': {
                    this.writeSingleQuoted(this.analysis.scalar, split);
                    break;
                }
                case '>': {
                    this.writeFolded(this.analysis.scalar, split);
                    break;
                }
                case '|': {
                    this.writeLiteral(this.analysis.scalar);
                    break;
                }
                default: {
                    throw new YAMLException("Unexpected style: " + this.style);
                }
            }
        }
        this.analysis = null;
        this.style = null;
    }
    
    private String prepareVersion(final DumperOptions.Version version) {
        if (version.major() != 1) {
            throw new EmitterException("unsupported YAML version: " + version);
        }
        return version.getRepresentation();
    }
    
    private String prepareTagHandle(final String handle) {
        if (handle.length() == 0) {
            throw new EmitterException("tag handle must not be empty");
        }
        if (handle.charAt(0) != '!' || handle.charAt(handle.length() - 1) != '!') {
            throw new EmitterException("tag handle must start and end with '!': " + handle);
        }
        if (!"!".equals(handle) && !Emitter.HANDLE_FORMAT.matcher(handle).matches()) {
            throw new EmitterException("invalid character in the tag handle: " + handle);
        }
        return handle;
    }
    
    private String prepareTagPrefix(final String prefix) {
        if (prefix.length() == 0) {
            throw new EmitterException("tag prefix must not be empty");
        }
        final StringBuilder chunks = new StringBuilder();
        final int start = 0;
        int end = 0;
        if (prefix.charAt(0) == '!') {
            end = 1;
        }
        while (end < prefix.length()) {
            ++end;
        }
        if (start < end) {
            chunks.append(prefix.substring(start, end));
        }
        return chunks.toString();
    }
    
    private String prepareTag(final String tag) {
        if (tag.length() == 0) {
            throw new EmitterException("tag must not be empty");
        }
        if ("!".equals(tag)) {
            return tag;
        }
        String handle = null;
        String suffix = tag;
        for (final String prefix : this.tagPrefixes.keySet()) {
            if (tag.startsWith(prefix) && ("!".equals(prefix) || prefix.length() < tag.length())) {
                handle = prefix;
            }
        }
        if (handle != null) {
            suffix = tag.substring(handle.length());
            handle = this.tagPrefixes.get(handle);
        }
        final int end = suffix.length();
        final String suffixText = (end > 0) ? suffix.substring(0, end) : "";
        if (handle != null) {
            return handle + suffixText;
        }
        return "!<" + suffixText + ">";
    }
    
    static String prepareAnchor(final String anchor) {
        if (anchor.length() == 0) {
            throw new EmitterException("anchor must not be empty");
        }
        if (!Emitter.ANCHOR_FORMAT.matcher(anchor).matches()) {
            throw new EmitterException("invalid character in the anchor: " + anchor);
        }
        return anchor;
    }
    
    private ScalarAnalysis analyzeScalar(final String scalar) {
        if (scalar.length() == 0) {
            return new ScalarAnalysis(scalar, true, false, false, true, true, false);
        }
        boolean blockIndicators = false;
        boolean flowIndicators = false;
        boolean lineBreaks = false;
        boolean specialCharacters = false;
        boolean leadingSpace = false;
        boolean leadingBreak = false;
        boolean trailingSpace = false;
        boolean trailingBreak = false;
        boolean breakSpace = false;
        boolean spaceBreak = false;
        if (scalar.startsWith("---") || scalar.startsWith("...")) {
            blockIndicators = true;
            flowIndicators = true;
        }
        boolean preceededByWhitespace = true;
        boolean followedByWhitespace = scalar.length() == 1 || Constant.NULL_BL_T_LINEBR.has(scalar.codePointAt(1));
        boolean previousSpace = false;
        boolean previousBreak = false;
        int index = 0;
        while (index < scalar.length()) {
            final int c = scalar.codePointAt(index);
            if (index == 0) {
                if ("#,[]{}&*!|>'\"%@`".indexOf(c) != -1) {
                    flowIndicators = true;
                    blockIndicators = true;
                }
                if (c == 63 || c == 58) {
                    flowIndicators = true;
                    if (followedByWhitespace) {
                        blockIndicators = true;
                    }
                }
                if (c == 45 && followedByWhitespace) {
                    flowIndicators = true;
                    blockIndicators = true;
                }
            }
            else {
                if (",?[]{}".indexOf(c) != -1) {
                    flowIndicators = true;
                }
                if (c == 58) {
                    flowIndicators = true;
                    if (followedByWhitespace) {
                        blockIndicators = true;
                    }
                }
                if (c == 35 && preceededByWhitespace) {
                    flowIndicators = true;
                    blockIndicators = true;
                }
            }
            final boolean isLineBreak = Constant.LINEBR.has(c);
            if (isLineBreak) {
                lineBreaks = true;
            }
            if (c != 10 && (32 > c || c > 126)) {
                if (c == 133 || (c >= 160 && c <= 55295) || (c >= 57344 && c <= 65533) || (c >= 65536 && c <= 1114111)) {
                    if (!this.allowUnicode) {
                        specialCharacters = true;
                    }
                }
                else {
                    specialCharacters = true;
                }
            }
            if (c == 32) {
                if (index == 0) {
                    leadingSpace = true;
                }
                if (index == scalar.length() - 1) {
                    trailingSpace = true;
                }
                if (previousBreak) {
                    breakSpace = true;
                }
                previousSpace = true;
                previousBreak = false;
            }
            else if (isLineBreak) {
                if (index == 0) {
                    leadingBreak = true;
                }
                if (index == scalar.length() - 1) {
                    trailingBreak = true;
                }
                if (previousSpace) {
                    spaceBreak = true;
                }
                previousSpace = false;
                previousBreak = true;
            }
            else {
                previousSpace = false;
                previousBreak = false;
            }
            index += Character.charCount(c);
            preceededByWhitespace = (Constant.NULL_BL_T.has(c) || isLineBreak);
            followedByWhitespace = true;
            if (index + 1 < scalar.length()) {
                final int nextIndex = index + Character.charCount(scalar.codePointAt(index));
                if (nextIndex >= scalar.length()) {
                    continue;
                }
                followedByWhitespace = (Constant.NULL_BL_T.has(scalar.codePointAt(nextIndex)) || isLineBreak);
            }
        }
        boolean allowFlowPlain = true;
        boolean allowBlockPlain = true;
        boolean allowSingleQuoted = true;
        boolean allowBlock = true;
        if (leadingSpace || leadingBreak || trailingSpace || trailingBreak) {
            allowBlockPlain = (allowFlowPlain = false);
        }
        if (trailingSpace) {
            allowBlock = false;
        }
        if (breakSpace) {
            allowBlockPlain = (allowFlowPlain = (allowSingleQuoted = false));
        }
        if (spaceBreak || specialCharacters) {
            allowBlockPlain = (allowFlowPlain = (allowSingleQuoted = (allowBlock = false)));
        }
        if (lineBreaks) {
            allowFlowPlain = false;
        }
        if (flowIndicators) {
            allowFlowPlain = false;
        }
        if (blockIndicators) {
            allowBlockPlain = false;
        }
        return new ScalarAnalysis(scalar, false, lineBreaks, allowFlowPlain, allowBlockPlain, allowSingleQuoted, allowBlock);
    }
    
    void flushStream() throws IOException {
        this.stream.flush();
    }
    
    void writeStreamStart() {
    }
    
    void writeStreamEnd() throws IOException {
        this.flushStream();
    }
    
    void writeIndicator(final String indicator, final boolean needWhitespace, final boolean whitespace, final boolean indentation) throws IOException {
        if (!this.whitespace && needWhitespace) {
            ++this.column;
            this.stream.write(Emitter.SPACE);
        }
        this.whitespace = whitespace;
        this.indention = (this.indention && indentation);
        this.column += indicator.length();
        this.openEnded = false;
        this.stream.write(indicator);
    }
    
    void writeIndent() throws IOException {
        int indent;
        if (this.indent != null) {
            indent = this.indent;
        }
        else {
            indent = 0;
        }
        if (!this.indention || this.column > indent || (this.column == indent && !this.whitespace)) {
            this.writeLineBreak(null);
        }
        this.writeWhitespace(indent - this.column);
    }
    
    private void writeWhitespace(final int length) throws IOException {
        if (length <= 0) {
            return;
        }
        this.whitespace = true;
        final char[] data = new char[length];
        for (int i = 0; i < data.length; ++i) {
            data[i] = ' ';
        }
        this.column += length;
        this.stream.write(data);
    }
    
    private void writeLineBreak(final String data) throws IOException {
        this.whitespace = true;
        this.indention = true;
        this.column = 0;
        if (data == null) {
            this.stream.write(this.bestLineBreak);
        }
        else {
            this.stream.write(data);
        }
    }
    
    void writeVersionDirective(final String versionText) throws IOException {
        this.stream.write("%YAML ");
        this.stream.write(versionText);
        this.writeLineBreak(null);
    }
    
    void writeTagDirective(final String handleText, final String prefixText) throws IOException {
        this.stream.write("%TAG ");
        this.stream.write(handleText);
        this.stream.write(Emitter.SPACE);
        this.stream.write(prefixText);
        this.writeLineBreak(null);
    }
    
    private void writeSingleQuoted(final String text, final boolean split) throws IOException {
        this.writeIndicator("'", true, false, false);
        boolean spaces = false;
        boolean breaks = false;
        int start = 0;
        for (int end = 0; end <= text.length(); ++end) {
            char ch = '\0';
            if (end < text.length()) {
                ch = text.charAt(end);
            }
            if (spaces) {
                if (ch == '\0' || ch != ' ') {
                    if (start + 1 == end && this.column > this.bestWidth && split && start != 0 && end != text.length()) {
                        this.writeIndent();
                    }
                    else {
                        final int len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                    }
                    start = end;
                }
            }
            else if (breaks) {
                if (ch == '\0' || Constant.LINEBR.hasNo(ch)) {
                    if (text.charAt(start) == '\n') {
                        this.writeLineBreak(null);
                    }
                    final String data = text.substring(start, end);
                    for (final char br : data.toCharArray()) {
                        if (br == '\n') {
                            this.writeLineBreak(null);
                        }
                        else {
                            this.writeLineBreak(String.valueOf(br));
                        }
                    }
                    this.writeIndent();
                    start = end;
                }
            }
            else if (Constant.LINEBR.has(ch, "\u0000 '") && start < end) {
                final int len = end - start;
                this.column += len;
                this.stream.write(text, start, len);
                start = end;
            }
            if (ch == '\'') {
                this.column += 2;
                this.stream.write("''");
                start = end + 1;
            }
            if (ch != '\0') {
                spaces = (ch == ' ');
                breaks = Constant.LINEBR.has(ch);
            }
        }
        this.writeIndicator("'", false, false, false);
    }
    
    private void writeDoubleQuoted(final String text, final boolean split) throws IOException {
        this.writeIndicator("\"", true, false, false);
        int start = 0;
        for (int end = 0; end <= text.length(); ++end) {
            Character ch = null;
            if (end < text.length()) {
                ch = text.charAt(end);
            }
            if (ch == null || "\"\\\u0085\u2028\u2029\ufeff".indexOf(ch) != -1 || ' ' > ch || ch > '~') {
                if (start < end) {
                    final int len = end - start;
                    this.column += len;
                    this.stream.write(text, start, len);
                    start = end;
                }
                if (ch != null) {
                    String data;
                    if (Emitter.ESCAPE_REPLACEMENTS.containsKey(ch)) {
                        data = "\\" + Emitter.ESCAPE_REPLACEMENTS.get(ch);
                    }
                    else if (!this.allowUnicode || !StreamReader.isPrintable(ch)) {
                        if (ch <= '\u00ff') {
                            final String s = "0" + Integer.toString(ch, 16);
                            data = "\\x" + s.substring(s.length() - 2);
                        }
                        else if (ch >= '\ud800' && ch <= '\udbff') {
                            if (end + 1 < text.length()) {
                                final Character ch2 = text.charAt(++end);
                                final String s2 = "000" + Long.toHexString(Character.toCodePoint(ch, ch2));
                                data = "\\U" + s2.substring(s2.length() - 8);
                            }
                            else {
                                final String s = "000" + Integer.toString(ch, 16);
                                data = "\\u" + s.substring(s.length() - 4);
                            }
                        }
                        else {
                            final String s = "000" + Integer.toString(ch, 16);
                            data = "\\u" + s.substring(s.length() - 4);
                        }
                    }
                    else {
                        data = String.valueOf(ch);
                    }
                    this.column += data.length();
                    this.stream.write(data);
                    start = end + 1;
                }
            }
            if (0 < end && end < text.length() - 1 && (ch == ' ' || start >= end) && this.column + (end - start) > this.bestWidth && split) {
                String data;
                if (start >= end) {
                    data = "\\";
                }
                else {
                    data = text.substring(start, end) + "\\";
                }
                if (start < end) {
                    start = end;
                }
                this.column += data.length();
                this.stream.write(data);
                this.writeIndent();
                this.whitespace = false;
                this.indention = false;
                if (text.charAt(start) == ' ') {
                    data = "\\";
                    this.column += data.length();
                    this.stream.write(data);
                }
            }
        }
        this.writeIndicator("\"", false, false, false);
    }
    
    private String determineBlockHints(final String text) {
        final StringBuilder hints = new StringBuilder();
        if (Constant.LINEBR.has(text.charAt(0), " ")) {
            hints.append(this.bestIndent);
        }
        final char ch1 = text.charAt(text.length() - 1);
        if (Constant.LINEBR.hasNo(ch1)) {
            hints.append("-");
        }
        else if (text.length() == 1 || Constant.LINEBR.has(text.charAt(text.length() - 2))) {
            hints.append("+");
        }
        return hints.toString();
    }
    
    void writeFolded(final String text, final boolean split) throws IOException {
        final String hints = this.determineBlockHints(text);
        this.writeIndicator(">" + hints, true, false, false);
        if (hints.length() > 0 && hints.charAt(hints.length() - 1) == '+') {
            this.openEnded = true;
        }
        this.writeLineBreak(null);
        boolean leadingSpace = true;
        boolean spaces = false;
        boolean breaks = true;
        int start = 0;
        for (int end = 0; end <= text.length(); ++end) {
            char ch = '\0';
            if (end < text.length()) {
                ch = text.charAt(end);
            }
            if (breaks) {
                if (ch == '\0' || Constant.LINEBR.hasNo(ch)) {
                    if (!leadingSpace && ch != '\0' && ch != ' ' && text.charAt(start) == '\n') {
                        this.writeLineBreak(null);
                    }
                    leadingSpace = (ch == ' ');
                    final String data = text.substring(start, end);
                    for (final char br : data.toCharArray()) {
                        if (br == '\n') {
                            this.writeLineBreak(null);
                        }
                        else {
                            this.writeLineBreak(String.valueOf(br));
                        }
                    }
                    if (ch != '\0') {
                        this.writeIndent();
                    }
                    start = end;
                }
            }
            else if (spaces) {
                if (ch != ' ') {
                    if (start + 1 == end && this.column > this.bestWidth && split) {
                        this.writeIndent();
                    }
                    else {
                        final int len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                    }
                    start = end;
                }
            }
            else if (Constant.LINEBR.has(ch, "\u0000 ")) {
                final int len = end - start;
                this.column += len;
                this.stream.write(text, start, len);
                if (ch == '\0') {
                    this.writeLineBreak(null);
                }
                start = end;
            }
            if (ch != '\0') {
                breaks = Constant.LINEBR.has(ch);
                spaces = (ch == ' ');
            }
        }
    }
    
    void writeLiteral(final String text) throws IOException {
        final String hints = this.determineBlockHints(text);
        this.writeIndicator("|" + hints, true, false, false);
        if (hints.length() > 0 && hints.charAt(hints.length() - 1) == '+') {
            this.openEnded = true;
        }
        this.writeLineBreak(null);
        boolean breaks = true;
        int start = 0;
        for (int end = 0; end <= text.length(); ++end) {
            char ch = '\0';
            if (end < text.length()) {
                ch = text.charAt(end);
            }
            if (breaks) {
                if (ch == '\0' || Constant.LINEBR.hasNo(ch)) {
                    final String data = text.substring(start, end);
                    for (final char br : data.toCharArray()) {
                        if (br == '\n') {
                            this.writeLineBreak(null);
                        }
                        else {
                            this.writeLineBreak(String.valueOf(br));
                        }
                    }
                    if (ch != '\0') {
                        this.writeIndent();
                    }
                    start = end;
                }
            }
            else if (ch == '\0' || Constant.LINEBR.has(ch)) {
                this.stream.write(text, start, end - start);
                if (ch == '\0') {
                    this.writeLineBreak(null);
                }
                start = end;
            }
            if (ch != '\0') {
                breaks = Constant.LINEBR.has(ch);
            }
        }
    }
    
    void writePlain(final String text, final boolean split) throws IOException {
        if (this.rootContext) {
            this.openEnded = true;
        }
        if (text.length() == 0) {
            return;
        }
        if (!this.whitespace) {
            ++this.column;
            this.stream.write(Emitter.SPACE);
        }
        this.whitespace = false;
        this.indention = false;
        boolean spaces = false;
        boolean breaks = false;
        int start = 0;
        for (int end = 0; end <= text.length(); ++end) {
            char ch = '\0';
            if (end < text.length()) {
                ch = text.charAt(end);
            }
            if (spaces) {
                if (ch != ' ') {
                    if (start + 1 == end && this.column > this.bestWidth && split) {
                        this.writeIndent();
                        this.whitespace = false;
                        this.indention = false;
                    }
                    else {
                        final int len = end - start;
                        this.column += len;
                        this.stream.write(text, start, len);
                    }
                    start = end;
                }
            }
            else if (breaks) {
                if (Constant.LINEBR.hasNo(ch)) {
                    if (text.charAt(start) == '\n') {
                        this.writeLineBreak(null);
                    }
                    final String data = text.substring(start, end);
                    for (final char br : data.toCharArray()) {
                        if (br == '\n') {
                            this.writeLineBreak(null);
                        }
                        else {
                            this.writeLineBreak(String.valueOf(br));
                        }
                    }
                    this.writeIndent();
                    this.whitespace = false;
                    this.indention = false;
                    start = end;
                }
            }
            else if (Constant.LINEBR.has(ch, "\u0000 ")) {
                final int len = end - start;
                this.column += len;
                this.stream.write(text, start, len);
                start = end;
            }
            if (ch != '\0') {
                spaces = (ch == ' ');
                breaks = Constant.LINEBR.has(ch);
            }
        }
    }
    
    static /* synthetic */ Event access$100(final Emitter x0) {
        return x0.event;
    }
    
    static /* synthetic */ EmitterState access$202(final Emitter x0, final EmitterState x1) {
        return x0.state = x1;
    }
    
    static /* synthetic */ boolean access$400(final Emitter x0) {
        return x0.openEnded;
    }
    
    static /* synthetic */ String access$500(final Emitter x0, final DumperOptions.Version x1) {
        return x0.prepareVersion(x1);
    }
    
    static /* synthetic */ Map access$602(final Emitter x0, final Map x1) {
        return x0.tagPrefixes = (Map<String, String>)x1;
    }
    
    static /* synthetic */ Map access$700() {
        return Emitter.DEFAULT_TAG_PREFIXES;
    }
    
    static /* synthetic */ Map access$600(final Emitter x0) {
        return x0.tagPrefixes;
    }
    
    static /* synthetic */ String access$800(final Emitter x0, final String x1) {
        return x0.prepareTagHandle(x1);
    }
    
    static /* synthetic */ String access$900(final Emitter x0, final String x1) {
        return x0.prepareTagPrefix(x1);
    }
    
    static /* synthetic */ Boolean access$1000(final Emitter x0) {
        return x0.canonical;
    }
    
    static /* synthetic */ boolean access$1100(final Emitter x0) {
        return x0.checkEmptyDocument();
    }
    
    static /* synthetic */ ArrayStack access$1500(final Emitter x0) {
        return x0.states;
    }
    
    static /* synthetic */ void access$1600(final Emitter x0, final boolean x1, final boolean x2, final boolean x3) throws IOException {
        x0.expectNode(x1, x2, x3);
    }
    
    static /* synthetic */ Integer access$1802(final Emitter x0, final Integer x1) {
        return x0.indent = x1;
    }
    
    static /* synthetic */ ArrayStack access$1900(final Emitter x0) {
        return x0.indents;
    }
    
    static /* synthetic */ int access$2010(final Emitter x0) {
        return x0.flowLevel--;
    }
    
    static /* synthetic */ int access$2100(final Emitter x0) {
        return x0.column;
    }
    
    static /* synthetic */ int access$2200(final Emitter x0) {
        return x0.bestWidth;
    }
    
    static /* synthetic */ boolean access$2300(final Emitter x0) {
        return x0.splitLines;
    }
    
    static /* synthetic */ Boolean access$2400(final Emitter x0) {
        return x0.prettyFlow;
    }
    
    static /* synthetic */ boolean access$2700(final Emitter x0) {
        return x0.checkSimpleKey();
    }
    
    static /* synthetic */ int access$3200(final Emitter x0) {
        return x0.indicatorIndent;
    }
    
    static /* synthetic */ void access$3300(final Emitter x0, final int x1) throws IOException {
        x0.writeWhitespace(x1);
    }
    
    static {
        ESCAPE_REPLACEMENTS = new HashMap<Character, String>();
        SPACE = new char[] { ' ' };
        Emitter.ESCAPE_REPLACEMENTS.put('\0', "0");
        Emitter.ESCAPE_REPLACEMENTS.put('\u0007', "a");
        Emitter.ESCAPE_REPLACEMENTS.put('\b', "b");
        Emitter.ESCAPE_REPLACEMENTS.put('\t', "t");
        Emitter.ESCAPE_REPLACEMENTS.put('\n', "n");
        Emitter.ESCAPE_REPLACEMENTS.put('\u000b', "v");
        Emitter.ESCAPE_REPLACEMENTS.put('\f', "f");
        Emitter.ESCAPE_REPLACEMENTS.put('\r', "r");
        Emitter.ESCAPE_REPLACEMENTS.put('\u001b', "e");
        Emitter.ESCAPE_REPLACEMENTS.put('\"', "\"");
        Emitter.ESCAPE_REPLACEMENTS.put('\\', "\\");
        Emitter.ESCAPE_REPLACEMENTS.put('\u0085', "N");
        Emitter.ESCAPE_REPLACEMENTS.put(' ', "_");
        Emitter.ESCAPE_REPLACEMENTS.put('\u2028', "L");
        Emitter.ESCAPE_REPLACEMENTS.put('\u2029', "P");
        (DEFAULT_TAG_PREFIXES = new LinkedHashMap<String, String>()).put("!", "!");
        Emitter.DEFAULT_TAG_PREFIXES.put("tag:yaml.org,2002:", "!!");
        HANDLE_FORMAT = Pattern.compile("^![-_\\w]*!$");
        ANCHOR_FORMAT = Pattern.compile("^[-_\\w]*$");
    }
    
    private class ExpectStreamStart implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectStreamStart(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            if (this.this$0.event instanceof StreamStartEvent) {
                this.this$0.writeStreamStart();
                this.this$0.state = this.this$0.new ExpectFirstDocumentStart();
                return;
            }
            throw new EmitterException("expected StreamStartEvent, but got " + this.this$0.event);
        }
        
        ExpectStreamStart(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectNothing implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectNothing(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            throw new EmitterException("expecting nothing, but got " + this.this$0.event);
        }
        
        ExpectNothing(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectFirstDocumentStart implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFirstDocumentStart(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.new ExpectDocumentStart(true).expect();
        }
        
        ExpectFirstDocumentStart(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectDocumentStart implements EmitterState
    {
        private boolean first;
        final /* synthetic */ Emitter this$0;
        
        public ExpectDocumentStart(final Emitter this$0, final boolean first) {
            this.this$0 = this$0;
            super();
            this.first = first;
        }
        
        @Override
        public void expect() throws IOException {
            if (this.this$0.event instanceof DocumentStartEvent) {
                final DocumentStartEvent ev = (DocumentStartEvent)this.this$0.event;
                if ((ev.getVersion() != null || ev.getTags() != null) && this.this$0.openEnded) {
                    this.this$0.writeIndicator("...", true, false, false);
                    this.this$0.writeIndent();
                }
                if (ev.getVersion() != null) {
                    final String versionText = Emitter.this.prepareVersion(ev.getVersion());
                    this.this$0.writeVersionDirective(versionText);
                }
                this.this$0.tagPrefixes = (Map<String, String>)new LinkedHashMap(Emitter.DEFAULT_TAG_PREFIXES);
                if (ev.getTags() != null) {
                    final Set<String> handles = new TreeSet<String>(ev.getTags().keySet());
                    for (final String handle : handles) {
                        final String prefix = ev.getTags().get(handle);
                        this.this$0.tagPrefixes.put(prefix, handle);
                        final String handleText = Emitter.this.prepareTagHandle(handle);
                        final String prefixText = Emitter.this.prepareTagPrefix(prefix);
                        this.this$0.writeTagDirective(handleText, prefixText);
                    }
                }
                final boolean implicit = this.first && !ev.getExplicit() && !this.this$0.canonical && ev.getVersion() == null && (ev.getTags() == null || ev.getTags().isEmpty()) && !Emitter.this.checkEmptyDocument();
                if (!implicit) {
                    this.this$0.writeIndent();
                    this.this$0.writeIndicator("---", true, false, false);
                    if (this.this$0.canonical) {
                        this.this$0.writeIndent();
                    }
                }
                this.this$0.state = this.this$0.new ExpectDocumentRoot();
            }
            else {
                if (!(this.this$0.event instanceof StreamEndEvent)) {
                    throw new EmitterException("expected DocumentStartEvent, but got " + this.this$0.event);
                }
                this.this$0.writeStreamEnd();
                this.this$0.state = this.this$0.new ExpectNothing();
            }
        }
    }
    
    private class ExpectDocumentEnd implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectDocumentEnd(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            if (this.this$0.event instanceof DocumentEndEvent) {
                this.this$0.writeIndent();
                if (((DocumentEndEvent)this.this$0.event).getExplicit()) {
                    this.this$0.writeIndicator("...", true, false, false);
                    this.this$0.writeIndent();
                }
                this.this$0.flushStream();
                this.this$0.state = this.this$0.new ExpectDocumentStart(false);
                return;
            }
            throw new EmitterException("expected DocumentEndEvent, but got " + this.this$0.event);
        }
        
        ExpectDocumentEnd(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectDocumentRoot implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectDocumentRoot(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.states.push(this.this$0.new ExpectDocumentEnd());
            Emitter.this.expectNode(true, false, false);
        }
        
        ExpectDocumentRoot(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectFirstFlowSequenceItem implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFirstFlowSequenceItem(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            if (this.this$0.event instanceof SequenceEndEvent) {
                this.this$0.indent = this.this$0.indents.pop();
                this.this$0.flowLevel--;
                this.this$0.writeIndicator("]", false, false, false);
                this.this$0.state = this.this$0.states.pop();
            }
            else {
                if (this.this$0.canonical || (this.this$0.column > this.this$0.bestWidth && this.this$0.splitLines) || this.this$0.prettyFlow) {
                    this.this$0.writeIndent();
                }
                this.this$0.states.push(this.this$0.new ExpectFlowSequenceItem());
                Emitter.this.expectNode(false, false, false);
            }
        }
        
        ExpectFirstFlowSequenceItem(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectFlowSequenceItem implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFlowSequenceItem(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            if (this.this$0.event instanceof SequenceEndEvent) {
                this.this$0.indent = this.this$0.indents.pop();
                this.this$0.flowLevel--;
                if (this.this$0.canonical) {
                    this.this$0.writeIndicator(",", false, false, false);
                    this.this$0.writeIndent();
                }
                this.this$0.writeIndicator("]", false, false, false);
                if (this.this$0.prettyFlow) {
                    this.this$0.writeIndent();
                }
                this.this$0.state = this.this$0.states.pop();
            }
            else {
                this.this$0.writeIndicator(",", false, false, false);
                if (this.this$0.canonical || (this.this$0.column > this.this$0.bestWidth && this.this$0.splitLines) || this.this$0.prettyFlow) {
                    this.this$0.writeIndent();
                }
                this.this$0.states.push(this.this$0.new ExpectFlowSequenceItem());
                Emitter.this.expectNode(false, false, false);
            }
        }
        
        ExpectFlowSequenceItem(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectFirstFlowMappingKey implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFirstFlowMappingKey(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            if (this.this$0.event instanceof MappingEndEvent) {
                this.this$0.indent = this.this$0.indents.pop();
                this.this$0.flowLevel--;
                this.this$0.writeIndicator("}", false, false, false);
                this.this$0.state = this.this$0.states.pop();
            }
            else {
                if (this.this$0.canonical || (this.this$0.column > this.this$0.bestWidth && this.this$0.splitLines) || this.this$0.prettyFlow) {
                    this.this$0.writeIndent();
                }
                if (!this.this$0.canonical && Emitter.this.checkSimpleKey()) {
                    this.this$0.states.push(this.this$0.new ExpectFlowMappingSimpleValue());
                    Emitter.this.expectNode(false, true, true);
                }
                else {
                    this.this$0.writeIndicator("?", true, false, false);
                    this.this$0.states.push(this.this$0.new ExpectFlowMappingValue());
                    Emitter.this.expectNode(false, true, false);
                }
            }
        }
        
        ExpectFirstFlowMappingKey(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectFlowMappingKey implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFlowMappingKey(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            if (this.this$0.event instanceof MappingEndEvent) {
                this.this$0.indent = this.this$0.indents.pop();
                this.this$0.flowLevel--;
                if (this.this$0.canonical) {
                    this.this$0.writeIndicator(",", false, false, false);
                    this.this$0.writeIndent();
                }
                if (this.this$0.prettyFlow) {
                    this.this$0.writeIndent();
                }
                this.this$0.writeIndicator("}", false, false, false);
                this.this$0.state = this.this$0.states.pop();
            }
            else {
                this.this$0.writeIndicator(",", false, false, false);
                if (this.this$0.canonical || (this.this$0.column > this.this$0.bestWidth && this.this$0.splitLines) || this.this$0.prettyFlow) {
                    this.this$0.writeIndent();
                }
                if (!this.this$0.canonical && Emitter.this.checkSimpleKey()) {
                    this.this$0.states.push(this.this$0.new ExpectFlowMappingSimpleValue());
                    Emitter.this.expectNode(false, true, true);
                }
                else {
                    this.this$0.writeIndicator("?", true, false, false);
                    this.this$0.states.push(this.this$0.new ExpectFlowMappingValue());
                    Emitter.this.expectNode(false, true, false);
                }
            }
        }
        
        ExpectFlowMappingKey(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectFlowMappingSimpleValue implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFlowMappingSimpleValue(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.writeIndicator(":", false, false, false);
            this.this$0.states.push(this.this$0.new ExpectFlowMappingKey());
            Emitter.this.expectNode(false, true, false);
        }
        
        ExpectFlowMappingSimpleValue(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectFlowMappingValue implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFlowMappingValue(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            if (this.this$0.canonical || this.this$0.column > this.this$0.bestWidth || this.this$0.prettyFlow) {
                this.this$0.writeIndent();
            }
            this.this$0.writeIndicator(":", true, false, false);
            this.this$0.states.push(this.this$0.new ExpectFlowMappingKey());
            Emitter.this.expectNode(false, true, false);
        }
        
        ExpectFlowMappingValue(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectFirstBlockSequenceItem implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFirstBlockSequenceItem(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.new ExpectBlockSequenceItem(true).expect();
        }
        
        ExpectFirstBlockSequenceItem(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectBlockSequenceItem implements EmitterState
    {
        private boolean first;
        final /* synthetic */ Emitter this$0;
        
        public ExpectBlockSequenceItem(final Emitter this$0, final boolean first) {
            this.this$0 = this$0;
            super();
            this.first = first;
        }
        
        @Override
        public void expect() throws IOException {
            if (!this.first && this.this$0.event instanceof SequenceEndEvent) {
                this.this$0.indent = this.this$0.indents.pop();
                this.this$0.state = this.this$0.states.pop();
            }
            else {
                this.this$0.writeIndent();
                Emitter.this.writeWhitespace(this.this$0.indicatorIndent);
                this.this$0.writeIndicator("-", true, false, true);
                this.this$0.states.push(this.this$0.new ExpectBlockSequenceItem(false));
                Emitter.this.expectNode(false, false, false);
            }
        }
    }
    
    private class ExpectFirstBlockMappingKey implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectFirstBlockMappingKey(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.new ExpectBlockMappingKey(true).expect();
        }
        
        ExpectFirstBlockMappingKey(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectBlockMappingKey implements EmitterState
    {
        private boolean first;
        final /* synthetic */ Emitter this$0;
        
        public ExpectBlockMappingKey(final Emitter this$0, final boolean first) {
            this.this$0 = this$0;
            super();
            this.first = first;
        }
        
        @Override
        public void expect() throws IOException {
            if (!this.first && this.this$0.event instanceof MappingEndEvent) {
                this.this$0.indent = this.this$0.indents.pop();
                this.this$0.state = this.this$0.states.pop();
            }
            else {
                this.this$0.writeIndent();
                if (Emitter.this.checkSimpleKey()) {
                    this.this$0.states.push(this.this$0.new ExpectBlockMappingSimpleValue());
                    Emitter.this.expectNode(false, true, true);
                }
                else {
                    this.this$0.writeIndicator("?", true, false, true);
                    this.this$0.states.push(this.this$0.new ExpectBlockMappingValue());
                    Emitter.this.expectNode(false, true, false);
                }
            }
        }
    }
    
    private class ExpectBlockMappingSimpleValue implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectBlockMappingSimpleValue(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.writeIndicator(":", false, false, false);
            this.this$0.states.push(this.this$0.new ExpectBlockMappingKey(false));
            Emitter.this.expectNode(false, true, false);
        }
        
        ExpectBlockMappingSimpleValue(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
    
    private class ExpectBlockMappingValue implements EmitterState
    {
        final /* synthetic */ Emitter this$0;
        
        private ExpectBlockMappingValue(final Emitter this$0) {
            this.this$0 = this$0;
            super();
        }
        
        @Override
        public void expect() throws IOException {
            this.this$0.writeIndent();
            this.this$0.writeIndicator(":", true, false, true);
            this.this$0.states.push(this.this$0.new ExpectBlockMappingKey(false));
            Emitter.this.expectNode(false, true, false);
        }
        
        ExpectBlockMappingValue(final Emitter x0, final Emitter$1 x1) {
            this(x0);
        }
    }
}
