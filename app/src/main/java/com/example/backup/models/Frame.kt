package com.example.backup.models

import com.example.backup.helper.array2OfByte

class Frame(private val wight: Int) {
    val data: ArrayList<ByteArray> = ArrayList()

    fun addRow(byteStr: String): Frame {
        val row = ByteArray(byteStr.length)

        for (index in byteStr.indices) {
            row[index] = "${byteStr[index]}".toByte()
        }

        data.add(row)
        return this
    }

    fun as2ByteArray():Array<ByteArray>{
        val bytes = array2OfByte(data.size,wight)
        return data.toArray(bytes)
    }
}