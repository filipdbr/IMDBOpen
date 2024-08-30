package Web.Model.DTO;

import Entities.Business.Role.Role;
import lombok.Getter;
import lombok.Setter;

public class RoleDTO {

    @Setter
    @Getter
    private Long id;

    @Setter
    @Getter
    private String roleName;

    @Setter
    @Getter
    private String filmImdb;

    @Setter
    @Getter
    private String actorImdb;

    public static RoleDTO fromEntity(Role role) {
        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setRoleName(role.getRoleName());
        dto.setFilmImdb(role.getFilmImdb());
        dto.setActorImdb(role.getActorImdb());
        return dto;
    }

    public Role toEntity() {
        Role role = new Role();
        role.setId(this.getId());
        role.setRoleName(this.getRoleName());
        role.setFilmImdb(this.getFilmImdb());
        role.setActorImdb(this.getActorImdb());
        return role;
    }
}