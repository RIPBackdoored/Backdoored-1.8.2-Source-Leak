package a.a.g.c;

import net.minecraftforge.fml.common.gameevent.*;
import org.lwjgl.input.*;
import com.backdoored.setting.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class a
{
    public a() {
        super();
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void a(final InputEvent.KeyInputEvent keyInputEvent) {
        if (Keyboard.getEventKeyState()) {
            final int eventKey = Keyboard.getEventKey();
            if (eventKey != 0) {
                final String keyName = Keyboard.getKeyName(eventKey);
                if (!keyName.equalsIgnoreCase("NONE")) {
                    for (final Setting<String> setting : b.xz.a()) {
                        if (keyName.equalsIgnoreCase(setting.getValInt())) {
                            setting.b().setEnabled(!setting.b().getEnabled());
                        }
                    }
                }
            }
        }
    }
}
