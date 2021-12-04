package space.rodionov.selfanalysis.ui

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.google.gson.Gson
import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.flatMapLatest
//import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import space.rodionov.selfanalysis.data.Note
import space.rodionov.selfanalysis.data.NoteDao
import space.rodionov.selfanalysis.data.PreferencesRepository
import space.rodionov.selfanalysis.generateFile
import space.rodionov.selfanalysis.goToFileIntent
import java.io.File

private const val TAG = "NotesViewModel"
class NotesViewModel @ViewModelInject constructor(
        private val noteDao: NoteDao,
        private val preferencesRepository: PreferencesRepository,
        @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val allEmoList = listOf("Mad", "Glad,", "Lonely", "Scared", "Sad")
    private var emoFilterList = ArrayList<String>()
    private val gson = Gson()

    private val oriNoteList = mutableListOf<Note>()

    val searchQuery = state.getLiveData("searchQuery", "")
    val emoFlow = preferencesRepository.emotionFlow
    val emotionLivedata = emoFlow.asLiveData()

    private val notesEventChannel = Channel<NotesEvent>() //2
    val notesEvent = notesEventChannel.receiveAsFlow() //4

    private val notesFlow = combine(searchQuery.asFlow(), emoFlow) { query, emoPrefs ->
        Pair(query, emoPrefs)
    }.flatMapLatest { (query, emoPrefs) ->
        noteDao.getNotes(query, emoPrefs)
    }

    val notes = notesFlow.asLiveData()

    private val _allNotes = MutableStateFlow<List<Note>>(oriNoteList)
    private val allNotes = noteDao.getAllNotes()

    //=========================CSV==================================
    fun onExportToCSV(context: Context) {
        val csvFile = generateFile(context, "notes.csv")
        if (csvFile != null) {
            writeNotesToRows(csvFile)
            val intent = goToFileIntent(context, csvFile)
            viewModelScope.launch {
                notesEventChannel.send(NotesEvent.GoToFileActivity(intent))
            }
        } else {
            viewModelScope.launch {
                notesEventChannel.send(NotesEvent.ShowSnackbar("CSV file not generated"))
            }
        }
    }

    private fun writeNotesToRows(csvFile: File) {
        csvWriter().open(csvFile, append = false) {
            //Header
            writeRow(listOf(
                    "[situation]",
                    "[emotions]",
                    "[feelings]",
                    "[inTheBody]",
                    "[wantedToDo]",
                    "[whatDoesTheFeelingMean]",
                    "[thoughts]",
                    "[fears]",
                    "[askingFromHP]",
                    "[innerCritic]",
                    "[lovingParent]",
                    "[date]"))
            //Body
            viewModelScope.launch {
                _allNotes.collectLatest { noteList ->
                    Log.d(TAG, "writeNotesToRows: list size = ${noteList.size}")
                    noteList.forEach { n ->
                        writeRow(n.situation, n.emotions, n.feelings, n.inTheBody,
                        n.wantedToDo, n.whatDoesTheFeelingMean, n.thoughts, n.fears,
                        n.askingFromHP, n.innerCritic, n.lovingParent, n.date)
                    }
                }
            }
        }
    }

    fun onImportFromCSV(context: Context) {

    }
    //=========================CSV==================================

    fun observeAllNotes() = viewModelScope.launch {
        noteDao.getAllNotes().collectLatest {
            updateAllNotes(it)
        }
    }

    private fun updateAllNotes(notes: List<Note>) {
        Log.d(TAG, "upd notes ${notes.size}")
        _allNotes.value = notes
    }

    fun onNoEmotionFilterClick(filterOn: Boolean) = viewModelScope.launch {
        preferencesRepository.updateFilterOn(filterOn)
    }

    fun onEmotionClick(emotion: String) = viewModelScope.launch {
        preferencesRepository.updateEmotion(emotion)
    }

    fun onNoteSelected(note: Note) = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToEditNoteScreen(note))
    }

    fun onNoteSwiped(note: Note) = viewModelScope.launch {
        noteDao.delete(note)
        notesEventChannel.send(NotesEvent.ShowUndoDeleteNoteMessage(note)) //3
    }

    fun onUndoDeleteClick(note: Note) = viewModelScope.launch {
        noteDao.insert(note)
    }

    fun onAddNewTaskClick() = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToAddNoteScreen)
    }

    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_TASK_RESULT_OK -> showNoteConfirmationMessage("Note added")
            EDIT_TASK_RESULT_OK -> showNoteConfirmationMessage("Note updated")
        }
    }

    private fun showNoteConfirmationMessage(text: String) = viewModelScope.launch {
        //we lounch coroutine cause we sent the event to the Fragment cause only Fragment can show Snackbars
        notesEventChannel.send(NotesEvent.ShowSnackbar(text))
    }

    fun onDeleteAllClick() = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToDeleteAllScreen)
    }

    sealed class NotesEvent { //1
        object NavigateToAddNoteScreen : NotesEvent()
        data class NavigateToEditNoteScreen(val note: Note) : NotesEvent()
        data class ShowUndoDeleteNoteMessage(val note: Note) : NotesEvent() // 1.1
        data class ShowSnackbar(val msg: String) : NotesEvent()
        object NavigateToDeleteAllScreen : NotesEvent()
        data class GoToFileActivity(val intent: Intent): NotesEvent()
    }

}










