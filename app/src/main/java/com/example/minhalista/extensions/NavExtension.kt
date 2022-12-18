package com.example.minhalista.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import com.example.minhalista.R

private val slideLeftOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navigateWithAnimations(
    destinationId: Int,
    animation: NavOptions = slideLeftOptions
) {
    this.navigate(destinationId, null, animation)
}

fun NavController.navigateWithAnimations(
    directions: NavDirections,
    animation: NavOptions = slideLeftOptions
) {
    this.navigate(directions, animation)
}

fun NavController.navigateSafe(
    @IdRes resId: Int,
    args: Bundle? = null,
    animation: NavOptions = slideLeftOptions
) {
    val destinationId = currentDestination?.getAction(resId)?.destinationId.orEmpty()
    currentDestination?.let { node ->
        val currentNode = when (node) {
            is NavGraph -> node
            else -> node.parent
        }
        if (destinationId != 0) {
            currentNode?.findNode(destinationId)?.let { navigate(resId, args, animation) }
        }
    }
}

fun Int?.orEmpty(default: Int = 0): Int {
    return this ?: default
}
