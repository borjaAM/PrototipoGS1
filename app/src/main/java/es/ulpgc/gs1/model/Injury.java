package es.ulpgc.gs1.model;

import java.util.Date;

public class Injury {
    private String type, consequences, origin, observations;
    private Date date;

    public Injury(String type, String consequences, String origin, String observations, Date date) {
        this.type = type;
        this.consequences = consequences;
        this.origin = origin;
        this.observations = observations;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConsequences() {
        return consequences;
    }

    public void setConsequences(String consequences) {
        this.consequences = consequences;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
