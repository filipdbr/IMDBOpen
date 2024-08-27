package com.example.demo.service;

import Entities.Business.Personne.Acteur;
import Persistence.Repository.IActeurRepository;
import Web.Model.DTO.ActeurDTO;
import Web.Model.DTO.FilmDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActeurService {

    private final IActeurRepository acteurRepository;

    @Autowired
    public ActeurService(IActeurRepository acteurRepository) {
        this.acteurRepository = acteurRepository;
    }

    // Convert Entity to DTO
    private ActeurDTO convertToDTO(Acteur acteur) {
        return ActeurDTO.fromEntity(acteur);
    }

    // Convert DTO to Entity
    private Acteur convertToEntity(ActeurDTO acteurDTO) {
        return acteurDTO.toEntity();
    }

    // Find by Name and First Name
    public List<ActeurDTO> findByNameAndFirstName(String name, String firstName) {
        List<Acteur> acteurs = acteurRepository.findByNomAndPrenom(name, firstName);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by Name
    public List<ActeurDTO> findByName(String name) {
        List<Acteur> acteurs = acteurRepository.findByNom(name);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by First Name
    public List<ActeurDTO> findByFirstName(String firstName) {
        List<Acteur> acteurs = acteurRepository.findByPrenom(firstName);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by IMDb ID
    public Optional<ActeurDTO> findByImdbId(Long imdbId) {
        Optional<Acteur> acteur = acteurRepository.findByIdImdb(imdbId);
        return acteur.map(this::convertToDTO);
    }

    // Find All by Role Name
    public List<ActeurDTO> findAllByRoleName(String roleName) {
        List<Acteur> acteurs = acteurRepository.findAllByRoleName(roleName);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by Partial Match of Name (Case-insensitive)
    public List<ActeurDTO> findByNameContainingIgnoreCase(String partialName) {
        List<Acteur> acteurs = acteurRepository.findByNomContainingIgnoreCase(partialName);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by Height greater than a specified value
    public List<ActeurDTO> findByHeightGreaterThan(double height) {
        List<Acteur> acteurs = acteurRepository.findByTailleGreaterThan(height);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by Height smaller than a specified value
    public List<ActeurDTO> findByHeightLessThan(double height) {
        List<Acteur> acteurs = acteurRepository.findByTailleLessThan(height);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by Date of Birth after a specific date
    public List<ActeurDTO> findByBirthDateAfter(LocalDateTime date) {
        List<Acteur> acteurs = acteurRepository.findByDateNaissanceAfter(date);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by Date of Birth before a specific date
    public List<ActeurDTO> findByBirthDateBefore(LocalDateTime date) {
        List<Acteur> acteurs = acteurRepository.findByDateNaissanceBefore(date);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find by Date of Birth within a specific range
    public List<ActeurDTO> findByBirthDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Acteur> acteurs = acteurRepository.findByDateNaissanceBetween(startDate, endDate);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find All Acteurs Ordered by Name (Alphabetical Order)
    public List<ActeurDTO> findAllByOrderByNameAsc() {
        List<Acteur> acteurs = acteurRepository.findAllByOrderByNomAsc();
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find All Acteurs Ordered by Name (Reverse Alphabetical Order)
    public List<ActeurDTO> findAllByOrderByNameDesc() {
        List<Acteur> acteurs = acteurRepository.findAllByOrderByNomDesc();
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Count Acteurs by Role Name
    public long countByRoleName(String roleName) {
        return acteurRepository.countByRoleName(roleName);
    }

    // Find by Name or First Name (Case-insensitive search for either field)
    public List<ActeurDTO> findByNameIgnoreCaseOrFirstNameIgnoreCase(String name, String firstName) {
        List<Acteur> acteurs = acteurRepository.findByNomIgnoreCaseOrPrenomIgnoreCase(name, firstName);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find Top 3 Acteurs by Height (e.g., tallest actors)
    public List<ActeurDTO> findTop3ByOrderByHeightDesc() {
        List<Acteur> acteurs = acteurRepository.findTop3ByOrderByTailleDesc();
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find Actors by Place of Birth City
    public List<ActeurDTO> findByPlaceOfBirthCity(String city) {
        List<Acteur> acteurs = acteurRepository.findByLieuNaissanceContainingIgnoreCase(city);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find Actors by Place of Birth Country
    public List<ActeurDTO> findByPlaceOfBirthCountry(String country) {
        List<Acteur> acteurs = acteurRepository.findByLieuNaissanceContainingIgnoreCase(country);
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Find Actors by Film
    public List<ActeurDTO> findByFilm(FilmDTO filmDTO) {
        List<Acteur> acteurs = acteurRepository.findByFilmsContaining(filmDTO.toEntity());
        return acteurs.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
}
