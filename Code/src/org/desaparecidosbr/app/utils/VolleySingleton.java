
package org.desaparecidosbr.app.utils;
import java.util.HashMap;
import java.util.Map;

import org.desaparecidosbr.app.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VolleySingleton {
    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingleton(Context cont) {
        mRequestQueue = Volley.newRequestQueue(cont);
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(Context cont) {
        if (mInstance == null) {
            mInstance = new VolleySingleton(cont);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return this.mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }
    public static String getMessage(Object error, Context context) {
        if (error instanceof TimeoutError) {
            return "Erro no nosso server. Tente novamente mais tarde.";
        }
        else if (isServerProblem(error)) {
            return handleServerError(error, context);
        }
        else if (isNetworkProblem(error)) {
            return context.getResources().getString(R.string.no_connection);
        }
        return error.toString();
    }
    
    /**
    * Determines whether the error is related to network
    * @param error
    * @return
    */
    private static boolean isNetworkProblem(Object error) {
        return (error instanceof NetworkError) || (error instanceof NoConnectionError);
    }
    /**
    * Determines whether the error is related to server
    * @param error
    * @return
    */
    private static boolean isServerProblem(Object error) {
        return (error instanceof ServerError) || (error instanceof AuthFailureError);
    }
    /**
    * Handles the server error, tries to determine whether to show a stock message or to 
    * show a message retrieved from the server.
    * 
    * @param err
    * @param context
    * @return
    */
    private static String handleServerError(Object err, Context context) {
        VolleyError error = (VolleyError) err;
    
        NetworkResponse response = error.networkResponse;
        if (response != null) {
            switch (response.statusCode) {
              case 404:
              case 422:
              case 401:
                  try {
                      // server might return error like this { "error": "Some error occured" }
                      // Use "Gson" to parse the result
                      HashMap<String, String> result = new Gson().fromJson(new String(response.data),
                              new TypeToken<Map<String, String>>() {
                              }.getType());

                      if (result != null && result.containsKey("error")) {
                          return result.get("error");
                      }

                  } catch (Exception e) {
                      e.printStackTrace();
                  }
                  // invalid request
                  return error.getMessage();

              default:
                  return "Erro no nosso server. Tente novamente mais tarde.";
              }
        }
          return "VolleryError";
    }

}
