package com.edwardstock.bip3x

import com.edwardstock.bip3x.BIP3X.Companion.encodeBytes
import com.edwardstock.bip3x.BIP3X.Companion.getWordsFromLanguage
import com.edwardstock.bip3x.BIP3X.Companion.languages
import com.edwardstock.bip3x.BIP3X.Companion.mnemonicToBip39Seed
import com.edwardstock.bip3x.BIP3X.Companion.validateMnemonic
import com.edwardstock.bip3x.HDKeyEncoder.makeBip32RootKey
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * * entropy: f0b9c942b9060af6a82d3ac340284d7e
 * * words: vague soft expose improve gaze kitten pass point select access battle wish
 * * bip39seed: f01e96ba468700a7fa92b8fdf500b8d3cef5cd88e1592a83f31631e9c3f3ed86cffbaba747e2d3f00476b17f3c8b4c19f3f6577cf619464886402ce0faeef01c
 * * bip32root key: xprv9s21ZrQH143K2Pr9zz5gPaxJHrJj1YR5As1SA2z6D5a9yTkN9nhUMt2Z1CJxFfSe8VzxmGYeeuVi26Uim7papujvs4hf5dwauQFrqgEU7Nf
 * *
 * * bip44:
 * * network: 36 (ETH)
 * * purpose: 44
 * * coin: 60
 * * accout: 0
 * * external/internal: 0/1
 * *
 * * derivation path: m/44'/60'/0'/0
 * * acc ext. private: xprv9zPRRprz3mGyL7YLgAFT1PoJ787ZZroCHxPpVdEhTaxsLh1uowZyX8cGMdbrmibV9bXBNMUtA6TGePGQrw5tWaM4VFFwwqFo52xTL8EXzZH
 * * acc ext. public:  xpub6DNmqLPst8qGYbconBnTNXk2f9x3yKX3fBKRJ1eK1vVrDVM4MUtE4vvkCw6N6Zj5YYTQB9G723vkNHaxEA7acuM5J2qH7QSs1ryRJ8Mb8kF
 */
class BIP3XTest {
    companion object {
        init {
            BIP3X.init()
        }
    }

    @Test
    fun testGetLanguages() {
        val langs = languages
        assertNotNull(langs)
        assertEquals(7, langs.size)
    }

    @Test
    fun testGetEnglishWords() {
        val words = getWordsFromLanguage(BIP3X.LANG_DEFAULT)
        assertEquals(2048, words.size)
        assertEquals("abandon", words[0])
        assertEquals("zoo", words[words.size - 1])
    }

    @Test
    fun testEncodeEntropy() {
        // 16 bytes
        val entropy = StringHelper.hexStringToBytes("f0b9c942b9060af6a82d3ac340284d7e")
        assertEquals(16, entropy.size)
        val res = encodeBytes(entropy, BIP3X.LANG_DEFAULT, BIP3X.ENTROPY_LEN_128)
        assertEquals(12, res.wordsCount)
        assertEquals("vague soft expose improve gaze kitten pass point select access battle wish", res.mnemonic)
        assertTrue(res.getStatus(), res.isOk)
    }

    @Test
    fun testValidateMnemonicWords() {
        val mnemonic = "vague soft expose improve gaze kitten pass point select access battle wish"
        assertTrue(validateMnemonic(mnemonic, BIP3X.LANG_DEFAULT))
        assertFalse(validateMnemonic(mnemonic, "it"))
        assertFalse(
            validateMnemonic(
                "unknown unknown unknown unknown unknown unknown unknown unknown unknown unknown unknown unknown",
                "en"
            )
        )
    }

    @Test
    fun testMakeBip39Seed() {
        val mnemonic = "vague soft expose improve gaze kitten pass point select access battle wish"
        val seed = mnemonicToBip39Seed(mnemonic)
        assertNotNull(seed)
        assertEquals(64, seed.size)
        val seedHex = StringHelper.bytesToHexString(seed)
        assertEquals(
            "Given seed: $seedHex",
            "f01e96ba468700a7fa92b8fdf500b8d3cef5cd88e1592a83f31631e9c3f3ed86cffbaba747e2d3f00476b17f3c8b4c19f3f6577cf619464886402ce0faeef01c",
            seedHex
        )
    }

    @Test
    fun testGenerateMnemonic() {
        val res = BIP3X.generate()
        assertNotNull(res.mnemonic)
        assertEquals(12, res.wordsCount)
        assertTrue(res.getStatus(), res.isOk)
    }

    @Test
    fun testSigningMessage() {
        val privateKey = "fae45a8d43fbea23bc6450c832f3f1ad20f9f3022b4c534e1edcfbb44fc439a3"
        val message = "Hello, Hyperlens!"

        val result = BIP3X.signMessage(privateKey.toByteArrayFromHex(), message.toByteArray())
        assertEquals("0ae77a1bcaaf0e28519a06221bc3d74a27ffd7fb1ffca6760b84b579184f0b45", result.r.toHex())
        assertEquals("552c5491a59d18b81fa4882725c019a0351d0f3e03bd70d23c25390daf13e6eb", result.s.toHex())
        assertEquals("1b", result.v.toHex())
        assertEquals(
            "0ae77a1bcaaf0e28519a06221bc3d74a27ffd7fb1ffca6760b84b579184f0b45552c5491a59d18b81fa4882725c019a0351d0f3e03bd70d23c25390daf13e6eb1b",
            result.signature.toHex()
        )
    }

    @Test
    fun testGetEthAddress() {
        val mnemonic = "vague soft expose improve gaze kitten pass point select access battle wish"
        val seed = mnemonicToBip39Seed(mnemonic)
        val key = makeBip32RootKey(seed).extend("m/44'/60'/0'/0/0")
        val ethAddress = BIP3X.getEthAddress(key.privateKeyBytes)

        assertEquals("0xa425ce86fe337ba87429f733ae3ad2606efcae20", ethAddress)
    }


}