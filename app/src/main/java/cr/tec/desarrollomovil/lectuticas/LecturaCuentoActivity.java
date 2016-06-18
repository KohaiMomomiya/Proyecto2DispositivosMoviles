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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class LecturaCuentoActivity extends AppCompatActivity {

  private String idCuento;
  private Cuento cuento;

  private int idFondoCuento; // ResId del fondo del cuento.
  private int idGifPersonajeCuento;  // ResId de GIF del personaje de cuento.

  private int contador;
  private AnalyticsTracker analyticsTracker;

  private ArrayList<Parrafo> lista;
  private TextView textParrafo;

  private int cantParrafos;
  private Button btnAnterior;
  private Button btnSiguiente;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    try {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lectura_cuento);

      analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());

      final Intent intent = getIntent();
      idCuento = intent.getStringExtra("Id");
      String nombreCuento = intent.getStringExtra("Nombre");

      idFondoCuento = intent.getIntExtra("fondoCuento", 0);
      idGifPersonajeCuento = intent.getIntExtra("gifPersonajeCuento", 0);



      // Asigna el fondo del layout recibiendo el identificador de la imagen como extra del Intent.

      RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutLecturaCuento);
      if ((relativeLayout != null) && (idFondoCuento != 0)) {
        try {
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            relativeLayout.setBackground(getDrawable(idFondoCuento));
          } else {
            relativeLayout.setBackgroundDrawable(getResources().getDrawable(idFondoCuento));
          }
        } catch (Exception e) {
          Log.e("ResourceError", "No se puede asignar el fondo de activity LecturaCuentoActivity.");
        }
      }

      cuento = new Cuento(nombreCuento, Integer.parseInt(idCuento));

      getParrafos();
      contador = 0;

      textParrafo = (TextView) findViewById(R.id.textoParrafo);
      lista = cuento.getLista();
      cantParrafos = lista.size();
      textParrafo.setText(lista.get(contador).getTexto());

      btnAnterior = (Button) findViewById(R.id.btnAtras);
      btnSiguiente = (Button) findViewById(R.id.btnAdelante);
      btnAnterior.setVisibility(View.INVISIBLE);

      btnSiguiente.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if ((contador + 1) < cantParrafos){
            ++contador;
            textParrafo.setText(lista.get(contador).getTexto());

            btnAnterior.setVisibility(View.VISIBLE);
            btnSiguiente.setVisibility(
                ((contador + 1) < cantParrafos)? View.VISIBLE : View.INVISIBLE
            );
          }
        }
      });

      btnAnterior.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (contador > 0) {
            --contador;
            textParrafo.setText(lista.get(contador).getTexto());

            btnSiguiente.setVisibility(View.VISIBLE);
            btnAnterior.setVisibility(
                (contador > 0)? View.VISIBLE : View.INVISIBLE
            );
          }
        }
      });

      Button btnMenu = (Button) findViewById(R.id.botonMenu);
      if (btnMenu != null) {
        btnMenu.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            volverAMenuPrincipal();
          }
        });
      }

      Button btnPreguntas = (Button) findViewById(R.id.btnPreguntas);
      if (btnPreguntas != null) {
        btnPreguntas.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intentGo = new Intent(LecturaCuentoActivity.this,
                IniciarPreguntasCuentoActivity.class);
            intentGo.putExtra("fondoCuento", idFondoCuento);
            intentGo.putExtra("idCuento", idCuento);
            intentGo.putExtra("idGifPersonajeCuento", idGifPersonajeCuento);
            startActivity(intentGo);
          }
        });
      }
    } catch (NumberFormatException e) {
      Log.e("Exception", "Id de historia no tiene formato válido. Dato no existe o es inválido.");
      Toast.makeText
          (getApplicationContext(), getString(R.string.error_iniciarPantallaCuento),
              Toast.LENGTH_LONG).show();
      finish();
    }
  }

  private void getParrafos() {
    try {
      String url = "http://lectuticas.esy.es/selectSectionsById.php?id=" + idCuento;
      String resultado = new AccesoBaseDatos(this, url).execute().get();
      if (!resultado.equals("")) {
        JSONObject objeto = new JSONObject(resultado);
        JSONArray lista = objeto.getJSONArray("Parrafos");
        for (int i = 0; i < lista.length(); i++) {
          JSONObject objetoIndividual = lista.getJSONObject(i);
          Parrafo parrafo = new Parrafo(Integer.parseInt(objetoIndividual.getString("Id_Parrafo")),
              objetoIndividual.getString("Texto"), Integer.parseInt(objetoIndividual.getString("Numero")));
          cuento.agregarParrafo(parrafo);
        }
      } else {
        Toast.makeText(this, "Error al realizar la consulta", Toast.LENGTH_LONG).show();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void onResume() {
    super.onResume();
    analyticsTracker.trackScreen("LecturaCuentoActivity");
  }

  private void volverAMenuPrincipal(){
    Intent intentMain = new Intent(this, LeyendasActivity.class);
    intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intentMain);
    finish();
  }
}
