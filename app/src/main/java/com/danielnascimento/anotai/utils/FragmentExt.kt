package com.danielnascimento.anotai.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.nav(id: Int) {
    findNavController().navigate(id)
}