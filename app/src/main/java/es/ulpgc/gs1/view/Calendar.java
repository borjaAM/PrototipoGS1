package es.ulpgc.gs1.view;

import es.ulpgc.gs1.R;
import es.ulpgc.gs1.model.Adapter;
import es.ulpgc.gs1.model.Session;
import es.ulpgc.gs1.model.TreatmentPlan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;


public class Calendar extends AppCompatActivity {
    private Button button;
    private ImageButton button2;
    private CalendarView calendar;
    private RecyclerView recycler;
    private GregorianCalendar cal;
    List <Session> lista = new ArrayList<Session>();
    private Adapter adaptador;
    private String m_Text = "";
    private AlertDialog.Builder builder;
    private boolean allowed;
    private Context context;
    private Context context2;
    int duration;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        recycler = (RecyclerView) findViewById(R.id.recicler1);
        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        cal = new GregorianCalendar();
        context = getApplicationContext();
        context2 = this;

        duration = Toast.LENGTH_SHORT;

        /*builder = new AlertDialog.Builder(context2);
        builder.setTitle("Hora: hora.minuto");
        final EditText input = new EditText(context2);
        input.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
                pushdate();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });*/


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
                Date b =  cal.getTime();
                long d = b.getTime();

                long c = calendar.getDate();


                if (c <= d) {
                    //
                    builder = new AlertDialog.Builder(context2);
                    builder.setTitle("Hora: hora:minuto");
                    final EditText input = new EditText(context2);
                    input.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_TIME);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_Text = input.getText().toString();
                            pushdate();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }else{
                    CharSequence text = "No se pueden crar sesiones en dias anteriores";
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }

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
    private void pushdate(){
        Date b =  cal.getTime();
        long d = b.getTime();
        long c = calendar.getDate();
        System.out.println(c);
        System.out.println(d);

        if (c <= d){
            String [] a = m_Text.split(":");
            if (a.length == 2) {
                long h = Long.parseLong(a[0]);
                Long m = Long.parseLong(a[1]);
                h = 3600*1000*h;
                m = 60*1000*m;
                d = d+h+m;
                lista.add(new Session(new Date(d), new TreatmentPlan("plandetratamiento")));
                adaptador = new Adapter(lista);
                recycler.setAdapter(adaptador);
            }else{}
        }else{

            CharSequence text = "No se pueden crar sesiones en dias anteriores";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }


        adaptador = new Adapter(lista);
        recycler.setAdapter(adaptador);

    }
}