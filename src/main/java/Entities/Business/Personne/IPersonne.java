package Entities.Business.Personne;

import Entities.Generic.IEntity;

import java.io.Serializable;
import java.time.LocalDate;

public interface IPersonne <ID extends Serializable> extends IEntity<ID> {
    String getIdentite();
    void setIdentite(String nom);


    String getDateNaissance();
    void setDateNaissance(String dateNaissance);
}
