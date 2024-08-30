package Entities.Business.Pays;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

<<<<<<< Updated upstream
    @NotBlank(message = "Country name cannot be blank")
    @Size(max = 56, message = "Country name should not exceed 255 characters")
    private String name;

    public String getNom() {
        return name;
    }

    public void setNom(String nom) {
        this.name = nom;
    }

    public Pays orElseGet(Object o) {
        return this;
    }
}
=======
    public Pays() {

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long aLong) {

    }

    @Override
    public LocalDateTime getCreatedDate() {
        return null;
    }

    @Override
    public void setCreatedDate(LocalDateTime createdDate) {

    }

    @Override
    public LocalDateTime getUpdatedDate() {
        return null;
    }

    @Override
    public void setUpdatedDate(LocalDateTime updatedDate) {

    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public void setDeleted(boolean deleted) {

    }
}  
>>>>>>> Stashed changes
