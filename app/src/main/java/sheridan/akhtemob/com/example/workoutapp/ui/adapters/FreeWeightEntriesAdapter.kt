package sheridan.akhtemob.com.example.workoutapp.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import sheridan.akhtemob.com.example.workoutapp.R
import sheridan.akhtemob.com.example.workoutapp.dataModels.FreeWeightsEntryModel
import sheridan.akhtemob.com.example.workoutapp.databinding.SingleRowFreeWeightEntryBinding
import sheridan.akhtemob.com.example.workoutapp.ui.common.Store
import sheridan.akhtemob.com.example.workoutapp.ui.interfaces.AdapterClickListener

class FreeWeightEntriesAdapter (
    private val list: ArrayList<FreeWeightsEntryModel>,
    private val listener: AdapterClickListener
) : RecyclerView.Adapter<FreeWeightEntriesAdapter.ViewClass>() {
    var context: Context? = null

    inner class ViewClass(val binding: SingleRowFreeWeightEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: FreeWeightsEntryModel) {
            binding.model = model
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClass {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: SingleRowFreeWeightEntryBinding =
            inflate(layoutInflater, R.layout.single_row_free_weight_entry, parent, false)
        context = parent.context
        return ViewClass(binding)
    }

    override fun onBindViewHolder(holder: ViewClass, position: Int) {
        holder.bind(list[position])
        holder.binding.date.text = Store.getReadableDate(list[position].date)
        holder.binding.time.text = Store.getReadableTime(list[position].time)
        holder.binding.parentLayout.setOnClickListener {
            listener.click(list[position].id)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}