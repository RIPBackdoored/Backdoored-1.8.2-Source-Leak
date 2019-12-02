package com.backdoored.hacks.chatbot.ChatBotScriptHandler;

import java.util.*;
import org.apache.logging.log4j.*;
import javax.script.*;

public class ScriptHandler
{
    private final ScriptEngine in;
    
    public ScriptHandler() {
        super();
        Objects.requireNonNull(this.in = new ScriptEngineManager(null).getEngineByName("nashorn"));
    }
    
    public ScriptHandler a(final String s) throws ScriptException {
        this.in.eval(s);
        return this;
    }
    
    public ScriptHandler a(final Logger logger) {
        this.in.put("logger", logger);
        return this;
    }
    
    public Object a(final String s, final Object... array) throws ScriptException, NoSuchMethodException {
        return ((Invocable)this.in).invokeFunction(s, array);
    }
}
