package es.ulpgc.gs1.model;


import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.ulpgc.gs1.R;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolderItem> {

    private List<Session> datos;

    public Adapter (List datos){
        this.datos = datos;
    }

    @NonNull
    @Override
    public Adapter.ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewer_1, null, false);
        //view.setBackgroundColor(0xFF488A8A);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolderItem holder, int position) {
        holder.assignData(datos.get(position));

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder{
        TextView dato;
        TextView dato2;
        View vista;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            dato = (TextView) itemView.findViewById(R.id.textView);
            dato2 = (TextView) itemView.findViewById(R.id.textView2);
            //vista = (View) itemView.findViewById(R.id.view3);
            //vista.setBackgroundColor(0xFF488A8A);

            //dato.setBackgroundColor(0xd65b85);
        }

        public void assignData(Session session) {
            String a = session.getDate().toString();
            String b = session.getTreatment().toString();
            dato.setText(a);
            dato2.setText(b);
        }
    }
}
