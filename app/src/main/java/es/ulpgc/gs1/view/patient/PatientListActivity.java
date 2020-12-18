package es.ulpgc.gs1.view.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Address;
import es.ulpgc.gs1.model.Patient;

public class PatientListActivity extends AppCompatActivity {

    private ListView patientsListView;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ArrayList<String> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        patientsListView = findViewById(R.id.listViewPatientsList);
        patients = new ArrayList<>();

        readPatients();
        ArrayAdapter <String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, patients);
        patientsListView.setAdapter(adapter);
    }

    private void readPatients() {
        Task<QuerySnapshot> docRef = db.collection("users/" + currentUser.getUid() + "/patients").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Patient patient = document.toObject(Patient.class);
                        System.out.println(patient.getName());
                        patients.add(patient.toString());
                    }
                } else {
                    System.out.println(task.getException());
                }
            }
        });
    }
}