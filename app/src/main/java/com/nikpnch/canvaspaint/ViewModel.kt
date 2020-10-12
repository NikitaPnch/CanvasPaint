package com.nikpnch.canvaspaint

import com.nikpnch.canvaspaint.base.BaseViewModel
import com.nikpnch.canvaspaint.base.Event

class ViewModel : BaseViewModel<ViewState>() {

    override fun initialViewState(): ViewState = ViewState(
        toolsList = enumValues<TOOLS>().map {
            ToolItem.ToolModel(
                it,
                TOOLS.NORMAL,
                SIZE.SMALL,
                COLOR.BLACK
            )
        },
        colorList = enumValues<COLOR>().map { ToolItem.ColorModel(it.value) },
        sizeList = enumValues<SIZE>().map { ToolItem.SizeModel(it.value) },
        canvasViewState = CanvasViewState(COLOR.BLACK, SIZE.SMALL, TOOLS.NORMAL),
        isPaletteVisible = false,
        isBrushSizeChangerVisible = false,
        isToolsVisible = false
    )

    override fun reduce(event: Event, previousState: ViewState): ViewState? {
        when (event) {
            is UiEvent.OnColorClick -> {
                return previousState.copy(
                    toolsList = enumValues<TOOLS>().map {
                        ToolItem.ToolModel(
                            type = it,
                            selectedTool = previousState.toolsList[0].selectedTool,
                            selectedColor = COLOR.from(previousState.colorList[event.index].color),
                            selectedSize = previousState.toolsList[0].selectedSize
                        )
                    },
                    canvasViewState = previousState.canvasViewState.copy(
                        color = COLOR.from(previousState.colorList[event.index].color),
                    ),
                    isPaletteVisible = false
                )
            }
            is UiEvent.OnSizeClick -> {
                return previousState.copy(
                    toolsList = enumValues<TOOLS>().map {
                        ToolItem.ToolModel(
                            type = it,
                            selectedTool = previousState.toolsList[0].selectedTool,
                            selectedSize = SIZE.from(previousState.sizeList[event.index].size),
                            selectedColor = previousState.toolsList[0].selectedColor
                        )
                    },
                    canvasViewState = previousState.canvasViewState.copy(
                        size = SIZE.from(previousState.sizeList[event.index].size),
                    ),
                    isBrushSizeChangerVisible = false
                )
            }
            is UiEvent.OnToolsClick -> {
                return when (event.index) {
                    0 -> {
                        previousState.copy(
                            toolsList = enumValues<TOOLS>().map {
                                ToolItem.ToolModel(
                                    type = it,
                                    selectedTool = TOOLS.NORMAL,
                                    selectedColor = previousState.toolsList[0].selectedColor,
                                    selectedSize = previousState.toolsList[0].selectedSize
                                )
                            },
                            canvasViewState = previousState.canvasViewState.copy(
                                tools = TOOLS.NORMAL
                            )
                        )
                    }
                    1 -> {
                        previousState.copy(
                            toolsList = enumValues<TOOLS>().map {
                                ToolItem.ToolModel(
                                    type = it,
                                    selectedTool = TOOLS.DASH,
                                    selectedColor = previousState.toolsList[0].selectedColor,
                                    selectedSize = previousState.toolsList[0].selectedSize
                                )
                            },
                            canvasViewState = previousState.canvasViewState.copy(
                                tools = TOOLS.DASH
                            )
                        )
                    }
                    2 -> {
                        previousState.copy(
                            isPaletteVisible = false,
                            isBrushSizeChangerVisible = true
                        )
                    }
                    3 -> {
                        previousState.copy(
                            isPaletteVisible = true,
                            isBrushSizeChangerVisible = false
                        )
                    }
                    else -> {
                        return null
                    }
                }
            }
            is UiEvent.OnToolbarClicked -> {
                return previousState.copy(
                    isToolsVisible = !previousState.isToolsVisible
                )
            }
            is UiEvent.OnDrawViewClicked -> {
                return previousState.copy(
                    isToolsVisible = false,
                    isPaletteVisible = false,
                    isBrushSizeChangerVisible = false
                )
            }
        }
        return null
    }
}