package com.example.minhalista.extensions

import android.view.View
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

fun View.show() {
    this.visibility = View.VISIBLE
}
fun View.hide() {
    this.visibility = View.GONE
}
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun MeowBottomNavigation.setTitle(title: String) {

}