package org.spongepowered.asm.lib;

class CurrentFrame extends Frame
{
    CurrentFrame() {
        super();
    }
    
    @Override
    void execute(final int a1, final int a2, final ClassWriter a3, final Item a4) {
        super.execute(a1, a2, a3, a4);
        final Frame v1 = new Frame();
        this.merge(a3, v1, 0);
        this.set(v1);
        this.owner.inputStackTop = 0;
    }
}
