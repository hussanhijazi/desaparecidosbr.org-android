package org.desaparecidosbr.api;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import org.desaparecidosbr.entity.Pessoa;
import org.json.JSONArray;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PessoasApi extends ApiCore {

    private Gson gson;

    public PessoasApi(Activity activity, ApiListener apiListener) {
        super(activity, apiListener);
    }

    @Override
    public String getUrl(HashMap<String, String> parameters) {
        return createUrl(ApiCore.BASE_URL + "api/pessoas", parameters);
    }

    @Override
    public void handleJsonResponse(int operation, JSONArray array) {

        ArrayList<Pessoa> arrayList = new ArrayList<Pessoa>();
        Type collectionType = new TypeToken<ArrayList<Pessoa>>() {
        }.getType();
        gson = new GsonBuilder().create();
        try {
            
          //  int nObjs = array.length();
            arrayList = gson.fromJson(array.toString(), collectionType);
            //for (int i = 0; i < nObjs; i++) {
                
              //  Tweet tweet = gson.fromJson(object.toString(), Tweet.class);
//              arrayList = gson.fromJson(array.toString(), collectionType);

//                lojasAdapter = new PessoasAdapter(context, lojasList);
//                // lojasAdapter.setItens(lojasList);
//                lojasListView.setAdapter(lojasAdapter);
//                
//                arrayList.add(tweet);
         //   }

            if (mApiListener != null) {
                mApiListener.onRequestEnds(true, arrayList);
            }
        } catch (Exception e) {
            if (mApiListener != null) {
                mApiListener.onRequestEnds( false, arrayList);
            }
        }
    }
}