package com.example.rickandmortytest.presentation.ui.characters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.asFlow
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.rickandmortytest.R
import com.example.rickandmortytest.databinding.FragmentCharactersBinding
import com.example.rickandmortytest.presentation.base.BaseFragment
import com.example.rickandmortytest.presentation.utils.ConnectionLiveData
import com.example.rickandmortytest.presentation.utils.LoadStatePagerAdapter
import com.example.rickandmortytest.presentation.utils.initDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class CharactersFragment : BaseFragment(R.layout.fragment_characters) {

    private val binding by viewBinding(FragmentCharactersBinding::bind)
    private val viewModel: CharactersViewModel by viewModel()
    private val characterAdapter = CharacterAdapter(this::onClick)
    private var name: String? = null
    private var statusID: String? = null
    private var speciesID: String? = null
    private var genderID: String? = null
    private var statusId: RadioButton? = null
    private var speciesId: RadioButton? = null
    private var genderId: RadioButton? = null
    private lateinit var cld: ConnectionLiveData
    private val loadStateAdapter = LoadStatePagerAdapter()

    override fun initialize() {
        super.initialize()
        cld = ConnectionLiveData(requireActivity().application)
        checkConnection(cld) { viewModel.getCharacter(name, statusID, speciesID, genderID) }
        setupRecyclerView()
        initObserve()
    }

    override fun setupSubscribers() {
        super.setupSubscribers()
        viewModel.getCharacterState.collectUIState(
            state = null,
            onSuccess = {
                characterAdapter.submitData(lifecycle, it)
                binding.recyclerView.scrollToPosition(0)
            }
        )
    }

    override fun initClickListeners() {
        super.initClickListeners()
        with(binding) {
            tvFilter.setOnClickListener {
                filterLogic()
            }
            recyclerView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                if ((binding.recyclerView.adapter?.itemCount ?: 0) > 0) {
                    binding.shimmerViewContainer.isVisible = false
                }
            }
            searchCharacters.baseSearchLogic(
                {viewModel.invalidate()},
                {name-> viewModel.getCharacter(name,statusID, speciesID, genderID)}
            )
        }
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerView.adapter = characterAdapter.withLoadStateFooter(loadStateAdapter)
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun onClick(id: Int) {
        findNavController().navigate(R.id.characterDetailFragment, bundleOf("key" to id))
    }

    private fun initObserve() {
        with(viewModel) {
            lifecycleScope.launch {
                combine(
                    species.asFlow(),
                    status.asFlow(),
                    gender.asFlow()
                ) { species, status, gender ->
                    statusID = status
                    speciesID = species
                    genderID = gender
                    getCharacter(name, status, species, gender)
                }.collect()
            }
        }
    }

    @SuppressLint("ResourceType", "InflateParams")
    private fun filterLogic() {
        val dialogBinding = layoutInflater.inflate(R.layout.filter_dialog, null)
        val dialog = dialogBinding.initDialog(requireContext())
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        val status = dialog.findViewById<RadioGroup>(R.id.status_group)
        val species = dialog.findViewById<RadioGroup>(R.id.species_group)
        val gender = dialog.findViewById<RadioGroup>(R.id.gender_group)
        val btnSearch = dialog.findViewById<Button>(R.id.btnSearch)
        val btnClear = dialog.findViewById<Button>(R.id.btnClear)
        statusId?.id?.let { status.check(it) }
        speciesId?.id?.let { species.check(it) }
        genderId?.id?.let { gender.check(it) }
        species.setOnCheckedChangeListener { group, checkedId ->
            speciesId = group.findViewById(checkedId)
        }
        gender.setOnCheckedChangeListener { group, checkedId ->
            genderId = group.findViewById(checkedId)
        }
        status.setOnCheckedChangeListener { group, checkedId ->

            statusId = group.findViewById(checkedId)
        }
        btnSearch.setOnClickListener {
            viewModel.addFilter(
                statusId?.text as String?,
                speciesId?.text as String?,
                genderId?.text as String?
            )
            dialog.dismiss()
        }
        btnClear.setOnClickListener {
            status.clearCheck()
            species.clearCheck()
            gender.clearCheck()
        }
    }
}