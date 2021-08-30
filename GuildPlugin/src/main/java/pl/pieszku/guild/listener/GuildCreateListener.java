package pl.pieszku.guild.listener;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.event.GuildCreateEvent;
import pl.pieszku.guild.helper.ChatHelper;
import pl.pieszku.guild.impl.Guild;
import qu.code.api.objects.ApiConfig;

import java.util.Arrays;
import java.util.List;

@Getter
public class GuildCreateListener implements Listener {

    private final DataHolder dataHolder;
    private final ApiConfig config;
    private final ChatHelper chatHelper = new ChatHelper();

    public GuildCreateListener(DataHolder dataHolder, ApiConfig configMessage) {
        this.dataHolder = dataHolder;
        this.config = configMessage;
    }
    @EventHandler
    public void onGuildCreate(GuildCreateEvent event){
        Player player = event.getPlayer();
        Guild guild = event.getGuild();

        List<String> messageForPlayer = config.getOrCreate("message.guild_create_message_player", Arrays.asList("&fGratki: &d{TAG}"));

        messageForPlayer.forEach(message -> {
            player.sendMessage(chatHelper.colored(message.replace("{TAG}", guild.getName())));
        });
        Bukkit.broadcastMessage(chatHelper.colored(config.getOrCreate("message.guild_create_message_server", "&5&lGILDIE &8* &fGracz: &d{PLAYER} &fzalozyl gildie &d{TAG} &5{NAME}"))
                .replace("{PLAYER}", player.getName())
                .replace("{TAG}", guild.getName())
                .replace("{NAME}", guild.getFullName()));
    }
}
