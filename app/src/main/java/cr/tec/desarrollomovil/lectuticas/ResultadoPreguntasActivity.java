package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ResultadoPreguntasActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_resultado_preguntas);

    Intent intent = getIntent();
    String puntaje = intent.getStringExtra("puntaje");
    String cantPreguntas = intent.getStringExtra("preguntas");
    int calificacion = (Integer.parseInt(puntaje) * 100)/Integer.parseInt(cantPreguntas);



    String mensajeResultado = getString(R.string.mensaje_resultados_parte1) + puntaje + " "
            + getString(R.string.mensaje_resultados_parte2)+ " "+cantPreguntas+ " "+
            getString(R.string.mensaje_resultados_parte3)+" "+String.valueOf(calificacion);

    TextView txtPuntaje = (TextView) findViewById(R.id.txtPuntaje);
    if (txtPuntaje != null) {
      txtPuntaje.setText(mensajeResultado);
    }

    int idFondoCuento = intent.getIntExtra("fondoCuento", 0);
    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutResultadosPreguntas);
    if ((relativeLayout != null) && (idFondoCuento != 0)) {
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          relativeLayout.setBackground(getDrawable(idFondoCuento));
        } else {
          relativeLayout.setBackgroundDrawable(getResources().getDrawable(idFondoCuento));
        }
      } catch (Exception e) {
        Log.e("ResourceError",
            "No se puede asignar el fondo de activity ResultadoPreguntasActivity ");
        e.printStackTrace();
      }
    }


    Button btnMenu = (Button) findViewById(R.id.botonMenu);

    if (btnMenu != null) {
      btnMenu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          volverAMenuPrincipal();
        }
      });
    }
  }

  private void volverAMenuPrincipal(){
    Intent intentMain = new Intent(this, LeyendasActivity.class);
    intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intentMain);
    finish();
  }
}
