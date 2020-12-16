package es.ulpgc.gs1.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Address;
import es.ulpgc.gs1.model.Patient;

public class ManagePatientsActivity extends AppCompatActivity {

    private EditText nameET, emailET, phoneET, birthdayET, addressET, numberET, zipcodeET, cityET;
    private FirebaseFirestore db;
    private Patient patient;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_patients);
        nameET = findViewById(R.id.nameEditTxt);
        emailET = findViewById(R.id.emailEditTxt);
        phoneET = findViewById(R.id.phoneEditText);
        birthdayET = findViewById(R.id.birthdateEditTxt);
        addressET = findViewById(R.id.addressEditTxt);
        numberET = findViewById(R.id.numberEditTxt);
        zipcodeET = findViewById(R.id.zipCodeEditTxt);
        cityET = findViewById(R.id.cityEditTxt);

        mAuth = FirebaseAuth.getInstance();
        db  = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

    }

    public void createPatientButton(View view){
        createPatient();
        addPatientDatabase();
        readPatient();
    }

    private void createPatient(){
        // excepciones integer parse int cuando esta el campo nulo
        Address address = new Address(addressET.getText().toString(), cityET.getText().toString(),
                Integer.parseInt(numberET.getText().toString()), Integer.parseInt(zipcodeET.getText().toString()));
        patient = new Patient(nameET.getText().toString(), emailET.getText().toString(),
                phoneET.getText().toString(), null/*(Date) birthdayET.getText()*/, address);
    }

    private void addPatientDatabase() {
        // Add a new document with a generated ID
        db.collection("users/"+currentUser.getUid()+"/patients").document(patient.getName()).set(patient)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Se ha creado correctamente el paciente.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "No se ha podido crear el paciente.", Toast.LENGTH_SHORT).show();
                    }
                });
        finish();
    }

    private void readPatient(){
        DocumentReference docRef = db.collection("users/"+currentUser.getUid()+"/patients").document(patient.getName());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Patient patient2 = documentSnapshot.toObject(Patient.class);
                System.out.println(patient2.toString());
            }
        });
    }
}