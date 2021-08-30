package pl.pieszku.guild.utilities;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import java.util.HashMap;

public class CommandUtilities {

    private final HashMap<String, Command> commands = new HashMap<>();
    private final Reflection.FieldAccessor<SimpleCommandMap> field = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);
    private static CommandMap cmdMap = null;

    public void enable() {
        cmdMap = field.get(Bukkit.getServer().getPluginManager());
    }

    public void register(Command cmd, String fallback) {
        cmdMap.register(fallback, cmd);
        commands.put(cmd.getName(), cmd);
    }

    public void register(Command cmd) {
        register(cmd, cmd.getName());
    }

}
