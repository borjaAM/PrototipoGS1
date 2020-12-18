package es.ulpgc.gs1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import es.ulpgc.gs1.R;

public class MainMenuActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        mAuth = FirebaseAuth.getInstance();
    }

    public void go_to_managePatients(View view){
        Intent i = new Intent(this, ManagePatientsActivity.class);
        startActivity(i);
    }

    public void go_to_patientsLists(View view){
        Intent i = new Intent(this, PatientListActivity.class);
        startActivity(i);
    }

    public void go_to_my_profile(View view){
        Intent i = new Intent(this, MyProfileActivity.class);
        startActivity(i);
    }
    public void go_to_calendar(View view){
        Intent i = new Intent(this, Calendar.class);
        startActivity(i);
    }

    public void signOut(View view){
        mAuth.signOut();
        Intent i = new Intent(this, MainActivity.class);
        Toast.makeText(this, "Se ha cerrado correctamente su sesión.", Toast.LENGTH_SHORT).show();
        startActivity(i);
        finish();
    }
}