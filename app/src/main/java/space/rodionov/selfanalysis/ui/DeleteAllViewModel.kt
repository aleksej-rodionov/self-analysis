package space.rodionov.selfanalysis.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.ApplicationScope
import space.rodionov.selfanalysis.data.NoteDao

class DeleteAllViewModel @ViewModelInject constructor(
        private val noteDao: NoteDao,
        @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClick() = applicationScope.launch {
        noteDao.deleteAll()
    }

}










