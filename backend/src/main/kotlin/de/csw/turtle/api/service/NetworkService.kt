package de.csw.turtle.api.service

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.net.InetAddress

@Service
class NetworkService {

    fun getClientIp(request: HttpServletRequest): String = request.getHeader("x-real-ip") ?: request.remoteAddr

    //TODO needs testing to see if it really blocks external calls
    fun isLocalNetwork(request: HttpServletRequest): Boolean {
        val ipAddress = getClientIp(request)

        try {
            val address = InetAddress.getByName(ipAddress)
            return address.isLoopbackAddress || address.isSiteLocalAddress
        } catch (_: Exception) {
            return false
        }
    }

}