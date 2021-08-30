package pl.pieszku.guild.service;

import org.bukkit.entity.Player;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.impl.Guild;

public class GuildService {

    private final DataHolder dataHolder;

    public GuildService(DataHolder dataHolder){
        this.dataHolder = dataHolder;
    }

    public void remove(Guild guild){
      this.dataHolder.getGuilds().remove(guild);
    }
    public Guild create(String name, String fullName, String dateCreate, String owner){
        Guild guild = new Guild(name, fullName, dateCreate, owner);
        this.dataHolder.getGuilds().add(guild);
        return guild;
    }
    public Guild getGuild(String name){
        return this.dataHolder
                .getGuilds()
                .stream()
                .filter(guild -> guild.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
    public Guild getGuild(Player player){
        return this.dataHolder
                .getGuilds()
                .stream()
                .filter(guild -> guild.hasMember(player.getName()))
                .findFirst()
                .orElse(null);
    }

}
