package pl.pieszku.guild.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import pl.pieszku.guild.impl.Guild;

@Getter
public class GuildCreateEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }

    private final Player player;
    private final Guild guild;

    public GuildCreateEvent(Player player, Guild guild){
        this.player = player;
        this.guild = guild;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
