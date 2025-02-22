package ru.aa.shapkin.Tanchiki.models

import android.view.View
import ru.aa.shapkin.Tanchiki.enums.Direction

data class Bullet(
    val view: View,
    val direction: Direction,
    val tank: Tank,
    var canMoveFurther: Boolean = true
)