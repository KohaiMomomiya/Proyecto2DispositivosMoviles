package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }

  public void irALeyendas(View view){
    Intent intent = new Intent(this, LeyendasActivity.class);
    startActivity(intent);
  }

  public void irAlAcerca(View view){
    Intent intent = new Intent(this, AcercaActivity.class);
    startActivity(intent);
  }

}
