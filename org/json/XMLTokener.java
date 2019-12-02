package org.json;

import java.util.*;
import java.io.*;

public class XMLTokener extends JSONTokener
{
    public static final HashMap<String, Character> entity;
    
    public XMLTokener(final Reader a1) {
        super(a1);
    }
    
    public XMLTokener(final String a1) {
        super(a1);
    }
    
    public String nextCDATA() throws JSONException {
        final StringBuilder v3 = new StringBuilder();
        while (this.more()) {
            final char v4 = this.next();
            v3.append(v4);
            final int v5 = v3.length() - 3;
            if (v5 >= 0 && v3.charAt(v5) == ']' && v3.charAt(v5 + 1) == ']' && v3.charAt(v5 + 2) == '>') {
                v3.setLength(v5);
                return v3.toString();
            }
        }
        throw this.syntaxError("Unclosed CDATA");
    }
    
    public Object nextContent() throws JSONException {
        char v1;
        do {
            v1 = this.next();
        } while (Character.isWhitespace(v1));
        if (v1 == '\0') {
            return null;
        }
        if (v1 == '<') {
            return XML.LT;
        }
        final StringBuilder v2 = new StringBuilder();
        while (v1 != '\0') {
            if (v1 == '<') {
                this.back();
                return v2.toString().trim();
            }
            if (v1 == '&') {
                v2.append(this.nextEntity(v1));
            }
            else {
                v2.append(v1);
            }
            v1 = this.next();
        }
        return v2.toString().trim();
    }
    
    public Object nextEntity(final char v2) throws JSONException {
        final StringBuilder v3 = new StringBuilder();
        char a1;
        while (true) {
            a1 = this.next();
            if (!Character.isLetterOrDigit(a1) && a1 != '#') {
                break;
            }
            v3.append(Character.toLowerCase(a1));
        }
        if (a1 == ';') {
            final String v4 = v3.toString();
            return unescapeEntity(v4);
        }
        throw this.syntaxError("Missing ';' in XML entity: &" + (Object)v3);
    }
    
    static String unescapeEntity(final String v0) {
        if (v0 == null || v0.isEmpty()) {
            return "";
        }
        if (v0.charAt(0) == '#') {
            int v = 0;
            if (v0.charAt(1) == 'x') {
                final int a1 = Integer.parseInt(v0.substring(2), 16);
            }
            else {
                v = Integer.parseInt(v0.substring(1));
            }
            return new String(new int[] { v }, 0, 1);
        }
        final Character v2 = XMLTokener.entity.get(v0);
        if (v2 == null) {
            return '&' + v0 + ';';
        }
        return v2.toString();
    }
    
    public Object nextMeta() throws JSONException {
        char v0;
        do {
            v0 = this.next();
        } while (Character.isWhitespace(v0));
        switch (v0) {
            case '\0': {
                throw this.syntaxError("Misshaped meta tag");
            }
            case '<': {
                return XML.LT;
            }
            case '>': {
                return XML.GT;
            }
            case '/': {
                return XML.SLASH;
            }
            case '=': {
                return XML.EQ;
            }
            case '!': {
                return XML.BANG;
            }
            case '?': {
                return XML.QUEST;
            }
            case '\"':
            case '\'': {
                final char v2 = v0;
                do {
                    v0 = this.next();
                    if (v0 == '\0') {
                        throw this.syntaxError("Unterminated string");
                    }
                } while (v0 != v2);
                return Boolean.TRUE;
            }
            default: {
                while (true) {
                    v0 = this.next();
                    if (Character.isWhitespace(v0)) {
                        return Boolean.TRUE;
                    }
                    switch (v0) {
                        case '\0':
                        case '!':
                        case '\"':
                        case '\'':
                        case '/':
                        case '<':
                        case '=':
                        case '>':
                        case '?': {
                            this.back();
                            return Boolean.TRUE;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                break;
            }
        }
    }
    
    public Object nextToken() throws JSONException {
        char v0;
        do {
            v0 = this.next();
        } while (Character.isWhitespace(v0));
        switch (v0) {
            case '\0': {
                throw this.syntaxError("Misshaped element");
            }
            case '<': {
                throw this.syntaxError("Misplaced '<'");
            }
            case '>': {
                return XML.GT;
            }
            case '/': {
                return XML.SLASH;
            }
            case '=': {
                return XML.EQ;
            }
            case '!': {
                return XML.BANG;
            }
            case '?': {
                return XML.QUEST;
            }
            case '\"':
            case '\'': {
                final char v2 = v0;
                final StringBuilder v3 = new StringBuilder();
                while (true) {
                    v0 = this.next();
                    if (v0 == '\0') {
                        throw this.syntaxError("Unterminated string");
                    }
                    if (v0 == v2) {
                        return v3.toString();
                    }
                    if (v0 == '&') {
                        v3.append(this.nextEntity(v0));
                    }
                    else {
                        v3.append(v0);
                    }
                }
                break;
            }
            default: {
                final StringBuilder v3 = new StringBuilder();
                while (true) {
                    v3.append(v0);
                    v0 = this.next();
                    if (Character.isWhitespace(v0)) {
                        return v3.toString();
                    }
                    switch (v0) {
                        case '\0': {
                            return v3.toString();
                        }
                        case '!':
                        case '/':
                        case '=':
                        case '>':
                        case '?':
                        case '[':
                        case ']': {
                            this.back();
                            return v3.toString();
                        }
                        case '\"':
                        case '\'':
                        case '<': {
                            throw this.syntaxError("Bad character in a name");
                        }
                        default: {
                            continue;
                        }
                    }
                }
                break;
            }
        }
    }
    
    public void skipPast(final String v2) {
        int v3 = 0;
        final int v4 = v2.length();
        final char[] v5 = new char[v4];
        for (int v6 = 0; v6 < v4; ++v6) {
            final char a1 = this.next();
            if (a1 == '\0') {
                return;
            }
            v5[v6] = a1;
        }
        while (true) {
            int v7 = v3;
            boolean v8 = true;
            for (int v6 = 0; v6 < v4; ++v6) {
                if (v5[v7] != v2.charAt(v6)) {
                    v8 = false;
                    break;
                }
                if (++v7 >= v4) {
                    v7 -= v4;
                }
            }
            if (v8) {
                return;
            }
            final char v9 = this.next();
            if (v9 == '\0') {
                return;
            }
            v5[v3] = v9;
            if (++v3 < v4) {
                continue;
            }
            v3 -= v4;
        }
    }
    
    static {
        (entity = new HashMap<String, Character>(8)).put("amp", XML.AMP);
        XMLTokener.entity.put("apos", XML.APOS);
        XMLTokener.entity.put("gt", XML.GT);
        XMLTokener.entity.put("lt", XML.LT);
        XMLTokener.entity.put("quot", XML.QUOT);
    }
}
