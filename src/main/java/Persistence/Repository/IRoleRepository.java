package Persistence.Repository;


import Entities.Business.Role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for accessing Role entities.
 *
 * This interface extends JpaRepository to provide CRUD operations and query methods
 * for the Role entity. Spring Data JPA will generate the implementation automatically
 * based on the method names.
 */
public interface IRoleRepository extends JpaRepository<Role, Long> {

    /**
     * Find roles by the film's ID.
     *
     * @param filmId The ID of the film.
     * @return A list of roles associated with the given film ID.
     */
    List<Role> findByFilmId(Long filmId);

    /**
     * Find roles by the actor's ID.
     *
     * @param actorId The ID of the actor.
     * @return A list of roles associated with the given actor ID.
     */
    List<Role> findByActorId(Long actorId);

    /**
     * Find roles by the role's name.
     *
     * @param roleName The name of the role.
     * @return A list of roles with the given role name.
     */
    List<Role> findByRoleName(String roleName);

    /**
     * Find roles by the film's ID and role's name.
     *
     * @param filmId The ID of the film.
     * @param roleName The name of the role.
     * @return A list of roles associated with the given film ID and role name.
     */
    List<Role> findByFilmIdAndRoleName(Long filmId, String roleName);

    /**
     * Find roles by the actor's ID and role's name.
     *
     * @param actorId The ID of the actor.
     * @param roleName The name of the role.
     * @return A list of roles associated with the given actor ID and role name.
     */
    List<Role> findByActorIdAndRoleName(Long actorId, String roleName);

    /**
     * Find roles by the film's ID and actor's ID.
     *
     * @param filmId The ID of the film.
     * @param actorId The ID of the actor.
     * @return A list of roles associated with the given film ID and actor ID.
     */
    List<Role> findByFilmIdAndActorId(Long filmId, Long actorId);

    /**
     * Find roles by the film's ID, actor's ID, and role's name.
     *
     * @param filmId The ID of the film.
     * @param actorId The ID of the actor.
     * @param roleName The name of the role.
     * @return A list of roles associated with the given film ID, actor ID, and role name.
     */
    List<Role> findByFilmIdAndActorIdAndRoleName(Long filmId, Long actorId, String roleName);

    /**
     * Find roles by the role's name with partial match.
     *
     * @param partialRoleName The partial name of the role.
     * @return A list of roles with names containing the given partial role name.
     */
    List<Role> findByRoleNameContaining(String partialRoleName);
}
