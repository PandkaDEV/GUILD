package pl.pieszku.guild.command;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

@Getter
public abstract class SubCommand extends Command {

    private final String name;
    private final String usage;
    private final String desc;
    private final String permission;

    public SubCommand(String name, String desc, String usage, String permission, String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));

        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.permission = permission;

    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        Player p = (Player) sender;

        return onCommand(p, args);
    }

    public abstract boolean onCommand(Player player, String[] args);

}
