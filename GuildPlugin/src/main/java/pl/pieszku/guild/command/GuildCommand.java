package pl.pieszku.guild.command;


import lombok.Getter;
import org.bukkit.entity.Player;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.utilities.CommandUtilities;
import qu.code.api.objects.ApiConfig;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Getter
public class GuildCommand extends SubCommand {

    @Getter
    private final Set<SubCommand> subCommands = new HashSet<>();

    private final CommandUtilities commandUtilities;
    private final DataHolder dataHolder;
    private final ApiConfig config;

    public GuildCommand(CommandUtilities commandUtilities, DataHolder dataHolder, ApiConfig configMessage) {
        super("gildia", "glowna komenda systemu gildii", "/g <subkomenda>", "", "gildie", "", "g");
        this.commandUtilities = commandUtilities;
        this.dataHolder = dataHolder;
        this.config = configMessage;

        this.subCommands.add(new CreateGuildCommand(this.dataHolder, this.config));
        this.subCommands.add(new PanelGuildCommand(this.dataHolder, this.config));
        this.subCommands.add(new InviteGuildCommand(this.dataHolder, this.config));

        this.subCommands.forEach(this.commandUtilities::register);

    }

    @Override
    public boolean onCommand(Player player, String[] args) {

        String name = args[0];

        SubCommand sc = getSub(name);

        if (sc == null) {
            //help
            return false;
        }
        return sc.onCommand(player, Arrays.copyOfRange(args, 1, args.length));
    }

    private SubCommand getSub(String sub) {
        for (SubCommand sc : subCommands)
            if (sc.getName().equalsIgnoreCase(sub) || sc.getAliases().contains(sub))
                return sc;
        return null;
    }
}
