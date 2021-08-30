package pl.pieszku.guild.data;

import lombok.Getter;
import pl.pieszku.guild.impl.Guild;
import pl.pieszku.guild.impl.User;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {


    @Getter
    private final List<User> users = new ArrayList<>();

    @Getter
    private final List<Guild> guilds = new ArrayList<>();

}
