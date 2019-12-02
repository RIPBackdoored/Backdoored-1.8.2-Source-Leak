package com.fasterxml.jackson.core;

import java.io.*;

public class Version implements Comparable<Version>, Serializable
{
    private static final long serialVersionUID = 1L;
    private static final Version UNKNOWN_VERSION;
    protected final int _majorVersion;
    protected final int _minorVersion;
    protected final int _patchLevel;
    protected final String _groupId;
    protected final String _artifactId;
    protected final String _snapshotInfo;
    
    @Deprecated
    public Version(final int a1, final int a2, final int a3, final String a4) {
        this(a1, a2, a3, a4, null, null);
    }
    
    public Version(final int a1, final int a2, final int a3, final String a4, final String a5, final String a6) {
        super();
        this._majorVersion = a1;
        this._minorVersion = a2;
        this._patchLevel = a3;
        this._snapshotInfo = a4;
        this._groupId = ((a5 == null) ? "" : a5);
        this._artifactId = ((a6 == null) ? "" : a6);
    }
    
    public static Version unknownVersion() {
        return Version.UNKNOWN_VERSION;
    }
    
    public boolean isUnknownVersion() {
        return this == Version.UNKNOWN_VERSION;
    }
    
    public boolean isSnapshot() {
        return this._snapshotInfo != null && this._snapshotInfo.length() > 0;
    }
    
    @Deprecated
    public boolean isUknownVersion() {
        return this.isUnknownVersion();
    }
    
    public int getMajorVersion() {
        return this._majorVersion;
    }
    
    public int getMinorVersion() {
        return this._minorVersion;
    }
    
    public int getPatchLevel() {
        return this._patchLevel;
    }
    
    public String getGroupId() {
        return this._groupId;
    }
    
    public String getArtifactId() {
        return this._artifactId;
    }
    
    public String toFullString() {
        return this._groupId + '/' + this._artifactId + '/' + this.toString();
    }
    
    @Override
    public String toString() {
        final StringBuilder v1 = new StringBuilder();
        v1.append(this._majorVersion).append('.');
        v1.append(this._minorVersion).append('.');
        v1.append(this._patchLevel);
        if (this.isSnapshot()) {
            v1.append('-').append(this._snapshotInfo);
        }
        return v1.toString();
    }
    
    @Override
    public int hashCode() {
        return this._artifactId.hashCode() ^ this._groupId.hashCode() + this._majorVersion - this._minorVersion + this._patchLevel;
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (a1 == this) {
            return true;
        }
        if (a1 == null) {
            return false;
        }
        if (a1.getClass() != this.getClass()) {
            return false;
        }
        final Version v1 = (Version)a1;
        return v1._majorVersion == this._majorVersion && v1._minorVersion == this._minorVersion && v1._patchLevel == this._patchLevel && v1._artifactId.equals(this._artifactId) && v1._groupId.equals(this._groupId);
    }
    
    @Override
    public int compareTo(final Version a1) {
        if (a1 == this) {
            return 0;
        }
        int v1 = this._groupId.compareTo(a1._groupId);
        if (v1 == 0) {
            v1 = this._artifactId.compareTo(a1._artifactId);
            if (v1 == 0) {
                v1 = this._majorVersion - a1._majorVersion;
                if (v1 == 0) {
                    v1 = this._minorVersion - a1._minorVersion;
                    if (v1 == 0) {
                        v1 = this._patchLevel - a1._patchLevel;
                    }
                }
            }
        }
        return v1;
    }
    
    @Override
    public /* bridge */ int compareTo(final Object a1) {
        return this.compareTo((Version)a1);
    }
    
    static {
        UNKNOWN_VERSION = new Version(0, 0, 0, null, null, null);
    }
}
