package com.my.mew;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * Created by yanyao on 11/26/14.
 */
public class LoginFragment extends Fragment
        implements View.OnClickListener {
  private final String TAG = "Login";
  private Context mContext;
  private EditText mName;
  private EditText mPass;

  public LoginFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mContext = getActivity();
    View root = inflater.inflate(R.layout.login_layout, container, false);
    mName = (EditText) root.findViewById(R.id.username);
    mPass = (EditText) root.findViewById(R.id.password);

    Button btn = (Button) root.findViewById(R.id.signin);
    btn.setOnClickListener(this);
    return root;
  }

  @Override
  public void onClick(View v) {
    String name = mName.getText().toString();
    String pass = mPass.getText().toString();
    RequestParams params = new RequestParams();
    params.put("name", name);
    params.put("password", pass);

    Api.post("login", params, new TextHttpResponseHandler() {

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString,
                            Throwable throwable) {
        Toast.makeText(mContext, "login failed!", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onSuccess(int statusCode, Header[] header, String responseString) {

        FragmentActivity fa = getActivity();
        View view = fa.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) fa.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        for (int i = 0; i < header.length; i++) {
          String v = header[i].getValue();
          String k = header[i].getName();
          if (k.equals("Set-Cookie")) {
            BasicClientCookie c = new BasicClientCookie("user", v.replace("user=", ""));
            c.setVersion(1);
            c.setDomain(Api.DOMAIN);
            c.setPath("/");
            PersistentCookieStore cookie = new PersistentCookieStore(mContext);
            cookie.addCookie(c);

            FragmentTransaction transaction = fa.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.timeline_container, new TimelineFragment());
            transaction.addToBackStack(null);
            transaction.commit();
          }
        }
      }
    });

  }
}
