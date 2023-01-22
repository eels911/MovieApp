package ru.androidschool.intensiv.ui.movie_details

import android.view.View
import android.widget.ImageView
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
        view.ivActor.loadImage(content.imagePath)
    }

    override fun initializeViewBinding(v: View) = ItemActorBinding.bind(v)

    private fun ImageView.loadImage(imgUrl:String?) {
        com.squareup.picasso.Picasso.get()
            .load(imgUrl)
            .into(this)
    }
}
