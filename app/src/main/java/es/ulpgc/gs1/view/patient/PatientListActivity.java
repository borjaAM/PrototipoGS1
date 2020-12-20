package es.ulpgc.gs1.view.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Patient;
import es.ulpgc.gs1.view.MyPatientAdapter;

public class PatientListActivity extends AppCompatActivity{

    private RecyclerView patientsRecyclerView;
    private MyPatientAdapter myPatientAdapter;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        patientsRecyclerView = findViewById(R.id.patientRecyclerView);
        patientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Query query = db.collection("users/" + currentUser.getUid() + "/patients").orderBy("name");

        FirestoreRecyclerOptions<Patient> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Patient>().setQuery(query, Patient.class).build();

        myPatientAdapter = new MyPatientAdapter(firestoreRecyclerOptions, this);
        myPatientAdapter.notifyDataSetChanged();
        patientsRecyclerView.setAdapter(myPatientAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        myPatientAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myPatientAdapter.stopListening();
    }

    public void go_to_createPatients(View view){
        Intent i = new Intent(this, CreatePatientsActivity.class);
        startActivity(i);
    }
}