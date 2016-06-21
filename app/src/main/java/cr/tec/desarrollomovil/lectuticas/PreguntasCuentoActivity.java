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
    private Pregunta pregunta;
    private ArrayList<Respuesta> respuestas;
    private TextView tvPregunta;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_cuento);

        Intent intent = getIntent();

        idCuento = intent.getStringExtra("idCuento");
        getPreguntas();
        getRespuesta();
        idFondoCuento = intent.getIntExtra("fondoCuento", 0);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.layoutPreguntasLectura);

        if ((relativeLayout != null) && (idFondoCuento != 0)) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    relativeLayout.setBackground(getDrawable(idFondoCuento));
                } else {
                    relativeLayout.setBackgroundDrawable(getResources().getDrawable(idFondoCuento));
                }
            } catch (Exception e) {
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
        respuestas = pregunta.getAllRespuestas();

        tvPregunta = (TextView) findViewById(R.id.tvPregunta);
        tvPregunta.setText(pregunta.getPregunta());

        conjunto = (RadioGroup) findViewById(R.id.conjunto);

        for(int p=0;p<respuestas.size();p++){
            RadioButton rb =  (RadioButton) getLayoutInflater().inflate(
                    R.layout.radiobutton_style, null);
            rb.setId(p);
            rb.setText(respuestas.get(p).getRespuesta());
            rb.setChecked(false);
            conjunto.addView(rb);
        }

        Button btnSR = (Button) findViewById(R.id.btnPreguntaSiguiente);

        if (btnSR != null) {
            btnSR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    revisarRespuesta();
                    if (contador + 1 < preguntas.size()) {
                        contador++;
                        pregunta = preguntas.get(contador);
                        respuestas = pregunta.getAllRespuestas();
                        tvPregunta.setText(pregunta.getPregunta());
                        conjunto.removeAllViews();
                        for(int p=0;p<respuestas.size();p++){
                            RadioButton rb =  (RadioButton) getLayoutInflater().inflate(
                                    R.layout.radiobutton_style, null);
                            rb.setId(p);
                            rb.setText(respuestas.get(p).getRespuesta());
                            rb.setChecked(false);
                            conjunto.addView(rb);
                        }

                    } else if (contador + 1 == preguntas.size()) {
                        Intent intentNext = new Intent(PreguntasCuentoActivity.this, ResultadoPreguntasActivity.class);
                        String puntaje = String.valueOf(cantidadRespuestasCorrectas);
                        intentNext.putExtra("puntaje", puntaje);
                        intentNext.putExtra("preguntas",String.valueOf(preguntas.size()));
                        intentNext.putExtra("fondoCuento", idFondoCuento);

                        finish();
                        startActivity(intentNext);
                    }
                    conjunto.clearCheck();
                }
            });
        }
    }

    private void getPreguntas() {
        try {
            String url = "http://lectuticas.esy.es/selectQuestionsById.php?id=" + idCuento;
            String resultado = new AccesoBaseDatos(this, url).execute().get();
            if (!resultado.equals("")) {
                JSONObject objeto = new JSONObject(resultado);
                JSONArray lista = objeto.getJSONArray("Preguntas");
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject objetoIndividual = lista.getJSONObject(i);
                    Pregunta pregunta = new Pregunta(
                            Integer.parseInt(objetoIndividual.getString("Id_Pregunta")),
                            objetoIndividual.getString("Pregunta"));
                    preguntas.add(pregunta);
                }
            } else {
                Toast.makeText(this, "Error al realizar la consulta", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getRespuesta() {
        try {
            String url = "http://lectuticas.esy.es/selectAnswersById.php?id=" + idCuento;
            String resultado = new AccesoBaseDatos(this, url).execute().get();
            if (!resultado.equals("")) {
                JSONObject objeto = new JSONObject(resultado);
                JSONArray lista = objeto.getJSONArray("Respuestas");
                for (int i = 0; i < lista.length(); i++) {
                    JSONObject objetoIndividual = lista.getJSONObject(i);
                    int idPregunta = Integer.parseInt(objetoIndividual.getString("Id_Pregunta"));
                    for (int j = 0; j < preguntas.size(); j++) {
                        if (preguntas.get(j).getIdPregunta() == idPregunta) {
                            boolean esCorrecta = true;
                            if (Integer.parseInt(objetoIndividual.getString("EsCorrecta")) == 0) {
                                esCorrecta = false;
                            }
                            Respuesta respuesta = new Respuesta(
                                    Integer.parseInt(objetoIndividual.getString("Id_Respuesta")),
                                    objetoIndividual.getString("Respuesta"), esCorrecta);
                            preguntas.get(j).addRespuesta(respuesta);
                        }

                    }
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
            for(int i=0;i<respuestas.size();i++){
                if(respuestas.get(i).getRespuesta().equals(opcion)){
                    if(respuestas.get(i).isEsCorrecta()){
                        cantidadRespuestasCorrectas+=1;
                    }
                }
            }
        } else {
            Toast.makeText(PreguntasCuentoActivity.this,
                    "Debe seleccionar al menos una respuesta", Toast.LENGTH_LONG).show();
        }
    }

    private void volverAMenuPrincipal() {
        Intent intentMain = new Intent(this, LeyendasActivity.class);
        intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intentMain);
        finish();
    }
}
