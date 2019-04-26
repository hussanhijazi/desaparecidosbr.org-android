package org.desaparecidosbr.app

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.EditText
import android.widget.ListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.desaparecidosbr.app.adapters.PessoasAdapter
import org.desaparecidosbr.app.data.PeopleDataSource
import org.desaparecidosbr.app.data.PeopleRepository
import org.desaparecidosbr.app.entity.Pessoa
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var pessoasList: ArrayList<Pessoa>? = null
    private var pessoasAdapter: PessoasAdapter? = null
    private var pessoasListView: ListView? = null
    lateinit var progressDialog: ProgressDialog

    val dataSource: PeopleDataSource by lazy {
        PeopleRepository()
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pessoasFilter = findViewById<View>(R.id.pessoas_filter) as EditText
        pessoasFilter.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count < before) {
                    pessoasAdapter?.resetData()
                }
                if (pessoasAdapter != null)
                    pessoasAdapter?.filter?.filter(s.toString())
            }

            override fun beforeTextChanged(arg0: CharSequence, arg1: Int, arg2: Int,
                                           arg3: Int) {
            }

            override fun afterTextChanged(arg0: Editable) {}
        })

        pessoasListView = findViewById<View>(R.id.pessoas_list) as ListView

        pessoasListView?.onItemClickListener = OnItemClickListener { arg0, v, position, arg3 ->
            val pessoa = pessoasAdapter?.getItem(position)
            val i = Intent(this@MainActivity, PessoaActivity::class.java)
            i.putExtra("pessoa", pessoa)
            startActivity(i)
        }
        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage(getString(R.string.carregando))
        progressDialog.setCancelable(false)

        dataSource.getPeople().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("h2", it.toString())
                    pessoasList = it as ArrayList<Pessoa>
                    pessoasAdapter = PessoasAdapter(this, pessoasList)
                    val lojasListView = findViewById<View>(R.id.pessoas_list) as ListView
                    lojasListView.adapter = pessoasAdapter

                    if (progressDialog.isShowing)
                        progressDialog.dismiss()
                }, {
                    it.printStackTrace()
                })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        actionBar?.setDisplayHomeAsUpEnabled(false)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.info -> {

                val builder = AlertDialog.Builder(this)

                val info = (resources.getString(R.string.atencao) + "."
                        + getString(R.string.origem_dados))
                builder.setMessage(info)
                builder.setNegativeButton(resources.getString(R.string.ok), null)
                        .setCancelable(true)
                builder.create()
                builder.show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}
