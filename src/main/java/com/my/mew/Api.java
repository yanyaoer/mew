package com.my.mew;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.impl.cookie.BasicClientCookie;

/**
 * Created by yanyao on 11/25/14.
 */
public class Api {
//  public static final String DOMAIN = "http://172.20.1.161:8000/";
  public static final String DOMAIN = "http://fallinlove.me/";
  public static final String BASE_URL = DOMAIN + "api/";

  public static AsyncHttpClient client = new AsyncHttpClient();
  public static PersistentCookieStore cookie;

  public Api(Context context) {
//    SharedPreferences sp = context.getSharedPreferences("cookie", Context.MODE_PRIVATE);
//    String user = sp.getString("user", null);
    cookie = new PersistentCookieStore(context);
//    BasicClientCookie c = new BasicClientCookie("user", user);
//    c.setVersion(1);
//    c.setDomain(DOMAIN);
//    c.setPath("/");
//    cookie.addCookie(c);
    client.setCookieStore(cookie);
  }

  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    client.get(getAbsoluteUrl(url), params, responseHandler);
  }

  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    client.post(getAbsoluteUrl(url), params, responseHandler);
  }

  private static String getAbsoluteUrl(String relativeUrl) {
    Log.d("API", "request: " + BASE_URL + relativeUrl);
    return BASE_URL + relativeUrl;
  }
}
