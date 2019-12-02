package club.minnced.discord.rpc;

import com.sun.jna.*;

public interface OnStatus extends Callback
{
    void accept(final int p0, final String p1);
}
