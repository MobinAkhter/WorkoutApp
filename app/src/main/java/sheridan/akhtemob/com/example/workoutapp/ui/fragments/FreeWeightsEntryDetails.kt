package sheridan.akhtemob.com.example.workoutapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import sheridan.akhtemob.com.example.workoutapp.R
import sheridan.akhtemob.com.example.workoutapp.databinding.FragmentFreeWeightsEntryDetailsBinding
import sheridan.akhtemob.com.example.workoutapp.ui.common.Dialogs
import sheridan.akhtemob.com.example.workoutapp.ui.common.Store
import sheridan.akhtemob.com.example.workoutapp.ui.interfaces.AlertDialogInterface
import sheridan.akhtemob.com.example.workoutapp.viewModels.FreeWeightsEntriesViewModel


class FreeWeightsEntryDetails : Fragment() {
    private var binding: FragmentFreeWeightsEntryDetailsBinding? = null

    private var entryId: Int = 0

    private val freeWeightsEntriesViewModel: FreeWeightsEntriesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(FreeWeightsEntriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFreeWeightsEntryDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding?.let { layout ->

            arguments?.let {
                val args = JoggingEntryDetailsArgs.fromBundle(it)
                entryId = args.id
            }

            freeWeightsEntriesViewModel.getJFreeWeightsEntryById(entryId)
                .observe(viewLifecycleOwner, {
                    it?.let { entryModel ->
                        layout.date.text = Store.getReadableDate(entryModel.date)
                        layout.time.text = Store.getReadableTime(entryModel.time)
                        layout.sets.text = entryModel.sets.toString()
                        layout.reps.text = entryModel.reps.toString()
                        layout.weight.text = entryModel.weight.toString()
                    }
                })

            layout.updateBtn.setOnClickListener {
                val action = FreeWeightsEntryDetailsDirections.forwardToUpdateFreeWeightsEntry(entryId)
                navController.navigate(action)
            }

            layout.deleteBtn.setOnClickListener {
                Dialogs.showMessage(
                    requireActivity(),
                    getString(R.string.delete_message),
                    getString(R.string.yes),
                    getString(R.string.no),
                    object : AlertDialogInterface {
                        override fun positiveButtonClick() {
                            freeWeightsEntriesViewModel.deleteFreeWeightsEntryModel(entryId)
                            Dialogs.showToast(
                                requireActivity(),
                                getString(R.string.deleted_successfully)
                            )
                            val action = FreeWeightsEntryDetailsDirections.backToWorkoutList()
                            navController.navigate(action)
                        }

                        override fun negativeButtonClick() {}
                    }
                )
            }

        }
    }

}