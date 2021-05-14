package space.rodionov.selfanalysis

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import space.rodionov.selfanalysis.NotesFragmentDirections
import space.rodionov.selfanalysis.databinding.FragmentNotesBinding

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes), NotesAdapter.OnItemClickListener {

    private val viewModel: NotesViewModel by viewModels()

    private lateinit var searchView: SearchView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNotesBinding.bind(view)

        val notesAdapter = NotesAdapter(this)

        binding.apply {
            recyclerView.apply {
                adapter = notesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val note = notesAdapter.currentList[viewHolder.adapterPosition]
                    viewModel.onNoteSwiped(note)
                }
            }).attachToRecyclerView(recyclerView)

            btnAdd.setOnClickListener {
                viewModel.onAddNewTaskClick()
            }
        }

        setFragmentResultListener("add_edit_request") { _, bundle ->
            val result = bundle.getInt("add_edit_result")
            viewModel.onAddEditResult(result)
        }

        viewModel.notes.observe(viewLifecycleOwner) {
            notesAdapter.submitList(it)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notesEvent.collect { event ->
                when (event) {
                    is NotesViewModel.NotesEvent.ShowUndoDeleteNoteMessage -> {
                        Snackbar.make(requireView(), requireContext().resources.getString(R.string.note_deleted), Snackbar.LENGTH_LONG)
                            .setAction(requireContext().resources.getString(R.string.undo)) {
                                viewModel.onUndoDeleteClick(event.note)
                            }.show()
                    }
                    is NotesViewModel.NotesEvent.NavigateToAddNoteScreen -> {
                        val action =
                            NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment(
                                null, requireContext().resources.getString(R.string.new_note)
                            )
                        findNavController().navigate(action)
                    }
                    is NotesViewModel.NotesEvent.NavigateToEditNoteScreen -> {
                        val action =
                            NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment(
                                event.note, requireContext().resources.getString(R.string.edit_note)
                            )
                        findNavController().navigate(action)
                    }
                    is NotesViewModel.NotesEvent.ShowTaskSavedConfirmationMessage -> {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                    }
                    NotesViewModel.NotesEvent.NavigateToDeleteAllScreen -> {
                        val action = NotesFragmentDirections.actionGlobalDeleteAllDialogFragment()
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }

        viewModel.emotionLivedata.observe(viewLifecycleOwner) { emo ->
            viewLifecycleOwner.lifecycleScope.launch {
                (activity as MainActivity).supportActionBar?.title = if (emo != "") requireContext().resources.getString((R.string.notes_filtered_by)) + " $emo" else requireContext().resources.getString((R.string.all_notes))
            }
        }

        setHasOptionsMenu(true)
    }

    override fun onItemClick(note: Note) {
        viewModel.onNoteSelected(note)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_notes, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }

        /*viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_filtered).isChecked =
                viewModel.filtFlow.first()
        }
        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_filter_mad).isChecked =
                viewModel.eFJsonFlow.first().contains("Mad")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_filter_glad).isChecked =
                viewModel.eFJsonFlow.first().contains("Glad")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_filter_lonely).isChecked =
                viewModel.eFJsonFlow.first().contains("Lonely")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_filter_scared).isChecked =
                viewModel.eFJsonFlow.first().contains("Scared")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_filter_sad).isChecked =
                viewModel.eFJsonFlow.first().contains("Sad")
        }
        viewLifecycleOwner.lifecycleScope.launch {
            menu.findItem(R.id.action_filtered).isChecked =
                viewModel.filtFlow.first()
        }*/

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all_notes -> {
                viewModel.onDeleteAllClick()
                true
            }
            R.id.action_no_filter -> {
                viewModel.onEmotionClick("")
                true
            }
            R.id.action_filter_mad -> {
                    viewModel.onEmotionClick(requireContext().resources.getString((R.string.mad)))
                true
            }
            R.id.action_filter_glad -> {
                    viewModel.onEmotionClick(requireContext().resources.getString((R.string.glad)))
                true
            }
            R.id.action_filter_lonely -> {
                    viewModel.onEmotionClick(requireContext().resources.getString((R.string.lonely)))
                true
            }
            R.id.action_filter_scared -> {
                    viewModel.onEmotionClick(requireContext().resources.getString((R.string.scared)))
                true
            }
            R.id.action_filter_sad -> {
                    viewModel.onEmotionClick(requireContext().resources.getString((R.string.sad)))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        searchView.setOnQueryTextListener(null)
    }

}

//R.id.action_filter_mad -> {
//    item.isChecked = !item.isChecked
////                viewModel.emoFilterJsontLiveData.observe(viewLifecycleOwner) {
//    viewModel.onEmotionClick(item.title.toString(), item.isChecked/*, it*/)
////                }
//    true
//}