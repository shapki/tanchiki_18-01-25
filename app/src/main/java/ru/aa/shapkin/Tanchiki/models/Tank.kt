package ru.aa.shapkin.Tanchiki.models

import android.view.View
import android.widget.FrameLayout
import ru.aa.shapkin.Tanchiki.CELL_SIZE
import ru.aa.shapkin.Tanchiki.binding
import ru.aa.shapkin.Tanchiki.drawers.BulletDrawer
import ru.aa.shapkin.Tanchiki.drawers.EnemyDrawer
import ru.aa.shapkin.Tanchiki.enums.Direction.UP
import ru.aa.shapkin.Tanchiki.enums.Direction.DOWN
import ru.aa.shapkin.Tanchiki.enums.Direction.LEFT
import ru.aa.shapkin.Tanchiki.enums.Direction.RIGHT
import ru.aa.shapkin.Tanchiki.enums.Material
import ru.aa.shapkin.Tanchiki.utils.*
import kotlin.random.Random

class Tank constructor(
    val element: Element,
    var direction: ru.aa.shapkin.Tanchiki.enums.Direction,
    private val enemyDrawer: EnemyDrawer
) {
    fun move(
        direction: ru.aa.shapkin.Tanchiki.enums.Direction,
        container: FrameLayout,
        elementsOnContainer: List<Element>
    ) {
        val view = container.findViewById<View>(element.viewId) ?: return
        val currentCoordinate = view.getViewCoordinate()
        this.direction = direction
        view.rotation = direction.rotation
        val nextCoordinate = getTankNewCoordinate(view)
        if (view.checkViewCanMoveThroughBorder(nextCoordinate)
            && element.checkTankCanMoveThroughMaterial(nextCoordinate, elementsOnContainer)
        ) {
            emulateViewMoving(container, view)
            element.coordinate = nextCoordinate
            generateRandomDirectionForEnemyTank()
        } else {
            element.coordinate = currentCoordinate
            (view.layoutParams as FrameLayout.LayoutParams).topMargin = currentCoordinate.top
            (view.layoutParams as FrameLayout.LayoutParams).leftMargin = currentCoordinate.left
            changeDirectionForEnemyTank()
        }
    }

    private fun generateRandomDirectionForEnemyTank() {
        if (element.material != Material.ENEMY_TANK) {
            return
        }
        if (checkIfChanceBiggerThanRandom(10)) {
            changeDirectionForEnemyTank()
        }
    }

    private fun changeDirectionForEnemyTank() {
        if (element.material == Material.ENEMY_TANK) {
            val randomDirection = ru.aa.shapkin.Tanchiki.enums.Direction.values()[Random.nextInt(ru.aa.shapkin.Tanchiki.enums.Direction.values().size)]
            this.direction = randomDirection
        }
    }

    private fun emulateViewMoving(container: FrameLayout, view: View) {
        container.runOnUiThread {
            binding.container.removeView(view)
            binding.container.addView(view, 0)
        }
    }

    private fun getTankNewCoordinate(view: View): Coordinate {
        val layoutParams = view.layoutParams as FrameLayout.LayoutParams
        when (direction) {
            UP -> {
                view.rotation = 0f
                (view.layoutParams as FrameLayout.LayoutParams).topMargin += -CELL_SIZE
            }
            DOWN -> {
                view.rotation = 180f
                (view.layoutParams as FrameLayout.LayoutParams).topMargin += CELL_SIZE
            }
            LEFT -> {
                view.rotation = 270f
                (view.layoutParams as FrameLayout.LayoutParams).leftMargin -= CELL_SIZE
            }
            RIGHT -> {
                view.rotation = 90f
                (view.layoutParams as FrameLayout.LayoutParams).leftMargin += CELL_SIZE
            }
        }

        return Coordinate(layoutParams.topMargin, layoutParams.leftMargin)
    }

    private fun Element.checkTankCanMoveThroughMaterial(
        coordinate: Coordinate,
        elementsOnContainer: List<Element>
    ): Boolean {
        for (anyCoordinate in getTankCoordinates(coordinate)) {
            var element = getElementByCoordinates(anyCoordinate, elementsOnContainer)
            if (element == null) {
                element = getTankByCoordinates(anyCoordinate, enemyDrawer.tanks)
            }
            if (element != null && !element.material.tankCanGoThrough) {
                if(this == element) {
                    continue
                }
                return false
            }
        }
        return true
    }

    private fun getTankCoordinates(topLeftCoordinate: Coordinate): List<Coordinate> {
        val coordinateList = mutableListOf<Coordinate>()
        coordinateList.add(topLeftCoordinate)
        coordinateList.add(Coordinate(topLeftCoordinate.top + CELL_SIZE, topLeftCoordinate.left))
        coordinateList.add(Coordinate(topLeftCoordinate.top, topLeftCoordinate.left + CELL_SIZE))
        coordinateList.add(Coordinate(topLeftCoordinate.top + CELL_SIZE, topLeftCoordinate.left + CELL_SIZE))
        return coordinateList
    }
}