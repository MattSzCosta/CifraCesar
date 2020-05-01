package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonProperty

class AnswerModel (
        @JsonProperty("numero_casa")
        val numero_casas: Int,

        @JsonProperty("token")
        val token: String,

        @JsonProperty("cifrado")
        val cifrado: String,

        @JsonProperty("decifrado")
        var decifrado: String,

        @JsonProperty("resumo_criptografico")
        var resumo_criptografico: String
)