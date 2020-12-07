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
import sheridan.akhtemob.com.example.workoutapp.databinding.FragmentJoggingEntryDetailsBinding
import sheridan.akhtemob.com.example.workoutapp.ui.common.Dialogs
import sheridan.akhtemob.com.example.workoutapp.ui.common.Store
import sheridan.akhtemob.com.example.workoutapp.ui.interfaces.AlertDialogInterface
import sheridan.akhtemob.com.example.workoutapp.viewModels.JoggingEntriesViewModel


class JoggingEntryDetails : Fragment() {
    private var binding: FragmentJoggingEntryDetailsBinding? = null

    private var entryId: Int = 0

    private val joggingEntriesViewModel: JoggingEntriesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JoggingEntriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJoggingEntryDetailsBinding.inflate(inflater, container, false)
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

            joggingEntriesViewModel.getJoggingEntryById(entryId).observe(viewLifecycleOwner, {
                it?.let { joggingEntryModel ->
                    layout.distance.text =
                        "${joggingEntryModel.distance} ( ${joggingEntryModel.distanceUnit} )"
                    layout.date.text = Store.getReadableDate(joggingEntryModel.date)
                    layout.time.text = Store.getReadableTime(joggingEntryModel.time)
                }
            })

            layout.updateBtn.setOnClickListener {
                val action = JoggingEntryDetailsDirections.forwardToUpdateJoggingEntry(entryId)
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
                            joggingEntriesViewModel.deleteJoggingEntry(entryId)
                            Dialogs.showToast(
                                requireActivity(),
                                getString(R.string.deleted_successfully)
                            )
                            val action = JoggingEntryDetailsDirections.backToWorkoutList()
                            navController.navigate(action)
                        }

                        override fun negativeButtonClick() {}
                    }
                )
            }

        }
    }

}