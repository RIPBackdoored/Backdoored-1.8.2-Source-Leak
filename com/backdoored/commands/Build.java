package com.backdoored.commands;

import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.backdoored.utils.*;
import net.minecraft.util.math.*;
import net.minecraftforge.event.entity.player.*;
import a.a.k.*;
import java.util.*;

public class Build extends CommandBase
{
    private static int m;
    private static final int n = 0;
    private static final int o = 1;
    private static final int p = 2;
    private static final int q = 3;
    private static Map<String, ArrayList<Vec3d>> r;
    private static ArrayList<Vec3d> s;
    private String t;
    private ArrayList<Vec3d> u;
    private int v;
    private int w;
    
    public Build() {
        super(new String[] { "build", "builder", "br" });
        this.u = new ArrayList<Vec3d>();
        this.v = 0;
        this.w = 6;
    }
    
    @Override
    public String a() {
        return "-builder/br <mode> [arg]";
    }
    
    @Override
    public boolean a(final String[] array) {
        if (array.length == 1) {
            if (array[0].equalsIgnoreCase("stop")) {
                if (this.u.size() > 0) {
                    Build.r.put(this.t, this.u);
                }
                this.u.clear();
                Build.m = 0;
                return true;
            }
            if (array[0].equalsIgnoreCase("list")) {
                final StringBuilder sb = new StringBuilder("Available configs: ");
                final Iterator<String> iterator = Build.r.keySet().iterator();
                while (iterator.hasNext()) {
                    sb.append(iterator.next());
                    sb.append(" ");
                }
                Utils.printMessage(sb.toString(), "red");
                return true;
            }
            if (Build.r.containsKey(array[0])) {
                Build.s = Build.r.get(array[0]);
                Build.m = 2;
                return true;
            }
        }
        if (array.length == 2) {
            if (array[0].equalsIgnoreCase("record")) {
                this.t = array[1];
                this.u.clear();
                Build.m = 1;
                return true;
            }
            if (array[0].equalsIgnoreCase("loop")) {
                if (Build.r.containsKey(array[1])) {
                    Build.s = Build.r.get(array[1]);
                    Build.m = 3;
                    return true;
                }
                Utils.printMessage("Config not found! Use 'br list' to see all configs", "red");
                return false;
            }
        }
        return false;
    }
    
    @SubscribeEvent
    public void a(final TickEvent.ClientTickEvent clientTickEvent) {
        if (Build.m == 0) {
            this.v = 0;
            return;
        }
        if (Build.m == 1) {
            this.b();
        }
        else if (Build.m == 2) {
            this.c();
        }
        else if (Build.m == 3) {
            this.d();
        }
    }
    
    private void b() {
        RenderUtils.glStart(0.0f, 255.0f, 0.0f, 1.0f);
        for (final Vec3d vec3d : this.u) {
            RenderUtils.drawOutlinedBox(RenderUtils.getBoundingBox(new BlockPos(this.mc.player.getPositionVector().add(vec3d).x, this.mc.player.getPositionVector().add(vec3d).y, this.mc.player.getPositionVector().add(vec3d).z)));
        }
        RenderUtils.glEnd();
    }
    
    @SubscribeEvent
    public void a(final PlayerInteractEvent.RightClickBlock rightClickBlock) {
        if (Build.m == 1) {
            final BlockPos offset = this.mc.objectMouseOver.getBlockPos().offset(this.mc.objectMouseOver.sideHit);
            this.u.add(new Vec3d(offset.getX() - this.mc.player.getPositionVector().x, offset.getY() - this.mc.player.getPositionVector().y, offset.getZ() - this.mc.player.getPositionVector().z));
            Utils.printMessage("added block" + this.u.get(this.u.size() - 1).toString());
        }
    }
    
    private void c() {
        if (this.v % this.w != 0) {
            ++this.v;
            return;
        }
        final int currentItem = this.mc.player.inventory.currentItem;
        final int b = f.b();
        if (b == -1) {
            Build.m = 0;
            Utils.printMessage("Blocks were not found in your hotbar!", "red");
            return;
        }
        this.mc.player.inventory.currentItem = b;
        for (final BlockPos blockPos : this.e()) {
            if (this.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
                f.a(blockPos);
                Utils.printMessage("place");
                ++this.v;
                return;
            }
        }
        this.mc.player.inventory.currentItem = currentItem;
        Build.m = 0;
    }
    
    private void d() {
        if (this.v % this.w != 0) {
            ++this.v;
            return;
        }
        final int currentItem = this.mc.player.inventory.currentItem;
        final int b = f.b();
        if (b == -1) {
            Build.m = 0;
            Utils.printMessage("Blocks were not found in your hotbar!", "red");
            return;
        }
        this.mc.player.inventory.currentItem = b;
        for (final BlockPos blockPos : this.e()) {
            if (this.mc.world.getBlockState(blockPos).getMaterial().isReplaceable()) {
                f.a(blockPos);
                Utils.printMessage("place");
                ++this.v;
                return;
            }
        }
        this.mc.player.inventory.currentItem = currentItem;
    }
    
    private ArrayList<BlockPos> e() {
        final ArrayList<BlockPos> list = new ArrayList<BlockPos>();
        final Iterator<Vec3d> iterator = Build.s.iterator();
        while (iterator.hasNext()) {
            list.add(new BlockPos(this.mc.player.getPositionVector().add((Vec3d)iterator.next())));
        }
        return list;
    }
    
    static {
        Build.m = 0;
        Build.r = new HashMap<String, ArrayList<Vec3d>>();
        Build.s = new ArrayList<Vec3d>();
    }
}
