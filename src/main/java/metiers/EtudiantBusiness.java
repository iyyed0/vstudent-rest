package metiers;

import entities.Option;
import entities.Etudiant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EtudiantBusiness {
    private static final List<Etudiant> etudiants = new ArrayList<Etudiant>();
    private final OptionBusiness optionBusiness = new OptionBusiness();

    static {
        OptionBusiness seed = new OptionBusiness();
        etudiants.add(new Etudiant("I001", "Doe", "Jean", seed.getOptionByCode(1), 2023, "jean.doe@example.com"));
        etudiants.add(new Etudiant("I002", "Smith", "Alice", seed.getOptionByCode(1), 2022, "alice.smith@example.com"));
        etudiants.add(new Etudiant("I003", "Durand", "Pierre", seed.getOptionByCode(2), 2023, "pierre.durand@example.com"));
    }

    public EtudiantBusiness() {
    }

    // Ajouter un etudiant
    public boolean addEtudiant(Etudiant etudiant) {
        int codeOption = etudiant.getOption().getCodeOption();
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option != null) {
            etudiant.setOption(option);
            return etudiants.add(etudiant);
        }
        return false;
    }

    // Récupérer un etudiant par son identifiant
    public Etudiant getEtudiantByIdentifiant(String identifiant) {
        for (Etudiant e : etudiants) {
            if (e.getIdentifiant().equals(identifiant)) {
                return e;
            }
        }
        return null;
    }

    // Récupérer les etudiants par option
    public List<Etudiant> getEtudiantsByOption(Option option) {
        if (option == null) {
            return new ArrayList<>();
        }
        return getEtudiantsByOption(option.getCodeOption());
    }

    // Récupérer les etudiants par code d'option
    public List<Etudiant> getEtudiantsByOption(int codeOption) {
        List<Etudiant> result = new ArrayList<>();
        for (Etudiant e : etudiants) {
            if (e.getOption() != null && e.getOption().getCodeOption() == codeOption) {
                result.add(e);
            }
        }
        return result;
    }

    // Récupérer tous les etudiants
    public List<Etudiant> getAllEtudiants() {
        return etudiants;
    }

    // Mettre à jour un etudiant
    public boolean updateEtudiant(String identifiant, Etudiant updatedEtudiant) {
        for (int i = 0; i < etudiants.size(); i++) {
            if (etudiants.get(i).getIdentifiant().equals(identifiant)) {
                etudiants.set(i, updatedEtudiant);
                return true;
            }
        }
        return false;
    }

    // Supprimer un etudiant
    public boolean deleteEtudiant(String identifiant) {
        Iterator<Etudiant> iterator = etudiants.iterator();
        while (iterator.hasNext()) {
            Etudiant e = iterator.next();
            if (e.getIdentifiant().equals(identifiant)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }
}