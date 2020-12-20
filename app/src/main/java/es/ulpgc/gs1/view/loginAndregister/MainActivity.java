package es.ulpgc.gs1.view.loginAndregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import es.ulpgc.gs1.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void go_to_logIn(View view){
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
    }

    public void go_to_signIn(View view){
        Intent i = new Intent(this, signInActivity.class);
        startActivity(i);
    }
}