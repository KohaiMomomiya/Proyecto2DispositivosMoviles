package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Arlyn on 12/06/2016.
 */
public class IniciarPreguntasCadejosActivity extends AppCompatActivity {

    private static String idCuento;
    AnalyticsTracker analyticsTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_preguntas_cadejos);

        analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());

        Button btnPreguntas = (Button) findViewById(R.id.btnpregunta);

        /*btnPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IniciarPreguntasCadejosActivity.this, PreguntasCadejosActivity.class));
            }
        });*/

    }

    protected void onResume(){

        super.onResume();
        analyticsTracker.trackScreen("IniciarPreguntasCadejosActivity");
    }
}
