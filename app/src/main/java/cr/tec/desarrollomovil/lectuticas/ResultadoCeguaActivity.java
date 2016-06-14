package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultadoCeguaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_cegua);

        Intent intent = getIntent();
        String puntaje = intent.getStringExtra("puntaje");
        TextView txtPuntaje = (TextView) findViewById(R.id.txtPuntaje);
        txtPuntaje.setText("Felicitaciones!!!\n"+"Tu puntaje fue de:\n"+puntaje+" repuestas correctas");

        Button btnMenu = (Button) findViewById(R.id.botonMenu);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(ResultadoCeguaActivity.this, MainActivity.class);
                intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentMain);
            }
        });
    }
}
