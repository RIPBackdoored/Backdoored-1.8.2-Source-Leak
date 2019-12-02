package a.a.k.e;

import a.a.*;
import net.minecraft.block.*;
import net.minecraft.item.*;

public class a implements e
{
    public a() {
        super();
    }
    
    public static int a(final Item item, final boolean b) {
        int n = 9;
        if (b) {
            n = 0;
        }
        for (int i = n; i <= 44; ++i) {
            if (a.mc.player.inventoryContainer.getSlot(i).getStack().getItem() == item) {
                return i;
            }
        }
        return -1;
    }
    
    public static void a(final Block block, final boolean b) {
        b(new ItemStack(block).getItem(), b);
    }
    
    public static int b(final Item item, final boolean b) {
        return a(item, b, a.bdb);
    }
    
    public static void a(final Block block, final boolean b, final a a) {
        a(new ItemStack(block).getItem(), b, a);
    }
    
    public static int a(final Item item, final boolean b, final a a) {
        int n = 0;
        int n2 = 9;
        if (b) {
            n2 = 0;
        }
        for (int i = n2; i <= 44; ++i) {
            final ItemStack stack = a.mc.player.inventoryContainer.getSlot(i).getStack();
            if (stack.getItem() == item) {
                if (a == a.bdb) {
                    ++n;
                }
                else {
                    n += stack.getCount();
                }
            }
        }
        return n;
    }
    
    public enum a
    {
        bdb("NUMSTACKS", 0), 
        bdc("NUMITEMS", 1);
        
        private static final /* synthetic */ a[] $VALUES;
        
        public static a[] values() {
            return a.$VALUES.clone();
        }
        
        public static a valueOf(final String s) {
            return Enum.valueOf(a.class, s);
        }
        
        private a(final String s, final int n) {
        }
        
        static {
            $VALUES = new a[] { a.bdb, a.bdc };
        }
    }
}
