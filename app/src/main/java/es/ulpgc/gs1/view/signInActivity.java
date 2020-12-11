package es.ulpgc.gs1.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Professional;

public class signInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText name, username, email, birthdate, password, verifyPassword, idProfessional;
    private RadioButton role;
    private RadioGroup radioGroup;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        name = findViewById(R.id.fullNameEditText);
        username = findViewById(R.id.usernameEditText);
        email = findViewById(R.id.emailEditText);
        birthdate = findViewById(R.id.birthdateEditText);
        password = findViewById(R.id.passwordEditText);
        verifyPassword = findViewById(R.id.verifyPasswordEditText);
        idProfessional = findViewById(R.id.idProfessionalEditText);

    }

    public void onStart(){
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    public void sign_in_user(View view){
        if(password.getText().toString().equals(verifyPassword.getText().toString())){
            mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(getApplicationContext(), "Usuario creado.", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                add_to_database(createProfessional());
                                //updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
        }
    }

    private void add_to_database(Professional professional) {
        // Create a new user with a first and last name
        Map<String, Object> users = new HashMap<>();
        users.put("name", professional.getName());
        users.put("username", professional.getUsername());
        users.put("password", professional.getPassword());
        users.put("email", professional.getEmail());
        users.put("birthdate", professional.getBirthdate());
        users.put("role", professional.getRole());
        users.put("idProfessional", professional.getIdProfessional());

// Add a new document with a generated ID
        db.collection("users")
                .add(users)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Create user", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Create user", "Error adding document", e);
                    }
                });
        go_to_mainMenu();
    }

    private void go_to_mainMenu(){
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
    }

    private Professional createProfessional(){
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        Date birthday = null;
        try {
            birthday = format.parse(birthdate.getText().toString());
        } catch (ParseException e){
            e.printStackTrace();
        }
        return new Professional(name.getText().toString(), username.getText().toString(), password.getText().toString(),
                email.getText().toString(), /*Professional.Role.valueOf(role.toString().replace(" ", ""))*/ Professional.Role.Fisioterapeuta,
                Integer.parseInt(idProfessional.getText().toString()), birthday);
    }
}