package org.d3if0125.assesment3_kalkulator_nilai.ui.penemu


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if0125.assesment3_kalkulator_nilai.R
import org.d3if0125.assesment3_kalkulator_nilai.databinding.ListItemBinding
import org.d3if0125.assesment3_kalkulator_nilai.model.PenemuHitung
import org.d3if0125.assesment3_kalkulator_nilai.network.PenemuApi
import org.d3if0125.assesment3_kalkulator_nilai.network.ServiceAPI


class PenemuAdapter : RecyclerView.Adapter<PenemuAdapter.ViewHolder>(){

    private val data = mutableListOf<PenemuHitung>()

    fun updateData(newData: List<PenemuHitung>){
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class ViewHolder(private val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(penemuHitung: PenemuHitung) = with(binding){
            judulTextView.text = penemuHitung.judul
            tglTextView.text = penemuHitung.tgl
            Glide.with(imageView.context)
                .load(ServiceAPI.getPenemuUrl(penemuHitung.imageId))
                .error(R.drawable.baseline_broken_image_24)
                .into(imageView)
            tempatTextView.text = penemuHitung.tempat
            deskripsiTextView.text = penemuHitung.deskripsi


            root.setOnClickListener{
                val message = root.context.getString(R.string.message, penemuHitung.judul)
                Toast.makeText(root.context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
}