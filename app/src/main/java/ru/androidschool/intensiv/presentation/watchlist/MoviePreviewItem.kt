package ru.androidschool.intensiv.presentation.watchlist

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.database.MovieEntity
import ru.androidschool.intensiv.databinding.ItemWithTextBinding

class MoviePreviewItem(
    private val content: MovieEntity,
    private val onClick: (movie: MovieEntity) -> Unit
) : BindableItem<ItemWithTextBinding>() {

    override fun getLayout() = R.layout.item_with_text

    override fun bind(view: ItemWithTextBinding, position: Int) {
        view.imagePreview.setOnClickListener {
            onClick.invoke(content)
        }
        view.description.text = content.title

        Picasso.get()
            .load(content.posterPath)
            .fit()
            .centerCrop()
            .into(view.imagePreview)
    }

    override fun initializeViewBinding(v: View): ItemWithTextBinding = ItemWithTextBinding.bind(v)
}
