package a.a.h.b.a;

import a.a.k.*;
import net.minecraft.tileentity.*;

public class b extends a.a.h.a.b
{
    public b() {
        super("Chest Count");
    }
    
    @Override
    public void b(final int n, final int n2, final float n3) {
        super.b(n, n2, n3);
        if (!this.g()) {
            return;
        }
        final String string = b.mc.world.loadedTileEntityList.stream().filter(tileEntity -> tileEntity instanceof TileEntityChest).count() + " chests";
        a.a.k.a.a(string, this.d().bco, this.d().bcp);
        this.e().bco = b.mc.fontRenderer.getStringWidth(string);
        this.e().bcp = b.mc.fontRenderer.FONT_HEIGHT;
    }
    
    private static /* synthetic */ boolean a(final TileEntity tileEntity) {
        return tileEntity instanceof TileEntityChest;
    }
}
