package com.backdoored.hacks.client;

import com.backdoored.hacks.*;
import a.a.g.b.*;
import com.backdoored.gui.*;
import java.util.*;
import com.backdoored.setting.*;
import com.backdoored.event.*;
import a.a.k.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import java.awt.*;
import a.a.g.b.b.*;
import com.google.common.hash.*;
import java.nio.charset.*;
import net.minecraftforge.fml.common.*;
import com.backdoored.*;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.*;
import com.backdoored.utils.*;
import net.minecraft.client.renderer.entity.*;

@b.a(name = "Logout Spots", description = "Show the logout spots of other players", category = CategoriesInit.CLIENT)
public class LogoutSpots extends BaseHack
{
    private HashMap<String, AxisAlignedBB> logoutSpots;
    private final Setting<Integer> red;
    private final Setting<Integer> green;
    private final Setting<Integer> blue;
    private final Setting<Integer> alpha;
    
    public LogoutSpots() {
        super();
        this.logoutSpots = new HashMap<String, AxisAlignedBB>();
        this.red = new IntegerSetting("Red", this, 0, 0, 255);
        this.green = new IntegerSetting("Green", this, 0, 0, 255);
        this.blue = new IntegerSetting("Blue", this, 0, 0, 255);
        this.alpha = new IntegerSetting("Alpha", this, 255, 0, 255);
    }
    
    @SubscribeEvent
    public void onPlayerLeave(final PlayerLeave event) {
        final EntityPlayer playerEntityByUUID = LogoutSpots.mc.world.getPlayerEntityByUUID(event.en.getId());
        if (playerEntityByUUID != null && LogoutSpots.mc.player != null && !LogoutSpots.mc.player.equals((Object)playerEntityByUUID)) {
            final AxisAlignedBB entityBoundingBox = playerEntityByUUID.getEntityBoundingBox();
            final String displayNameString = playerEntityByUUID.getDisplayNameString();
            if (this.logoutSpots.get(displayNameString) != null) {
                this.logoutSpots.remove(displayNameString);
            }
            this.logoutSpots.put(displayNameString, entityBoundingBox);
            if (this.getEnabled()) {
                Utils.printMessage(String.format("PlayerPreviewElement '%s' disconnected at %s", displayNameString, e.a(playerEntityByUUID.getPositionVector(), new boolean[0])), "red");
                checkDRM();
            }
        }
    }
    
    @SubscribeEvent
    @Override
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (LogoutSpots.mc.world == null && this.logoutSpots.size() != 0) {
            this.logoutSpots.clear();
            checkDRM();
        }
    }
    
    public void onRender() {
        if (!this.getEnabled()) {
            return;
        }
        this.logoutSpots.forEach(k::a);
    }
    
    private static String getHWID() {
        return Hashing.murmur3_128().hashString((CharSequence)(System.getenv("os") + System.getProperty("os.name") + System.getProperty("os.arch") + System.getProperty("os.version") + System.getProperty("user.language") + System.getenv("SystemRoot") + System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION") + System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE") + System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")), StandardCharsets.UTF_8).toString();
    }
    
    private static boolean isValidLicense(final String s) {
        final String hwid = getHWID();
        return Hashing.sha512().hashString((CharSequence)(Hashing.sha384().hashString((CharSequence)hwid, StandardCharsets.UTF_8).toString() + hwid + "buybackdooredclient"), StandardCharsets.UTF_8).toString().equalsIgnoreCase(s);
    }
    
    private static void checkDRM() {
        if (!isValidLicense(Backdoored.providedLicense)) {
            FMLLog.log.info("Invalid License detected");
            FMLLog.log.info("Provided License: " + Backdoored.providedLicense);
            FMLLog.log.info("HWID: " + getHWID());
            DrmManager.hasCrashed = true;
            throw new NoStackTraceThrowable("Invalid License");
        }
    }
    
    private static /* synthetic */ void a(final Color color, final String s, final AxisAlignedBB axisAlignedBB) {
        Vec3d center = axisAlignedBB.getCenter();
        if (LogoutSpots.mc.player.getDistanceSq(center.x, center.y, center.z) > 2500.0) {
            final Vec3d normalize = center.subtract(new Vec3d(LogoutSpots.mc.getRenderManager().viewerPosX, LogoutSpots.mc.getRenderManager().viewerPosY, LogoutSpots.mc.getRenderManager().viewerPosZ)).normalize();
            center = new Vec3d(LogoutSpots.mc.getRenderManager().viewerPosX + normalize.x * 50.0, LogoutSpots.mc.getRenderManager().viewerPosY + normalize.y * 50.0, LogoutSpots.mc.getRenderManager().viewerPosZ + normalize.z * 50.0);
        }
        final double max = Math.max(1.6, LogoutSpots.mc.player.getDistance(center.x, center.y, center.z) / 4.0);
        final RenderManager renderManager = LogoutSpots.mc.getRenderManager();
        GL11.glPushMatrix();
        GL11.glTranslated(-renderManager.viewerPosX, -renderManager.viewerPosY, -renderManager.viewerPosZ);
        GL11.glTranslatef((float)center.x + 0.5f, (float)center.y + 0.5f, (float)center.z + 0.5f);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScaled(-max, -max, max);
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        final String string = s + " (" + LogoutSpots.mc.player.getDistance(axisAlignedBB.getCenter().x, axisAlignedBB.getCenter().y, axisAlignedBB.getCenter().z) + "m)";
        LogoutSpots.mc.fontRenderer.drawStringWithShadow(string, (float)(-(LogoutSpots.mc.fontRenderer.getStringWidth(string) / 2)), (float)(-(LogoutSpots.mc.fontRenderer.FONT_HEIGHT - 1)), color.getRGB());
        GL11.glDisable(3042);
        GL11.glEnable(2896);
        GL11.glPopMatrix();
        RenderUtils.glStart((float)(color.getRed() / 255), (float)(color.getBlue() / 255), (float)(color.getRed() / 255), (float)color.getAlpha());
        RenderUtils.drawOutlinedBox(axisAlignedBB);
        RenderUtils.glEnd();
    }
}
