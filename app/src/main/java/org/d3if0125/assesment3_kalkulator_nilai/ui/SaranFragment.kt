package org.d3if0125.assesment3_kalkulator_nilai.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import org.d3if0125.assesment3_kalkulator_nilai.R
import org.d3if0125.assesment3_kalkulator_nilai.databinding.FragmentSaranBinding
import org.d3if0125.assesment3_kalkulator_nilai.model.KategoriNilai

class SaranFragment : Fragment() {

    private lateinit var binding: FragmentSaranBinding
    private val args: SaranFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSaranBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun updateUI(kategori: KategoriNilai) {
        val actionBar = (requireActivity() as AppCompatActivity).supportActionBar
        when (kategori) {
            KategoriNilai.A -> {
                actionBar?.title = getString(R.string.judul_a)
                binding.imageView.setImageResource(R.drawable.nilai_a)
                binding.textView.text = getString(R.string.saran_a)
            }
            KategoriNilai.B -> {
                actionBar?.title = getString(R.string.judul_b)
                binding.imageView.setImageResource(R.drawable.nilai_b)
                binding.textView.text = getString(R.string.saran_b)
            }
            KategoriNilai.C -> {
                actionBar?.title = getString(R.string.judul_C)
                binding.imageView.setImageResource(R.drawable.nilai_c)
                binding.textView.text = getString(R.string.saran_c)
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI(args.kategori)
    }
}