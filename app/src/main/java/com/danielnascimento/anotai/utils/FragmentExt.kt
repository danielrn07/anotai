package com.danielnascimento.anotai.utils

import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.danielnascimento.anotai.R
import com.google.android.material.snackbar.Snackbar

fun Fragment.nav(id: Int) {
    findNavController().navigate(id)
}

fun Fragment.snackbar(message: Int, resColor: Int) {
    val snackbar = Snackbar.make(requireView(), getString(message), Snackbar.LENGTH_SHORT)
    snackbar.setTextColor(ContextCompat.getColor(requireContext(), resColor))
    snackbar.show()
}


