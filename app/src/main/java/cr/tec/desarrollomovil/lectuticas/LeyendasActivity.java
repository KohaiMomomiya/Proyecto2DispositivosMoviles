package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LeyendasActivity extends AppCompatActivity {

    private static String idCuento;
    private static String nombreCuento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leyendas);


    }

    public void IrAlCadejos(View view){
        Intent intent = new Intent(this, CuentoCadejosActivity.class);
        getIdStory("El%20Cadejos");
        intent.putExtra("Id",idCuento);
        intent.putExtra("Nombre",nombreCuento);
        startActivity(intent);
    }

    public void IrACegua(View view){
        Intent intent = new Intent(this, CuentoCeguaActivity.class);
        getIdStory("La%20Cegua");
        intent.putExtra("Id",idCuento);
        intent.putExtra("Nombre",nombreCuento);
        startActivity(intent);
    }

    public void getIdStory(String cuento){
        try{
            String url = "http://lectuticas.esy.es/selectBookByName.php?" +
                    "cuento=%27"+cuento+"%27";
            String resultado = new AccesoBaseDatos(this,url).execute().get();
            if (!resultado.equals("")) {
                JSONObject objeto = new JSONObject(resultado);
                JSONArray lista = objeto.getJSONArray("Cuentos");
                JSONObject objetoIndividual = lista.getJSONObject(0);
                idCuento = objetoIndividual.getString("Id_Cuento");
                nombreCuento = objetoIndividual.getString("Nombre");
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
