package es.ulpgc.gs1.view.userView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.view.loginAndregister.MainActivity;

public class MyProfileActivity extends AppCompatActivity {

    private EditText usernameET, nameET, passwordET, emailET, birthdayET, roleET, idProfessionalET;
    private Button modifyButton, saveButton, cancelButton, deleteButton;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        usernameET = findViewById(R.id.usernameEditTxt);
        nameET = findViewById(R.id.nameEditTxt);
        passwordET = findViewById(R.id.passwordEditTxt);
        emailET = findViewById(R.id.emailEditTxt);
        birthdayET = findViewById(R.id.birthdateEditTxt);
        roleET = findViewById(R.id.roleEditTxt);
        idProfessionalET = findViewById(R.id.idProfessionalEditTxt);

        modifyButton = findViewById(R.id.modifyProfileButton);
        saveButton = findViewById(R.id.saveProfileButton);
        cancelButton = findViewById(R.id.cancelProfileButton);
        deleteButton = findViewById(R.id.deleteProfileButton);

        read_users_information();
    }

    private void read_users_information() {
        DocumentReference docRef = getUsers();
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        System.out.println("Datos del documento: " + document.getData());
                        showProfileDataToUser(document.getData());
                    } else {
                        System.out.println("No existe el documento");
                    }
                } else {
                    System.out.println("Error: " + task.getException());
                }
            }
        });
    }

    private void showProfileDataToUser(Map<String, Object> map){
        usernameET.setText((String) map.get("username"));
        nameET.setText((String) map.get("name"));
        passwordET.setText((String) map.get("password"));
        emailET.setText((String) map.get("email"));
        roleET.setText((String) map.get("role"));
        idProfessionalET.setText(String.valueOf(map.get("idProfessional")));
        Timestamp instante = (Timestamp) map.get("birthdate");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(new Locale("es", "ES"));
        gregorianCalendar.setTime(instante.toDate());
        String dia = String.valueOf(gregorianCalendar.get(Calendar.DAY_OF_MONTH));
        String mes = String.valueOf(gregorianCalendar.get(Calendar.MONTH) + 1);
        String fecha = dia + "/" +  mes + "/" + gregorianCalendar.get(Calendar.YEAR);
        birthdayET.setText(fecha);
    }

    public void modifyUserData(View view){
        updateUI();
    }

    public void saveModifiedUserData(View view){
        if(passwordET.getText().toString().length() >= 6){
            updateFirebaseUser();
            modifyUserDataDatabase();
            read_users_information();
            restartUI();
        } else {
            Toast.makeText(this, "La contraseña debe tener 6 o más caracteres", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelDataModification(View view){
        read_users_information();
        restartUI();
    }

    public void deleteUserProfile(View view){
        getUsers().delete();
        currentUser.delete();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void updateUI() {
        usernameET.setFocusableInTouchMode(true);
        nameET.setFocusableInTouchMode(true);
        emailET.setFocusableInTouchMode(true);
        passwordET.setFocusableInTouchMode(true);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
        modifyButton.setVisibility(View.INVISIBLE);
    }

    private void restartUI(){
        usernameET.setFocusableInTouchMode(false);
        nameET.setFocusableInTouchMode(false);
        emailET.setFocusableInTouchMode(false);
        passwordET.setFocusableInTouchMode(false);
        saveButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        modifyButton.setVisibility(View.VISIBLE);
    }

    private void modifyUserDataDatabase() {
        DocumentReference docRef = getUsers();
        docRef.update("username", usernameET.getText().toString());
        docRef.update("name", nameET.getText().toString());
        docRef.update("email", emailET.getText().toString());
        docRef.update("password", passwordET.getText().toString());
    }

    @NotNull
    private DocumentReference getUsers() {
        return db.collection("users").document(currentUser.getUid());
    }

    private void updateFirebaseUser(){
        currentUser.updateEmail(emailET.getText().toString());
        currentUser.updatePassword(passwordET.getText().toString());
    }

}