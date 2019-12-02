package com.backdoored.event;

import java.util.*;

public class RenderTab extends BackdooredEvent
{
    public List players;
    public int size;
    
    public RenderTab(final List players, final int size) {
        super();
        this.players = players;
        this.size = size;
    }
}
