package org.d3if0125.assesment3_kalkulator_nilai.ui.histori

import android.graphics.drawable.GradientDrawable
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if0125.assesment3_kalkulator_nilai.R
import org.d3if0125.assesment3_kalkulator_nilai.databinding.ItemHistoriBinding
import org.d3if0125.assesment3_kalkulator_nilai.db.NilaiEntity
import org.d3if0125.assesment3_kalkulator_nilai.model.KategoriNilai
import org.d3if0125.assesment3_kalkulator_nilai.model.hitungnilai
import java.util.*

class HistoriAdapter :
    ListAdapter<NilaiEntity, HistoriAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<NilaiEntity>() {
                override fun areItemsTheSame(
                    oldData: NilaiEntity, newData: NilaiEntity
                ): Boolean {
                    return oldData.id == newData.id
                }

                override fun areContentsTheSame(
                    oldData: NilaiEntity, newData: NilaiEntity
                ): Boolean {
                    return oldData == newData
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoriBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ItemHistoriBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormatter = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale("id", "ID")
        )

        fun bind(item: NilaiEntity) = with(binding) {
            val hasilNilai = item.hitungnilai()
            kategoriTextView.text = hasilNilai.kategori.toString().substring(0, 1)
            val colorRes = when (hasilNilai.kategori) {
                KategoriNilai.A -> R.color.aa
                KategoriNilai.B -> R.color.be
                else -> R.color.ce
            }

            val background = kategoriTextView.background
            if (background is GradientDrawable) {
                background.setColor(ContextCompat.getColor(root.context, colorRes))
            }

            tanggalTextView.text = dateFormatter.format(Date(item.tanggal))
            nilaiTextView.text = root.context.getString(
                R.string.hasil_x,
                hasilNilai.nilai, hasilNilai.kategori.toString()
            )

            dataTextView.text = root.context.getString(
                R.string.data_x,
                item.praktikum,
                item.assessment1,
                item.assessment2,
                item.assessment3
            )
        }
    }
    }