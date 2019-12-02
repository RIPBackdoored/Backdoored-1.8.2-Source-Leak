package club.minnced.discord.rpc;

import com.sun.jna.*;

public interface OnReady extends Callback
{
    void accept(final DiscordUser p0);
}
