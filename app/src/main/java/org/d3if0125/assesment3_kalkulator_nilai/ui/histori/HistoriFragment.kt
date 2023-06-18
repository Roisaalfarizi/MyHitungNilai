package org.d3if0125.assesment3_kalkulator_nilai.ui.histori

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.d3if0125.assesment3_kalkulator_nilai.R
import org.d3if0125.assesment3_kalkulator_nilai.databinding.FragmentHistoriBinding
import org.d3if0125.assesment3_kalkulator_nilai.db.NilaiDb
import org.d3if0125.assesment3_kalkulator_nilai.db.SettingDataStore
import org.d3if0125.assesment3_kalkulator_nilai.db.dataStore

class HistoriFragment : Fragment() {

    private val viewModel: HistoriViewModel by lazy {
        val db = NilaiDb.getInstance(requireContext())

        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HistoriViewModel::class.java]
    }
    private var isLinearLayout = true
    private val layoutDataStore: SettingDataStore by lazy {
        SettingDataStore(requireContext().dataStore)
    }



    private lateinit var binding: FragmentHistoriBinding
    private lateinit var myAdapter: HistoriAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHistoriBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = HistoriAdapter()
        with(binding.recyclerView){
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        layoutDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) {
            isLinearLayout = it
            setLayout()
            activity?.invalidateOptionsMenu()
        }


        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        })
    }
    private fun setLayout() {
        binding.recyclerView.layoutManager = if (isLinearLayout)
            LinearLayoutManager(context)
        else
            GridLayoutManager(context, 2)
    }
    private fun setIcon(menuItem: MenuItem) {
        val iconId = if (isLinearLayout)
            R.drawable.baseline_grid_view_24
        else
            R.drawable.baseline_view_list_24
        menuItem.icon = ContextCompat.getDrawable(requireContext(), iconId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.histori_menu, menu)
        val menuItem = menu.findItem(R.id.action_switch_layout)
        setIcon(menuItem)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_switch_layout -> {
                lifecycleScope.launch {
                    layoutDataStore.saveLayout(!isLinearLayout, requireContext())
                }
                return true
            }
            R.id.menu_hapus -> {
                hapusData()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun hapusData() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.hapus)) { _, _ ->
                viewModel.hapusData()
            }
            .setNegativeButton(getString(R.string.batal)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}