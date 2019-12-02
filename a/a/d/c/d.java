package a.a.d.c;

import com.backdoored.event.*;
import net.minecraft.client.network.*;
import net.minecraft.world.*;

public class d extends BackdooredEvent
{
    public final NetworkPlayerInfo networkPlayerInfo;
    public GameType gameType;
    
    public d(final NetworkPlayerInfo networkPlayerInfo, final GameType gameType) {
        super();
        this.networkPlayerInfo = networkPlayerInfo;
        this.gameType = gameType;
    }
}
