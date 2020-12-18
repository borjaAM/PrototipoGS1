package es.ulpgc.gs1.model;
import java.util.Date;
import java.util.GregorianCalendar;
    public class Session {
        private Date date;
        private TreatmentPlan treatment;
    public Session (Date date, TreatmentPlan treatment){
        this.date = date;
        this.treatment = treatment;
    }

        public Date getDate() {
            return date;
        }
        public TreatmentPlan getTreatment() {
            return treatment;
        }
        @Override
        public String toString(){

        return "Date: " + date.toString()+ " Treatment: "+ treatment.toString();
        }
    }
