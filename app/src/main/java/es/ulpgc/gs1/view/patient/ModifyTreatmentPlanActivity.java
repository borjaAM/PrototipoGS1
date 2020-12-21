package es.ulpgc.gs1.view.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Injury;
import es.ulpgc.gs1.model.Patient;
import es.ulpgc.gs1.model.TreatmentPlan;

public class ModifyTreatmentPlanActivity extends AppCompatActivity {

    private EditText injuryTypeET, injuryConsequencesET, injuryOriginET, injuryObservationsET, injuryDateET;
    private EditText treatmentET, treatmentDurationET, treatmentObservationsET, treatmentObjectivesET, treatmentAppliedTechniquesET;
    private Button modifyButton, deleteButton, saveButton, cancelButton;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Injury injury;
    private TreatmentPlan treatmentPlan;
    private String patientId;
    private String treatmentPlanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_treatment_plan);

        mAuth = FirebaseAuth.getInstance();
        db  = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        modifyButton = findViewById(R.id.modifyTreatmentPlanButton);
        deleteButton = findViewById(R.id.deleteTreatmentPlanButton);
        saveButton = findViewById(R.id.saveTreatmentPlanButton);
        cancelButton = findViewById(R.id.cancelModifyTreatmentPlanButton);

        injuryTypeET = findViewById(R.id.injuryTypeEditTxt);
        injuryConsequencesET = findViewById(R.id.injuryConsequencesEditTxt);
        injuryOriginET = findViewById(R.id.injuryOriginEditTxt);
        injuryObservationsET = findViewById(R.id.injuryObservationsEditTxt);
        injuryDateET = findViewById(R.id.injuryDateEditTxt);

        treatmentET = findViewById(R.id.treatmentEditTxt);
        treatmentDurationET = findViewById(R.id.treatmentDurationEditText);
        treatmentObservationsET = findViewById(R.id.treatmentObservationsEditTxt);
        treatmentObjectivesET = findViewById(R.id.treatmentObjectivesEditTxt);
        treatmentAppliedTechniquesET = findViewById(R.id.appliedTechniquesEditTxt);

        patientId = getIntent().getStringExtra("patientID");
        treatmentPlanId =  getIntent().getStringExtra("treatmentPlanID");
        readTreatmentPlan();
    }

    private void readTreatmentPlan(){
        DocumentReference docRef = getCollection();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("Datos del documento: " + document.getData());
                        showTreatmentPlanData(document.getData());
                    } else {
                        System.out.println("No existe el documento");
                    }
                } else {
                    System.out.println("Error: " + task.getException());
                }
            }
        });
    }

    private void showTreatmentPlanData(Map<String, Object> map){
        Map injuryMap = (Map) map.get("injury");
        injuryTypeET.setText((String) injuryMap.get("type"));
        injuryConsequencesET.setText((String) injuryMap.get("consequences"));
        injuryOriginET.setText((String) injuryMap.get("origin"));
        injuryObservationsET.setText((String) injuryMap.get("observations"));
        Timestamp instant = (Timestamp) injuryMap.get("date");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(new Locale("es", "ES"));
        gregorianCalendar.setTime(instant.toDate());
        String dia = String.valueOf(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        String mes = String.valueOf(gregorianCalendar.get(Calendar.MONTH) + 1);
        String fecha = dia + "/" +  mes + "/" + gregorianCalendar.get(Calendar.YEAR);
        injuryDateET.setText(fecha);

        treatmentET.setText((String) map.get("treatment"));
        treatmentDurationET.setText((String) map.get("duration"));
        treatmentObservationsET.setText((String) map.get("observations"));
        treatmentObjectivesET.setText((String) map.get("objectives"));
        treatmentAppliedTechniquesET.setText((String) map.get("appliedTechniques"));
    }

    private void modify_treatmentPlan_in_database() {
        DocumentReference docRef = getDocument();
        createNewInjury();
        createNewTreatmentPlan();
        docRef.update("injury", treatmentPlan.getInjury());
        docRef.update("treatment", treatmentPlan.getTreatment());
        docRef.update("duration", treatmentPlan.getDuration());
        docRef.update("observations", treatmentPlan.getObservations());
        docRef.update("objectives", treatmentPlan.getObjectives());
        docRef.update("appliedTechniques", treatmentPlan.getAppliedTechniques());
    }

    private void createNewInjury(){
        String[] fecha = injuryDateET.getText().toString().split("/");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(new Locale("es", "ES"));
        gregorianCalendar.set(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1])-1, Integer.parseInt(fecha[0]));
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date injuryOrigin = gregorianCalendar.getTime();
        injury = new Injury(injuryTypeET.getText().toString(), injuryConsequencesET.getText().toString(), injuryOriginET.getText().toString(),
                injuryObservationsET.getText().toString(), injuryOrigin);
    }

    private void createNewTreatmentPlan() {
        String treatment = treatmentET.getText().toString();
        String treatmentDuration = treatmentDurationET.getText().toString();
        String treatmentObservation = treatmentObservationsET.getText().toString();
        String treatmentObjectives = treatmentObjectivesET.getText().toString();
        String appliedTechniques = treatmentAppliedTechniquesET.getText().toString();
        treatmentPlan = new TreatmentPlan(injury, treatment, treatmentDuration,
                treatmentObservation, treatmentObjectives, appliedTechniques);
    }

    @NotNull
    private DocumentReference getCollection() {
        return getDocument();
    }

    private void updateUI_buttonVisibility(boolean mode) {
        injuryTypeET.setFocusableInTouchMode(mode);
        injuryConsequencesET.setFocusableInTouchMode(mode);
        injuryOriginET.setFocusableInTouchMode(mode);
        injuryObservationsET.setFocusableInTouchMode(mode);
        injuryDateET.setFocusableInTouchMode(mode);
        treatmentET.setFocusableInTouchMode(mode);
        treatmentDurationET.setFocusableInTouchMode(mode);
        treatmentObservationsET.setFocusableInTouchMode(mode);
        treatmentObjectivesET.setFocusableInTouchMode(mode);
        treatmentAppliedTechniquesET.setFocusableInTouchMode(mode);
        if(mode){
            modifyButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            saveButton.setVisibility(View.VISIBLE);
            cancelButton.setVisibility(View.VISIBLE);
        } else{
            modifyButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            saveButton.setVisibility(View.GONE);
            cancelButton.setVisibility(View.GONE);
        }
    }

    public void modifyTreatmentPlan(View view){
        updateUI_buttonVisibility(true);
    }

    public void updateTreatmentPlan(View view){
        modify_treatmentPlan_in_database();
        updateUI_buttonVisibility(false);
    }

    public void cancelModifiedTreatmentPlan(View view){
        readTreatmentPlan();
        updateUI_buttonVisibility(false);
    }

    public void deleteTreatmentPlan(View view){
        getDocument().delete();
        finish();
    }

    @NotNull
    private DocumentReference getDocument() {
        return db.collection("users/" + currentUser.getUid() + "/patients/" + patientId + "/treatmentplans").document(treatmentPlanId);
    }
}