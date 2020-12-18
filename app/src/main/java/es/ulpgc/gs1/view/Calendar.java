package es.ulpgc.gs1.view;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Adapter;
import es.ulpgc.gs1.model.Session;
import es.ulpgc.gs1.model.TreatmentPlan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class Calendar extends AppCompatActivity {
    private Button button;
    private ImageButton button2;
    private CalendarView calendar;
    private RecyclerView recycler;
    private GregorianCalendar cal;
    List <Session> lista = new ArrayList<Session>();
    private Adapter adaptador;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        recycler = (RecyclerView) findViewById(R.id.recicler1);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        cal = new GregorianCalendar();
        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;



        calendar = (CalendarView) findViewById(R.id.calendarView4);
        button =(Button) findViewById(R.id.AddSessionButton);
        button2 =  findViewById(R.id.imageButton);

        calendar.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                cal = new GregorianCalendar( year, month, dayOfMonth );
            }//met
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CharSequence text = "se crea";
                //Toast toast = Toast.makeText(context, text, duration);
                //toast.show();



                Date b =  cal.getTime();
                lista.add(new Session(b, new TreatmentPlan("plandetratamiento")));
                adaptador = new Adapter(lista);
                recycler.setAdapter(adaptador);

            }

        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence text = "se visualiza";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });




    }
}