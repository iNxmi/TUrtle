package de.csw.turtle.api.components

import com.github.benmanes.caffeine.cache.Caffeine
import io.github.bucket4j.Bucket
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RateLimitBucketStore {

    sealed class RateLimitKey {
        data class User(val id: Long) : RateLimitKey()
        data class Ip(val ip: String) : RateLimitKey()
    }

    private val duration = Duration.ofMinutes(30)
    private val maxSize = 200_000L
    private val cache = Caffeine.newBuilder()
        .expireAfterAccess(duration)
        .maximumSize(maxSize)
        .build<RateLimitKey, Bucket>()

    fun getUserBucket(userId: Long, supplier: () -> Bucket): Bucket =
        cache.get(RateLimitKey.User(userId)) { supplier() }

    fun getIpBucket(ip: String, supplier: () -> Bucket): Bucket =
        cache.get(RateLimitKey.Ip(ip)) { supplier() }

}