package com.nikpnch.canvaspaint

import android.graphics.PorterDuff
import android.view.View
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.nikpnch.canvaspaint.base.Item
import kotlinx.android.synthetic.main.item_palette.view.*
import kotlinx.android.synthetic.main.item_size.view.*
import kotlinx.android.synthetic.main.item_tools.view.*

fun colorAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateLayoutContainer<ToolItem.ColorModel, Item>(
        R.layout.item_palette
    ) {
        bind { list ->
            itemView.color.setColorFilter(
                context.resources.getColor(item.color, null),
                PorterDuff.Mode.SRC_IN
            )
            itemView.setOnClickListener { onClick(adapterPosition) }
        }
    }

fun sizeChangeAdapterDelegate(
    onSizeClick: (Int) -> Unit
): AdapterDelegate<List<Item>> = adapterDelegateLayoutContainer<ToolItem.SizeModel, Item>(
    R.layout.item_size
) {
    bind { list ->
        itemView.tvToolsSize.text = item.size.toString()
        itemView.setOnClickListener {
            onSizeClick(adapterPosition)
        }
    }
}

fun toolsAdapterDelegate(
    onToolsClick: (Int) -> Unit
): AdapterDelegate<List<Item>> = adapterDelegateLayoutContainer<ToolItem.ToolModel, Item>(
    R.layout.item_tools
) {
    bind { list ->
        itemView.ivTool.setImageResource(item.type.value)

        if (itemView.tvToolsText.visibility == View.VISIBLE) {
            itemView.tvToolsText.visibility = View.GONE
        }

        when (item.type) {
            TOOLS.NORMAL -> {
                if (item.selectedTool == TOOLS.NORMAL) {
                    itemView.ivTool.setBackgroundResource(R.drawable.bg_selected)
                } else {
                    itemView.ivTool.setBackgroundResource(android.R.color.transparent)
                }
            }

            TOOLS.DASH -> {
                if (item.selectedTool == TOOLS.DASH) {
                    itemView.ivTool.setBackgroundResource(R.drawable.bg_selected)
                } else {
                    itemView.ivTool.setBackgroundResource(android.R.color.transparent)
                }
            }

            TOOLS.SIZE -> {
                itemView.tvToolsText.visibility = View.VISIBLE
                itemView.tvToolsText.text = item.selectedSize.value.toString()
            }

            TOOLS.PALETTE -> {
                itemView.ivTool.setColorFilter(
                    context.resources.getColor(item.selectedColor.value, null),
                    PorterDuff.Mode.SRC_IN
                )
            }
        }

        itemView.setOnClickListener {
            onToolsClick(adapterPosition)
        }
    }
}