package util

import java.util.UUID

object Security {
    def getUUID: String = UUID.randomUUID.toString.replace("-", "")
    def sha256Hash(text: String) : String = String.format("%064x", new java.math.BigInteger(1, java.security.MessageDigest.getInstance("SHA-256").digest(text.getBytes("UTF-8"))))
}