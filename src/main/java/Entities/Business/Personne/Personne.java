package Entities.Business.Personne;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "personne")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "personne_type")
public class Personne implements IPersonne<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Getter
    @Column(name = "nom")
    private String nom;

    @Setter
    @Getter
    @Column(name = "prenom")
    private String prenom;

    @Setter
    @Getter
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Setter
    @Getter
    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Setter
    @Getter
    @Column(name = "url")
    private String url;



    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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