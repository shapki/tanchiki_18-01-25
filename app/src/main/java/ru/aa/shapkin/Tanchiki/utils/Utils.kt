package ru.aa.shapkin.Tanchiki.utils

import android.view.View
import ru.aa.shapkin.Tanchiki.binding
import ru.aa.shapkin.Tanchiki.models.Coordinate

fun View.checkViewCanMoveThroughBorder(coordinate: Coordinate): Boolean {
    return coordinate.top >= 0 &&
            coordinate.top + this.height <= binding.container.height &&
            coordinate.left >= 0 &&
            coordinate.left + this.width <= binding.container.width
}