package com.my.mew;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.my.mew.model.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    ImageView iv = (ImageView) v.findViewById(R.id.cover);
    Message msg = list.get(position);
    content.setText(msg.content);
    meta.setText(msg.time);
    if (msg.uid.equals("2")) {
//      content.setBackgroundColor(activity.getResources().getColor(R.color.pink_alpha));
//      content.setTextColor(activity.getResources().getColor(R.color.white));
    }

    List<String> img = findImg(msg.content);
    if (img.size() > 0) {
      iv.setVisibility(View.VISIBLE);
      Picasso.with(activity).load(img.get(0)).into(iv);
    }
    return v;
  }

  public static List<String> findImg(String input) {
    List<String> result = new ArrayList<String>();
    String regex = "(http(s?):/)(/[^/]+)+" + "\\.(?:jpg|gif|png)";
    Pattern pattern = Pattern.compile(regex);

    Matcher matcher = pattern.matcher(input);
    while (matcher.find()) {
      result.add(matcher.group());
//      Log.d("Imgfinder", matcher.group());
    }
    return result;
  }
}
