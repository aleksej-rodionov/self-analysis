package space.rodionov.selfanalysis.ui

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import space.rodionov.selfanalysis.R

@AndroidEntryPoint
class DeleteAllDialogFragment : DialogFragment() {

    private val viewModel: DeleteAllViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(requireContext().resources.getString(R.string.confirm_deletion))
            .setMessage(requireContext().resources.getString(R.string.really_delete_all))
            .setNegativeButton(requireContext().resources.getString(R.string.cancel_action), null)
            .setPositiveButton(requireContext().resources.getString(R.string.yes)) { _, _ ->
                viewModel.onConfirmClick()
            }
            .create()

}








