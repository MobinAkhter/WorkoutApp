package sheridan.akhtemob.com.example.workoutapp.ui.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import sheridan.akhtemob.com.example.workoutapp.R
import sheridan.akhtemob.com.example.workoutapp.dataModels.FreeWeightsEntryModel
import sheridan.akhtemob.com.example.workoutapp.databinding.FragmentAddFreeWeightsEntryBinding
import sheridan.akhtemob.com.example.workoutapp.ui.common.Dialogs
import sheridan.akhtemob.com.example.workoutapp.ui.common.Store
import sheridan.akhtemob.com.example.workoutapp.viewModels.FreeWeightsEntriesViewModel
import sheridan.akhtemob.com.example.workoutapp.viewModels.JoggingEntriesViewModel
import java.util.*

class AddFreeWeightsEntry: Fragment() {
    private var binding: FragmentAddFreeWeightsEntryBinding? = null

    private val currentDateTime = Calendar.getInstance()
    private var startYear = currentDateTime.get(Calendar.YEAR)
    private var startMonth = currentDateTime.get(Calendar.MONTH)
    private var startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
    private var startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
    private var startMinute = currentDateTime.get(Calendar.MINUTE)

    private var selectedDate: Long = 0L
    private var selectedTime: Long = 0L

    private val freeWeightsEntriesViewModel: FreeWeightsEntriesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(FreeWeightsEntriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFreeWeightsEntryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding?.let { layout ->

            selectedDate = System.currentTimeMillis()
            layout.dateTextView.text = Store.getReadableDate(selectedDate)

            selectedTime = System.currentTimeMillis()
            layout.timeTextView.text = Store.getReadableTime(selectedTime)

            layout.addDateView.setOnClickListener {
                DatePickerDialog(
                    requireContext(), { _, year, month, day ->
                        startYear = year
                        startMonth = month
                        startDay = day
                        currentDateTime.set(year, month, day)
                        selectedDate = currentDateTime.timeInMillis
                        layout.dateTextView.text = Store.getReadableDate(selectedDate)
                    },
                    startYear,
                    startMonth,
                    startDay
                ).show()
            }

            layout.addTimeView.setOnClickListener {
                val calender = Calendar.getInstance()
                TimePickerDialog(
                    requireContext(), { timePicker, hour, minute ->
                        startHour = hour
                        startMinute = minute
                        calender.set(Calendar.HOUR_OF_DAY, hour)
                        calender.set(Calendar.MINUTE, minute)
                        selectedTime = calender.timeInMillis
                        layout.timeTextView.text = Store.getReadableTime(selectedTime)
                    },
                    startHour,
                    startMinute,
                    false
                ).show()
            }

            layout.saveBtn.setOnClickListener {

                val repsStr = layout.repsEditText.text.toString()
                val setsStr = layout.setsEditText.text.toString()
                val weightStr = layout.weightEditText.text.toString()

                var isError = false

                if (weightStr.isEmpty()) {
                    layout.weightEditText.error = getString(R.string.weight_required)
                    layout.weightEditText.requestFocus()
                    isError = true
                }

                if (setsStr.isEmpty()) {
                    layout.setsEditText.error = getString(R.string.sets_required)
                    layout.setsEditText.requestFocus()
                    isError = true
                }

                if (repsStr.isEmpty()) {
                    layout.repsEditText.error = getString(R.string.reps_required)
                    layout.repsEditText.requestFocus()
                    isError = true
                }

                if(!isError){

                    val weight = Store.convertStringToDoubleValue(weightStr)
                    val reps = Store.convertStringToIntValue(repsStr)
                    val sets = Store.convertStringToIntValue(setsStr)

                    freeWeightsEntriesViewModel.addFreeWeightsEntry(
                        FreeWeightsEntryModel(
                            selectedDate,
                            selectedTime,
                            reps,
                            sets,
                            weight
                        )
                    )

                    Dialogs.showToast(requireActivity(), getString(R.string.saved_successfully))
                    val action = AddFreeWeightsEntryDirections.backToWorkoutList()
                    navController.navigate(action)

                }

            }

        }
    }
}