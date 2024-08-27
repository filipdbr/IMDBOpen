package Persistence.Repository;


import Entities.Business.Pays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPaysRepository extends JpaRepository<Pays, Long> {
    Optional<Pays> findByName(String paysName);
}
