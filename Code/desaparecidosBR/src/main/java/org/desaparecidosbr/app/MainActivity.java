
package org.desaparecidosbr.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.desaparecidosbr.app.adapters.PessoasAdapter;
import org.desaparecidosbr.app.api.ApiListener;
import org.desaparecidosbr.app.api.PessoasApi;
import org.desaparecidosbr.app.entity.Pessoa;
import org.desaparecidosbr.app.utils.MyApplication;
import org.desaparecidosbr.app.utils.MyApplication.TrackerName;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This activity is used to let the user chose the default page, and to redirect
 * the user to that page on start.
 */

public class MainActivity extends Activity implements ApiListener {

    private ArrayList<Pessoa> pessoasList = null;
    private PessoasAdapter pessoasAdapter = null;
    private ListView pessoasListView = null;
    public static ProgressDialog progressDialog;
    
    // private ShareActionProvider mShareActionProvider;
    @Override
    protected void onStart() {
       super.onStart();
        GoogleAnalytics.getInstance(this).reportActivityStart(this);
    }
    @Override
    protected void onStop() {
        super.onStop();
        GoogleAnalytics.getInstance(this).reportActivityStop(this);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        GoogleAnalytics.getInstance(this).newTracker("UA-1814735-20");
        Tracker t = ((MyApplication) getApplication()).getTracker(
                TrackerName.APP_TRACKER);
        setContentView(R.layout.activity_main);
        // The factory instance is re-useable and thread safe.
        final EditText pessoasFilter = (EditText) findViewById(R.id.pessoas_filter);
        pessoasFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count < before) {
                    pessoasAdapter.resetData();
                }
                if (pessoasAdapter != null)
                    pessoasAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

        pessoasListView = (ListView) findViewById(R.id.pessoas_list);

        pessoasListView.setOnItemClickListener(new OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v, int position, long arg3)
            {
                // Log.d("lnp2", Integer.toString(position));
                Pessoa pessoa = pessoasAdapter.getItem(position);
                Intent i = new Intent(MainActivity.this, PessoaActivity.class);
                i.putExtra("pessoa", pessoa);
                // i.putExtra("pessoa_nome", pessoa.getNome());

                startActivity(i);

            }
        });
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage(getString(R.string.carregando));
        progressDialog.setCancelable(false);

        PessoasApi api = new PessoasApi(this, this);
        api.doRequest(0, new HashMap<String, String>());

        // Utility.addAds((AdView) this.findViewById(R.id.adView));
    }

    // public boolean isOnline() {
    // ConnectivityManager cm = (ConnectivityManager)
    // this.getSystemService(Context.CONNECTIVITY_SERVICE);
    // NetworkInfo ni = cm.getActiveNetworkInfo();
    //
    // if (ni != null && ni.isConnected())
    // return true;
    // else
    // return false;
    // }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.menu, menu);
        inflater.inflate(R.menu.menu_main, menu);
        getActionBar().setDisplayHomeAsUpEnabled(false);

        // // Locate MenuItem with ShareActionProvider
        // MenuItem item = menu.findItem(R.id.menu_item_share);
        //
        // // Fetch and store ShareActionProvider
        // mShareActionProvider = (ShareActionProvider)
        // item.getActionProvider();
        // mShareActionProvider.setShareIntent(getDefaultShareIntent());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                String info = getResources().getString(R.string.atencao) + "."
                        + getString(R.string.origem_dados);
                builder.setMessage(info);
                builder.setNegativeButton(getResources().getString(R.string.ok), null)
                        .setCancelable(true);
                builder.create();
                builder.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // /** Returns a share intent */
    // private Intent getDefaultShareIntent(){
    // Intent intent = new Intent(Intent.ACTION_SEND);
    // intent.setType("text/plain");
    // intent.putExtra(Intent.EXTRA_SUBJECT, "SUBJECT");
    // intent.putExtra(Intent.EXTRA_TEXT,"Extra Text");
    // return intent;
    // }
    // // Call to update the share intent
    // private void setShareIntent(Intent shareIntent) {
    // if (mShareActionProvider != null) {
    // mShareActionProvider.setShareIntent(shareIntent);
    // }
    // }

    @Override
    public void onPreExecute() {
        // TODO Auto-generated method stub
        progressDialog.show();
    }

    @Override
    public void onRequestEnds(int operation, boolean isSuccess, ArrayList<?> parsedData) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onRequestEnds(boolean isSuccess, ArrayList<?> parsedData) {
        pessoasList = (ArrayList<Pessoa>) parsedData;
        pessoasAdapter = new PessoasAdapter(this, pessoasList);
        final ListView lojasListView = (ListView) findViewById(R.id.pessoas_list);
        // lojasAdapter.setItens(lojasList);
        lojasListView.setAdapter(pessoasAdapter);
        if (progressDialog.isShowing())
            progressDialog.dismiss();
        // Utility.setListViewSize2(pessoasListView);
        // progressDialog.dismiss();

    }

    @Override
    public void onRequestEnds(boolean isSuccess, JSONArray parsedData) {
        // TODO Auto-generated method stub

    }
}
