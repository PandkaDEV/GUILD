package pl.pieszku.guild.impl;

import lombok.Getter;
import lombok.Setter;
import pl.pieszku.guild.type.RoleType;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Guild {

    private final String name;
    private final String fullName;
    private final String dateCreate;
    private String description;
    private String owner;
    private String leader;
    private String master;
    private String guildHead;
    private long protectionTime;
    private long expiresTime;
    private RolePermission roleMember;
    private RolePermission roleMaster;
    private RolePermission roleLeader;
    private RolePermission roleOwner;
    private CameraGuild cameraGuildOne;
    private CameraGuild cameraGuildTwo;
    private CameraGuild cameraGuildThree;
    private final Set<String> members;
    private final Set<String> invites;

    public Guild(String name, String fullName, String dateCreate, String owner) {
        this.name = name;
        this.fullName = fullName;
        this.dateCreate = dateCreate;
        this.description = "null";
        this.owner = owner;
        this.leader = "null";
        this.master = "null";
        this.guildHead = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODVkYWFmYTJlMDMwM2EyZjljNmEwZmJhZDVkZTA4MDEwODk3NmViNmQ0NTgzNjJjYmJlNGEwYTM2OTg4NjZjIn19fQ==";
        this.protectionTime = System.currentTimeMillis();
        this.expiresTime = System.currentTimeMillis();
        this.roleMember = new RolePermission(this, RoleType.MEMBER, true);
        this.roleMaster = new RolePermission(this, RoleType.MASTER, false);
        this.roleLeader = new RolePermission(this, RoleType.LEADER, false);
        this.roleOwner = new RolePermission(this, RoleType.OWNER, false);
        this.cameraGuildOne = new CameraGuild(this,"cameraOne");
        this.cameraGuildTwo = new CameraGuild(this,"cameraTwo");
        this.cameraGuildThree = new CameraGuild(this,"cameraThree");
        this.members = new HashSet<>();
        this.invites = new HashSet<>();
        this.addMember(owner);
    }
    public void addInvite(String nickName){
        this.invites.add(nickName);
    }
    public void removeInvite(String nickName){
        this.invites.remove(nickName);
    }
    public boolean hasInvite(String nickName){
        return this.invites.contains(nickName);
    }

    public void addMember(String nickName){
        this.members.add(nickName);
    }
    public void removeMember(String nickName){
        this.members.remove(nickName);
    }
    public boolean hasMember(String nickName){
        return this.members.contains(nickName);
    }
}
