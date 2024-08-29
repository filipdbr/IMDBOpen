package Persistence.Repository;

import Entities.Business.Personne.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IPersonneRepository extends JpaRepository<Personne, Long> {

    // Find by Nom
    List<Personne> findByNom(String nom);

    // Find by Date of Birth after a specific date
    List<Personne> findByDateNaissanceAfter(LocalDateTime date);

    // Find by Date of Birth before a specific date
    List<Personne> findByDateNaissanceBefore(LocalDateTime date);

    // Find by Date of Birth Range
    List<Personne> findByDateNaissanceBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find All Personnes Ordered by Nom (Alphabetical Order)
    List<Personne> findAllByOrderByNomAsc();

    // Find All Personnes Ordered by Nom (Reverse Alphabetical Order)
    List<Personne> findAllByOrderByNomDesc();
}