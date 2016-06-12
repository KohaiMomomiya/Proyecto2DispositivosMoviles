package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class CuentoCadejosActivity extends AppCompatActivity {

    private static String idCuento;
    private static String nombreCuento;
    private static Cuento cuentoCegua;
    private static int contador;
    private ArrayList<Parrafo> lista;
    private TextView textParrafo;
    private int cantParrafos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuento_cadejos);

        final Intent intent = getIntent();
        idCuento = intent.getStringExtra("Id");
        nombreCuento = intent.getStringExtra("Nombre");

        cuentoCegua = new Cuento(nombreCuento,Integer.parseInt(idCuento));
        getParrafos();
        contador = 0;
        textParrafo = (TextView) findViewById(R.id.textoParrafo);
        lista = cuentoCegua.getLista();
        cantParrafos = lista.size();
        textParrafo.setText(lista.get(contador).getTexto());

        Button btnAnterior = (Button) findViewById(R.id.btnAtras);
        Button btnSiguiente = (Button) findViewById(R.id.btnAdelante);
        Button btnMenu = (Button) findViewById(R.id.btnMenu);
        Button btnPreguntas = (Button) findViewById(R.id.btnPreguntas);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contador+1< cantParrafos){
                    contador++;
                    textParrafo.setText(lista.get(contador).getTexto());
                }else if(contador+1 == cantParrafos){

                }
            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador > 0){
                    contador--;
                    textParrafo.setText(lista.get(contador).getTexto());
                }else if(contador == 0){
                    textParrafo.setText(lista.get(contador).getTexto());
                }else if(contador < 0){
                    contador = 0;
                    textParrafo.setText(lista.get(contador).getTexto());
                }
            }
        });

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(CuentoCadejosActivity.this,MainActivity.class));
            }
        });

        btnPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CuentoCadejosActivity.this,PreguntasCadejosActivity.class));
            }
        });
    }

    public void getParrafos(){
        try{
            String url = "http://lectuticas.esy.es/selectSectionsById.php?id="+idCuento;
            String resultado = new AccesoBaseDatos(this,url).execute().get();
            if (!resultado.equals("")) {
                JSONObject objeto = new JSONObject(resultado);
                JSONArray lista = objeto.getJSONArray("Parrafos");
                for(int i=0;i<lista.length();i++){
                    JSONObject objetoIndividual = lista.getJSONObject(i);
                    Parrafo parrafo = new Parrafo(Integer.parseInt(objetoIndividual.getString("Id_Parrafo")),
                            objetoIndividual.getString("Texto"),Integer.parseInt(objetoIndividual.getString("Numero")));
                    cuentoCegua.agregarParrafo(parrafo);
                }
            } else {
                Toast.makeText(this, "Error al realizar la consulta", Toast.LENGTH_LONG).show();
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
