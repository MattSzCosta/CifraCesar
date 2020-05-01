package com.example.demo.service

import com.example.demo.model.AnswerModel
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.swing.JOptionPane

interface IoService {
    fun writeFile(content: AnswerModel, name:String)

    fun readFile(name:String): File?
}

@Service
class IoServiceImpl: IoService{

    override fun writeFile(content: AnswerModel, name: String) {
        val file = File(System.getProperty("user.dir") + '\\' + name)

        print(System.getProperty("user.dir") + '\\' + name)
        if (!file.exists()) {
            file.createNewFile()
        }
        val buffer = BufferedWriter(FileWriter(file.absoluteFile))
        buffer.write(ObjectMapper().writeValueAsString(content))
        buffer.close()

    }

    override fun readFile(name:String): File? {
        try {
            return File(System.getProperty("user.dir") + '\\' + name)
        } catch (e: IOException) {
            JOptionPane.showMessageDialog(null, e.message)
        }
        return null
    }

}