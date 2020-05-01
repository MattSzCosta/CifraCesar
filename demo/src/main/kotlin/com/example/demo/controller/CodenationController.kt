package com.example.demo.controller

import com.example.demo.service.CipherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CodenationController {

    @Autowired
    lateinit var cipherService: CipherService

    @GetMapping("/cipher")
    fun cipher(@RequestParam token: String): ResponseEntity<Any>{
        return ResponseEntity(cipherService.executeCipher(token),HttpStatus.OK)
    }
}