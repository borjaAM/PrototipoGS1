package es.ulpgc.gs1.view.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Injury;
import es.ulpgc.gs1.model.TreatmentPlan;

public class CreateTreatmentPlanActivity extends AppCompatActivity {

    private EditText injuryTypeET, injuryConsequencesET, injuryOriginET, injuryObservationsET, injuryDateET;
    private EditText treatmentET, treatmentDurationET, treatmentObservationsET, treatmentObjectivesET, treatmentAppliedTechniquesET;
    private Button createTreatmentPlan;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db;
    private Injury injury;
    private TreatmentPlan treatmentPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_treatment_plan);

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

        createTreatmentPlan = findViewById(R.id.createTreatmentPlanButton);

        mAuth = FirebaseAuth.getInstance();
        db  = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void register_TreatmentPlan_in_database(View view){
        createInjury();
        createTreatmentPlan();
        addTreatmentPlanDatabase();
        finish();
    }

    private void createInjury(){
        String[] fecha = injuryDateET.getText().toString().split("/");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(new Locale("es", "ES"));
        gregorianCalendar.set(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1])-1, Integer.parseInt(fecha[0]));
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date injuryOrigin = gregorianCalendar.getTime();
        injury = new Injury(injuryTypeET.getText().toString(), injuryConsequencesET.getText().toString(), injuryOriginET.getText().toString(),
                injuryObservationsET.getText().toString(), injuryOrigin);
    }

    private void createTreatmentPlan(){
        String treatment = treatmentET.getText().toString();
        String treatmentDuration = treatmentDurationET.getText().toString();
        String treatmentObservation = treatmentObservationsET.getText().toString();
        String treatmentObjectives = treatmentObjectivesET.getText().toString();
        String appliedTechniques = treatmentAppliedTechniquesET.getText().toString();
        treatmentPlan = new TreatmentPlan(injury, treatment, treatmentDuration,
                treatmentObservation, treatmentObjectives, appliedTechniques);
    }

    private void addTreatmentPlanDatabase() {
        // Add a new document with a generated ID
        db.collection("users/"+currentUser.getUid()+"/patients/" + getIntent().getStringExtra("name") + "/treatmentplans").document().set(treatmentPlan)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Plan de tratamiento añadido.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "No se ha añadido el plan de tratamiento.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}