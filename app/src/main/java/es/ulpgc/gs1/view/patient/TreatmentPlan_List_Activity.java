package es.ulpgc.gs1.view.patient;

import androidx.appcompat.app.AppCompatActivity;
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
import es.ulpgc.gs1.model.TreatmentPlan;

public class TreatmentPlan_List_Activity extends AppCompatActivity {

    private RecyclerView recyclerViewTreatmentPlan;
    private MyTreatmentPlanAdapter mAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment_plan_list);

        recyclerViewTreatmentPlan = findViewById(R.id.treatmentPlanRecyclerView);
        recyclerViewTreatmentPlan.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        patientID = getIntent().getStringExtra("patientName");
        Query query = db.collection("users/" + currentUser.getUid() + "/patients/" + patientID + "/treatmentplans");

        FirestoreRecyclerOptions<TreatmentPlan> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<TreatmentPlan>()
                .setQuery(query, TreatmentPlan.class).build();

        mAdapter = new MyTreatmentPlanAdapter(firestoreRecyclerOptions, this, patientID);
        mAdapter.notifyDataSetChanged();
        recyclerViewTreatmentPlan.setAdapter(mAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }

    public void go_to_createTreatmentPlan(View view){
        Intent i = new Intent(this, CreateTreatmentPlanActivity.class);
        i.putExtra("patientID", patientID);
        startActivity(i);
    }
}