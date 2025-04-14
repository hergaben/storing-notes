import java.util.*
import kotlinx.serialization.*

@Serializable
data class Note(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var content: String,
    var createdAt: String = Date().toString()
)