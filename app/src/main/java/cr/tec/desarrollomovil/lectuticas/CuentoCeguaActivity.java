package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CuentoCeguaActivity extends AppCompatActivity {

  private static String idCuento;
  private static String nombreCuento;
  private static Cuento cuentoCegua;
  private static int contador;
  private ArrayList<Parrafo> lista;
  private TextView textParrafo;
  private int cantParrafos;

  AnalyticsTracker analyticsTracker;

  private Button btnAnterior;
  private Button btnSiguiente;
  private Button btnMenu;
  private Button btnPreguntas;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    try {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_cuento_cegua);
      analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());

      final Intent intent = getIntent();
      idCuento = intent.getStringExtra("Id");
      nombreCuento = intent.getStringExtra("Nombre");

      cuentoCegua = new Cuento(nombreCuento, Integer.parseInt(idCuento));
      getParrafos();
      contador = 0;
      textParrafo = (TextView) findViewById(R.id.textoParrafo);
      lista = cuentoCegua.getLista();
      cantParrafos = lista.size();
      textParrafo.setText(lista.get(contador).getTexto());

      btnAnterior = (Button) findViewById(R.id.btnAtras);
      btnSiguiente = (Button) findViewById(R.id.btnAdelante);
      btnMenu = (Button) findViewById(R.id.botonMenu);
      btnPreguntas = (Button) findViewById(R.id.btnPreguntas);
      btnAnterior.setVisibility(View.INVISIBLE);

      btnSiguiente.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (contador + 1 < cantParrafos) {
            btnSiguiente.setVisibility(View.VISIBLE);
            btnAnterior.setVisibility(View.VISIBLE);
            contador++;
            textParrafo.setText(lista.get(contador).getTexto());
          } else if (contador + 1 == cantParrafos) {
            btnSiguiente.setVisibility(View.INVISIBLE);
          }
        }
      });

      btnAnterior.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

          if (contador > 0) {
            btnAnterior.setVisibility(View.VISIBLE);
            btnSiguiente.setVisibility(View.VISIBLE);
            contador--;
            textParrafo.setText(lista.get(contador).getTexto());
          } else if (contador == 0) {
            textParrafo.setText(lista.get(contador).getTexto());
            btnAnterior.setVisibility(View.INVISIBLE);
          } else if (contador < 0) {
            contador = 0;
            textParrafo.setText(lista.get(contador).getTexto());
          }
        }
      });

      btnMenu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intentMain= new Intent(CuentoCeguaActivity.this, MainActivity.class);
          intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intentMain);
        }
      });

      btnPreguntas.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intentGo = new Intent(CuentoCeguaActivity.this,
                  IniciarPreguntasCeguaActivity.class);
          intentGo.putExtra("idCuento", idCuento);
          startActivity(intentGo);
        }
      });
    } catch (NumberFormatException e) {
      Log.e("Exception", "Id de historia no tiene formato válido. Dato no existe o es inválido.");
      Toast.makeText
          (getApplicationContext(), "Hubo un error al iniciar esta pantalla.",
              Toast.LENGTH_LONG).show();
      finish();
    }
  }

  public void getParrafos() {
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
          cuentoCegua.agregarParrafo(parrafo);
        }
      } else {
        Toast.makeText(this, "Error al realizar la consulta", Toast.LENGTH_LONG).show();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected void onResume(){

    super.onResume();
    analyticsTracker.trackScreen("CuentoCeguaActivity");
  }

}
