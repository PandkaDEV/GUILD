package pl.pieszku.guild.helper;

import org.bukkit.ChatColor;

public class ChatHelper {

    public String colored(String text){
        return ChatColor.translateAlternateColorCodes('&', text.replace(">>", "»"));
    }
}
