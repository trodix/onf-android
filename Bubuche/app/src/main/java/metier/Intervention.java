package metier;

/**
 * Created by aurelien.thazet on 08/02/2018.
 */

public class Intervention {

    private int id;
    private int idArbre;
    private String dateIntervention;
    private String heureIntervention;
    private String observations;

    public Intervention() {
    }

    public Intervention( int idArbre,String dateIntervention, String heureIntervention, String observations) {
        this.dateIntervention = dateIntervention;
        this.heureIntervention = heureIntervention;
        this.observations = observations;
    }

    public Intervention(int id, int idArbre,String dateIntervention, String heureIntervention, String observations) {
        this.id = id;
        this.idArbre = idArbre;
        this.dateIntervention = dateIntervention;
        this.heureIntervention = heureIntervention;
        this.observations = observations;
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
                ", dateIntervention='" + dateIntervention + '\'' +
                ", heureIntervention='" + heureIntervention + '\'' +
                ", observations='" + observations + '\'' +
                '}';
    }
}
