package pl.pieszku.guild;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.pieszku.guild.command.GuildCommand;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.inventory.PanelGuildInventory;
import pl.pieszku.guild.listener.AsyncChatListener;
import pl.pieszku.guild.listener.GuildCreateListener;
import pl.pieszku.guild.listener.GuildInviteListener;
import pl.pieszku.guild.listener.PlayerJoinQuitListener;
import pl.pieszku.guild.service.GuildService;
import pl.pieszku.guild.utilities.CommandUtilities;
import qu.code.api.objects.ApiConfig;

import java.util.Arrays;

public final class Main extends JavaPlugin {

    private ApiConfig configMessage;

    @Override
    public void onEnable() {
        DataHolder dataHolder = new DataHolder();
        this.configMessage = new ApiConfig(this);
        this.configMessage.setConfigName("guild");
        this.configMessage.load(false);

        registerCommand(dataHolder, this.configMessage);
        registerListener(dataHolder, this.configMessage);
    }

    private void registerListener(DataHolder dataHolder, ApiConfig configMessage) {
        GuildService guildService = new GuildService(dataHolder);
        Listener[] listeners = new Listener[]{
                new PlayerJoinQuitListener(dataHolder),
                new GuildCreateListener(dataHolder, configMessage),
                new GuildInviteListener(dataHolder, configMessage),
                new AsyncChatListener(dataHolder),
                new PanelGuildInventory(guildService, configMessage),
        };

        Arrays.stream(listeners).forEach(listener -> Bukkit.getPluginManager().registerEvents(listener, this));
    }

    private void registerCommand(DataHolder dataHolder, ApiConfig configMessage) {
        CommandUtilities commandUtilities = new CommandUtilities();
        commandUtilities.enable();
        Command[] commands = new Command[]{
                new GuildCommand(commandUtilities, dataHolder, configMessage),
        };
        Arrays.stream(commands).forEach(commandUtilities::register);


    }

    @Override
    public void onDisable() {
        this.configMessage.save();
    }
}
