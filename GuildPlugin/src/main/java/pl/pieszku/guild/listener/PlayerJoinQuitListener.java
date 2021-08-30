package pl.pieszku.guild.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.impl.User;
import pl.pieszku.guild.service.UserService;


public class PlayerJoinQuitListener implements Listener {

    private final DataHolder dataHolder;
    private final UserService userService;

    public PlayerJoinQuitListener(DataHolder dataHolder) {
        this.dataHolder = dataHolder;
        this.userService = new UserService(this.dataHolder);
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage(null);
        Player player = event.getPlayer();
        User user = this.userService.getUser(player.getName());
        if(user == null)this.userService.createUser(player.getName());
    }

}
