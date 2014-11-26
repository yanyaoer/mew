package com.my.mew;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.loopj.android.http.PersistentCookieStore;


public class MainActivity extends ActionBarActivity {
  private final String TAG = "Main";
  private PersistentCookieStore cookie;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    cookie = new PersistentCookieStore(this);

    if (savedInstanceState == null) {
      Fragment fragment = cookie.getCookies().isEmpty() ? new LoginFragment() : new TimelineFragment();
      getSupportFragmentManager().beginTransaction()
              .add(R.id.timeline_container, fragment)
              .commit();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    } else if (id == R.id.action_signout) {
      cookie.clear();
      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.replace(R.id.timeline_container, new LoginFragment());
      transaction.addToBackStack(null);
      transaction.commit();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
