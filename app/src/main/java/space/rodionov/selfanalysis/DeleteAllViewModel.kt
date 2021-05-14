package space.rodionov.selfanalysis

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DeleteAllViewModel @ViewModelInject constructor(
    private val noteDao: NoteDao,
    @ApplicationScope private val applicationScope: CoroutineScope
) : ViewModel() {

    fun onConfirmClick() = applicationScope.launch {
        noteDao.deleteAll()
    }

}










