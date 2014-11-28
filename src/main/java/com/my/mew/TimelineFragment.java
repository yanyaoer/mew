package com.my.mew;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.my.mew.model.Message;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by yanyao on 11/25/14.
 */
public class TimelineFragment extends Fragment
        implements PullToRefreshBase.OnRefreshListener<ListView>, View.OnClickListener {
  private final String TAG = "Timeline";
  private Context mContext;
  private Button mAdd;
  private PullToRefreshListView mList;
  private TimelineAdapter adapter;

  public TimelineFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mContext = getActivity();
    View root = inflater.inflate(R.layout.timeline_layout, container, false);
    mAdd = (Button) root.findViewById(R.id.add);
    mAdd.setOnClickListener(this);

    mList = (PullToRefreshListView) root.findViewById(R.id.list);
//    mList.setMode(PullToRefreshBase.Mode.BOTH);
//    mList.setPullToRefreshOverScrollEnabled(false);
    mList.setOnRefreshListener(this);
    load();
    return root;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }

//  @Override
//  public void onListItemClick(ListView l, View v, int position, long id) {
//    mCallback.onArticleSelected(position);
//  }

  private int mPage = 1;

  public void load() {
    new Api(mContext).get("msg?p=" + Integer.toString(mPage), null, new JsonHttpResponseHandler() {

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
        ArrayList<Message> tl = Message.parse(timeline);
        adapter = new TimelineAdapter(getActivity(), tl);
        mList.setAdapter(adapter);
        mList.onRefreshComplete();
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable,
                            JSONObject errorResponse) {
        mList.onRefreshComplete();
        Toast.makeText(mContext, "request failed!", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  public void onRefresh(PullToRefreshBase<ListView> listViewPullToRefreshBase) {
    mList.setRefreshing();
    int offset = (mList.getScrollY() < 0) ? -1 : 1;
    mPage += offset;
    if (mPage < 1) {
      mPage = 1;
      Toast.makeText(mContext, "page end... " + Integer.toString(mPage), Toast.LENGTH_SHORT).show();
    }
    load();
  }

  @Override
  public void onClick(View v) {
    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.timeline_container, new NewMessageFragment());
    transaction.addToBackStack(null);
    transaction.commit();

  }
}
