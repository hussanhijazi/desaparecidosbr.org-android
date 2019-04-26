package org.desaparecidosbr.app.entity

import java.io.Serializable

data class Pessoa(var id: Int = 0,
                  var nome: String? = null,

                  var foto: String? = null,
                  var data_des: String? = null,
                  var cidade: String? = null,
                  var estado: String? = null,
                  var feito_boletim: String? = null,
                  var sexo: String? = null,
                  var idade_des: String? = null,
                  var transtorno_mental: String? = null,
                  var tipo_fisico: String? = null,
                  var pele: String? = null,
                  var peso: String? = null,
                  var altura: String? = null,
                  var olhos: String? = null,
                  var cabelos: String? = null,
                  var data_registro: String? = null,
                  var data_atualizacao: String? = null,
                  var situacao: String? = null) : Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
}
