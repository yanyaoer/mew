package com.my.mew;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.my.mew.model.Message;

import java.util.ArrayList;

/**
 * Created by yanyao on 11/25/14.
 */
public class TimelineAdapter extends BaseAdapter {
  private Activity activity;
  private static ArrayList<Message> list;
  private static LayoutInflater inflater = null;

  public TimelineAdapter(Activity a, ArrayList<Message> l) {
    activity = a;
//    if (list != null && list.size() > 0) {
//      list.addAll(l);
//    } else {
//      list = l;
//    }
    list = l;
    inflater = (LayoutInflater) activity
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override
  public int getCount() {
    return list.size();
  }

  @Override
  public Object getItem(int position) {
    return position;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View v, ViewGroup parent) {

    if (v == null) {
      v = inflater.inflate(R.layout.timeline_item, null);
    }

    TextView content = (TextView) v.findViewById(R.id.content);
    TextView meta = (TextView) v.findViewById(R.id.meta);
    Message msg = list.get(position);
    content.setText(msg.content);
    meta.setText(msg.time);

    return v;
  }
}
