package ru.androidschool.intensiv.ui.feed

import android.view.View
import android.widget.ImageView
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.databinding.ItemWithTextBinding

class MovieItem(
    private val content: MovieDto,
    private val onClick: (movie: MovieDto) -> Unit
) : BindableItem<ItemWithTextBinding>() {

    override fun getLayout(): Int = R.layout.item_with_text

    override fun bind(view: ItemWithTextBinding, position: Int) {
        view.description.text = content.title
        view.content.setOnClickListener {
            onClick.invoke(content)
        }
        view.movieRating.rating = content.voteAverage
        view.imagePreview.loadImage(content, view)

    }

    override fun initializeViewBinding(v: View) = ItemWithTextBinding.bind(v)
}

private fun ImageView.loadImage(content: MovieDto,view:ItemWithTextBinding) {
    com.squareup.picasso.Picasso.get()
        .load(content.posterPath)
        .into(view.imagePreview)

}
