package cr.tec.desarrollomovil.lectuticas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LeyendasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leyendas);


    }

    public void IrAlCadejos(View view){
        Intent intent = new Intent(this, CuentoCadejosActivity.class);
        startActivity(intent);
    }

    public void IrACegua(View view){
        Intent intent = new Intent(this, CuentoCeguaActivity.class);
        startActivity(intent);
    }
}
