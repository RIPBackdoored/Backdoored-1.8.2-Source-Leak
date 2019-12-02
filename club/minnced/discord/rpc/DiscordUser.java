package club.minnced.discord.rpc;

import com.sun.jna.*;
import java.util.*;

public class DiscordUser extends Structure
{
    private static final List<String> FIELD_ORDER;
    public String userId;
    public String username;
    public String discriminator;
    public String avatar;
    
    public DiscordUser(final String a1) {
        super();
        this.setStringEncoding(a1);
    }
    
    public DiscordUser() {
        this("UTF-8");
    }
    
    @Override
    public boolean equals(final Object a1) {
        if (this == a1) {
            return true;
        }
        if (!(a1 instanceof DiscordUser)) {
            return false;
        }
        final DiscordUser v1 = (DiscordUser)a1;
        return Objects.equals(this.userId, v1.userId) && Objects.equals(this.username, v1.username) && Objects.equals(this.discriminator, v1.discriminator) && Objects.equals(this.avatar, v1.avatar);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.userId, this.username, this.discriminator, this.avatar);
    }
    
    @Override
    protected List<String> getFieldOrder() {
        return DiscordUser.FIELD_ORDER;
    }
    
    static {
        FIELD_ORDER = Collections.unmodifiableList((List<? extends String>)Arrays.asList("userId", "username", "discriminator", "avatar"));
    }
}
