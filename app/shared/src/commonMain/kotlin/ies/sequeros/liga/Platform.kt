package ies.sequeros.liga

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform