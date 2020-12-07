package sheridan.akhtemob.com.example.workoutapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import sheridan.akhtemob.com.example.workoutapp.dataModels.FreeWeightsEntryModel
import sheridan.akhtemob.com.example.workoutapp.dataModels.JoggingEntryModel
import sheridan.akhtemob.com.example.workoutapp.databinding.FragmentWorkoutListBinding
import sheridan.akhtemob.com.example.workoutapp.ui.adapters.FreeWeightEntriesAdapter
import sheridan.akhtemob.com.example.workoutapp.ui.adapters.JoggingEntriesAdapter
import sheridan.akhtemob.com.example.workoutapp.ui.interfaces.AdapterClickListener
import sheridan.akhtemob.com.example.workoutapp.viewModels.JoggingEntriesViewModel
import sheridan.akhtemob.com.example.workoutapp.viewModels.FreeWeightsEntriesViewModel



class WorkoutList : Fragment() {
    private var binding: FragmentWorkoutListBinding? = null

    private val joggingEntriesViewModel: JoggingEntriesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(JoggingEntriesViewModel::class.java)
    }

    private val freeWeightsEntriesViewModel: FreeWeightsEntriesViewModel by lazy {
        ViewModelProvider(requireActivity()).get(FreeWeightsEntriesViewModel::class.java)
    }

    private val listOfJoggingEntries = ArrayList<JoggingEntryModel>()
    private lateinit var joggingEntriesAdapter: JoggingEntriesAdapter

    private val listOfFreeWeightEntries = ArrayList<FreeWeightsEntryModel>()
    private lateinit var freeWeightEntriesAdapter: FreeWeightEntriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        binding?.let { layout ->

            joggingEntriesAdapter = JoggingEntriesAdapter(listOfJoggingEntries, object : AdapterClickListener {
                override fun click(id: Int) {
                    val action = WorkoutListDirections.forwardToJoggingEntryDetails(id)
                    navController.navigate(action)
                }
            })

            layout.recViewJoggingEntries.let {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.adapter = joggingEntriesAdapter
            }

            joggingEntriesViewModel.getAllJoggingEntries().observe(viewLifecycleOwner, {
                it?.let { joggingEntriesModels ->
                    listOfJoggingEntries.clear()
                    listOfJoggingEntries.addAll(joggingEntriesModels)
                    if (listOfJoggingEntries.isNotEmpty()) {
                        layout.joggingEntriesView.visibility = View.VISIBLE
                    } else {
                        layout.joggingEntriesView.visibility = View.GONE
                    }
                }
            })

            freeWeightEntriesAdapter = FreeWeightEntriesAdapter(listOfFreeWeightEntries, object : AdapterClickListener {
                override fun click(id: Int) {
                    val action = WorkoutListDirections.forwardToFreeWeightsEntryDetails(id)
                    navController.navigate(action)
                }
            })

            layout.recViewFreeWeightsEntries.let {
                it.layoutManager = LinearLayoutManager(requireContext())
                it.adapter = freeWeightEntriesAdapter
            }

            freeWeightsEntriesViewModel.getAllFreeWeightsEntries().observe(viewLifecycleOwner, {
                it?.let { freeWeightsEntries ->
                    listOfFreeWeightEntries.clear()
                    listOfFreeWeightEntries.addAll(freeWeightsEntries)
                    if (listOfFreeWeightEntries.isNotEmpty()) {
                        layout.freeWeightsEntriesView.visibility = View.VISIBLE
                    } else {
                        layout.freeWeightsEntriesView.visibility = View.GONE
                    }
                }
            })

            layout.addJoggingEntryView.setOnClickListener {
                val action = WorkoutListDirections.forwardToAddJoggingEntry()
                navController.navigate(action)
            }

            layout.addFreeWeightsEntryView.setOnClickListener {
                val action = WorkoutListDirections.forwardToAddFreeWeightsEntry()
                navController.navigate(action)
            }

        }// layout ends here

    }// onViewCreated ends here
}