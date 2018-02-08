package metier;

/**
 * Created by aurelien.thazet on 08/02/2018.
 */

public class Intervention {

    private int id;
    private int idArbre;
    private String typeIntervention;
    private int idType;
    private String dateIntervention;
    private String heureIntervention;
    private String observations;

    public Intervention() {
    }

    public Intervention(int idArbre,String typeIntervention/*int idType*/, String dateIntervention, String heureIntervention, String observations) {
        this.dateIntervention = dateIntervention;
        this.typeIntervention = typeIntervention;
        //this.idType = idType;
        this.heureIntervention = heureIntervention;
        this.observations = observations;
    }

    public Intervention(int id, int idArbre,String typeIntervention, int idType, String dateIntervention, String heureIntervention, String observations) {
        this.id = id;
        this.idArbre = idArbre;
        this.typeIntervention = typeIntervention;
        this.idType = idType;
        this.dateIntervention = dateIntervention;
        this.heureIntervention = heureIntervention;
        this.observations = observations;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public int getId() {
        return id;
    }

    public int getIdArbre() {
        return idArbre;
    }

    public void setIdArbre(int idArbre) {
        this.idArbre = idArbre;
    }

    public String getTypeIntervention() {
        return typeIntervention;
    }

    public void setTypeIntervention(String typeIntervention) {
        this.typeIntervention = typeIntervention;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateIntervention() {
        return dateIntervention;
    }

    public void setDateIntervention(String dateIntervention) {
        this.dateIntervention = dateIntervention;
    }

    public String getHeureIntervention() {
        return heureIntervention;
    }

    public void setHeureIntervention(String heureIntervention) {
        this.heureIntervention = heureIntervention;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "Intervention{" +
                "id=" + id +
                ", idArbre=" + idArbre +
                ", typeIntervention='" + typeIntervention + '\'' +
                ", idType=" + idType +
                ", dateIntervention='" + dateIntervention + '\'' +
                ", heureIntervention='" + heureIntervention + '\'' +
                ", observations='" + observations + '\'' +
                '}';
    }
}
