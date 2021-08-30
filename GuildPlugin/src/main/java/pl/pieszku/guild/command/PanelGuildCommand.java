package pl.pieszku.guild.command;

import lombok.Getter;
import lombok.var;
import org.bukkit.entity.Player;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.helper.ChatHelper;
import pl.pieszku.guild.impl.Guild;
import pl.pieszku.guild.impl.User;
import pl.pieszku.guild.inventory.PanelGuildInventory;
import pl.pieszku.guild.service.GuildService;
import pl.pieszku.guild.service.UserService;
import qu.code.api.objects.ApiConfig;

@Getter
public class PanelGuildCommand extends SubCommand {


    private final DataHolder dataHolder;
    private final UserService userService;
    private final ApiConfig config;
    private final GuildService guildService;
    private final ChatHelper chatHelper = new ChatHelper();
    private final PanelGuildInventory panelGuildInventory;

    public PanelGuildCommand(DataHolder dataHolder, ApiConfig configMessage) {
        super("panel", "", "", "");
        this.dataHolder = dataHolder;
        this.userService = new UserService(dataHolder);
        this.guildService = new GuildService(dataHolder);
        this.config = configMessage;
        this.panelGuildInventory = new PanelGuildInventory(this.guildService, this.config);
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        var user = this.userService.getUser(player.getName());
        var guild = user.getGuild();

        if(guild == null){
            player.sendMessage(this.chatHelper.colored(this.config.getOrCreate("message.you_not_have_guild", "&4Blad: &cNie posiadasz gildii!!")));
            return false;
        }
        this.panelGuildInventory.show(player);
        return false;
    }
}
