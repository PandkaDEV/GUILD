package pl.pieszku.guild.impl;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private final String nickName;
    private Guild guild;
    private int points;
    private int kills;
    private int deaths;

    public User(String nickName){
        this.nickName = nickName;
        this.guild = null;
        this.points = 1000;
        this.kills = 0;
        this.deaths = 0;
    }
    public boolean hasGuild(){
        return this.guild != null;
    }
}
