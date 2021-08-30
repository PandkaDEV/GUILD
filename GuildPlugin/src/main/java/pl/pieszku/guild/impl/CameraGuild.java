package pl.pieszku.guild.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

@Getter
@Setter
public class CameraGuild {

    private final Guild guild;
    private final String name;
    private double locationX;
    private double locationY;
    private double locationZ;

    public CameraGuild(Guild guild, String name){
        this.guild = guild;
        this.name = name;
        this.locationX = 0;
        this.locationY = 0;
        this.locationZ = 0;
    }
    public boolean isActive(){
        return locationX != 0 || this.locationY != 0 || this.locationZ != 0;
    }
    public Location getLocation(){
        return new Location(Bukkit.getWorld("world"), this.locationX, this.locationY, this.locationZ);
    }
}
