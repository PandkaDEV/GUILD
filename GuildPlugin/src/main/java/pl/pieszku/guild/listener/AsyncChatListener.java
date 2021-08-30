package pl.pieszku.guild.listener;

import lombok.var;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.helper.ChatHelper;
import pl.pieszku.guild.impl.User;
import pl.pieszku.guild.service.UserService;

public class AsyncChatListener implements Listener {

    private final DataHolder dataHolder;
    private final ChatHelper chatHelper = new ChatHelper();
    private final UserService userService;

    public AsyncChatListener(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
        this.userService = new UserService(dataHolder);
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        var player = event.getPlayer();
        var message = event.getMessage();
        var user = this.userService.getUser(player.getName());
        var guild = user.getGuild();

        message = message.replace("&", "");
        message = message.replace("%", "");



        if (guild == null) {
            event.setFormat(chatHelper.colored("&7" + player.getName() + ": &f" + message));
            return;
        }
        event.setFormat(chatHelper.colored("&8[&c" + guild.getName().toUpperCase() + "&8] &7" + player.getName() + ": &f" + message));
    }
}
