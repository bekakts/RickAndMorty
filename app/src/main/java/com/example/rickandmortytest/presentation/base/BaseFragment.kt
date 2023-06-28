package com.example.rickandmortytest.presentation.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.rickandmortytest.presentation.utils.ConnectionLiveData
import com.example.rickandmortytest.presentation.utils.UIState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupSubscribers()
        initClickListeners()
    }

    protected open fun initialize() {}
    protected open fun setupSubscribers() {}
    protected open fun initClickListeners() {}


    protected fun <T> StateFlow<UIState<T>>.collectUIState(
        state: ((UIState<T>) -> Unit)? = null,
        onSuccess: (data: T) -> Unit
    ) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                this@collectUIState.collect {
                    state?.invoke(it)
                    when (it) {
                        is UIState.Empty -> {}
                        is UIState.Error -> {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
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

    protected fun SearchView.baseSearchLogic(
        invalidate: () -> Unit,
        request: (name: String) -> Unit
    ) {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                clearFocus()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                p0?.let {
                    invalidate()
                    request(it)
                }
                return false
            }
        })
    }

    protected fun checkConnection(cld:ConnectionLiveData,request: () -> Unit) {
        if (activity != null && isAdded) {

            cld.observe(viewLifecycleOwner) { answer ->
                if (answer) {
                    request()
                }
            }
        }
    }
}