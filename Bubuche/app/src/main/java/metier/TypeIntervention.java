package metier;

/**
 * Created by fabien.ladouce on 08/02/2018.
 */

public class TypeIntervention {

    private int id;
    private String libelle;

    public TypeIntervention() {
    }

    public TypeIntervention(int id, String libelle) {
        this.id = id;
        this.libelle = libelle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
