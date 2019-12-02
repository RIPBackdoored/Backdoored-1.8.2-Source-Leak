package club.minnced.discord.rpc;

import com.sun.jna.*;
import java.util.*;

public class DiscordRichPresence extends Structure
{
    private static final List<String> FIELD_ORDER;
    public String state;
    public String details;
    public long startTimestamp;
    public long endTimestamp;
    public String largeImageKey;
    public String largeImageText;
    public String smallImageKey;
    public String smallImageText;
    public String partyId;
    public int partySize;
    public int partyMax;
    public String matchSecret;
    public String joinSecret;
    public String spectateSecret;
    public byte instance;
    
    public DiscordRichPresence(final String a1) {
        super();
        this.setStringEncoding(a1);
    }
    
    public DiscordRichPresence() {
        this("UTF-8");
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (!(a1 instanceof DiscordRichPresence)) {
            return false;
        }
        final DiscordRichPresence v1 = (DiscordRichPresence)a1;
        return this.startTimestamp == v1.startTimestamp && this.endTimestamp == v1.endTimestamp && this.partySize == v1.partySize && this.partyMax == v1.partyMax && this.instance == v1.instance && Objects.equals(this.state, v1.state) && Objects.equals(this.details, v1.details) && Objects.equals(this.largeImageKey, v1.largeImageKey) && Objects.equals(this.largeImageText, v1.largeImageText) && Objects.equals(this.smallImageKey, v1.smallImageKey) && Objects.equals(this.smallImageText, v1.smallImageText) && Objects.equals(this.partyId, v1.partyId) && Objects.equals(this.matchSecret, v1.matchSecret) && Objects.equals(this.joinSecret, v1.joinSecret) && Objects.equals(this.spectateSecret, v1.spectateSecret);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.state, this.details, this.startTimestamp, this.endTimestamp, this.largeImageKey, this.largeImageText, this.smallImageKey, this.smallImageText, this.partyId, this.partySize, this.partyMax, this.matchSecret, this.joinSecret, this.spectateSecret, this.instance);
    }
    
    @Override
    protected List<String> getFieldOrder() {
        return DiscordRichPresence.FIELD_ORDER;
    }
    
    static {
        FIELD_ORDER = Collections.unmodifiableList((List<? extends String>)Arrays.asList("state", "details", "startTimestamp", "endTimestamp", "largeImageKey", "largeImageText", "smallImageKey", "smallImageText", "partyId", "partySize", "partyMax", "matchSecret", "joinSecret", "spectateSecret", "instance"));
    }
}
