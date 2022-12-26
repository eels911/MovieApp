package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import com.squareup.picasso.Picasso
import com.xwray.groupie.viewbinding.BindableItem
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.Actor
import ru.androidschool.intensiv.databinding.ItemActorBinding

class ActorItem(
    private val content: Actor
) : BindableItem<ItemActorBinding>() {

    override fun getLayout(): Int = R.layout.item_actor

    override fun bind(view: ItemActorBinding, position: Int) {

        view.tvActorName.text = content.title

        // TODO Получать из модели
        Picasso.get()
            .load("https://m.media-amazon.com/images/M/MV5BYTk3MDljOWQtNGI2My00OTEzLTlhYjQtOTQ4ODM2MzUwY2IwXkEyXkFqcGdeQXVyNTIzOTk5ODM@._V1_.jpg")
            .into(view.ivActor)
    }

    override fun initializeViewBinding(v: View) = ItemActorBinding.bind(v)
}
