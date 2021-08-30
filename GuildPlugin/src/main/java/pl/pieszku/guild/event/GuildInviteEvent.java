package pl.pieszku.guild.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.pieszku.guild.impl.Guild;

@Getter
@Setter
public class GuildInviteEvent extends Event implements Cancellable {


    @Getter
    private static final HandlerList handlerList = new HandlerList();

    private Guild guild;
    private String inviteNickName;
    private Player sender;
    private boolean cancelled;

    public GuildInviteEvent(Guild guild, String inviteNickName, Player sender){
        this.guild = guild;
        this.inviteNickName = inviteNickName;
        this.cancelled = false;
        this.sender = sender;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean canceled) {
        this.cancelled = canceled;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
