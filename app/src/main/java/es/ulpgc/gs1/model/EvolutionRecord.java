package es.ulpgc.gs1.model;

public class EvolutionRecord {
    private String improvements, restrictions, recommendations, observations;

    public EvolutionRecord(String improvements, String restrictions, String recommendations, String observations) {
        this.improvements = improvements;
        this.restrictions = restrictions;
        this.recommendations = recommendations;
        this.observations = observations;
    }

    public String getImprovements() {
        return improvements;
    }

    public void setImprovements(String improvements) {
        this.improvements = improvements;
    }

    public String getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(String restrictions) {
        this.restrictions = restrictions;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}
