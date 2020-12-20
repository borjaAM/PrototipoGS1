package es.ulpgc.gs1.view;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Patient;
import es.ulpgc.gs1.view.patient.ModifyPatientsActivity;

public class MyPatientAdapter extends FirestoreRecyclerAdapter<Patient, MyPatientAdapter.ViewHolder> {

    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyPatientAdapter(@NonNull FirestoreRecyclerOptions<Patient> options, Activity activity) {
        super(options);
        this.activity = activity;
    }

    // establece los valores de cada elemento de la lista
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Patient patient) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getBindingAdapterPosition());
        String id = documentSnapshot.getId();
        holder.textViewNombre.setText(patient.getName());
        holder.textViewEmail.setText(patient.getEmail());
        holder.textViewPhone.setText(patient.getTelephone());

        holder.viewPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ModifyPatientsActivity.class);
                i.putExtra("name", id);
                activity.startActivity(i);
            }
        });
    }

    // nos crea cada una de las vistas que necesitamos mostrar en la pantalla
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patients_row, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewNombre;
        private TextView textViewEmail;
        private TextView textViewPhone;
        private Button viewPatientButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.txtViewNamePatient);
            textViewEmail = itemView.findViewById(R.id.txtViewEmailPatient);
            textViewPhone = itemView.findViewById(R.id.txtViewPhonePatient);
            viewPatientButton = itemView.findViewById(R.id.viewPatientButton);
        }

    }
}