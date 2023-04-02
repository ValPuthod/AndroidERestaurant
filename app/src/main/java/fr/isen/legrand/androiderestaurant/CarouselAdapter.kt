package fr.isen.legrand.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CarouselAdapter(private val carouselDataList: ArrayList<String>) :
    RecyclerView.Adapter<CarouselAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.item_carousel, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageView)

        val imageUrl = carouselDataList[position]
        if(imageUrl.isNotEmpty()) {

            Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.food_placeholder)
                .error(R.drawable.food_placeholder)
                .into(imageView)
        }

    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

}