package pl.pieszku.guild.impl;

import lombok.Getter;
import lombok.Setter;
import pl.pieszku.guild.type.RoleType;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RolePermission {

    private final Guild guild;
    private final RoleType role;
    private boolean defaults;
    private Set<String> members;
    private Set<Integer> permissions;

    public RolePermission(Guild guild, RoleType role, boolean defaults){
        this.guild = guild;
        this.role = role;
        this.defaults =defaults;
        this.members = new HashSet<>();
        this.permissions = new HashSet<>();
    }
}
