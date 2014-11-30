package com.my.mew;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

/**
 * Created by yanyao on 11/26/14.
 */
public class NewMessageFragment extends Fragment
        implements View.OnClickListener {
  private final String TAG = "NewMessage";
  private Context mContext;
  private EditText mData;
  private InputMethodManager imm;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mContext = getActivity();
    View root = inflater.inflate(R.layout.newmessage_layout, container, false);
    mData = (EditText) root.findViewById(R.id.data);

//    Button btn = (Button) root.findViewById(R.id.action_submit);
//    btn.setOnClickListener(this);
    imm = (InputMethodManager)
            mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    ActionBar ab = ((MainActivity) getActivity()).getSupportActionBar();
    ab.setDisplayHomeAsUpEnabled(true);
    setHasOptionsMenu(true);
    return root;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.submit, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_submit:
        submit();
//        Toast.makeText(mContext, "hello there", Toast.LENGTH_LONG).show();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onClick(View v) {
    submit();
  }

  public void submit() {
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
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        FragmentTransaction transaction = fa.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new TimelineFragment());
        transaction.addToBackStack(null);
        transaction.commit();
      }
    });

  }

}
