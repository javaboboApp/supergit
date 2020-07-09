package com.theappexperts.supergit.utils

.getQueryTextChangeStateFlow

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SearchView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun EditText.getQueryTextChangeStateFlow(): Flow<String> {
    val query = MutableStateFlow("")


    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            s?.let { searchText ->

                query.value = searchText.toString()
            }
        }
    })

    return query

}