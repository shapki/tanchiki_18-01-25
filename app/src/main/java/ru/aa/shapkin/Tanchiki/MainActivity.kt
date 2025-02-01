package ru.aa.shapkin.Tanchiki

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.*
import android.view.Menu
import android.view.MenuItem
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import ru.aa.shapkin.Tanchiki.enums.Direction.UP
import ru.aa.shapkin.Tanchiki.enums.Direction.DOWN
import ru.aa.shapkin.Tanchiki.enums.Direction.LEFT
import ru.aa.shapkin.Tanchiki.enums.Direction.RIGHT
import ru.aa.shapkin.Tanchiki.databinding.ActivityMainBinding
import ru.aa.shapkin.Tanchiki.drawers.BulletDrawer
import ru.aa.shapkin.Tanchiki.drawers.ElementsDrawer
import ru.aa.shapkin.Tanchiki.drawers.GridDrawer
import ru.aa.shapkin.Tanchiki.drawers.TankDrawer
import ru.aa.shapkin.Tanchiki.enums.Material

const val CELL_SIZE = 50

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var editMode = false
    private val gridDrawer by lazy {
        GridDrawer(binding.container)
    }

    private val elementsDrawer by lazy {
        ElementsDrawer(binding.container)
    }

    private val tankDrawer by lazy {
        TankDrawer(binding.container)
    }

    private val bulletDrawer by lazy {
        BulletDrawer(binding.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Menu"

        binding.editorClear.setOnClickListener { elementsDrawer.currentMaterial = Material.EMPTY }
        binding.editorBrick.setOnClickListener { elementsDrawer.currentMaterial = Material.BRICK }
        binding.editorConcrete.setOnClickListener { elementsDrawer.currentMaterial = Material.CONCRETE }
        binding.editorGrass.setOnClickListener { elementsDrawer.currentMaterial = Material.GRASS }
        binding.container.setOnTouchListener() { _, event ->
            elementsDrawer.onTouchContainer(event.x, event.y)
            return@setOnTouchListener true
        }
    }

    private fun switchEditMode() {
        if (editMode) {
            gridDrawer.removeGrid()
            binding.materialsContainer.visibility = INVISIBLE
        } else {
            gridDrawer.drawGrid()
            binding.materialsContainer.visibility = VISIBLE
        }
        editMode = !editMode
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_settings -> {
                switchEditMode()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KEYCODE_DPAD_UP -> tankDrawer.move(
                binding.myTank,
                UP,
                elementsDrawer.elementsOnContainer
            )

            KEYCODE_DPAD_DOWN -> tankDrawer.move(
                binding.myTank,
                DOWN,
                elementsDrawer.elementsOnContainer
            )

            KEYCODE_DPAD_LEFT -> tankDrawer.move(
                binding.myTank,
                LEFT,
                elementsDrawer.elementsOnContainer
            )

            KEYCODE_DPAD_RIGHT -> tankDrawer.move(
                binding.myTank,
                RIGHT,
                elementsDrawer.elementsOnContainer
            )

            KEYCODE_SPACE -> bulletDrawer.makeBulletMove(
                binding.myTank,
                tankDrawer.currentDirection,
                elementsDrawer.elementsOnContainer
            )
        }
        return super.onKeyDown(keyCode, event)
    }
}