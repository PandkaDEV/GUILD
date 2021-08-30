package pl.pieszku.guild.inventory;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.pieszku.guild.helper.ChatHelper;
import pl.pieszku.guild.service.GuildService;
import qu.code.api.objects.ApiConfig;

@Getter
public class VisualGuildIInventory {

    private final GuildService guildService;
    private final ApiConfig config;
    private final ChatHelper chatHelper = new ChatHelper();

    public VisualGuildIInventory(GuildService guildService, ApiConfig config){
        this.guildService = guildService;
        this.config = config;

    }

    public void show(Player player) {
        int rows = this.config.getOrCreate("guild.inventory.visual_rows", 6 *9);
        Inventory inventory = Bukkit.createInventory(player, rows, this.config.getOrCreate("guild.inventory.visual_title", this.chatHelper.colored("&fWizualzacja serca")));

        player.openInventory(inventory);
    }
}
