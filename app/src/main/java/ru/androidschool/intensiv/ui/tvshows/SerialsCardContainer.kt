package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import androidx.annotation.StringRes
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.databinding.ItemCardBinding
import ru.androidschool.intensiv.databinding.ItemSeriesBinding

class SerialsCardContainer(
    @StringRes
    private val title: Int,
    private val items: List<BindableItem<*>>
) : BindableItem<ItemSeriesBinding>() {

    override fun getLayout() = R.layout.item_series



    override fun initializeViewBinding(p0: View): ItemSeriesBinding = ItemSeriesBinding.bind(p0)
    override fun bind(viewBinding: ItemSeriesBinding, position: Int) {
        TODO("Not yet implemented")
    }
}
