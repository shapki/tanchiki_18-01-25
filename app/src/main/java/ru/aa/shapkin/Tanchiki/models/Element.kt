package ru.aa.shapkin.Tanchiki.models

import ru.aa.shapkin.Tanchiki.enums.Material

data class Element(
    val viewId: Int,
    val material: Material,
    val coordinate: Coordinate,
    val width: Int,
    val height: Int
) {

}