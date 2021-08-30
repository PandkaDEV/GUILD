package pl.pieszku.guild.command;

import org.bukkit.entity.Player;
import pl.pieszku.guild.data.DataHolder;
import qu.code.api.objects.ApiConfig;

public class InviteGuildCommand extends SubCommand {


    public InviteGuildCommand(DataHolder dataHolder, ApiConfig config) {
        super("zapros", "", "", "");

    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        return false;
    }
}
