package com.backdoored.event;

public class IsKeyboardCreated extends BackdooredEvent
{
    public boolean cu;
    public boolean cv;
    
    public IsKeyboardCreated(final boolean cu, final boolean cv) {
        super();
        this.cu = cu;
        this.cv = cv;
    }
}
