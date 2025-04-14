import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

class NoteRepository(private val filePath: String) {
    private val notes = mutableListOf<Note>()

    init {
        loadNotes()
    }

    fun addNote(note: Note) {
        notes.add(note)
        saveNotes()
    }

    fun getNoteById(id: String): Note? {
        return notes.find { it.id == id }
    }

    fun getAllNotes(): List<Note> {
        return notes.toList()
    }

    fun deleteNote(id: String) {
        notes.removeIf { it.id == id }
        saveNotes()
    }

    private fun saveNotes() {
        val json = Json { prettyPrint = true }
        val jsonString = json.encodeToString(notes)
        File(filePath).writeText(jsonString)
    }

    private fun loadNotes() {
        val file = File(filePath)
        if (file.exists()) {
            val jsonString = file.readText()
            val loadedNotes = Json.decodeFromString<List<Note>>(jsonString)
            notes.clear()
            notes.addAll(loadedNotes)
        }
    }
}