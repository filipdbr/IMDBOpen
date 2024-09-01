package Entities.Business.Personne;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "personne")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Personne implements IPersonne<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "identite")
    @Getter
    @Setter
    private String identite;

    @Getter
    @Setter
    @Column(name = "date_naissance")
    private String dateNaissance;

    @Getter
    @Setter
    @Column(name = "lieu_naissance")
    private String lieuNaissance;

    @Getter
    @Setter
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
        // No implementation needed
    }

    @Override
    public LocalDateTime getUpdatedDate() {
        return null;
    }

    @Override
    public void setUpdatedDate(LocalDateTime updatedDate) {
        // No implementation needed
    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public void setDeleted(boolean deleted) {
        // No implementation needed
    }
}
