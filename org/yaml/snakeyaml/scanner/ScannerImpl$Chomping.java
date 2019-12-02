package org.yaml.snakeyaml.scanner;

private static class Chomping
{
    private final Boolean value;
    private final int increment;
    
    public Chomping(final Boolean value, final int increment) {
        super();
        this.value = value;
        this.increment = increment;
    }
    
    public boolean chompTailIsNotFalse() {
        return this.value == null || this.value;
    }
    
    public boolean chompTailIsTrue() {
        return this.value != null && this.value;
    }
    
    public int getIncrement() {
        return this.increment;
    }
}
