package es.ulpgc.gs1.model;

public class TreatmentPlan {
    private String plan = "";
    public TreatmentPlan(String plan){
        this.plan = plan;

    }
    @Override
    public String toString(){
        return plan;
    }
}
