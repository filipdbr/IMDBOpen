package Entities.Business.Film;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.*;

@Setter
@Getter
@Entity
@Data
@Builder
@Table(name = "Genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Genre name cannot be blank")
    @Column(unique = true, nullable = false)
    @Size(max = 100, message = "Genre name should not exceed 100 characters")
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Film> films;

    // Default constructor
    public Genre() {}


    public Genre(Object o, String genreName) {
        // Empty constructor
    }

    public Genre orElseGet(Object o) {
        return this;
    }
}