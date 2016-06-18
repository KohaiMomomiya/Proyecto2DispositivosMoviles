package cr.tec.desarrollomovil.lectuticas;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.app.Application;
import android.content.Context;
import android.util.Log;


class AnalyticsTracker extends Application {

  private static AnalyticsTracker analyticsTracker = null;
  private Tracker tracker;

  private AnalyticsTracker(Context context) {
    GoogleAnalytics analytics = GoogleAnalytics.getInstance(context.getApplicationContext());
    tracker = analytics.newTracker(R.xml.global_tracker);
  }

  static AnalyticsTracker getAnalyticsTracker(Context context) {
    if (analyticsTracker == null)
      analyticsTracker = new AnalyticsTracker(context);
    return analyticsTracker;
  }

  void trackScreen(String screenName) {
    Log.i(screenName, "Setting screen name: " + screenName);
    tracker.setScreenName(screenName);
    tracker.send(new HitBuilders.ScreenViewBuilder().build());
  }

  void trackEvent(String category, String action, String label) {
    tracker.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
  }


}

