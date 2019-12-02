package a.a.d.b.g;

import com.backdoored.event.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class b extends BackdooredEvent
{
    public ItemRenderer itemRenderer;
    public AbstractClientPlayer abstractClientPlayer;
    public float dd;
    public float de;
    public EnumHand enumHand;
    public float dg;
    public ItemStack itemStack;
    public float di;
    
    public b(final ItemRenderer itemRenderer, final AbstractClientPlayer abstractClientPlayer, final float dd, final float de, final EnumHand enumHand, final float dg, final ItemStack itemStack, final float di) {
        super();
        this.itemRenderer = itemRenderer;
        this.abstractClientPlayer = abstractClientPlayer;
        this.dd = dd;
        this.de = de;
        this.enumHand = enumHand;
        this.dg = dg;
        this.itemStack = itemStack;
        this.di = di;
    }
}
