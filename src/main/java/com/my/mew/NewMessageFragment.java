package com.my.mew;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * Created by yanyao on 11/26/14.
 */
public class NewMessageFragment extends Fragment
        implements View.OnClickListener {
  private final String TAG = "NewMessage";
  private Context mContext;
  private EditText mData;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mContext = getActivity();
    View root = inflater.inflate(R.layout.newmessage_layout, container, false);
    mData = (EditText) root.findViewById(R.id.data);

    Button btn = (Button) root.findViewById(R.id.submit);
    btn.setOnClickListener(this);
    return root;
  }

  @Override
  public void onClick(View v) {
    String data = mData.getText().toString();
    RequestParams params = new RequestParams();
    params.put("data", data);

    Api.post("msg", params, new TextHttpResponseHandler() {

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString,
                            Throwable throwable) {
        Toast.makeText(mContext, "submit new message failed!", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onSuccess(int statusCode, Header[] header, String responseString) {

        FragmentActivity fa = getActivity();
        View view = fa.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) fa.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        FragmentTransaction transaction = fa.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.timeline_container, new TimelineFragment());
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });

  }

  public void onEditorAction(TextView v, int actionId, KeyEvent event) {
    if (event != null) {
      if (!event.isShiftPressed()) {
      }
    }
  }
}
