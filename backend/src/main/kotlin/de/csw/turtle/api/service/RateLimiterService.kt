package de.csw.turtle.api.service

import de.csw.turtle.api.components.RateLimitBucketStore
import de.csw.turtle.api.entity.UserEntity
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class RateLimiterService(
    private val bucketStore: RateLimitBucketStore
) {


    private fun bucket(max: Long, refillRatePerMinute: Long): Bucket {
        val refill = Refill.greedy(refillRatePerMinute, Duration.ofMinutes(1))
        val limit = Bandwidth.classic(max, refill)
        return Bucket.builder().addLimit(limit).build()
    }

    fun tryConsume(ip: String, user: UserEntity?): Boolean {
        val bucket = if (user != null) {
            bucketStore.getUserBucket(user.id) { bucket(max = 60, refillRatePerMinute = 300) }
        } else {
            bucketStore.getIpBucket(ip) { bucket(max = 30, refillRatePerMinute = 120) }
        }

        return bucket.tryConsume(1)
    }

}