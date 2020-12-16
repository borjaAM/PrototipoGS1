package es.ulpgc.gs1.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import es.ulpgc.gs1.R;

public class MyProfileActivity extends AppCompatActivity {

    private EditText usernameET, nameET, passwordET, emailET, birthdayET, roleET, idProfessionalET;
    private Button modifyButton, saveButton, cancelButton;
    private FirebaseFirestore db;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = mAuth.getCurrentUser();

        usernameET = findViewById(R.id.usernameEditTxt);
        nameET = findViewById(R.id.nameEditTxt);
        passwordET = findViewById(R.id.passwordEditTxt);
        emailET = findViewById(R.id.emailEditTxt);
        birthdayET = findViewById(R.id.birthdateEditTxt);
        roleET = findViewById(R.id.roleEditTxt);
        idProfessionalET = findViewById(R.id.idProfessionalEditTxt);

        modifyButton = (Button) findViewById(R.id.modifyButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        read_users_information();
    }

    private void read_users_information() {
        DocumentReference docRef = db.collection("users").document(user.getUid());
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
        //birthdayET.setText());
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

    private void updateUI() {
        usernameET.setFocusableInTouchMode(true);
        nameET.setFocusableInTouchMode(true);
        emailET.setFocusableInTouchMode(true);
        passwordET.setFocusableInTouchMode(true);
        saveButton.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.VISIBLE);
        modifyButton.setVisibility(View.INVISIBLE);
    }

    private void restartUI(){
        usernameET.setFocusableInTouchMode(false);
        nameET.setFocusableInTouchMode(false);
        emailET.setFocusableInTouchMode(false);
        passwordET.setFocusableInTouchMode(false);
        saveButton.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);
        modifyButton.setVisibility(View.VISIBLE);
    }

    private void modifyUserDataDatabase() {
        DocumentReference docRef = db.collection("users").document(user.getUid());
        docRef.update("username", usernameET.getText().toString());
        docRef.update("name", nameET.getText().toString());
        docRef.update("email", emailET.getText().toString());
        docRef.update("password", passwordET.getText().toString());
    }

    private void updateFirebaseUser(){
        user.updateEmail(emailET.getText().toString());
        user.updatePassword(passwordET.getText().toString());
    }

}