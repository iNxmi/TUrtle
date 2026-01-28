package de.csw.turtle.api.service

import de.csw.turtle.api.entity.UserEntity
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import org.springframework.stereotype.Service
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap

@Service
class RateLimiterService {

    private val ipBuckets = ConcurrentHashMap<String, Bucket>()
    private val userBuckets = ConcurrentHashMap<Long, Bucket>()

    private fun bucket(max: Long, refillRatePerMinute: Long): Bucket {
        val refill = Refill.greedy(refillRatePerMinute, Duration.ofMinutes(1))
        val limit = Bandwidth.classic(max, refill)
        return Bucket.builder().addLimit(limit).build()
    }

    fun tryConsume(ip: String, user: UserEntity?): Boolean {
        val bucket = if (user != null) {
            userBuckets.computeIfAbsent(user.id) { bucket(max = 60, refillRatePerMinute = 300) }
        } else {
            ipBuckets.computeIfAbsent(ip) { bucket(max = 30, refillRatePerMinute = 120) }
        }

        return bucket.tryConsume(1)
    }

}