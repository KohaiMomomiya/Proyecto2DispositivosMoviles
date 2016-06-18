package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PreguntasCuentoActivity extends AppCompatActivity {

  private String idCuento;
  private int idFondoCuento;

  private int contador = 0;
  private int cantidadRespuestasCorrectas = 0;

  private ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
  private RadioGroup conjunto;
  private RadioButton rbResp1;
  private RadioButton rbResp2;
  private RadioButton rbResp3;

  private Pregunta pregunta;
  private TextView tvPregunta;

  private Respuesta resp1;
  private Respuesta resp2;
  private Respuesta resp3;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_preguntas_cuento);

    Intent intent = getIntent();

    idCuento = intent.getStringExtra("idCuento");
    getPreguntasRespuestas();

    idFondoCuento = intent.getIntExtra("fondoCuento", 0);
    RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutPreguntasLectura);

    if ((relativeLayout != null) && (idFondoCuento != 0)) {
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
          relativeLayout.setBackground(getDrawable(idFondoCuento));
        } else {
          relativeLayout.setBackgroundDrawable(getResources().getDrawable(idFondoCuento));
        }
      } catch (Exception e){
        Log.e("ResourceError", "No se puede asignar el fondo de activity " +
            "PreguntasCuentoActivity.");
        e.printStackTrace();
      }
    }

    Button botonMenu = (Button) findViewById(R.id.botonMenu);
    if (botonMenu != null) {
      botonMenu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          volverAMenuPrincipal();
        }
      });
    }



    pregunta = preguntas.get(contador);
    resp1 = pregunta.getRespuesta(0);
    resp2 = pregunta.getRespuesta(1);
    resp3 = pregunta.getRespuesta(2);

    tvPregunta = (TextView) findViewById(R.id.tvPregunta);
    tvPregunta.setText(pregunta.getPregunta());

    conjunto = (RadioGroup) findViewById(R.id.conjunto);

    rbResp1 = (RadioButton) findViewById(R.id.rbRespuesta1);
    rbResp2 = (RadioButton) findViewById(R.id.rbRespuesta2);
    rbResp3 = (RadioButton) findViewById(R.id.rbRespuesta3);

    rbResp1.setText(resp1.getRespuesta());
    rbResp2.setText(resp2.getRespuesta());
    rbResp3.setText(resp3.getRespuesta());

    Button btnSR = (Button) findViewById(R.id.btnPreguntaSiguiente);

    if (btnSR != null) {
      btnSR.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          // TODO Cambiar esto para hacer respuestas parametrizables.
          revisarRespuesta();
          if (contador + 1 < preguntas.size()) {
            contador++;
            pregunta = preguntas.get(contador);
            resp1 = pregunta.getRespuesta(0);
            resp2 = pregunta.getRespuesta(1);
            resp3 = pregunta.getRespuesta(2);
            tvPregunta.setText(pregunta.getPregunta());
            rbResp1.setText(resp1.getRespuesta());
            rbResp2.setText(resp2.getRespuesta());
            rbResp3.setText(resp3.getRespuesta());

          } else if (contador + 1 == preguntas.size()) {
            Intent intentNext = new Intent(PreguntasCuentoActivity.this, ResultadoPreguntasActivity.class);
            String puntaje = String.valueOf(cantidadRespuestasCorrectas);
            intentNext.putExtra("puntaje", puntaje);
            intentNext.putExtra("fondoCuento", idFondoCuento);

            finish();
            startActivity(intentNext);
          }
          conjunto.clearCheck();
        }
      });
    }
  }

  private void getPreguntasRespuestas() {
    try {
      String url = "http://lectuticas.esy.es/selectQuestionsById.php?id=" + idCuento;
      String resultado = new AccesoBaseDatos(this, url).execute().get();
      if (!resultado.equals("")) {
        JSONObject objeto = new JSONObject(resultado);
        JSONArray lista = objeto.getJSONArray("Preguntas");
        for (int i = 0; i < lista.length(); i += 3) {
          JSONObject objetoIndividual = lista.getJSONObject(i);
          JSONObject objetoIndividual2 = lista.getJSONObject(i + 1);
          JSONObject objetoIndividual3 = lista.getJSONObject(i + 2);
          Pregunta pregunta = new Pregunta(
              Integer.parseInt(objetoIndividual.getString("Id_Pregunta")),
              objetoIndividual.getString("Pregunta"));
          Boolean escorrecta1 = false;
          Boolean escorrecta2 = false;
          Boolean escorrecta3 = false;

          if (objetoIndividual.getString("esCorrecta").equals("1"))
            escorrecta1 = true;

          if (objetoIndividual2.getString("esCorrecta").equals("1"))
            escorrecta2 = true;

          if (objetoIndividual3.getString("esCorrecta").equals("1"))
            escorrecta3 = true;

          Respuesta respuesta1 = new Respuesta(
              Integer.parseInt(objetoIndividual.getString("Id_Respuesta")),
              objetoIndividual.getString("Respuesta"),
              escorrecta1);
          Respuesta respuesta2 = new Respuesta(
              Integer.parseInt(objetoIndividual2.getString("Id_Respuesta")),
              objetoIndividual2.getString("Respuesta"),
              escorrecta2);
          Respuesta respuesta3 = new Respuesta(
              Integer.parseInt(objetoIndividual3.getString("Id_Respuesta")),
              objetoIndividual3.getString("Respuesta"),
              escorrecta3);
          pregunta.addRespuesta(respuesta1);
          pregunta.addRespuesta(respuesta2);
          pregunta.addRespuesta(respuesta3);
          preguntas.add(pregunta);
        }
      } else {
        Toast.makeText(this, "Error al realizar la consulta", Toast.LENGTH_LONG).show();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void revisarRespuesta() {
    if (conjunto.getCheckedRadioButtonId() >= 0) {
      int id = conjunto.getCheckedRadioButtonId();
      RadioButton rb = (RadioButton) findViewById(id);

      String opcion = null;
      if (rb != null) {
        opcion = rb.getText().toString();
      }
      if (resp1.getRespuesta().equals(opcion)) {
        if (resp1.isEsCorrecta())
          cantidadRespuestasCorrectas += 1;
      } else if (resp2.getRespuesta().equals(opcion)) {
        if (resp2.isEsCorrecta())
          cantidadRespuestasCorrectas += 1;
      } else if (resp3.getRespuesta().equals(opcion)) {
        if (resp3.isEsCorrecta())
          cantidadRespuestasCorrectas += 1;
      }
    } else {
      Toast.makeText(PreguntasCuentoActivity.this,
          "Debe seleccionar al menos una respuesta", Toast.LENGTH_LONG).show();
    }
  }

  private void volverAMenuPrincipal(){
    Intent intentMain = new Intent(this, LeyendasActivity.class);
    intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intentMain);
    finish();
  }
}
