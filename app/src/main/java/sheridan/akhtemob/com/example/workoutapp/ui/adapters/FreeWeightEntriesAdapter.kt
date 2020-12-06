package sheridan.akhtemob.com.example.workoutapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import sheridan.akhtemob.com.example.workoutapp.dataModels.FreeWeightsEntryModel
import sheridan.akhtemob.com.example.workoutapp.ui.interfaces.AdapterClickListener

class FreeWeightEntriesAdapter (
    private val list: ArrayList<FreeWeightsEntryModel>,
    private val listener: AdapterClickListener
) : RecyclerView.Adapter<FreeWeightEntriesAdapter.ViewClass>() {

}