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
import sheridan.akhtemob.com.example.workoutapp.databinding.FragmentUpdateJoggingEntryBinding
import sheridan.akhtemob.com.example.workoutapp.ui.common.Dialogs
import sheridan.akhtemob.com.example.workoutapp.ui.common.Store
import sheridan.akhtemob.com.example.workoutapp.viewModels.JoggingEntriesViewModel
import java.util.*

class UpdateJoggingEntry: Fragment() {
    private var binding: FragmentUpdateJoggingEntryBinding? = null

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

    private var entryId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateJoggingEntryBinding.inflate(inflater, container, false)
        return binding?.root
    }

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

                    selectedDate = joggingEntryModel.date
                    layout.dateTextView.text = Store.getReadableDate(selectedDate)

                    selectedTime = joggingEntryModel.time
                    layout.timeTextView.text = Store.getReadableTime(selectedTime)

                    layout.distanceEditText.setText(joggingEntryModel.distance.toString())

                    if (joggingEntryModel.distanceUnit.trim()
                            .equals(getString(R.string.kilo_meters), true)
                    ) {
                        layout.meters.isChecked = false
                        layout.kiloMeters.isChecked = true
                    } else {
                        layout.kiloMeters.isChecked = false
                        layout.meters.isChecked = true
                    }

                    currentDateTime.timeInMillis = joggingEntryModel.date
                    startYear = currentDateTime.get(Calendar.YEAR)
                    startMonth = currentDateTime.get(Calendar.MONTH)
                    startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

                    val calender = Calendar.getInstance()
                    calender.timeInMillis = joggingEntryModel.time
                    startHour = calender.get(Calendar.HOUR_OF_DAY)
                    startMinute = calender.get(Calendar.MINUTE)

                }
            })

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

            layout.updateBtn.setOnClickListener {

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

                    joggingEntriesViewModel.updateJoggingEntry(
                        selectedDate,
                        selectedTime,
                        distance,
                        distanceUnit,
                        entryId
                    )

                    Dialogs.showToast(requireActivity(), getString(R.string.updated_successfully))
                    val action = UpdateJoggingEntryDirections.backToJoggingEntryDetails(entryId)
                    navController.navigate(action)

                }
            }

        }
    }

}