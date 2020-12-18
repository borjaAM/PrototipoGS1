package es.ulpgc.gs1.view.loginAndregister;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.view.userView.MainMenuActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.emailLogInEditText);
        password = findViewById(R.id.passwordLogInEditText);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void go_to_mainMenu(){
        Intent i = new Intent(this, MainMenuActivity.class);
        startActivity(i);
        finish();
    }

    public void logIn(View view){
        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            go_to_mainMenu();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}