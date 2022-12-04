package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemSeriesBinding

class SerialsCardContainer(
    private val items: List<BindableItem<*>>
) : BindableItem<ItemSeriesBinding>() {

    override fun getLayout() = R.layout.item_series

    override fun initializeViewBinding(p0: View): ItemSeriesBinding = ItemSeriesBinding.bind(p0)
    override fun bind(viewBinding: ItemSeriesBinding, position: Int) {
        viewBinding.seriesContainer.adapter = GroupAdapter<GroupieViewHolder>().apply { addAll(items) }
    }
}
