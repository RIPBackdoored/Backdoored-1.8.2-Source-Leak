package org.yaml.snakeyaml.representer;

import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.error.*;
import java.util.*;

protected class RepresentPrimitiveArray implements Represent
{
    final /* synthetic */ SafeRepresenter this$0;
    
    protected RepresentPrimitiveArray(final SafeRepresenter this$0) {
        this.this$0 = this$0;
        super();
    }
    
    @Override
    public Node representData(final Object data) {
        final Class<?> type = data.getClass().getComponentType();
        if (Byte.TYPE == type) {
            return this.this$0.representSequence(Tag.SEQ, this.asByteList(data), null);
        }
        if (Short.TYPE == type) {
            return this.this$0.representSequence(Tag.SEQ, this.asShortList(data), null);
        }
        if (Integer.TYPE == type) {
            return this.this$0.representSequence(Tag.SEQ, this.asIntList(data), null);
        }
        if (Long.TYPE == type) {
            return this.this$0.representSequence(Tag.SEQ, this.asLongList(data), null);
        }
        if (Float.TYPE == type) {
            return this.this$0.representSequence(Tag.SEQ, this.asFloatList(data), null);
        }
        if (Double.TYPE == type) {
            return this.this$0.representSequence(Tag.SEQ, this.asDoubleList(data), null);
        }
        if (Character.TYPE == type) {
            return this.this$0.representSequence(Tag.SEQ, this.asCharList(data), null);
        }
        if (Boolean.TYPE == type) {
            return this.this$0.representSequence(Tag.SEQ, this.asBooleanList(data), null);
        }
        throw new YAMLException("Unexpected primitive '" + type.getCanonicalName() + "'");
    }
    
    private List<Byte> asByteList(final Object in) {
        final byte[] array = (byte[])in;
        final List<Byte> list = new ArrayList<Byte>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
    
    private List<Short> asShortList(final Object in) {
        final short[] array = (short[])in;
        final List<Short> list = new ArrayList<Short>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
    
    private List<Integer> asIntList(final Object in) {
        final int[] array = (int[])in;
        final List<Integer> list = new ArrayList<Integer>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
    
    private List<Long> asLongList(final Object in) {
        final long[] array = (long[])in;
        final List<Long> list = new ArrayList<Long>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
    
    private List<Float> asFloatList(final Object in) {
        final float[] array = (float[])in;
        final List<Float> list = new ArrayList<Float>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
    
    private List<Double> asDoubleList(final Object in) {
        final double[] array = (double[])in;
        final List<Double> list = new ArrayList<Double>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
    
    private List<Character> asCharList(final Object in) {
        final char[] array = (char[])in;
        final List<Character> list = new ArrayList<Character>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
    
    private List<Boolean> asBooleanList(final Object in) {
        final boolean[] array = (boolean[])in;
        final List<Boolean> list = new ArrayList<Boolean>(array.length);
        for (int i = 0; i < array.length; ++i) {
            list.add(array[i]);
        }
        return list;
    }
}
