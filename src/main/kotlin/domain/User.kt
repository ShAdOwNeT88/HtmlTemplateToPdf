package domain

import java.util.*

class User(
    val userId: UUID = UUID.randomUUID(),
    val name: String,
    val surname: String,
    val phone: String,
    val email: String
)
