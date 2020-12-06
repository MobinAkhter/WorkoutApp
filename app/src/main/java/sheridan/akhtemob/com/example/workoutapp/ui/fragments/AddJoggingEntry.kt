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
import sheridan.akhtemob.com.example.workoutapp.dataModels.JoggingEntryModel
import sheridan.akhtemob.com.example.workoutapp.databinding.FragmentAddJoggingEntryBinding
import sheridan.akhtemob.com.example.workoutapp.ui.common.Dialogs
import sheridan.akhtemob.com.example.workoutapp.ui.common.Store
import sheridan.akhtemob.com.example.workoutapp.viewModels.JoggingEntriesViewModel
import java.util.*

class AddJoggingEntry: Fragment() {
    private var binding: FragmentAddJoggingEntryBinding? = null

    private val currentDateTime = Calendar.getInstance()
    private var startYear = currentDateTime.get(Calendar.YEAR)
    private var startMonth = currentDateTime.get(Calendar.MONTH)
    private var startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
    private var startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
    private var startMinute = currentDateTime.get(Calendar.MINUTE)

    private var selectedDate: Long = 0L
    private var selectedTime: Long = 0L

    private val joggingEntriesViewModel: JoggingEntriesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JoggingEntriesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddJoggingEntryBinding.inflate(inflater, container, false)
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

                val distanceStr = layout.distanceEditText.text.toString()

                if (distanceStr.isEmpty()) {
                    layout.distanceEditText.error = getString(R.string.distance_required)
                    layout.distanceEditText.requestFocus()
                } else {

                    val distanceUnit =
                        if (layout.distanceUnitGroup.checkedRadioButtonId == R.id.kiloMeters) {
                            getString(R.string.kilo_meters)
                        } else {
                            getString(R.string.meters)
                        }

                    val distance = Store.convertStringToDoubleValue(distanceStr)
                    joggingEntriesViewModel.addJoggingEntry(
                        JoggingEntryModel(
                            selectedDate,
                            selectedTime,
                            distance,
                            distanceUnit
                        )
                    )

                    Dialogs.showToast(requireActivity(), getString(R.string.saved_successfully))
                    val action = AddJoggingEntryDirections.backToWorkoutList()
                    navController.navigate(action)

                }
            }

        }
    }

}