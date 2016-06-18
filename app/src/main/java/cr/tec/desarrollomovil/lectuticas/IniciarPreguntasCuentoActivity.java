package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import pl.droidsonroids.gif.GifImageView;


public class IniciarPreguntasCuentoActivity extends AppCompatActivity {

  private String idCuento;
  private int idFondoCuento;

  private AnalyticsTracker analyticsTracker;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_iniciar_preguntas);
    analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());

    Intent intent = getIntent();
    idCuento = intent.getStringExtra("idCuento");

    // Asigna fondo de layout para Activity.
    idFondoCuento = intent.getIntExtra("fondoCuento", 0);
    RelativeLayout relativeLayout =
        (RelativeLayout) findViewById(R.id.layoutIniciarPreguntasCuento);
    if ((relativeLayout != null) && (idFondoCuento != 0)) {
      try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          relativeLayout.setBackground(getDrawable(idFondoCuento));
        } else {
          relativeLayout.setBackgroundDrawable(getResources().getDrawable(idFondoCuento));
        }
      } catch (Exception e){
        Log.e("ResourceError",
            "No se puede asignar el fondo de activity IniciarPreguntasCuentoActivity.");
        e.printStackTrace();
      }
    }

    // Asigna GIF animado que se muestra en Activity.
    int idGifPersonaje = intent.getIntExtra("idGifPersonajeCuento", 0);
    GifImageView gifImageView = (GifImageView) findViewById(R.id.gifPersonajeCuento);
    if ((gifImageView != null) && (idGifPersonaje != 0)){
      try {
        gifImageView.setImageResource(idGifPersonaje);
      } catch (Exception e) {
        Log.e("ResourceError", "No se puede asignar la imagen GIF para ser animada.");
        e.printStackTrace();
      }
    }


    Button btnPreguntas = (Button) findViewById(R.id.botonIniciarPreguntas);
    if (btnPreguntas != null) {
      btnPreguntas.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intentGo = new Intent(IniciarPreguntasCuentoActivity.this,
              PreguntasCuentoActivity.class);
          intentGo.putExtra("fondoCuento", idFondoCuento);
          intentGo.putExtra("idCuento", idCuento);
          startActivity(intentGo);
        }
      });
    }
  }

  protected void onResume() {
    super.onResume();
    analyticsTracker.trackScreen("IniciarPreguntasCuentoActivity");
  }
}
