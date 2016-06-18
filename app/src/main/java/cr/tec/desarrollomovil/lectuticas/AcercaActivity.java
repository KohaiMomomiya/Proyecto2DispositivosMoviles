package cr.tec.desarrollomovil.lectuticas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AcercaActivity extends AppCompatActivity {

  private AnalyticsTracker analyticsTracker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_acerca);

    analyticsTracker = AnalyticsTracker.getAnalyticsTracker(this.getApplicationContext());
  }

  protected void onResume() {

    super.onResume();
    analyticsTracker.trackScreen("AcercaActivity");
  }
}
