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
              .add(R.id.container, fragment)
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
    switch (item.getItemId()) {
      case R.id.action_submit:
        // must be false then use fragment impl
        // <http://stackoverflow.com/a/18319184>
        return false;
      case R.id.action_signout:
        cookie.clear();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new LoginFragment());
        transaction.addToBackStack(null);
        transaction.commit();
        return true;
      case android.R.id.home:
        super.onBackPressed();
        return true;
    }

    return super.onOptionsItemSelected(item);
  }

}
