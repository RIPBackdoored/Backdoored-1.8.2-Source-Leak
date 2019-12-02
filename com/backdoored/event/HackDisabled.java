package com.backdoored.event;

import com.backdoored.hacks.*;

public class HackDisabled extends BackdooredEvent
{
    public BaseHack hack;
    
    public HackDisabled(final BaseHack hack) {
        super();
        this.hack = hack;
    }
}
