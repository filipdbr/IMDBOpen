package Entities.Business.Personne;

import Entities.Generic.IEntity;

import java.io.Serializable;
import java.time.LocalDate;

public interface IPersonne <ID extends Serializable> extends IEntity<ID> {
    String getNom();
    void setNom(String nom);
    String getPrenom();
    void setPrenom(String prenom);
    LocalDate getDateNaissance();
    void setDateNaissance(LocalDate dateNaissance);
}
