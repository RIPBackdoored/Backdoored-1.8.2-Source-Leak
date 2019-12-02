package org.json.simple.parser;

import java.io.*;
import java.util.*;
import org.json.simple.*;

public class JSONParser
{
    public static final int S_INIT = 0;
    public static final int S_IN_FINISHED_VALUE = 1;
    public static final int S_IN_OBJECT = 2;
    public static final int S_IN_ARRAY = 3;
    public static final int S_PASSED_PAIR_KEY = 4;
    public static final int S_IN_PAIR_VALUE = 5;
    public static final int S_END = 6;
    public static final int S_IN_ERROR = -1;
    private LinkedList handlerStatusStack;
    private Yylex lexer;
    private Yytoken token;
    private int status;
    
    public JSONParser() {
        super();
        this.lexer = new Yylex((Reader)null);
        this.token = null;
        this.status = 0;
    }
    
    private int peekStatus(final LinkedList a1) {
        if (a1.size() == 0) {
            return -1;
        }
        final Integer v1 = a1.getFirst();
        return v1;
    }
    
    public void reset() {
        this.token = null;
        this.status = 0;
        this.handlerStatusStack = null;
    }
    
    public void reset(final Reader a1) {
        this.lexer.yyreset(a1);
        this.reset();
    }
    
    public int getPosition() {
        return this.lexer.getPosition();
    }
    
    public Object parse(final String a1) throws ParseException {
        return this.parse(a1, (ContainerFactory)null);
    }
    
    public Object parse(final String v1, final ContainerFactory v2) throws ParseException {
        final StringReader v3 = new StringReader(v1);
        try {
            return this.parse(v3, v2);
        }
        catch (IOException a1) {
            throw new ParseException(-1, 2, a1);
        }
    }
    
    public Object parse(final Reader a1) throws IOException, ParseException {
        return this.parse(a1, (ContainerFactory)null);
    }
    
    public Object parse(final Reader v-4, final ContainerFactory v-3) throws IOException, ParseException {
        this.reset(v-4);
        final LinkedList a3 = new LinkedList();
        final LinkedList list = new LinkedList();
        try {
            do {
                this.nextToken();
                Label_0922: {
                    switch (this.status) {
                        case 0: {
                            switch (this.token.type) {
                                case 0: {
                                    this.status = 1;
                                    a3.addFirst(new Integer(this.status));
                                    list.addFirst(this.token.value);
                                    break Label_0922;
                                }
                                case 1: {
                                    this.status = 2;
                                    a3.addFirst(new Integer(this.status));
                                    list.addFirst(this.createObjectContainer(v-3));
                                    break Label_0922;
                                }
                                case 3: {
                                    this.status = 3;
                                    a3.addFirst(new Integer(this.status));
                                    list.addFirst(this.createArrayContainer(v-3));
                                    break Label_0922;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0922;
                                }
                            }
                            break;
                        }
                        case 1: {
                            if (this.token.type == -1) {
                                return list.removeFirst();
                            }
                            throw new ParseException(this.getPosition(), 1, this.token);
                        }
                        case 2: {
                            switch (this.token.type) {
                                case 5: {
                                    break Label_0922;
                                }
                                case 0: {
                                    if (this.token.value instanceof String) {
                                        final String a1 = (String)this.token.value;
                                        list.addFirst(a1);
                                        this.status = 4;
                                        a3.addFirst(new Integer(this.status));
                                        break Label_0922;
                                    }
                                    this.status = -1;
                                    break Label_0922;
                                }
                                case 2: {
                                    if (list.size() > 1) {
                                        a3.removeFirst();
                                        list.removeFirst();
                                        this.status = this.peekStatus(a3);
                                        break Label_0922;
                                    }
                                    this.status = 1;
                                    break Label_0922;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0922;
                                }
                            }
                            break;
                        }
                        case 4: {
                            switch (this.token.type) {
                                case 6: {
                                    break;
                                }
                                case 0: {
                                    a3.removeFirst();
                                    final String a2 = list.removeFirst();
                                    final Map v1 = list.getFirst();
                                    v1.put(a2, this.token.value);
                                    this.status = this.peekStatus(a3);
                                    break;
                                }
                                case 3: {
                                    a3.removeFirst();
                                    final String a2 = list.removeFirst();
                                    final Map v1 = list.getFirst();
                                    final List v2 = this.createArrayContainer(v-3);
                                    v1.put(a2, v2);
                                    this.status = 3;
                                    a3.addFirst(new Integer(this.status));
                                    list.addFirst(v2);
                                    break;
                                }
                                case 1: {
                                    a3.removeFirst();
                                    final String a2 = list.removeFirst();
                                    final Map v1 = list.getFirst();
                                    final Map v3 = this.createObjectContainer(v-3);
                                    v1.put(a2, v3);
                                    this.status = 2;
                                    a3.addFirst(new Integer(this.status));
                                    list.addFirst(v3);
                                    break;
                                }
                                default: {
                                    this.status = -1;
                                    break;
                                }
                            }
                            break;
                        }
                        case 3: {
                            switch (this.token.type) {
                                case 5: {
                                    break;
                                }
                                case 0: {
                                    final List v4 = list.getFirst();
                                    v4.add(this.token.value);
                                    break;
                                }
                                case 4: {
                                    if (list.size() > 1) {
                                        a3.removeFirst();
                                        list.removeFirst();
                                        this.status = this.peekStatus(a3);
                                        break;
                                    }
                                    this.status = 1;
                                    break;
                                }
                                case 1: {
                                    final List v4 = list.getFirst();
                                    final Map v1 = this.createObjectContainer(v-3);
                                    v4.add(v1);
                                    this.status = 2;
                                    a3.addFirst(new Integer(this.status));
                                    list.addFirst(v1);
                                    break;
                                }
                                case 3: {
                                    final List v4 = list.getFirst();
                                    final List v2 = this.createArrayContainer(v-3);
                                    v4.add(v2);
                                    this.status = 3;
                                    a3.addFirst(new Integer(this.status));
                                    list.addFirst(v2);
                                    break;
                                }
                                default: {
                                    this.status = -1;
                                    break;
                                }
                            }
                            break;
                        }
                        case -1: {
                            throw new ParseException(this.getPosition(), 1, this.token);
                        }
                    }
                }
                if (this.status == -1) {
                    throw new ParseException(this.getPosition(), 1, this.token);
                }
            } while (this.token.type != -1);
        }
        catch (IOException v5) {
            throw v5;
        }
        throw new ParseException(this.getPosition(), 1, this.token);
    }
    
    private void nextToken() throws ParseException, IOException {
        this.token = this.lexer.yylex();
        if (this.token == null) {
            this.token = new Yytoken(-1, null);
        }
    }
    
    private Map createObjectContainer(final ContainerFactory a1) {
        if (a1 == null) {
            return new JSONObject();
        }
        final Map v1 = a1.createObjectContainer();
        if (v1 == null) {
            return new JSONObject();
        }
        return v1;
    }
    
    private List createArrayContainer(final ContainerFactory a1) {
        if (a1 == null) {
            return new JSONArray();
        }
        final List v1 = a1.creatArrayContainer();
        if (v1 == null) {
            return new JSONArray();
        }
        return v1;
    }
    
    public void parse(final String a1, final ContentHandler a2) throws ParseException {
        this.parse(a1, a2, false);
    }
    
    public void parse(final String a3, final ContentHandler v1, final boolean v2) throws ParseException {
        final StringReader v3 = new StringReader(a3);
        try {
            this.parse(v3, v1, v2);
        }
        catch (IOException a4) {
            throw new ParseException(-1, 2, a4);
        }
    }
    
    public void parse(final Reader a1, final ContentHandler a2) throws IOException, ParseException {
        this.parse(a1, a2, false);
    }
    
    public void parse(final Reader v-3, final ContentHandler v-2, boolean v-1) throws IOException, ParseException {
        if (!v-1) {
            this.reset(v-3);
            this.handlerStatusStack = new LinkedList();
        }
        else if (this.handlerStatusStack == null) {
            v-1 = false;
            this.reset(v-3);
            this.handlerStatusStack = new LinkedList();
        }
        final LinkedList v0 = this.handlerStatusStack;
        try {
            do {
                Label_0911: {
                    switch (this.status) {
                        case 0: {
                            v-2.startJSON();
                            this.nextToken();
                            switch (this.token.type) {
                                case 0: {
                                    this.status = 1;
                                    v0.addFirst(new Integer(this.status));
                                    if (!v-2.primitive(this.token.value)) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 1: {
                                    this.status = 2;
                                    v0.addFirst(new Integer(this.status));
                                    if (!v-2.startObject()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 3: {
                                    this.status = 3;
                                    v0.addFirst(new Integer(this.status));
                                    if (!v-2.startArray()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0911;
                                }
                            }
                            break;
                        }
                        case 1: {
                            this.nextToken();
                            if (this.token.type == -1) {
                                v-2.endJSON();
                                this.status = 6;
                                return;
                            }
                            this.status = -1;
                            throw new ParseException(this.getPosition(), 1, this.token);
                        }
                        case 2: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 5: {
                                    break Label_0911;
                                }
                                case 0: {
                                    if (!(this.token.value instanceof String)) {
                                        this.status = -1;
                                        break Label_0911;
                                    }
                                    final String a1 = (String)this.token.value;
                                    this.status = 4;
                                    v0.addFirst(new Integer(this.status));
                                    if (!v-2.startObjectEntry(a1)) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 2: {
                                    if (v0.size() > 1) {
                                        v0.removeFirst();
                                        this.status = this.peekStatus(v0);
                                    }
                                    else {
                                        this.status = 1;
                                    }
                                    if (!v-2.endObject()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0911;
                                }
                            }
                            break;
                        }
                        case 4: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 6: {
                                    break Label_0911;
                                }
                                case 0: {
                                    v0.removeFirst();
                                    this.status = this.peekStatus(v0);
                                    if (!v-2.primitive(this.token.value)) {
                                        return;
                                    }
                                    if (!v-2.endObjectEntry()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 3: {
                                    v0.removeFirst();
                                    v0.addFirst(new Integer(5));
                                    this.status = 3;
                                    v0.addFirst(new Integer(this.status));
                                    if (!v-2.startArray()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 1: {
                                    v0.removeFirst();
                                    v0.addFirst(new Integer(5));
                                    this.status = 2;
                                    v0.addFirst(new Integer(this.status));
                                    if (!v-2.startObject()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0911;
                                }
                            }
                            break;
                        }
                        case 5: {
                            v0.removeFirst();
                            this.status = this.peekStatus(v0);
                            if (!v-2.endObjectEntry()) {
                                return;
                            }
                            break;
                        }
                        case 3: {
                            this.nextToken();
                            switch (this.token.type) {
                                case 5: {
                                    break Label_0911;
                                }
                                case 0: {
                                    if (!v-2.primitive(this.token.value)) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 4: {
                                    if (v0.size() > 1) {
                                        v0.removeFirst();
                                        this.status = this.peekStatus(v0);
                                    }
                                    else {
                                        this.status = 1;
                                    }
                                    if (!v-2.endArray()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 1: {
                                    this.status = 2;
                                    v0.addFirst(new Integer(this.status));
                                    if (!v-2.startObject()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                case 3: {
                                    this.status = 3;
                                    v0.addFirst(new Integer(this.status));
                                    if (!v-2.startArray()) {
                                        return;
                                    }
                                    break Label_0911;
                                }
                                default: {
                                    this.status = -1;
                                    break Label_0911;
                                }
                            }
                            break;
                        }
                        case 6: {
                            return;
                        }
                        case -1: {
                            throw new ParseException(this.getPosition(), 1, this.token);
                        }
                    }
                }
                if (this.status == -1) {
                    throw new ParseException(this.getPosition(), 1, this.token);
                }
            } while (this.token.type != -1);
        }
        catch (IOException a2) {
            this.status = -1;
            throw a2;
        }
        catch (ParseException a3) {
            this.status = -1;
            throw a3;
        }
        catch (RuntimeException v2) {
            this.status = -1;
            throw v2;
        }
        catch (Error v3) {
            this.status = -1;
            throw v3;
        }
        this.status = -1;
        throw new ParseException(this.getPosition(), 1, this.token);
    }
}
