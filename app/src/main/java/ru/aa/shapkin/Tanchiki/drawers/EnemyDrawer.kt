package ru.aa.shapkin.Tanchiki.drawers

import android.widget.FrameLayout
import ru.aa.shapkin.Tanchiki.CELL_SIZE
import ru.aa.shapkin.Tanchiki.models.Coordinate

class EnemyDrawer(private val container: FrameLayout) {
    private val respawnList: List<Coordinate>

    init {
        respawnList = getRespawnList()
    }

    private fun getRespawnList(): List<Coordinate> {
        val respawnList = mutableListOf<Coordinate>()
        respawnList.add(Coordinate(0, 0))
        respawnList.add(Coordinate(0, container.width / 2 - CELL_SIZE))
        respawnList.add(Coordinate(0, container.width - CELL_SIZE))
        return respawnList
    }
}