package com.sun.jna;

import java.io.*;
import java.util.*;
import java.nio.*;

class ELFAnalyser
{
    private static final byte[] ELF_MAGIC;
    private static final int EF_ARM_ABI_FLOAT_HARD = 1024;
    private static final int EF_ARM_ABI_FLOAT_SOFT = 512;
    private static final int EI_DATA_BIG_ENDIAN = 2;
    private static final int E_MACHINE_ARM = 40;
    private static final int EI_CLASS_64BIT = 2;
    private final String filename;
    private boolean ELF;
    private boolean _64Bit;
    private boolean bigEndian;
    private boolean armHardFloat;
    private boolean armSoftFloat;
    private boolean arm;
    
    public static ELFAnalyser analyse(final String a1) throws IOException {
        final ELFAnalyser v1 = new ELFAnalyser(a1);
        v1.runDetection();
        return v1;
    }
    
    public boolean isELF() {
        return this.ELF;
    }
    
    public boolean is64Bit() {
        return this._64Bit;
    }
    
    public boolean isBigEndian() {
        return this.bigEndian;
    }
    
    public String getFilename() {
        return this.filename;
    }
    
    public boolean isArmHardFloat() {
        return this.armHardFloat;
    }
    
    public boolean isArmSoftFloat() {
        return this.armSoftFloat;
    }
    
    public boolean isArm() {
        return this.arm;
    }
    
    private ELFAnalyser(final String a1) {
        super();
        this.ELF = false;
        this._64Bit = false;
        this.bigEndian = false;
        this.armHardFloat = false;
        this.armSoftFloat = false;
        this.arm = false;
        this.filename = a1;
    }
    
    private void runDetection() throws IOException {
        final RandomAccessFile v0 = new RandomAccessFile(this.filename, "r");
        if (v0.length() > 4L) {
            final byte[] v2 = new byte[4];
            v0.seek(0L);
            v0.read(v2);
            if (Arrays.equals(v2, ELFAnalyser.ELF_MAGIC)) {
                this.ELF = true;
            }
        }
        if (!this.ELF) {
            return;
        }
        v0.seek(4L);
        final byte v3 = v0.readByte();
        this._64Bit = (v3 == 2);
        v0.seek(0L);
        final ByteBuffer v4 = ByteBuffer.allocate(this._64Bit ? 64 : 52);
        v0.getChannel().read(v4, 0L);
        this.bigEndian = (v4.get(5) == 2);
        v4.order(this.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        this.arm = (v4.get(18) == 40);
        if (this.arm) {
            final int v5 = v4.getInt(this._64Bit ? 48 : 36);
            this.armSoftFloat = ((v5 & 0x200) == 0x200);
            this.armHardFloat = ((v5 & 0x400) == 0x400);
        }
    }
    
    static {
        ELF_MAGIC = new byte[] { 127, 69, 76, 70 };
    }
}
