package com.my.mew.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Message {
  public String _id;
  public String uid;
  public String content;
  public String time;

  public Message(String id, String uid, String content, String time) {
    this._id = id;
    this.uid = uid;
    this.content = content;
    this.time = time;
  }


  public static ArrayList<Message> parse(JSONArray in) {
    ArrayList<Message> list = new ArrayList<Message>();
    try {
      for (int i = 0; i < in.length(); i++) {
        JSONObject row = in.getJSONObject(i);
        String id = row.getString("_id");
        String uid = row.getString("uid");
        String time = row.getString("time");
        String content = row.getString("content");
        Message msg = new Message(id, uid, content, time);
        list.add(msg);
      }
      return list;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
