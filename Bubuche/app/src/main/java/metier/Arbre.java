package metier;

public class Arbre {

    private int id;
    private String libelleFrancais;
    private String datePlantation;
    private String commune;
    private String cp;
    private String espece;
    private String genre;

    public Arbre() {
    }

    public Arbre(int id, String libelleFrancais, String datePlantation, String commune, String cp, String espece, String genre) {
        this.setId(id);
        this.setLibelleFrancais(libelleFrancais);
        this.setDatePlantation(datePlantation);
        this.setCommune(commune);
        this.setCp(cp);
        this.setEspece(espece);
        this.setGenre(genre);
    }

    public Arbre(String libelleFrancais, String datePlantation, String commune, String cp, String espece, String genre) {
        this.setLibelleFrancais(libelleFrancais);
        this.setDatePlantation(datePlantation);
        this.setCommune(commune);
        this.setCp(cp);
        this.setEspece(espece);
        this.setGenre(genre);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelleFrancais() {
        return libelleFrancais;
    }

    public void setLibelleFrancais(String libelleFrancais) {
        this.libelleFrancais = libelleFrancais;
    }

    public String getDatePlantation() {
        return datePlantation;
    }

    public void setDatePlantation(String datePlantation) {
        this.datePlantation = datePlantation;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Arbre{" +
                "id=" + id +
                ", libelleFrancais='" + libelleFrancais + '\'' +
                ", datePlantation='" + datePlantation + '\'' +
                ", commune='" + commune + '\'' +
                ", cp='" + cp + '\'' +
                ", espece='" + espece + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}

