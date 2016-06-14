package cr.tec.desarrollomovil.lectuticas;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class IniciarPreguntasCeguaActivity extends AppCompatActivity {

    private static String idCuento;
    AnalyticsTracker analyticsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_preguntas_cegua);

        analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());
        Intent intent = getIntent();

        idCuento = intent.getStringExtra("idCuento");
        Button btnPreguntas = (Button) findViewById(R.id.botonIniciarPreguntas);

        btnPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGo = new Intent(IniciarPreguntasCeguaActivity.this,
                        PreguntasCeguaActivity.class);
                intentGo.putExtra("idCuento", idCuento);
                startActivity(intentGo);
            }
        });

    }

    protected void onResume(){

        super.onResume();
        analyticsTracker.trackScreen("IniciarPreguntasCeguaActivity");
    }
}