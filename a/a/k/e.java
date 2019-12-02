package a.a.k;

import net.minecraft.client.*;
import net.minecraft.block.*;
import java.util.*;
import org.apache.commons.lang3.*;
import net.minecraft.util.math.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import a.a.k.f.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.util.vector.*;
import java.nio.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.utils.*;
import com.backdoored.*;
import net.minecraft.init.*;

public class e
{
    private static Minecraft mc;
    public static final Block[] block;
    private static final Random bex;
    
    public e() {
        super();
    }
    
    public static boolean a(final Block objectToFind) {
        return ArrayUtils.contains(e.block, objectToFind);
    }
    
    public static String a(final Vec3d vec3d, final boolean... array) {
        final boolean b = array.length <= 0 || array[0];
        final StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append((int)Math.floor(vec3d.x));
        sb.append(", ");
        if (b) {
            sb.append((int)Math.floor(vec3d.y));
            sb.append(", ");
        }
        sb.append((int)Math.floor(vec3d.z));
        sb.append(")");
        return sb.toString();
    }
    
    public static String a(final BlockPos blockPos) {
        return a(new Vec3d((Vec3i)blockPos), new boolean[0]);
    }
    
    public static void a(final String s, final int n, final int n2, final float n3) {
        int n4 = n;
        final char[] charArray = s.toCharArray();
        for (int length = charArray.length, i = 0; i < length; ++i) {
            String.valueOf(charArray[i]);
            n4 += n;
        }
    }
    
    public static Color a(final long n, final float n2) {
        final Color color = new Color((int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB((System.nanoTime() + n) / 1.0E10f % 1.0f, 1.0f, 1.0f)), 16));
        return new Color(color.getRed() / 255.0f * n2, color.getGreen() / 255.0f * n2, color.getBlue() / 255.0f * n2, color.getAlpha() / 255.0f);
    }
    
    public static double a(final double n, final int n2) {
        final double pow = Math.pow(10.0, n2);
        return Math.round(n * pow) / pow;
    }
    
    public static void a(final String s) {
        final StringSelection stringSelection = new StringSelection(s);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
    }
    
    public static boolean a(final String s, final String s2) {
        try {
            final BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(s));
            bufferedWriter.write(s2);
            bufferedWriter.close();
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static b a(final Vec3d vec3d) {
        final Entity renderViewEntity = e.mc.getRenderViewEntity();
        if (renderViewEntity == null) {
            return new b(0.0, 0.0, false);
        }
        final ActiveRenderInfo activeRenderInfo = new ActiveRenderInfo();
        final Vec3d positionEyes = e.mc.player.getPositionEyes(e.mc.getRenderPartialTicks());
        final Vec3d projectViewFromEntity = ActiveRenderInfo.projectViewFromEntity(renderViewEntity, (double)e.mc.getRenderPartialTicks());
        final Vector4f vector4f = new Vector4f((float)(positionEyes.x + projectViewFromEntity.x - (float)vec3d.x), (float)(positionEyes.y + projectViewFromEntity.y - (float)vec3d.y), (float)(positionEyes.z + projectViewFromEntity.z - (float)vec3d.z), 1.0f);
        final Matrix4f matrix4f = new Matrix4f();
        matrix4f.load((FloatBuffer)ObfuscationReflectionHelper.getPrivateValue((Class)ActiveRenderInfo.class, (Object)new ActiveRenderInfo(), new String[] { "MODELVIEW", "field_178812_b" }));
        final Matrix4f matrix4f2 = new Matrix4f();
        matrix4f2.load((FloatBuffer)ObfuscationReflectionHelper.getPrivateValue((Class)ActiveRenderInfo.class, (Object)new ActiveRenderInfo(), new String[] { "PROJECTION", "field_178813_c" }));
        a(vector4f, matrix4f);
        a(vector4f, matrix4f2);
        if (vector4f.w > 0.0f) {
            final Vector4f vector4f2 = vector4f;
            vector4f2.x *= -100000.0f;
            final Vector4f vector4f3 = vector4f;
            vector4f3.y *= -100000.0f;
        }
        else {
            final float n = 1.0f / vector4f.w;
            final Vector4f vector4f4 = vector4f;
            vector4f4.x *= n;
            final Vector4f vector4f5 = vector4f;
            vector4f5.y *= n;
        }
        final ScaledResolution scaledResolution = new ScaledResolution(e.mc);
        final float n2 = scaledResolution.getScaledWidth() / 2.0f;
        final float n3 = scaledResolution.getScaledHeight() / 2.0f;
        vector4f.x = n2 + (0.5f * vector4f.x * scaledResolution.getScaledWidth() + 0.5f);
        vector4f.y = n3 - (0.5f * vector4f.y * scaledResolution.getScaledHeight() + 0.5f);
        boolean b = true;
        if (vector4f.x < 0.0f || vector4f.y < 0.0f || vector4f.x > scaledResolution.getScaledWidth() || vector4f.y > scaledResolution.getScaledHeight()) {
            b = false;
        }
        return new b(vector4f.x, vector4f.y, b);
    }
    
    private static void a(final Vector4f vector4f, final Matrix4f matrix4f) {
        final float x = vector4f.x;
        final float y = vector4f.y;
        final float z = vector4f.z;
        vector4f.x = x * matrix4f.m00 + y * matrix4f.m10 + z * matrix4f.m20 + matrix4f.m30;
        vector4f.y = x * matrix4f.m01 + y * matrix4f.m11 + z * matrix4f.m21 + matrix4f.m31;
        vector4f.z = x * matrix4f.m02 + y * matrix4f.m12 + z * matrix4f.m22 + matrix4f.m32;
        vector4f.w = x * matrix4f.m03 + y * matrix4f.m13 + z * matrix4f.m23 + matrix4f.m33;
    }
    
    public static float a() {
        float rotationYaw = e.mc.player.rotationYaw;
        if (e.mc.player.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float n = 1.0f;
        if (e.mc.player.moveForward < 0.0f) {
            n = -0.5f;
        }
        else if (e.mc.player.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (e.mc.player.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (e.mc.player.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    private static String b() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean b(final String s) {
        final String b = b();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)b, StandardCharsets.UTF_8).toString() + b + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void c() {
        if (e.bex.nextBoolean() && !b(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + b());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
    
    static {
        e.mc = Globals.mc;
        block = new Block[] { Blocks.WHITE_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.PINK_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.SILVER_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX };
        bex = new Random();
    }
}
