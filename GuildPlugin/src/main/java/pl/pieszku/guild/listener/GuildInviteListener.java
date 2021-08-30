package pl.pieszku.guild.listener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.event.GuildInviteEvent;
import pl.pieszku.guild.helper.ChatHelper;
import pl.pieszku.guild.impl.Guild;
import qu.code.api.objects.ApiConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GuildInviteListener implements Listener {

    private final DataHolder dataHolder;
    private final ApiConfig config;
    private final ChatHelper chatHelper = new ChatHelper();

    public GuildInviteListener(DataHolder dataHolder, ApiConfig config) {
        this.dataHolder = dataHolder;
        this.config = config;
    }
    @EventHandler
    public void onInviteGuild(GuildInviteEvent event){
        if(event.isCancelled())return;

        Guild guild = event.getGuild();
        String nickName = event.getInviteNickName();
        Player playerSender = event.getSender();

        guild.addInvite(nickName);


        List<String> invite_message = this.config.getOrCreate("message.guild_invite_sender", Arrays.asList(
                "&8&l&m-[&d&l&m---&8&m&l[--&8 &d&lGILDIA &8&l&m--]&d&l&m---&8&l&m]-&8",
                "&8>> &fWyslano pomyslnie zaproszenie do gildi dla: &d{PLAYER}",
                "&8>> &fDo twojej gildi &8[&d{GUILD_TAG}&8]",
                "&8&l&m-[&d&l&m---&8&m&l[--&8 &d&lGILDIA &8&l&m--]&d&l&m---&8&l&m]-&8"));


        invite_message.stream().map(message -> {
            message = message.replace("{PLAYER}", nickName);
            message = message.replace("{GUILD_TAG}", guild.getName().toUpperCase());
            return message;
        }).collect(Collectors.toList());

        invite_message.forEach(message -> playerSender.sendMessage(this.chatHelper.colored(message)));


        Player player = Bukkit.getPlayer(nickName);
        if(player == null) return;

        invite_message = this.config.getOrCreate("message.guild_invite_target", Arrays.asList(
                ""));


        invite_message
                .stream()
                .map(message -> {
                    message = message.replace("{PLAYER}", playerSender.getName());
                    message = message.replace("{GUILD_TAG}", guild.getName().toUpperCase());
                    return message;
                }).collect(Collectors.toList());

        invite_message.forEach(message-> player.sendMessage(this.chatHelper.colored(message)));

    }
}
