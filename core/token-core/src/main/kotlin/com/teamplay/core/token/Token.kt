package com.teamplay.core.token

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey
import kotlin.reflect.KClass

class Token(private val claims: Claims) : Claims {
    companion object {
        @JvmStatic
        fun from(claims: Map<String, Any>) = from(Jwts.claims(claims))

        @JvmStatic
        fun from(claims: Claims) = Token(claims)

        @JvmStatic
        fun decode(jwt: String, secretKey: String) = decode(jwt, Keys.hmacShaKeyFor(secretKey.toByteArray()))

        @JvmStatic
        fun decode(jwt: String, secretKey: SecretKey): Token {
            return Token(
                Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .body
            )
        }
    }

    override val entries: MutableSet<MutableMap.MutableEntry<String, Any>> = claims.entries
    override val keys: MutableSet<String> = claims.keys
    override val values: MutableCollection<Any> = claims.values
    override val size: Int = claims.size

    fun encode(secretKey: String) = encode(Keys.hmacShaKeyFor(secretKey.toByteArray()))

    fun encode(secretKey: SecretKey): String {
        return Jwts.builder()
            .setClaims(claims)
            .signWith(secretKey)
            .compact()
    }

    override fun containsKey(key: String) = claims.containsKey(key)

    override fun containsValue(value: Any) = claims.containsValue(value)

    override fun remove(key: String) = claims.remove(key)

    override fun putAll(from: Map<out String, Any>) = claims.putAll(from)

    override fun put(key: String, value: Any?) = claims.put(key, value)

    fun <T : Any> get(claimName: String, requiredType: KClass<T>): T? = get(claimName, requiredType.java)

    override fun <T : Any> get(claimName: String, requiredType: Class<T>): T? = claims.get(claimName, requiredType)

    override fun get(key: String): Any? = claims[key]

    override fun isEmpty() = claims.isEmpty()

    override fun clear() = claims.clear()

    override fun setId(jti: String?): Token {
        claims.id = jti
        return this
    }

    override fun getId(): String? = claims.id

    override fun setIssuedAt(iat: Date?): Token {
        claims.issuedAt = iat
        return this
    }

    override fun getIssuedAt(): Date? = claims.issuedAt

    override fun setAudience(aud: String?): Token {
        claims.audience = aud
        return this
    }

    override fun getAudience(): String? = claims.audience

    override fun setNotBefore(nbf: Date?): Token {
        claims.notBefore = nbf
        return this
    }

    override fun getNotBefore(): Date? = claims.notBefore

    override fun setIssuer(iss: String?): Token {
        claims.issuer = iss
        return this
    }

    override fun getIssuer(): String? = claims.issuer

    override fun setExpiration(exp: Date?): Token {
        claims.expiration = exp
        return this
    }

    override fun getExpiration(): Date? = claims.expiration

    override fun setSubject(sub: String?): Token {
        claims.subject = sub
        return this
    }

    override fun getSubject(): String? = claims.subject
}