package sheridan.akhtemob.com.example.workoutapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.DataBindingUtil.inflate
import sheridan.akhtemob.com.example.workoutapp.R
import sheridan.akhtemob.com.example.workoutapp.dataModels.JoggingEntryModel
import sheridan.akhtemob.com.example.workoutapp.databinding.SingleRowJoggingEntryBinding
import sheridan.akhtemob.com.example.workoutapp.ui.common.Store
import sheridan.akhtemob.com.example.workoutapp.ui.interfaces.AdapterClickListener



class JoggingEntriesAdapter {
    private val list: ArrayList<JoggingEntryModel>,
    private val listener: AdapterClickListener
    ) : RecyclerView.Adapter<JoggingEntriesAdapter.ViewClass>() {

        var context: Context? = null

        inner class ViewClass(val binding: SingleRowJoggingEntryBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(model: JoggingEntryModel) {
                binding.model = model
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewClass {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: SingleRowJoggingEntryBinding =
                inflate(layoutInflater, R.layout.single_row_jogging_entry, parent, false)
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

}