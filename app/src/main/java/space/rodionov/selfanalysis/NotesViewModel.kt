package space.rodionov.selfanalysis

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.flatMapLatest
//import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*

class NotesViewModel @ViewModelInject constructor(
    private val noteDao: NoteDao,
    private val preferencesRepository: PreferencesRepository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private val allEmoList = listOf("Mad", "Glad,", "Lonely", "Scared", "Sad")
    private var emoFilterList = ArrayList<String>()
    private val gson = Gson()
//    val typeToken = object : TypeToken<List<String>>() {}.type

    val searchQuery = state.getLiveData("searchQuery", "")
    val emoFlow = preferencesRepository.emotionFlow
    val emotionLivedata = emoFlow.asLiveData()

//    val filtFlow = preferencesRepository.filteredFlow
//
//    val eFJsonFlow = preferencesRepository.emoFilterJsonFlow
//    val emoFilterJsontLiveData = eFJsonFlow.asLiveData()

//    private val mFiltFlow = preferencesRepository.mainFilterFlow

    private val notesEventChannel = Channel<NotesEvent>() //2
    val notesEvent = notesEventChannel.receiveAsFlow() //4

    private val notesFlow = combine(searchQuery.asFlow(), emoFlow) { query, emoPrefs ->
        Pair(query, emoPrefs)
    }.flatMapLatest { (query, emoPrefs) ->
        noteDao.getNotes(query, emoPrefs)
    }

//    private val notesFlow = searchQuery.asFlow().flatMapLatest { // asFlow() added
//        noteDao.getNotes(it)
//    }

    val notes = notesFlow.asLiveData()

    fun onNoEmotionFilterClick(filterOn: Boolean) = viewModelScope.launch {
        preferencesRepository.updateFilterOn(filterOn)
    }

    fun onEmotionClick(emotion: String) = viewModelScope.launch {
        preferencesRepository.updateEmotion(emotion)
    }

    /*fun onEmotionClick(emotion: String, isChecked: Boolean*//*, emoFilterJson: String*//*) =
        viewModelScope.launch {
            preferencesRepository.updateFilterOn(true)
//            emoFilterList.clear()
            eFJsonFlow.collect { json ->
                emoFilterList = gson.fromJson<ArrayList<String>>(json, typeToken)
//                allEmoList.forEach { emoItem ->
//                    if (jsonFlow.contains(emoItem)) {
//                        emoFilterList.add(emoItem)
//                    }
//                }
            }
//        emoFilterList = gson.fromJson<List<String>>(emoFilterJson, typeToken)
            if (isChecked) {
                emoFilterList.add(emotion)
            } else {
                emoFilterList.remove(emotion)
            }
//        emoFilterJson = gson.toJson(emoFilterList)
            preferencesRepository.updateEmoFilterList(gson.toJson(emoFilterList))
//            emoFilterList.clear()
        }*/

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
        notesEventChannel.send(NotesEvent.ShowTaskSavedConfirmationMessage(text))
    }

    fun onDeleteAllClick() = viewModelScope.launch {
        notesEventChannel.send(NotesEvent.NavigateToDeleteAllScreen)
    }

    sealed class NotesEvent { //1
        object NavigateToAddNoteScreen : NotesEvent()
        data class NavigateToEditNoteScreen(val note: Note) : NotesEvent()
        data class ShowUndoDeleteNoteMessage(val note: Note) : NotesEvent() // 1.1
        data class ShowTaskSavedConfirmationMessage(val msg: String) : NotesEvent()
        object NavigateToDeleteAllScreen : NotesEvent()
    }

}










