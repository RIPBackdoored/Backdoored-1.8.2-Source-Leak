package com.fasterxml.jackson.core.json;

import java.util.*;
import com.fasterxml.jackson.core.*;

public class DupDetector
{
    protected final Object _source;
    protected String _firstName;
    protected String _secondName;
    protected HashSet<String> _seen;
    
    private DupDetector(final Object a1) {
        super();
        this._source = a1;
    }
    
    public static DupDetector rootDetector(final JsonParser a1) {
        return new DupDetector(a1);
    }
    
    public static DupDetector rootDetector(final JsonGenerator a1) {
        return new DupDetector(a1);
    }
    
    public DupDetector child() {
        return new DupDetector(this._source);
    }
    
    public void reset() {
        this._firstName = null;
        this._secondName = null;
        this._seen = null;
    }
    
    public JsonLocation findLocation() {
        if (this._source instanceof JsonParser) {
            return ((JsonParser)this._source).getCurrentLocation();
        }
        return null;
    }
    
    public Object getSource() {
        return this._source;
    }
    
    public boolean isDup(final String a1) throws JsonParseException {
        if (this._firstName == null) {
            this._firstName = a1;
            return false;
        }
        if (a1.equals(this._firstName)) {
            return true;
        }
        if (this._secondName == null) {
            this._secondName = a1;
            return false;
        }
        if (a1.equals(this._secondName)) {
            return true;
        }
        if (this._seen == null) {
            (this._seen = new HashSet<String>(16)).add(this._firstName);
            this._seen.add(this._secondName);
        }
        return !this._seen.add(a1);
    }
}
