package pl.pieszku.guild.command;

import lombok.Getter;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.event.GuildCreateEvent;
import pl.pieszku.guild.helper.ChatHelper;
import pl.pieszku.guild.service.GuildService;
import pl.pieszku.guild.service.UserService;
import qu.code.api.objects.ApiConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class CreateGuildCommand extends SubCommand {


    private final DataHolder dataHolder;
    private final UserService userService;
    private final ApiConfig config;
    private final GuildService guildService;
    private final ChatHelper chatHelper = new ChatHelper();

    public CreateGuildCommand(DataHolder dataHolder, ApiConfig configMessage) {
        super("zaloz", "", "", "", "create");
        this.dataHolder = dataHolder;
        this.userService = new UserService(dataHolder);
        this.guildService = new GuildService(dataHolder);
        this.config = configMessage;
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        var user = this.userService.getUser(player.getName());
        var guild = user.getGuild();


        if(args.length < 2){
            player.sendMessage(this.chatHelper.colored(this.config.getOrCreate("message.command_usage", "&fPoprawne uzycie: &d{COMMAND}"))
                    .replace("{COMMAND}", "/g zaloz <tag> <pelnaNazwa>"));
            return false;
        }

        if(guild != null){
            player.sendMessage(this.chatHelper.colored(this.config.getOrCreate("message.you_have_guild", "&4Blad: &cPosiadasz juz gildie!!")));
            return false;
        }
        var name = args[0];
        var fullName = args[1];

        if(name.length() >= 5 || name.length() <= 2){
            player.sendMessage(this.chatHelper.colored(this.config.getOrCreate("message.guild_tag", "&4Blad: &cTag gildii musi zawierac od 3 do 4 liter!!!")));
            return false;
        }
        if(fullName.length() <= 8 || fullName.length() >= 20){
            player.sendMessage(this.chatHelper.colored(this.config.getOrCreate("message.guild_fullname", "&4Blad: &cNazwa gildii musi zawierac od 9 do 19 liter!!!")));
            return false;
        }
        var guildExists = this.guildService.getGuild(name);
        if(guildExists != null){
            player.sendMessage(this.chatHelper.colored(this.config.getOrCreate("message.guild_exists", "&4Blad: &cPodana gildia juz istnieje!")));
            return false;
        }
        var guildCreate = this.guildService.create(name, fullName, new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss").format(new Date()), player.getName());
        user.setGuild(guildCreate);
        Bukkit.getPluginManager().callEvent(new GuildCreateEvent(player, guildCreate));
        return false;
    }
}
