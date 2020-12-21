package es.ulpgc.gs1.view.patient;

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
import es.ulpgc.gs1.model.Injury;
import es.ulpgc.gs1.model.TreatmentPlan;

public class MyTreatmentPlanAdapter extends FirestoreRecyclerAdapter<TreatmentPlan, MyTreatmentPlanAdapter.ViewHolder> {

    Activity activity;
    private String patientID;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyTreatmentPlanAdapter(@NonNull FirestoreRecyclerOptions<TreatmentPlan> options, Activity activity, String patientID) {
        super(options);
        this.activity = activity;
        this.patientID = patientID;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull TreatmentPlan treatmentPlan) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getBindingAdapterPosition());
        String id = documentSnapshot.getId();
        Injury injury = treatmentPlan.getInjury();
        holder.txtViewTreatmentPlanTitle.setText("Tipo: " + injury.getType() + ", Tratamiento:" + treatmentPlan.getTreatment());
        holder.txtViewTreatmentPlanDate.setText(injury.getDate().toString());
        holder.viewTreatmentPlanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, ModifyTreatmentPlanActivity.class);
                i.putExtra("patientID", patientID);
                i.putExtra("treatmentPlanID", id);
                activity.startActivity(i);
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patients_treatmentplan_row, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtViewTreatmentPlanTitle;
        TextView txtViewTreatmentPlanDate;
        Button viewTreatmentPlanButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTreatmentPlanTitle = itemView.findViewById(R.id.treatmentPlanTitleTxtView);
            txtViewTreatmentPlanDate = itemView.findViewById(R.id.treatmentPlanDateTxtView);
            viewTreatmentPlanButton = itemView.findViewById(R.id.viewTreatmentPlanButton);
        }
    }
}
