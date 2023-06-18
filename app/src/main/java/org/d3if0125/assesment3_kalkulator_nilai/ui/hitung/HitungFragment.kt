package org.d3if0125.assesment3_kalkulator_nilai.ui.hitung

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if0125.assesment3_kalkulator_nilai.R
import org.d3if0125.assesment3_kalkulator_nilai.databinding.FragmentHitungBinding
import org.d3if0125.assesment3_kalkulator_nilai.db.NilaiDb
import org.d3if0125.assesment3_kalkulator_nilai.model.HasilNilai
import org.d3if0125.assesment3_kalkulator_nilai.model.KategoriNilai
import org.d3if0125.assesment3_kalkulator_nilai.ui.penemu.PenemuActivity

class HitungFragment : Fragment() {

    private lateinit var binding: FragmentHitungBinding

    private val viewModel: HitungViewModel by lazy {
        val db = NilaiDb.getInstance(requireContext())
        val factory = HitungViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HitungViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { hitungNilai() }
        binding.btnReset.setOnClickListener { reset() }
        binding.saranButton.setOnClickListener { viewModel.mulaiNavigasi() }
        binding.shareButton.setOnClickListener { shareData() }
        binding.btnAPI.setOnClickListener { lanjutAPI() }

        viewModel.getHasilNilai().observe(requireActivity(), { showResult(it) })
        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(
                HitungFragmentDirections
                .actionHitungFragmentToSaranFragment(it)
            )
            viewModel.selesaiNavigasi()
        })
    }

    private fun lanjutAPI() {
        val lanjut = Intent(requireContext(), PenemuActivity::class.java)
        startActivity(lanjut)
    }

    private fun shareData() {
        val message = getString(
            R.string.bagikan_template,
            binding.praktikumInp.text,
            binding.assessment1Inp.text,
            binding.assessment2Inp.text,
            binding.assessment3Inp.text,
            binding.nilaiTextView.text,
            binding.indexTextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager
            ) != null
        ) {
            startActivity(shareIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_hitungFragment_to_historiFragment)
            return true
        }
        R.id.menu_about  -> {
            findNavController().navigate(R.id.action_hitungFragment_to_aboutFragment)
            return true
        }
    }
    return super.onOptionsItemSelected(item)
    }

    private fun hitungNilai() {
        val praktikum = binding.praktikumInp.text.toString()
        if (TextUtils.isEmpty(praktikum)) {
            Toast.makeText(context, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment1 = binding.assessment1Inp.text.toString()
        if (TextUtils.isEmpty(assessment1)) {
            Toast.makeText(context, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment2 = binding.assessment2Inp.text.toString()
        if (TextUtils.isEmpty(assessment2)) {
            Toast.makeText(context, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val assessment3 = binding.assessment3Inp.text.toString()
        if (TextUtils.isEmpty(assessment3)) {
            Toast.makeText(context, R.string.nilai_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungNilai(
            praktikum.toFloat(),
            assessment1.toFloat(),
            assessment2.toFloat(),
            assessment3.toFloat()
        )
    }

    private fun getKategoriLabel(kategori: KategoriNilai): String {
        val stringRes = when (kategori) {
            KategoriNilai.A -> R.string.bagus
            KategoriNilai.B -> R.string.lumayan
            KategoriNilai.C -> R.string.buruk
        }
        return getString(stringRes)
    }

    private fun reset() {
        val text1 = binding.praktikumInp.text.toString()
        val text2 = binding.assessment1Inp.text.toString()
        val text3 = binding.assessment2Inp.text.toString()
        val text4 = binding.assessment3Inp.text.toString()

        if (text1.isEmpty() && text2.isEmpty() && text3.isEmpty() && text4.isEmpty()) {
            Toast.makeText(context, "Already Empty!", Toast.LENGTH_LONG).show()
        } else {
            binding.praktikumInp.setText("")
            binding.assessment1Inp.setText("")
            binding.assessment2Inp.setText("")
            binding.assessment3Inp.setText("")
            binding.nilaiTextView.text = ""
            binding.indexTextView.text = ""
            binding.praktikumInp.requestFocus()
            //supaya cursornya hilang
            binding.praktikumInp.clearFocus()
            return
        }
    }

    private fun showResult(result: HasilNilai?) {
        if (result == null) return
        binding.nilaiTextView.text = getString(R.string.nilai_x, result.nilai)
        binding.indexTextView.text = getString(
            R.string.index_x,
            getKategoriLabel(result.kategori)
        )
        binding.buttonGroup.visibility = View.VISIBLE
    }

}