package space.rodionov.selfanalysis

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.util.*

class AddEditNoteViewModel @ViewModelInject constructor(
    private val noteDao: NoteDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val calendar = Calendar.getInstance()
    val currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.time)

    val note = state.get<Note>("note") // the same String name as in the NavGraph argument

    var noteDate = state.get<String>("noteDate") ?: note?.date ?: ""
        set(value) {
            field = value
            state.set("noteDate", value)
        }

    var noteSituation = state.get<String>("noteSituation") ?: note?.situation ?: ""
        set(value) {
            field = value
            state.set("noteSituation", value)
        }

    var noteEmotions = state.get<String>("noteEmotions") ?: note?.emotions ?: ""
        set(value) {
            field = value
            state.set("noteEmotions", value)
        }

    var noteFeelings = state.get<String>("noteFeelings") ?: note?.feelings ?: ""
        set(value) {
            field = value
            state.set("noteFeelings", value)
        }

    var noteInTheBody = state.get<String>("noteInTheBody") ?: note?.inTheBody ?: ""
        set(value) {
            field = value
            state.set("noteInTheBody", value)
        }

    var noteWantedToDo = state.get<String>("noteWantedToDo") ?: note?.wantedToDo ?: ""
        set(value) {
            field = value
            state.set("noteWantedToDo", value)
        }

    var noteWhatDoesTheFeelingMean =
        state.get<String>("noteWhatDoesTheFeelingMean") ?: note?.whatDoesTheFeelingMean ?: ""
        set(value) {
            field = value
            state.set("noteWhatDoesTheFeelingMean", value)
        }

    var noteThoughts = state.get<String>("noteThoughts") ?: note?.thoughts ?: ""
        set(value) {
            field = value
            state.set("noteThoughts", value)
        }

    var noteFears = state.get<String>("noteFears") ?: note?.fears ?: ""
        set(value) {
            field = value
            state.set("noteFears", value)
        }

    var noteAskingFromHP = state.get<String>("noteAskingFromHP") ?: note?.askingFromHP ?: ""
        set(value) {
            field = value
            state.set("noteAskingFromHP", value)
        }

    var noteInnerCritic = state.get<String>("noteInnerCritic") ?: note?.innerCritic ?: ""
        set(value) {
            field = value
            state.set("noteInnerCritic", value)
        }

    var noteLovingParent = state.get<String>("noteLovingParent") ?: note?.lovingParent ?: ""
        set(value) {
            field = value
            state.set("noteLovingParent", value)
        }

    private val addEditNoteEventChannel = Channel<AddEditNoteEvent>()
    val addEditNoteEvent = addEditNoteEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (noteSituation.isBlank()) {
            showInvalidInputMessage("Situation cannot be empty")
            return
        }

        if (note != null) {
            val updatedNote = note.copy(
//                date = noteDate,
                situation = noteSituation,
                emotions = noteEmotions,
                feelings = noteFeelings,
                inTheBody = noteInTheBody,
                wantedToDo = noteWantedToDo,
                thoughts = noteThoughts,
                fears = noteFears,
                askingFromHP = noteAskingFromHP,
                innerCritic = noteInnerCritic,
                lovingParent = noteLovingParent
            )
            updateNote(updatedNote)
        } else {
            val newNote = Note(
                date = noteDate,
                situation = noteSituation,
                emotions = noteEmotions,
                feelings = noteFeelings,
                inTheBody = noteInTheBody,
                wantedToDo = noteWantedToDo,
                thoughts = noteThoughts,
                fears = noteFears,
                askingFromHP = noteAskingFromHP,
                innerCritic = noteInnerCritic,
                lovingParent = noteLovingParent
            )
            createNote(newNote)
        }
    }

    private fun createNote(note: Note) = viewModelScope.launch {
        noteDao.insert(note)
        addEditNoteEventChannel.send(AddEditNoteEvent.NavigateBackWithResult(ADD_TASK_RESULT_OK))
    }

    private fun updateNote(note: Note) = viewModelScope.launch {
        noteDao.update(note)
        addEditNoteEventChannel.send(AddEditNoteEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditNoteEventChannel.send(AddEditNoteEvent.ShowInvalidInputMessage(text))
    }

    sealed class AddEditNoteEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditNoteEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditNoteEvent()
    }

}







