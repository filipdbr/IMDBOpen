package com.example.demo.service;

import Entities.Business.Personne.Acteur;
import Persistence.Repository.IActeurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActeurService {

    private final IActeurRepository acteurRepository;

    @Autowired
    public ActeurService(IActeurRepository acteurRepository) {
        this.acteurRepository = acteurRepository;
    }

    // Find by Name and First Name
    public List<Acteur> findByNameAndFirstName(String name, String firstName) {
        return acteurRepository.findByNomAndPrenom(name, firstName);
    }

    // Find by Name
    public List<Acteur> findByName(String name) {
        return acteurRepository.findByNom(name);
    }

    // Find by First Name
    public List<Acteur> findByFirstName(String firstName) {
        return acteurRepository.findByPrenom(firstName);
    }

    // Find by IMDb ID
    public Optional<Acteur> findByImdbId(Long imdbId) {
        return acteurRepository.findByIdImdb(imdbId);
    }

    // Find All by Role Name
    public List<Acteur> findAllByRoleName(String roleName) {
        return acteurRepository.findAllByRoleName(roleName);
    }

    // Find by Partial Match of Name (Case-insensitive)
    public List<Acteur> findByNameContainingIgnoreCase(String partialName) {
        return acteurRepository.findByNomContainingIgnoreCase(partialName);
    }

    // Find by Height greater than a specified value
    public List<Acteur> findByHeightGreaterThan(double height) {
        return acteurRepository.findByTailleGreaterThan(height);
    }

    // Find by Height smaller than a specified value
    public List<Acteur> findByHeightLessThan(double height) {
        return acteurRepository.findByTailleLessThan(height);
    }

    // Find by Date of Birth after a specific date
    public List<Acteur> findByBirthDateAfter(LocalDateTime date) {
        return acteurRepository.findByDateNaissanceAfter(date);
    }

    // Find by Date of Birth before a specific date
    public List<Acteur> findByBirthDateBefore(LocalDateTime date) {
        return acteurRepository.findByDateNaissanceBefore(date);
    }

    // Find by Date of Birth within a specific range
    public List<Acteur> findByBirthDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return acteurRepository.findByDateNaissanceBetween(startDate, endDate);
    }

    // Find by CreatedDate before a specific date
    public List<Acteur> findByCreatedDateBefore(LocalDateTime date) {
        return acteurRepository.findByCreatedDateBefore(date);
    }

    // Find by CreatedDate after a specific date
    public List<Acteur> findByCreatedDateAfter(LocalDateTime date) {
        return acteurRepository.findByCreatedDateAfter(date);
    }

    // Find by CreatedDate within a specific range
    public List<Acteur> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return acteurRepository.findByCreatedDateBetween(startDate, endDate);
    }

    // Find All Acteurs Ordered by Name (Alphabetical Order)
    public List<Acteur> findAllByOrderByNameAsc() {
        return acteurRepository.findAllByOrderByNomAsc();
    }

    // Find All Acteurs Ordered by Name (Reverse Alphabetical Order)
    public List<Acteur> findAllByOrderByNameDesc() {
        return acteurRepository.findAllByOrderByNomDesc();
    }

    // Count Acteurs by Role Name
    public long countByRoleName(String roleName) {
        return acteurRepository.countByRoleName(roleName);
    }

    // Find by Name or First Name (Case-insensitive search for either field)
    public List<Acteur> findByNameIgnoreCaseOrFirstNameIgnoreCase(String name, String firstName) {
        return acteurRepository.findByNomIgnoreCaseOrPrenomIgnoreCase(name, firstName);
    }

    // Find Top 3 Acteurs by Height (e.g., tallest actors)
    public List<Acteur> findTop3ByOrderByHeightDesc() {
        return acteurRepository.findTop3ByOrderByTailleDesc();
    }

    /* todo after we have the remaining classes we can add:
        top N by Rating
        actors by Country
        actors by City
        actors with most collaborations with specific directors
        actors by age range
        most frequent actor collaborations in films
        actors by genre specialization
     */

}
