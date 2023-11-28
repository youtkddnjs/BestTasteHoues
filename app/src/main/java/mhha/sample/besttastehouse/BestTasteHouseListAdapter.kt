package mhha.sample.besttastehouse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128
import mhha.sample.besttastehouse.databinding.ItemRestaurantBinding

class BestTasteHouseListAdapter(private val onClick:(LatLng) -> Unit): RecyclerView.Adapter<BestTasteHouseListAdapter.ViewHolder>(){//class BestTasteHouseListAdapter

    private var dataSet = emptyList<SearchItem>()

    inner class ViewHolder(private val binding: ItemRestaurantBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item:SearchItem){
            binding.titleTextView.text= item.title
            binding.categoryTextView.text = item.category
            binding.locationTextView.text = item.roadAddress

            binding.root.setOnClickListener {
                val tm128 = Tm128(item.mapx.toDouble(),item.mapy.toDouble())
                val latLng = tm128.toLatLng()
                onClick( latLng )
            }
        }//fun bind(item:SearchItem)
    }//inner class ViewHolder(private val binding: ItemRestaurantBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    fun setData(dataSet: List<SearchItem>){
        this.dataSet = dataSet
    }

}//class BestTasteHouseListAdapter(private val onClick:(LatLng) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>()