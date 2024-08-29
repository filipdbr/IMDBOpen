package Utilities.CSVExtractors;

import Entities.Business.Personne.Personne;
import Entities.Business.Personne.Acteur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActorExtractor {

    public static List<Personne> extractPersonsFromCSV(String filePath) {
        List<Personne> personnes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Personne personne = new Personne();
                String[] identite = values[1].split(" ", 2);
                personne.setPrenom(identite[0]);
                personne.setNom(identite.length > 1 ? identite[1] : "");
                personne.setDateNaissance(LocalDateTime.parse(values[2].trim(), DateTimeFormatter.ofPattern("MMMM d yyyy")));
                personne.setLieuNaissance(values[3]);
                personne.setUrl(values[5]);
                personnes.add(personne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return personnes;
    }

    public static List<Acteur> extractActorsFromCSV(String filePath, List<Personne> personnes) {
        List<Acteur> acteurs = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Acteur acteur = new Acteur();
                acteur.setIdImdb(values[0]);
                acteur.setPersonne(personnes.get(index));
                acteurs.add(acteur);
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return acteurs;
    }
}