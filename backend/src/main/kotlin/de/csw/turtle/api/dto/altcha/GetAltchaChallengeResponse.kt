package de.csw.turtle.api.dto.altcha

import org.altcha.altcha.Altcha

data class GetAltchaChallengeResponse(
    val challenge: String,
    val salt: String,
    val signature: String,
    val algorithm: String
) {

    constructor(challenge: Altcha.Challenge) : this(
        challenge.challenge,
        challenge.salt,
        challenge.signature,
        challenge.algorithm,
    )

}