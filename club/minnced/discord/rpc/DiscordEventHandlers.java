package club.minnced.discord.rpc;

import java.util.*;
import com.sun.jna.*;

public class DiscordEventHandlers extends Structure
{
    private static final List<String> FIELD_ORDER;
    public OnReady ready;
    public OnStatus disconnected;
    public OnStatus errored;
    public OnGameUpdate joinGame;
    public OnGameUpdate spectateGame;
    public OnJoinRequest joinRequest;
    
    public DiscordEventHandlers() {
        super();
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (!(a1 instanceof DiscordEventHandlers)) {
            return false;
        }
        final DiscordEventHandlers v1 = (DiscordEventHandlers)a1;
        return Objects.equals(this.ready, v1.ready) && Objects.equals(this.disconnected, v1.disconnected) && Objects.equals(this.errored, v1.errored) && Objects.equals(this.joinGame, v1.joinGame) && Objects.equals(this.spectateGame, v1.spectateGame) && Objects.equals(this.joinRequest, v1.joinRequest);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.ready, this.disconnected, this.errored, this.joinGame, this.spectateGame, this.joinRequest);
    }
    
    @Override
    protected List<String> getFieldOrder() {
        return DiscordEventHandlers.FIELD_ORDER;
    }
    
    static {
        FIELD_ORDER = Collections.unmodifiableList((List<? extends String>)Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest"));
    }
    
    public interface OnJoinRequest extends Callback
    {
        void accept(final DiscordUser p0);
    }
    
    public interface OnGameUpdate extends Callback
    {
        void accept(final String p0);
    }
    
    public interface OnStatus extends Callback
    {
        void accept(final int p0, final String p1);
    }
    
    public interface OnReady extends Callback
    {
        void accept(final DiscordUser p0);
    }
}
