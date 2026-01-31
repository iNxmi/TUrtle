package de.csw.turtle.api.service

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import java.net.InetAddress

@Service
class NetworkService {

    //TODO needs testing to see if it really blocks external calls
    fun isLocalNetwork(request: HttpServletRequest): Boolean {
        val address = InetAddress.getByName(request.remoteAddr)
        return address.isLoopbackAddress || address.isSiteLocalAddress
    }

}