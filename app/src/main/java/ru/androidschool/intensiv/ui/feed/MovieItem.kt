package ru.androidschool.intensiv.ui.feed

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie2
import ru.androidschool.intensiv.databinding.ItemWithTextBinding

class MovieItem(
    private val content: Movie2,
    private val onClick: (movie: Movie2) -> Unit
) : BindableItem<ItemWithTextBinding>() {

    override fun getLayout(): Int = R.layout.item_with_text

    override fun bind(view: ItemWithTextBinding, position: Int) {
        view.description.text = content.title
        view.content.setOnClickListener {
            onClick.invoke(content)
        }
        view.movieRating.rating = content.voteAverage

        // TODO Получать из модели
        Picasso.get()
            .load(content.posterPath)
            .into(view.imagePreview)
    }

    override fun initializeViewBinding(v: View) = ItemWithTextBinding.bind(v)
}
