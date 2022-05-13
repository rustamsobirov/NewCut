package me.ruyeo.newcut.adapter.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import me.ruyeo.newcut.R
import me.ruyeo.newcut.model.DetailModel

class DetailImageAdapter(
    private val context: Context, private val lists: ArrayList<DetailModel>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.detail_image_item, parent, false)
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val home = lists[position]

        if (holder is DetailViewHolder) {
            val imageItem = holder.detailImageView

            Glide.with(context)
                .load(home.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(imageItem)


        }
    }

    override fun getItemCount(): Int = lists.size

    class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val detailImageView: ImageView = view.findViewById(R.id.detailImageItem)
    }
}