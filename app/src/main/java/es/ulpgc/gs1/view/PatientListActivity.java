package es.ulpgc.gs1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Address;
import es.ulpgc.gs1.model.Patient;

public class PatientListActivity extends AppCompatActivity {

    private ListView patientsListView;

    private ArrayList<Patient> patients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        patientsListView = findViewById(R.id.listViewPatientsList);

        patients = new ArrayList<>();
        patients.add(new Patient("martin matin", "martin@gmail.es", "958696867",
                null, new Address("triana", "las palmas", 35, 35002)));
        patients.add(new Patient("marina matina", "marinita77@gmail.es", "958696023",
                null, new Address("san telmo", "las palmas", 55, 24562)));

        ArrayAdapter <Patient> adapter = new ArrayAdapter<Patient>(this, android.R.layout.simple_list_item_1, patients);

        patientsListView.setAdapter(adapter);
    }
}