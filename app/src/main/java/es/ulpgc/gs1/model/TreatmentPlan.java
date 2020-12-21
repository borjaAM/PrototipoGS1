package es.ulpgc.gs1.model;

public class TreatmentPlan {
    private Injury injury;
    private String treatment, duration, observations, objectives, appliedTechniques;

    public TreatmentPlan(){}

    public TreatmentPlan(Injury injury, String treatment, String duration, String observations, String objectives, String appliedTechniques) {
        this.injury = injury;
        this.treatment = treatment;
        this.duration = duration;
        this.observations = observations;
        this.objectives = objectives;
        this.appliedTechniques = appliedTechniques;
    }

    public Injury getInjury() {
        return injury;
    }

    public void setInjury(Injury injury) {
        this.injury = injury;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getAppliedTechniques() {
        return appliedTechniques;
    }

    public void setAppliedTechniques(String appliedTechniques) {
        this.appliedTechniques = appliedTechniques;
    }
}
