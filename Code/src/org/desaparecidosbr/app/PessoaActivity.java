
package org.desaparecidosbr.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.desaparecidosbr.app.entity.Pessoa;
import org.desaparecidosbr.app.utils.MyApplication;
import org.desaparecidosbr.app.utils.Utility;
import org.desaparecidosbr.app.utils.VolleySingleton;
import org.desaparecidosbr.app.utils.MyApplication.TrackerName;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphPlace;
import com.facebook.model.GraphUser;
import com.facebook.widget.FacebookDialog;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * This activity is used to let the user chose the default page, and to redirect
 * the user to that page on start.
 */

public class PessoaActivity extends Activity {

    private RequestQueue rq;
    // private ShareActionProvider mShareActionProvider;
    private Pessoa p;
    private String message;
    private UiLifecycleHelper uiHelper;
    private GraphUser user;
    private GraphPlace place;
    private List<GraphUser> tags;
    private boolean canPresentShareDialog;
    public static ProgressDialog progressDialog;
    
    
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
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tracker t = ((MyApplication) getApplication()).getTracker(
                TrackerName.APP_TRACKER);
        progressDialog = new ProgressDialog(PessoaActivity.this);
        progressDialog.setMessage(getString(R.string.carregando));
        progressDialog.setCancelable(false);
        setContentView(R.layout.activity_pessoa);
        uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        // Can we present the share dialog for regular links?
        canPresentShareDialog = FacebookDialog.canPresentShareDialog(this,
                FacebookDialog.ShareDialogFeature.SHARE_DIALOG);
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "org.desaparecidosbr.app",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        // 1. get passed intent
        Intent intent = getIntent();
        // 2. get person object from intent
        p = (Pessoa) intent.getSerializableExtra("pessoa");
        message = p.getNome()
                + " desapareceu no dia "
                + Utility
                        .formatData(p.getData_des()) + " em " + p.getCidade() + "/" + p.getEstado()
                + ". " + getString(R.string.atencao);

        ((TextView) findViewById(R.id.pessoa_situacao)).setText(p.getSituacao().toUpperCase());
//        Log.d("lnp", p.getSituacao());
        int color =
                (p.getSituacao().equalsIgnoreCase("Desaparecido(a)")) ? getResources().getColor(
                        R.color.pink) : getResources().getColor(R.color.green_escuro);
        ((TextView) findViewById(R.id.pessoa_situacao)).setTextColor(color);
        ((TextView) findViewById(R.id.pessoa_txt_nome)).setText(p.getNome());
        ((TextView) findViewById(R.id.pessoa_txt_data_des)).setText(Utility.formatData(p
                .getData_des())
                + " com " + p.getIdade_des() + " anos");
        ((TextView) findViewById(R.id.pessoa_txt_cidade)).setText(p.getCidade());
        ((TextView) findViewById(R.id.pessoa_txt_estado)).setText(p.getEstado());
        ((TextView) findViewById(R.id.pessoa_txt_boletim))
                .setText((p.getFeito_boletim() == "S") ? "Sim" : "N‹o");
        ((TextView) findViewById(R.id.pessoa_txt_sexo)).setText(p.getSexo());
        ((TextView) findViewById(R.id.pessoa_txt_pele)).setText(p.getPele());
        ((TextView) findViewById(R.id.pessoa_txt_peso)).setText(p.getPeso());
        ((TextView) findViewById(R.id.pessoa_txt_altura)).setText(p.getAltura());
        ((TextView) findViewById(R.id.pessoa_txt_olhos)).setText(p.getOlhos());
        ((TextView) findViewById(R.id.pessoa_txt_cabelos)).setText(p.getCabelos());

        ((TextView) findViewById(R.id.pessoa_txt_transtorno))
                .setText((p.getTranstorno_mental() == "S") ? "Sim" : "N‹o");
        ((TextView) findViewById(R.id.pessoa_txt_tipo_fisico)).setText(p.getTipo_fisico());

        ((TextView) findViewById(R.id.pessoa_txt_data_registro)).setText("Registrado em: "
                + Utility.formatData(p.getData_registro()) + " e atualizado em: "
                + Utility.formatData(p.getData_atualizacao()));
        // ((TextView)
        // findViewById(R.id.pessoa_txt_data_atualizacao)).setText();

        rq = VolleySingleton.getInstance(this.getApplicationContext())
                .getRequestQueue();
        final ImageLoader mImageLoader = new ImageLoader(rq,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(
                            10);

                    public void putBitmap(String url, Bitmap bitmap) {
                        mCache.put(url, bitmap);
                    }

                    public Bitmap getBitmap(String url) {
                        return mCache.get(url);
                    }
                });
        String str = p.getFoto();
        // String str2 = str.replace("newwidth=250&newheight=250",
        // "newwidth=200&newheight=200");
        ((NetworkImageView) findViewById(R.id.pessoa_img)).setImageUrl(str, mImageLoader);

        Button compartilhar = (Button) findViewById(R.id.pessoa_share);

        compartilhar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                progressDialog.show();

                loginFacebook();

            }
        });
        Utility.addAds((AdView) this.findViewById(R.id.adView));
        if (progressDialog.isShowing())
            progressDialog.dismiss();
//        Log.d("lnp", p.getData_des());
//        Log.d("lnp", p.getNome());

    }

    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }

    };
//    private FacebookDialog.Callback dialogCallback = new FacebookDialog.Callback() {
//        @Override
//        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
//            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
//        }
//
//        @Override
//        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
//            Log.d("HelloFacebookCOMPLETE", String.format("Error: %s", pendingCall.toString()));
//        }
//    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
//        if (state.isOpened()) {
//            Log.i("lnp", "Logged in...");
//        } else if (state.isClosed()) {
//            Log.i("lnp", "Logged out...");
//        }
    }

    private boolean hasPublishPermission() {
        Session session = Session.getActiveSession();
        return session != null && session.getPermissions().contains("publish_actions");
    }

    private void postarFacebook()
    {
        if (canPresentShareDialog) {
            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
                    .setName("Pessoa Desaparecida")
                    .setDescription(message)
                    .setCaption(message)
                    .setPicture(p.getFoto())
                    .setLink(
                            "http://www.desaparecidos.gov.br/desaparecidos/application/modulo/detalhes.php?id="
                                    + p.getId())
                    .build();

            uiHelper.trackPendingDialogCall(shareDialog.present());
          

        } else if (user != null && hasPublishPermission()) {
            // final String message = getString(R.string.status_update,
            // user.getFirstName(),
            // (new Date().toString()));
            Request request = Request
                    .newStatusUpdateRequest(Session.getActiveSession(), message, place, tags,
                            new Request.Callback() {
                                @Override
                                public void onCompleted(Response response) {
                                    // Toast toast =
                                    // Toast.makeText(PessoaActivity.this,
                                    // "Post adicionado com sucesso a sua conta do facebook!",
                                    // Toast.LENGTH_SHORT);
                                }
                            });
            request.executeAsync();
        }

    }

    private void loginFacebook()
    {

        // start Facebook Login
        Session.openActiveSession(this, true, new Session.StatusCallback() {

            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    // session.requestNewPublishPermissions(new
                    // Session.NewPermissionsRequest(PessoaActivity.this,
                    // PERMISSION));
                    // make request to the /me API
                    Request.newMeRequest(session, new Request.GraphUserCallback() {

                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                // TextView welcome = (TextView)
                                // findViewById(R.id.welcome);
                                // welcome.setText("Hello " + user.getName() +
                                // "!");
                                postarFacebook();
                            }
                        }

                    }).executeAsync();
                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback()
        {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data)
            {
             // track on error                                                   
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data)
            {
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
                if (FacebookDialog.getNativeDialogDidComplete(data))
                {
                    if (FacebookDialog.getNativeDialogCompletionGesture(data) == null 
                          || FacebookDialog.COMPLETION_GESTURE_CANCEL.equals(FacebookDialog.getNativeDialogCompletionGesture(data)))
                    {
                        // track cancel                                                   
                    }
                    else
                    {
                        
                    }
                }
                else
                {
                    // track cancel              
                }
            }
        });

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

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                String info = getResources().getString(R.string.atencao) + " "
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

    protected void facebookLogin()
    {
        // start Facebook Login

    }

}
