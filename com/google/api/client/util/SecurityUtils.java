package com.google.api.client.util;

import java.io.*;
import java.security.*;
import javax.net.ssl.*;
import java.security.cert.*;
import java.util.*;

public final class SecurityUtils
{
    public static KeyStore getDefaultKeyStore() throws KeyStoreException {
        return KeyStore.getInstance(KeyStore.getDefaultType());
    }
    
    public static KeyStore getJavaKeyStore() throws KeyStoreException {
        return KeyStore.getInstance("JKS");
    }
    
    public static KeyStore getPkcs12KeyStore() throws KeyStoreException {
        return KeyStore.getInstance("PKCS12");
    }
    
    public static void loadKeyStore(final KeyStore a1, final InputStream a2, final String a3) throws IOException, GeneralSecurityException {
        try {
            a1.load(a2, a3.toCharArray());
        }
        finally {
            a2.close();
        }
    }
    
    public static PrivateKey getPrivateKey(final KeyStore a1, final String a2, final String a3) throws GeneralSecurityException {
        return (PrivateKey)a1.getKey(a2, a3.toCharArray());
    }
    
    public static PrivateKey loadPrivateKeyFromKeyStore(final KeyStore a1, final InputStream a2, final String a3, final String a4, final String a5) throws IOException, GeneralSecurityException {
        loadKeyStore(a1, a2, a3);
        return getPrivateKey(a1, a4, a5);
    }
    
    public static KeyFactory getRsaKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance("RSA");
    }
    
    public static Signature getSha1WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA1withRSA");
    }
    
    public static Signature getSha256WithRsaSignatureAlgorithm() throws NoSuchAlgorithmException {
        return Signature.getInstance("SHA256withRSA");
    }
    
    public static byte[] sign(final Signature a1, final PrivateKey a2, final byte[] a3) throws InvalidKeyException, SignatureException {
        a1.initSign(a2);
        a1.update(a3);
        return a1.sign();
    }
    
    public static boolean verify(final Signature a2, final PublicKey a3, final byte[] a4, final byte[] v1) throws InvalidKeyException, SignatureException {
        a2.initVerify(a3);
        a2.update(v1);
        try {
            return a2.verify(a4);
        }
        catch (SignatureException a5) {
            return false;
        }
    }
    
    public static X509Certificate verify(final Signature v-10, final X509TrustManager v-9, final List<String> v-8, final byte[] v-7, final byte[] v-6) throws InvalidKeyException, SignatureException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: astore          a1
        //     5: goto            12
        //     8: astore          a2
        //    10: aconst_null    
        //    11: areturn        
        //    12: aload_2         /* v-8 */
        //    13: invokeinterface java/util/List.size:()I
        //    18: anewarray       Ljava/security/cert/X509Certificate;
        //    21: astore          v-4
        //    23: iconst_0       
        //    24: istore          v-3
        //    26: aload_2         /* v-8 */
        //    27: invokeinterface java/util/List.iterator:()Ljava/util/Iterator;
        //    32: astore          8
        //    34: aload           8
        //    36: invokeinterface java/util/Iterator.hasNext:()Z
        //    41: ifeq            116
        //    44: aload           8
        //    46: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    51: checkcast       Ljava/lang/String;
        //    54: astore          v-1
        //    56: aload           v-1
        //    58: invokestatic    com/google/api/client/util/Base64.decodeBase64:(Ljava/lang/String;)[B
        //    61: astore          a5
        //    63: new             Ljava/io/ByteArrayInputStream;
        //    66: dup            
        //    67: aload           a5
        //    69: invokespecial   java/io/ByteArrayInputStream.<init>:([B)V
        //    72: astore          v1
        //    74: aload           v-5
        //    76: aload           v1
        //    78: invokevirtual   java/security/cert/CertificateFactory.generateCertificate:(Ljava/io/InputStream;)Ljava/security/cert/Certificate;
        //    81: astore          a3
        //    83: aload           a3
        //    85: instanceof      Ljava/security/cert/X509Certificate;
        //    88: ifne            93
        //    91: aconst_null    
        //    92: areturn        
        //    93: aload           v-4
        //    95: iload           v-3
        //    97: iinc            v-3, 1
        //   100: aload           a3
        //   102: checkcast       Ljava/security/cert/X509Certificate;
        //   105: aastore        
        //   106: goto            113
        //   109: astore          a4
        //   111: aconst_null    
        //   112: areturn        
        //   113: goto            34
        //   116: aload_1         /* v-9 */
        //   117: aload           v-4
        //   119: ldc             "RSA"
        //   121: invokeinterface javax/net/ssl/X509TrustManager.checkServerTrusted:([Ljava/security/cert/X509Certificate;Ljava/lang/String;)V
        //   126: goto            133
        //   129: astore          v-2
        //   131: aconst_null    
        //   132: areturn        
        //   133: aload           v-4
        //   135: iconst_0       
        //   136: aaload         
        //   137: invokevirtual   java/security/cert/X509Certificate.getPublicKey:()Ljava/security/PublicKey;
        //   140: astore          v-2
        //   142: aload_0         /* v-10 */
        //   143: aload           v-2
        //   145: aload_3         /* v-7 */
        //   146: aload           v-6
        //   148: invokestatic    com/google/api/client/util/SecurityUtils.verify:(Ljava/security/Signature;Ljava/security/PublicKey;[B[B)Z
        //   151: ifeq            159
        //   154: aload           v-4
        //   156: iconst_0       
        //   157: aaload         
        //   158: areturn        
        //   159: aconst_null    
        //   160: areturn        
        //    Exceptions:
        //  throws java.security.InvalidKeyException
        //  throws java.security.SignatureException
        //    Signature:
        //  (Ljava/security/Signature;Ljavax/net/ssl/X509TrustManager;Ljava/util/List<Ljava/lang/String;>;[B[B)Ljava/security/cert/X509Certificate;
        //    StackMapTable: 00 0A 48 07 00 82 FC 00 03 07 00 88 FE 00 15 07 00 96 01 07 00 98 FF 00 3A 00 0D 07 00 58 07 00 B1 07 00 8A 07 00 B2 07 00 B2 07 00 88 07 00 96 01 07 00 98 07 00 21 07 00 B2 07 00 A8 07 00 B4 00 00 FF 00 0F 00 0C 07 00 58 07 00 B1 07 00 8A 07 00 B2 07 00 B2 07 00 88 07 00 96 01 07 00 98 07 00 21 07 00 B2 07 00 A8 00 01 07 00 82 F8 00 03 FA 00 02 4C 07 00 82 03 FC 00 19 07 00 C0
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                     
        //  -----  -----  -----  -----  -----------------------------------------
        //  0      5      8      12     Ljava/security/cert/CertificateException;
        //  74     92     109    113    Ljava/security/cert/CertificateException;
        //  93     106    109    113    Ljava/security/cert/CertificateException;
        //  116    126    129    133    Ljava/security/cert/CertificateException;
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static CertificateFactory getX509CertificateFactory() throws CertificateException {
        return CertificateFactory.getInstance("X.509");
    }
    
    public static void loadKeyStoreFromCertificates(final KeyStore a2, final CertificateFactory a3, final InputStream v1) throws GeneralSecurityException {
        int v2 = 0;
        for (final Certificate a4 : a3.generateCertificates(v1)) {
            a2.setCertificateEntry(String.valueOf(v2), a4);
            ++v2;
        }
    }
    
    private SecurityUtils() {
        super();
    }
}
