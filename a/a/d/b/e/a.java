package a.a.d.b.e;

import com.backdoored.event.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class a extends BackdooredEvent
{
    public NonNullList<ItemStack> nonNullList;
    
    public a(final NonNullList<ItemStack> nonNullList) {
        super();
        this.nonNullList = nonNullList;
    }
}
