package org.desaparecidosbr.app

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_img
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_situacao
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_altura
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_boletim
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_cabelos
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_cidade
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_data_des
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_data_registro
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_estado
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_nome
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_olhos
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_pele
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_peso
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_sexo
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_tipo_fisico
import kotlinx.android.synthetic.main.activity_pessoa.pessoa_txt_transtorno
import org.desaparecidosbr.app.data.PeopleDataSource
import org.desaparecidosbr.app.data.PeopleRepository
import org.desaparecidosbr.app.entity.Pessoa
import org.desaparecidosbr.app.utils.Utility

//
//import com.facebook.Session;
//import com.facebook.SessionState;

/**
 * This activity is used to let the user chose the default page, and to redirect
 * the user to that page on start.
 */

class PessoaActivity : AppCompatActivity() {


    private lateinit var person: Pessoa
    val dataSource: PeopleDataSource by lazy {
        PeopleRepository()
    }


    //
//    val progressDialog: ProgressDialog
    //    //    private RequestQueue rq;
    //    // private ShareActionProvider mShareActionProvider;
    //    private Pessoa p;
    //    private String message;
    //    private UiLifecycleHelper uiHelper;
    //    private GraphUser user;
    //    private GraphPlace place;
    //    private List<GraphUser> tags;

    @SuppressLint("SetTextI18n")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pessoa)

        person = intent.getSerializableExtra("pessoa") as Pessoa

        pessoa_situacao.text = person.situacao?.toUpperCase()

//
        val color = if (person.situacao?.toLowerCase().equals("desaparecido(a)"))
            resources.getColor(
                    R.color.pink) else
            resources.getColor(R.color.green_escuro)
        pessoa_situacao.setTextColor(color);
        pessoa_txt_nome.text = person.nome;
        pessoa_txt_data_des.text = (Utility.formatData(person.data_des)
                + " com " + person.idade_des + " anos");
        pessoa_txt_cidade.text = person.cidade
        pessoa_txt_estado.text = person.estado
        pessoa_txt_boletim.text = person.feito_boletim
        pessoa_txt_sexo.text = person.sexo
        pessoa_txt_pele.text = person.pele
        pessoa_txt_peso.text = person.peso
        pessoa_txt_altura.text = person.altura
        pessoa_txt_olhos.text = person.olhos
        pessoa_txt_cabelos.text = person.cabelos

        pessoa_txt_transtorno.text = person.transtorno_mental
        pessoa_txt_tipo_fisico.text = person.tipo_fisico

        pessoa_txt_data_registro.text = ("Registrado em: "
                + Utility.formatData(person.data_registro) + " e atualizado em: "
                + Utility.formatData(person.data_atualizacao));
        person.foto?.let {
            Picasso.get().load(person.foto).into(pessoa_img)
        }
        //
        //        Button compartilhar = (Button) findViewById(R.id.pessoa_share);
        //
        //        compartilhar.setOnClickListener(new View.OnClickListener() {
        //            public void onClick(View view) {
        //                progressDialog.show();
        //
        //                loginFacebook();
        //
        //            }
        //        });
        //        if (progressDialog.isShowing())
        //            progressDialog.dismiss();
        ////        Log.d("lnp", people.getData_des());
        ////        Log.d("lnp", people.getNome());

    }
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

    //    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
    ////        if (state.isOpened()) {
    ////            Log.i("lnp", "Logged in...");
    ////        } else if (state.isClosed()) {
    ////            Log.i("lnp", "Logged out...");
    ////        }
    //    }

    private fun hasPublishPermission(): Boolean {
        //        Session session = Session.getActiveSession();
        //        return session != null && session.getPermissions().contains("publish_actions");
        return true
    }

    private fun postarFacebook() {
        //        if (canPresentShareDialog) {
        //            FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
        //                    .setName("Pessoa Desaparecida")
        //                    .setDescription(message)
        //                    .setCaption(message)
        //                    .setPicture(people.getFoto())
        //                    .setLink(
        //                            "http://www.desaparecidos.gov.br/desaparecidos/application/modulo/detalhes.php?id="
        //                                    + people.getId())
        //                    .build();
        //
        //            uiHelper.trackPendingDialogCall(shareDialog.present());
        //
        //
        //        } else if (user != null && hasPublishPermission()) {
        //            // final String message = getString(R.string.status_update,
        //            // user.getFirstName(),
        //            // (new Date().toString()));
        //            Request request = Request
        //                    .newStatusUpdateRequest(Session.getActiveSession(), message, place, tags,
        //                            new Request.Callback() {
        //                                @Override
        //                                public void onCompleted(Response response) {
        //                                    // Toast toast =
        //                                    // Toast.makeText(PessoaActivity.this,
        //                                    // "Post adicionado com sucesso a sua conta do facebook!",
        //                                    // Toast.LENGTH_SHORT);
        //                                }
        //                            });
        //            request.executeAsync();
        //        }

    }

    private fun loginFacebook() {

        //        // start Facebook Login
        //        Session.openActiveSession(this, true, new Session.StatusCallback() {
        //
        //            // callback when session changes state
        //            @Override
        //            public void call(Session session, SessionState state, Exception exception) {
        //                if (session.isOpened()) {
        //                    // session.requestNewPublishPermissions(new
        //                    // Session.NewPermissionsRequest(PessoaActivity.this,
        //                    // PERMISSION));
        //                    // make request to the /me API
        //                    Request.newMeRequest(session, new Request.GraphUserCallback() {
        //
        //                        // callback after Graph API response with user object
        //                        @Override
        //                        public void onCompleted(GraphUser user, Response response) {
        //                            if (user != null) {
        //                                // TextView welcome = (TextView)
        //                                // findViewById(R.id.welcome);
        //                                // welcome.setText("Hello " + user.getName() +
        //                                // "!");
        //                                postarFacebook();
        //                            }
        //                        }
        //
        //                    }).executeAsync();
        //                }
        //            }
        //        });

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
        //            @Override
        //            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
        //                // track on error
        //            }
        //
        //            @Override
        //            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
        //                if (progressDialog.isShowing())
        //                    progressDialog.dismiss();
        //                if (FacebookDialog.getNativeDialogDidComplete(data)) {
        //                    if (FacebookDialog.getNativeDialogCompletionGesture(data) == null
        //                            || FacebookDialog.COMPLETION_GESTURE_CANCEL.equals(FacebookDialog
        //                            .getNativeDialogCompletionGesture(data))) {
        //                        // track cancel
        //                    } else {
        //
        //                    }
        //                } else {
        //                    // track cancel
        //                }
        //            }
        //        });

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //        MenuInflater inflater = getMenuInflater();
        //        // inflater.inflate(R.menu.menu, menu);
        //        inflater.inflate(R.menu.menu_main, menu);
        //        getActionBar().setDisplayHomeAsUpEnabled(false);

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //        switch (item.getItemId()) {
        //            case R.id.info:
        //
        //                AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //
        //                String info = getResources().getString(R.string.atencao) + " "
        //                        + getString(R.string.origem_dados);
        //                builder.setMessage(info);
        //                builder.setNegativeButton(getResources().getString(R.string.ok), null)
        //                        .setCancelable(true);
        //                builder.create();
        //                builder.show();
        //                return true;
        //
        //            default:
        return super.onOptionsItemSelected(item)
        //        }
        //        }
    }

    protected fun facebookLogin() {
        // start Facebook Login

    }

}
