package com.edwardstock.bip3x

import java.util.Locale

object StringHelper {
    private const val HEX_NUM_PATTERN = "^(0x)?([a-fA-F0-9]{2,})$"

    @JvmStatic
    fun bytesToHexString(bytes: ByteArray, upperCase: Boolean = true, userFriendly: Boolean = false): String {
        val sb = StringBuilder(bytes.size * 2)
        for ((n, b) in bytes.withIndex()) {
            val i = b.toInt() and 0xff
            if (i < 0x10) {
                sb.append('0')
            }
            sb.append(Integer.toHexString(i))
            if (userFriendly) {
                if (n + 1 < bytes.size) {
                    sb.append(':')
                }
            }
        }
        return sb.toString().also {
            if (upperCase) {
                it.uppercase(Locale.getDefault())
            }
        }
    }

    fun hexStringToBytes(hexString: String): ByteArray {
        val len = hexString.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(hexString[i], 16) shl 4) + Character.digit(hexString[i + 1], 16)).toByte()
            i += 2
        }
        return data
    }

    fun bytesToString(hexString: String): String {
        return bytesToString(hexStringToBytes(hexString))
    }

    fun bytesToString(data: ByteArray?): String {
        return String(data!!)
    }

    fun bytesToString(data: ByteArray, readLength: Int): String {
        if (data.size < readLength) {
            throw ArrayIndexOutOfBoundsException(
                "Read length less than array size: " + readLength + " of " + data.size
            )
        }
        val out = CharArray(readLength)
        for (i in 0 until readLength) {
            out[i] = Char(data[i].toUShort())
        }
        return String(out)
    }

    fun testHex(s: String): Boolean {
        return s.matches(HEX_NUM_PATTERN.toRegex())
    }
}

fun String.toByteArrayFromHex(): ByteArray {
    return StringHelper.hexStringToBytes(this)
}

fun ByteArray.toHex(): String {
    return StringHelper.bytesToHexString(this)
}