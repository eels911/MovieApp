package ru.androidschool.intensiv.ui.tvshows

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.databinding.SeriesWithTextBinding

class SeriesItem(
    private val content: TvShow,
    private val onClick: (tvShow: TvShow) -> Unit
) : BindableItem<SeriesWithTextBinding>() {

    override fun getLayout(): Int = R.layout.series_with_text

    override fun bind(viewBinding: SeriesWithTextBinding, position: Int) {
        viewBinding.seriesName.text = content.name
        viewBinding.movieRating.rating = content.rating.toFloat()
        viewBinding.seriesContent.setOnClickListener {
            onClick.invoke(content)
        }
        Picasso.get()
            .load(content.banner)
            .into(viewBinding.ivTvposter)
    }


    override fun initializeViewBinding(view: View) = SeriesWithTextBinding.bind(view)
}
