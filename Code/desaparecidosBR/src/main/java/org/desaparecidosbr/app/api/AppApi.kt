package org.desaparecidosbr.app.api

import io.reactivex.Observable
import org.desaparecidosbr.app.entity.Pessoa
import retrofit2.http.GET

interface AppApi {
    @GET("pessoas")
    fun getPeople(): Observable<List<Pessoa>>
}
