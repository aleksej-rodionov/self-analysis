package space.rodionov.selfanalysis

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.core.os.bundleOf
import androidx.core.view.children
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import space.rodionov.selfanalysis.databinding.FragmentAddEditNoteBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note), CompoundButton.OnCheckedChangeListener {

    private val viewModel: AddEditNoteViewModel by viewModels()
    private val emotionsArrayList = ArrayList<String>()
    private val gson = Gson()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        val currentDate = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT).format(calendar.time)

        val binding = FragmentAddEditNoteBinding.bind(view)

        binding.apply {
//            tvAddEditDate.text = viewModel.noteDate
            etSituation.setText(viewModel.noteSituation)
            // EMOTIONS ADD HERE START
            cgEmotions.children.forEach {
                checkRepres(viewModel.noteEmotions, (it as Chip), emotionsArrayList)
            }
            // EMOTIONS ADD HERE END
            etFeelings.setText(viewModel.noteFeelings)
            etInTheBody.setText(viewModel.noteInTheBody)
            etWantedToDo.setText(viewModel.noteWantedToDo)
            etWhatDoesTheFeelingMean.setText(viewModel.noteWhatDoesTheFeelingMean)
            etThoughts.setText(viewModel.noteThoughts)
            etFears.setText(viewModel.noteFears)
            etAskingFromHp.setText(viewModel.noteAskingFromHP)
            etInnerCritic.setText(viewModel.noteInnerCritic)
            etLovingParent.setText(viewModel.noteLovingParent)
//            tvAddEditDate.isVisible = viewModel.note != null
            if (viewModel.note != null) {
                tvAddEditDate.text = "${requireContext().resources.getString(R.string.date_created)} ${viewModel.note?.date/*Formatted*/}"
            } else {
                tvAddEditDate.text = currentDate
            }

            viewModel.noteDate = currentDate

            etSituation.addTextChangedListener { // to automatically write it to the SaveStateHandle
                viewModel.noteSituation = it.toString()
            }

            // the same for chips by analogy with CheckBox in MVVMTODO START
            cgEmotions.children.forEach {
                (it as Chip).setOnCheckedChangeListener(this@AddEditNoteFragment)
//                viewModel.noteEmotions = gson.toJson(emotionsArrayList)
            }
            // the same for chips by analogy with CheckBox in MVVMTODO END

            etFeelings.addTextChangedListener {
                viewModel.noteFeelings = it.toString()
            }

            etInTheBody.addTextChangedListener {
                viewModel.noteInTheBody = it.toString()
            }

            etWantedToDo.addTextChangedListener {
                viewModel.noteWantedToDo = it.toString()
            }

            etWhatDoesTheFeelingMean.addTextChangedListener {
                viewModel.noteWhatDoesTheFeelingMean = it.toString()
            }

            etThoughts.addTextChangedListener {
                viewModel.noteThoughts = it.toString()
            }

            etFears.addTextChangedListener {
                viewModel.noteFears = it.toString()
            }

            etAskingFromHp.addTextChangedListener {
                viewModel.noteAskingFromHP = it.toString()
            }

            etInnerCritic.addTextChangedListener {
                viewModel.noteInnerCritic = it.toString()
            }

            etLovingParent.addTextChangedListener {
                viewModel.noteLovingParent = it.toString()
            }

            btnSave.setOnClickListener {
                viewModel.onSaveClick()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditNoteEvent.collect { event ->
                when (event) {
                    is AddEditNoteViewModel.AddEditNoteEvent.NavigateBackWithResult -> {
                        binding.etSituation.clearFocus() // hides the keyboard (CHECK IF DELETE THIS LINE!!!)
                        setFragmentResult(
                                "add_edit_request",
                                bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is AddEditNoteViewModel.AddEditNoteEvent.ShowInvalidInputMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                    }
                }.exhaustive
            }
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        if (isChecked) {
            emotionsArrayList.add(buttonView.text.toString())
        } else {
            emotionsArrayList.remove(buttonView.text.toString())
        }
        viewModel.noteEmotions = gson.toJson(emotionsArrayList)
    }

    fun checkRepres(json: String, chip: CompoundButton, list: ArrayList<String>) {
        if (json.contains(chip.text.toString())) {
            chip.isChecked = true
            list.add(chip.text.toString())
        }
    }

}






