package ru.aa.shapkin.Tanchiki.models

import android.view.View
import ru.aa.shapkin.Tanchiki.enums.Material

data class Element constructor(
    val viewId: Int = View.generateViewId(),
    val material: Material,
    var coordinate: Coordinate,
    val width: Int = material.width,
    val height: Int = material.height
) {

}