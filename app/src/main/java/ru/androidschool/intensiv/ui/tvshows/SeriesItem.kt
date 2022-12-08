package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.databinding.SeriesWithTextBinding

class SeriesItem(
    private val content: Movie, private val onClick: (movie: Movie) -> Unit
) : BindableItem<SeriesWithTextBinding>() {

    override fun getLayout(): Int = R.layout.series_with_text

    override fun bind(viewBinding: SeriesWithTextBinding, position: Int) {
        viewBinding.seriesName.text = content.title
        viewBinding.movieRating.rating = content.rating
        viewBinding.seriesContent.setOnClickListener {
            onClick.invoke(content)
        }
    }

    override fun initializeViewBinding(view: View) = SeriesWithTextBinding.bind(view)
}