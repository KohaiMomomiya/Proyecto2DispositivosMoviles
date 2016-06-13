package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PreguntasCadejosActivity extends AppCompatActivity {

    private String idCuento;
    private int contador = 0;
    private int cantidadRespuestasCorrectas = 0;
    private ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_cadejos);

        Intent intent = getIntent();

        idCuento = intent.getStringExtra("idCuento");
        getPreguntasRespuestas();

        final Pregunta pregunta = preguntas.get(contador);
        Respuesta resp1 = pregunta.getRespuesta(0);
        Respuesta resp2 = pregunta.getRespuesta(1);
        Respuesta resp3 = pregunta.getRespuesta(2);

        final TextView tvPregunta = (TextView) findViewById(R.id.tvPregunta);
        tvPregunta.setText(pregunta.getPregunta());

        final RadioGroup conjunto = (RadioGroup) findViewById(R.id.conjunto);

        final RadioButton rbResp1 = (RadioButton) findViewById(R.id.rbRespuesta1);
        final RadioButton rbResp2 = (RadioButton) findViewById(R.id.rbRespuesta2);
        final RadioButton rbResp3 = (RadioButton) findViewById(R.id.rbRespuesta3);

        rbResp1.setText(resp1.getRespuesta());
        rbResp2.setText(resp2.getRespuesta());
        rbResp3.setText(resp3.getRespuesta());

        Button btnSR = (Button) findViewById(R.id.btnSiguientePregunta);

        btnSR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conjunto.getCheckedRadioButtonId() >= 0) {
                    int id = conjunto.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(id);
                    if (pregunta.getRespuesta(0).isEsCorrecta()) {
                        if (pregunta.getRespuesta(0).getRespuesta().equals(rb.getText().toString()))
                            cantidadRespuestasCorrectas += 1;
                    } else if (pregunta.getRespuesta(1).isEsCorrecta()) {
                        if (pregunta.getRespuesta(1).getRespuesta().equals(rb.getText().toString()))
                            cantidadRespuestasCorrectas += 1;
                    } else if (pregunta.getRespuesta(2).isEsCorrecta()) {
                        if (pregunta.getRespuesta(2).getRespuesta().equals(rb.getText().toString()))
                            cantidadRespuestasCorrectas += 1;
                    }
                    if (contador + 1 < preguntas.size()) {
                        contador++;
                        Pregunta pregunta = preguntas.get(contador);
                        Respuesta resp1 = pregunta.getRespuesta(0);
                        Respuesta resp2 = pregunta.getRespuesta(1);
                        Respuesta resp3 = pregunta.getRespuesta(2);

                        tvPregunta.setText(pregunta.getPregunta());
                        rbResp1.setText(resp1.getRespuesta());
                        rbResp2.setText(resp2.getRespuesta());
                        rbResp3.setText(resp3.getRespuesta());
                    } else if (contador + 1 == preguntas.size()) {
                        //Aqui cambia a una pantalla con el resultado de las respuestas
                    }

                    conjunto.clearCheck();
                } else {
                    Toast.makeText(PreguntasCadejosActivity.this,
                            "Debe seleccionar al menos una respuesta", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public void getPreguntasRespuestas() {
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
                    Respuesta respuesta1 = new Respuesta(
                            Integer.parseInt(objetoIndividual.getString("Id_Respuesta")),
                            objetoIndividual.getString("Respuesta"),
                            Boolean.parseBoolean(objetoIndividual.getString("esCorrecta")));
                    Respuesta respuesta2 = new Respuesta(
                            Integer.parseInt(objetoIndividual2.getString("Id_Respuesta")),
                            objetoIndividual2.getString("Respuesta"),
                            Boolean.parseBoolean(objetoIndividual2.getString("esCorrecta")));
                    Respuesta respuesta3 = new Respuesta(
                            Integer.parseInt(objetoIndividual3.getString("Id_Respuesta")),
                            objetoIndividual3.getString("Respuesta"),
                            Boolean.parseBoolean(objetoIndividual3.getString("esCorrecta")));
                    pregunta.addRespuesta(respuesta1);
                    pregunta.addRespuesta(respuesta2);
                    pregunta.addRespuesta(respuesta3);
                    preguntas.add(pregunta);
                }
            } else {
                Toast.makeText(this, "Error al realizar la consulta", Toast.LENGTH_LONG).show();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
