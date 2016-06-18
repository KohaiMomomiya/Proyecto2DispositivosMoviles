package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class LeyendasActivity extends AppCompatActivity {

  private String idCuento;
  private String nombreCuento;

  private AnalyticsTracker analyticsTracker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_leyendas);

    analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());
  }

  public void verLeyendaCadejos(View view) {
    Intent intent = new Intent(this, LecturaCuentoActivity.class);
    getIdStory("El%20Cadejos");

    intent.putExtra("fondoCuento", R.drawable.cadejos);
    intent.putExtra("gifPersonajeCuento", R.drawable.cadejos_anim);
    intent.putExtra("Id", idCuento);
    intent.putExtra("Nombre", nombreCuento);

    startActivity(intent);
  }

  public void verLeyendaCegua(View view) {
    Intent intent = new Intent(this, LecturaCuentoActivity.class);
    getIdStory("La%20Cegua");

    intent.putExtra("fondoCuento", R.drawable.cegua);
    intent.putExtra("gifPersonajeCuento", R.drawable.cegua_anim);
    intent.putExtra("Id", idCuento);
    intent.putExtra("Nombre", nombreCuento);

    startActivity(intent);
  }

  private void getIdStory(String cuento) {
    try {
      String url = "http://lectuticas.esy.es/selectBookByName.php?" + "cuento=%27" + cuento + "%27";
      String resultado = new AccesoBaseDatos(this, url).execute().get();

      if (!resultado.equals("")) {
        JSONObject objeto = new JSONObject(resultado);
        JSONArray lista = objeto.getJSONArray("Cuentos");
        JSONObject objetoIndividual = lista.getJSONObject(0);

        idCuento = objetoIndividual.getString("Id_Cuento");
        nombreCuento = objetoIndividual.getString("Nombre");
      } else {
        throw new Exception(getString(R.string.error_consultaDatos));
      }
    } catch (Exception e) {
      String stackTrace = Arrays.toString(e.getStackTrace());
      Log.e("ERROR", stackTrace);

      Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }

  protected void onResume() {
    super.onResume();
    analyticsTracker.trackScreen("LeyendasActivity");
  }
}
