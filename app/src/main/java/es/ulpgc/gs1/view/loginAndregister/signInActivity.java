package es.ulpgc.gs1.view.loginAndregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Professional;
import es.ulpgc.gs1.model.Role;
import es.ulpgc.gs1.view.userView.MainMenuActivity;

public class signInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText nameET, usernameET, emailET, birthdayET, passwordET, verifyPasswordET, idProfessionalET;
    private String name, username, email, password, verifyPassword;
    private int idProfessional;
    private Role role;
    private Date birthday;
    private RadioButton rbFisioterapeuta, rbTerapeutaOcupacional, rbLogopeda;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nameET = findViewById(R.id.fullNameEditText);
        usernameET = findViewById(R.id.usernameEditText);
        emailET = findViewById(R.id.emailEditText);
        birthdayET = findViewById(R.id.birthdateEditText);
        passwordET = findViewById(R.id.passwordEditText);
        verifyPasswordET = findViewById(R.id.verifyPasswordEditText);
        idProfessionalET = findViewById(R.id.idProfessionalEditText);

        rbFisioterapeuta = findViewById(R.id.rbFisioterapeuta);
        rbTerapeutaOcupacional = findViewById(R.id.rbOcupacional);
        rbLogopeda = findViewById(R.id.rbLogopeda);
    }

    public void onStart(){
        super.onStart();
        currentUser = mAuth.getCurrentUser(); // Check if user is signed in (non-null) and update UI accordingly.
    }

    public void sign_in_user(View view){
        obtainEditTextValues();
        obtainRadioButtonValues();
        obtainDateValue();
        if(password.length() >= 6){
            if(password.equals(verifyPassword)){
                registerUser();
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getApplicationContext(), "Usuario creado.", Toast.LENGTH_SHORT).show();
                        currentUser = mAuth.getCurrentUser();
                        add_to_database(createProfessional());
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(), "Fallo al registrarse.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }

    private void obtainEditTextValues(){
        name = nameET.getText().toString();
        username = usernameET.getText().toString();
        password = passwordET.getText().toString();
        verifyPassword = verifyPasswordET.getText().toString();
        email = emailET.getText().toString();
        idProfessional = Integer.parseInt(idProfessionalET.getText().toString());
    }

    private void obtainDateValue() {
        String[] fecha = birthdayET.getText().toString().split("/");
        GregorianCalendar gregorianCalendar = new GregorianCalendar(new Locale("es", "ES"));
        gregorianCalendar.set(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1])-1, Integer.parseInt(fecha[0]));
        gregorianCalendar.setTimeZone(TimeZone.getTimeZone("Europe/Madrid"));
        birthday = gregorianCalendar.getTime();
    }

    private void obtainRadioButtonValues(){
        if (rbFisioterapeuta.isChecked()){
            role = Role.Fisioterapeuta;
        } else if (rbTerapeutaOcupacional.isChecked()){
            role = Role.TerapeutaOcupacional;
        } else if (rbLogopeda.isChecked()){
            role = Role.Logopeda;
        } else {
            Toast.makeText(this, "No ha indicado su profesión.", Toast.LENGTH_SHORT).show();
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
        System.out.println("Display name: " + currentUser.getDisplayName());
        db.collection("users").document(currentUser.getUid()).set(users);
        go_to_mainMenu();
    }

    private void go_to_mainMenu(){
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
        finish();
    }

    private Professional createProfessional(){
        return new Professional(name, username, password, email, role, idProfessional, birthday);
    }
}