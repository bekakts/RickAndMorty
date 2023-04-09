package com.example.rickandmortytest.presentation.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment(@LayoutRes layoutId: Int): Fragment(layoutId) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupRequests()
        setupSubscribers()
        initClickListeners()
    }

    protected open fun initClickListeners(){}
    protected open fun setupSubscribers(){}
    protected open fun setupRequests(){}
    protected open fun initialize(){}

    protected fun <T> StateFlow<UIState<T>>.collectUIState(
        state: ((UIState<T>) -> Unit)? = null,
        onSuccess: (data: T) -> Unit
    ){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                this@collectUIState.collect{
                    state?.invoke(it)
                    when(it){
                        is UIState.Empty -> {}
                        is UIState.Error -> {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        is UIState.Loading -> {}
                        is UIState.Success -> {
                            onSuccess(it.data)
                        }
                    }

                }
            }
        }
    }

}