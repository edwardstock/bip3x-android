package com.edwardstock.bip3x

import com.edwardstock.bip3x.HDKeyEncoder.makeBip32RootKey
import com.edwardstock.bip3x.HDKeyEncoder.makeExtendedKey
import org.junit.Assert
import org.junit.Test
import java.nio.charset.StandardCharsets

class HDKeyEncoderTest {
    @Test
    fun testMakeBip39RootKey() {
        val seedString =
            "f01e96ba468700a7fa92b8fdf500b8d3cef5cd88e1592a83f31631e9c3f3ed86cffbaba747e2d3f00476b17f3c8b4c19f3f6577cf619464886402ce0faeef01c"
        val seed = StringHelper.hexStringToBytes(seedString)
        Assert.assertEquals(64, seed.size.toLong())
        val rootKey = makeBip32RootKey(seed)
        Assert.assertNotNull("HDKey is null", rootKey)
        Assert.assertNotNull("Priv key is null", rootKey.publicKeyBytes)
        Assert.assertNotNull("Pub key is null", rootKey.privateKeyBytes)
        Assert.assertNotNull("Chain code is null", rootKey.chainCodeBytes)
        Assert.assertNotNull("Ext priv key is null", rootKey.extPrivateKeyBytes)
        Assert.assertNotNull("Ext pub key is null", rootKey.extPublicKeyBytes)
        Assert.assertEquals(HDKey.PUB_KEY_LEN.toLong(), rootKey.publicKeyBytes.size.toLong())
        Assert.assertEquals(HDKey.PRIV_KEY_LEN.toLong(), rootKey.privateKeyBytes.size.toLong())
        Assert.assertEquals(HDKey.CHAIN_CODE_LEN.toLong(), rootKey.chainCodeBytes.size.toLong())
        Assert.assertEquals(HDKey.EXT_PUB_KEY_LEN.toLong(), rootKey.extPublicKeyBytes.size.toLong())
        Assert.assertEquals(HDKey.EXT_PRIV_KEY_LEN.toLong(), rootKey.extPrivateKeyBytes.size.toLong())
        val extPrivKeyString = rootKey.extPrivateKeyString
        Assert.assertEquals(
            "xprv9s21ZrQH143K2Pr9zz5gPaxJHrJj1YR5As1SA2z6D5a9yTkN9nhUMt2Z1CJxFfSe8VzxmGYeeuVi26Uim7papujvs4hf5dwauQFrqgEU7Nf",
            extPrivKeyString
        )
    }

    @Test
    fun testMakeBipExtendedKey() {
        val seedString =
            "f01e96ba468700a7fa92b8fdf500b8d3cef5cd88e1592a83f31631e9c3f3ed86cffbaba747e2d3f00476b17f3c8b4c19f3f6577cf619464886402ce0faeef01c"
        val seed = StringHelper.hexStringToBytes(seedString)
        Assert.assertEquals(64, seed.size.toLong())
        val rootKey = makeBip32RootKey(seed)
        Assert.assertNotNull(rootKey)
        val extKey = makeExtendedKey(rootKey)
        val b44ExtPriv = "xprvA41WfdCkwqbHFi9MTEJrJ1RxPhZXgYzVUkpzXNht4RtQ7V3K1t8QkCTNDEGNxHnck4cN9J4A6bxj3vG6FYQK5gQuSEkiRFV9VLaaiiytAyJ"
        val b44ExtPub = "xpub6Gzs58jenD9aUCDpZFqrf9NgwjQ261iLqykbKm7VcmRNzHNTZRSfHzmr4Wv5ffJyBAQD8noAsTR6xkJbspPwv9UXimt9HhJPKymZwWDZGWD"
        Assert.assertEquals(
            b44ExtPriv,
            extKey.extPrivateKeyString
        )
        Assert.assertEquals(
            b44ExtPub,
            extKey.extPublicKeyString
        )
    }

    companion object {
        init {
            BIP3X.init()
        }
    }
}