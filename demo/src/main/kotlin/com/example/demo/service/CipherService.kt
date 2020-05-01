package com.example.demo.service

import com.example.demo.model.AnswerModel
import com.example.demo.model.ScoreDTO
import com.example.demo.repository.CodenationRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.MessageDigest


interface CipherService {
    fun executeCipher(token: String): ScoreDTO
}

@Service
class CipherServiceImpl : CipherService {
    @Autowired
    lateinit var codenationRepository: CodenationRepository

    @Autowired
    lateinit var ioService: IoService

    val SHA1 = "SHA-1"

    val name = "answer.json"

    override fun executeCipher(token: String): ScoreDTO {
        val answer = getAnswer(token)
        //write in a json file
        ioService.writeFile(answer,name)

        answer.decifrado = decrypt(answer.cifrado,answer.numero_casas)
        ioService.writeFile(answer,name)

        answer.resumo_criptografico = hashString(SHA1,answer.decifrado)
        ioService.writeFile(answer,name)

        val file = ioService.readFile(name)
        val filePart = MultipartBody.Part.createFormData("answer", file!!.name, RequestBody.create(MediaType.parse("text/*"), file))
        return codenationRepository.submit(token,filePart).execute().body()!!



    }

    //call the api and get the object
    private fun getAnswer(token: String): AnswerModel {

        val response = codenationRepository.getAnswer(token).execute()
        if(!response.isSuccessful)
            throw Exception("Service failed to call Codenation API")

        return response.body()!!
    }

    private fun decrypt(cipher:String, shift:Int): String{
        val ans = ArrayList<Char>()
        val offset = 26 - (shift % 26)
        for(char in cipher){
            if(char == ' ' || char == '.' || char == ',')
                ans.add(char)
            else {
                val originalAlphabetPosition: Int = char - 'a'
                val newAlphabetPosition: Int = (originalAlphabetPosition + offset) % 26
                val newCharacter = ('a'.toInt() + newAlphabetPosition).toChar()
                ans.add(newCharacter)
            }
        }

        return ans.joinToString("")
    }

    private fun hashString(type: String, input: String): String
            = MessageDigest.getInstance(type).digest(input.toByteArray()).map { it ->  "%02x".format(it)}.joinToString("")


}