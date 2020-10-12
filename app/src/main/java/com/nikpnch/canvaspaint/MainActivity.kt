package com.nikpnch.canvaspaint

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nikpnch.canvaspaint.base.showIf
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    companion object {
        private const val PALETTE = 0
        private const val SIZE = 1
        private const val TOOLS = 2
    }

    private val viewModel: ViewModel by viewModel()

    private val appNameColorsList = listOf(
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.CYAN,
        Color.RED,
        Color.GREEN,
        Color.BLUE,
        Color.CYAN,
        Color.RED,
        Color.GREEN,
        Color.BLUE
    )

    private lateinit var toolsLayouts: List<ToolsLayout>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initColoredAppNameToolbar()

        toolsLayouts = listOf(
            palette as ToolsLayout,
            size as ToolsLayout,
            tools as ToolsLayout
        )

        toolsLayouts[0].setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnColorClick(it))
        }
        toolsLayouts[1].setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnSizeClick(it))
        }
        toolsLayouts[2].setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolsClick(it))
        }
        drawView.setOnClickField {
            viewModel.processUiEvent(UiEvent.OnDrawViewClicked)
        }
        viewModel.viewState.observe(this, Observer(::render))
        openPalette.setOnClickListener {
            viewModel.processUiEvent(UiEvent.OnToolbarClicked)
        }
        clearField.setOnClickListener {
            drawView.clear()
        }
    }

    private fun initColoredAppNameToolbar() {
        val word: Spannable = SpannableString(appName.text)
        appName.text.forEachIndexed { index, c ->
            word.setSpan(
                ForegroundColorSpan(appNameColorsList[index]),
                index,
                index + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

        }
        appName.text = word
    }

    private fun render(viewState: ViewState) {
        toolsLayouts[PALETTE].showIf(viewState.isPaletteVisible)
        toolsLayouts[PALETTE].render(viewState.colorList)
        toolsLayouts[SIZE].showIf(viewState.isBrushSizeChangerVisible)
        toolsLayouts[SIZE].render(viewState.sizeList)
        toolsLayouts[TOOLS].showIf(viewState.isToolsVisible)
        toolsLayouts[TOOLS].render(viewState.toolsList)
        drawView.render(viewState.canvasViewState)
    }
}

