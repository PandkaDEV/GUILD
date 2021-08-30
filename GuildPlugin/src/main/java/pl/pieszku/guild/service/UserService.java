package pl.pieszku.guild.service;

import pl.pieszku.guild.data.DataHolder;
import pl.pieszku.guild.impl.User;

public class UserService {

    private final DataHolder dataHolder;

    public UserService(DataHolder dataHolder){
        this.dataHolder = dataHolder;
    }
    public void createUser(String nickName){
        this.dataHolder.getUsers().add(new User(nickName));
    }
    public User getUser(String nickName){
        return dataHolder
                .getUsers()
                .stream()
                .filter(user -> user.getNickName().equalsIgnoreCase(nickName))
                .findFirst()
                .orElse(null);
    }
}
