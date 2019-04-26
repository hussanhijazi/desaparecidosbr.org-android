package org.desaparecidosbr.app.data

import io.reactivex.Observable
import org.desaparecidosbr.app.api.AppApi
import org.desaparecidosbr.app.entity.Pessoa
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

interface PeopleDataSource {
    fun getPeople(): Observable<List<Pessoa>>
}

class PeopleRepository : PeopleDataSource {
    private val api: AppApi by lazy {
        Retrofit.Builder()
                .baseUrl("http://www.desaparecidosbr.org/api/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create<AppApi>(AppApi::class.java)

    }

    override fun getPeople(): Observable<List<Pessoa>> {
        return api.getPeople()
    }

}
