class NoteService(private val noteRepository: NoteRepository) {

    fun createNote(title: String, content: String): Note {
        val note = Note(title = title, content = content)
        noteRepository.addNote(note)
        return note
    }

    fun getNote(id: String): Note? {
        return noteRepository.getNoteById(id)
    }

    fun getAllNotes(): List<Note> {
        return noteRepository.getAllNotes()
    }

    fun deleteNote(id: String): Boolean {
        return noteRepository.getNoteById(id)?.let {
            noteRepository.deleteNote(id)
            true
        } ?: false
    }
}

fun main() {
    val filePath = "notes.json"
    val noteRepository = NoteRepository(filePath)
    val noteService = NoteService(noteRepository)

    while (true) {
        println("Выберите действие:")
        println("1. Создать заметку")
        println("2. Просмотреть все заметки")
        println("3. Просмотреть заметку по ID")
        println("4. Обновить заметку")
        println("5. Удалить заметку")
        println("6. Выйти")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Введите заголовок заметки:")
                val title = readLine() ?: ""
                println("Введите содержание заметки:")
                val content = readLine() ?: ""
                val note = noteService.createNote(title, content)
                println("Заметка создана с ID: ${note.id}")
            }
            2 -> {
                val notes = noteService.getAllNotes()
                if (notes.isEmpty()) {
                    println("Заметок нет.")
                } else {
                    notes.forEach { println("ID: ${it.id}, Заголовок: ${it.title}, Содержание: ${it.content}") }
                }
            }
            3 -> {
                println("Введите ID заметки:")
                val id = readLine() ?: ""
                val note = noteService.getNote(id)
                if (note != null) {
                    println("Заголовок: ${note.title}, Содержание: ${note.content}")
                } else {
                    println("Заметка не найдена.")
                }
            }
            4 -> {
                println("Введите ID заметки для удаления:")
                val id = readLine() ?: ""
                if (noteService.deleteNote(id)) {
                    println("Заметка удалена.")
                } else {
                    println("Заметка не найдена.")
                }
            }
            5 -> {
                println("Выход из программы.")
                return
            }
            else -> {
                println("Неверный ввод. Попробуйте снова.")
            }
        }
    }
}