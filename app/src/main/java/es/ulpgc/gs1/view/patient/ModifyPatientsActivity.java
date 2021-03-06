package es.ulpgc.gs1.view.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import es.ulpgc.gs1.model.Address;
import es.ulpgc.gs1.model.Patient;

public class ModifyPatientsActivity extends AppCompatActivity {

    private EditText nameET, emailET, phoneET, birthdayET, addressET, numberET, zipcodeET, cityET;
    private Button modifyButton, deleteButton, saveButton, cancelButton;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String patientId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_patients);

        nameET = findViewById(R.id.nameEditTxt);
        emailET = findViewById(R.id.treatmentEditTxt);
        phoneET = findViewById(R.id.durationEditText);
        birthdayET = findViewById(R.id.birthdateEditTxt);
        addressET = findViewById(R.id.addressEditTxt);
        numberET = findViewById(R.id.numberEditTxt);
        zipcodeET = findViewById(R.id.zipCodeEditTxt);
        cityET = findViewById(R.id.cityEditTxt);

        modifyButton = findViewById(R.id.modifyPatientButton);
        deleteButton = findViewById(R.id.deletePatientButton);
        saveButton = findViewById(R.id.savePatientButton);
        cancelButton = findViewById(R.id.cancelModifyPatientButton);

        mAuth = FirebaseAuth.getInstance();
        db  = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        patientId = getIntent().getStringExtra("name");
        readPatient();
    }

    private void readPatient() {
        DocumentReference docRef = getCollection(patientId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("Datos del documento: " + document.getData());
                        showPatientData(document.getData());
                    } else {
                        System.out.println("No existe el documento");
                    }
                } else {
                    System.out.println("Error: " + task.getException());
                }
            }
        });
    }

    private void showPatientData(Map<String, Object> map){
        nameET.setText((String) map.get("name"));
        emailET.setText((String) map.get("email"));
        phoneET.setText((String) map.get("telephone"));
        Timestamp instante = (Timestamp) map.get("birthdate");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(new Locale("es", "ES"));
        gregorianCalendar.setTime(instante.toDate());
        String dia = String.valueOf(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        String mes = String.valueOf(gregorianCalendar.get(Calendar.MONTH) + 1);
        String fecha = dia + "/" +  mes + "/" + gregorianCalendar.get(Calendar.YEAR);
        birthdayET.setText(fecha);
        Map<String, Object> address = (Map) map.get("address");
        addressET.setText((String) address.get("street"));
        numberET.setText(String.valueOf(address.get("number")));
        zipcodeET.setText(String.valueOf(address.get("zipcode")));
        cityET.setText((String) address.get("city"));
    }

    public void modifyPatientButton(View view) {
        updateUI_editText(true);
        updateUI_buttonVisibility(true);
    }

    public void saveModifiedPatient(View view){
        modify_patient_in_database();
        updateUI_editText(false);
        updateUI_buttonVisibility(false);
    }

    public void cancelModifiedPatient(View view){
        readPatient();
        updateUI_editText(false);
        updateUI_buttonVisibility(false);
    }

    private void updateUI_editText(boolean mode){
        nameET.setFocusableInTouchMode(mode);
        emailET.setFocusableInTouchMode(mode);
        phoneET.setFocusableInTouchMode(mode);
        birthdayET.setFocusableInTouchMode(mode);
        addressET.setFocusableInTouchMode(mode);
        numberET.setFocusableInTouchMode(mode);
        zipcodeET.setFocusableInTouchMode(mode);
        cityET.setFocusableInTouchMode(mode);
    }

    private void updateUI_buttonVisibility(boolean mode) {
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

    private void modify_patient_in_database() {
        DocumentReference docRef = getCollection(patientId);
        Patient patient = createNewPatient();
        docRef.update("name", patient.getName());
        docRef.update("email", patient.getEmail());
        docRef.update("telephone", patient.getTelephone());
        docRef.update("birthdate", patient.getBirthdate());
        docRef.update("address", patient.getAddress());
    }

    @NotNull
    private Patient createNewPatient() {
        String[] fecha = birthdayET.getText().toString().split("/");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(new Locale("es", "ES"));
        gregorianCalendar.set(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1])-1, Integer.parseInt(fecha[0]));
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        Date birthdate = gregorianCalendar.getTime();

        Address direccion = new Address(addressET.getText().toString(), cityET.getText().toString(),
                Integer.parseInt(numberET.getText().toString()), Integer.parseInt(zipcodeET.getText().toString()));

        return new Patient(nameET.getText().toString(), emailET.getText().toString(),
                phoneET.getText().toString(), birthdate, direccion);
    }

    @NotNull
    private DocumentReference getCollection(String patientPath) {
        return db.collection("users/" + currentUser.getUid() + "/patients").document(patientPath);
    }

    public void deletePatientButton(View view){
        delete_patient_in_database();
        finish();
    }

    private void delete_patient_in_database() {
        getCollection(patientId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Paciente eliminado.", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "No se pudo eliminar el paciente", Toast.LENGTH_SHORT).show();
            }
        });
    }
}