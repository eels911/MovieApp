package ru.androidschool.intensiv.presentation.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.database.MovieEntity
import ru.androidschool.intensiv.data.network.MovieApiClient
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.domain.subObserve
import ru.androidschool.intensiv.domain.subObserveComp
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

private var _binding: MovieDetailsFragmentBinding? = null

    private val binding get() = _binding!!
    private var db : MovieDatabase? = null
    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


    // Постер фильма в хэдере
    Picasso.get()
        .load(requireArguments().getString(POSTERPATH))
        .fit()
        .centerCrop()
        .into(binding.ivTopBackground)

    // Устанавливаем Название
    val movieTitle = requireArguments().getString(TITLE)
    val movieDesc = requireArguments().getString(OVERVIEW)

    binding.tvMovieName.text = movieTitle
    binding.tvDesc.text = movieDesc
    binding.movieRating.rating = arguments?.getFloat(RATING) ?: 5f


    // БД
    val databaseMovie = MovieDatabase.get(requireContext())
    // Кнопка лайка
    val likeMovie = binding.ivFavorite

    databaseMovie.movieDao()
        .getMovieDetails(requireArguments().getInt(ID))
        .subObserve()
        .subscribe({ equal ->
            likeMovie.isChecked = equal
        }, {
                error -> Timber.tag("TAGERROR").e(error.toString())
        })

    likeMovie.setOnClickListener {
        if (!likeMovie.isChecked) {
            likeMovie.isChecked = false
            databaseMovie.movieDao().deleteById(requireArguments().getInt(ID))
                .subObserveComp()
                .subscribe({ databaseMovie.movieDao()
                    .deleteById(requireArguments().getInt(ID))
                }, {
                        error -> Timber.tag("TAGERROR").e(error.toString())
                })
        } else {
            likeMovie.isChecked = true
            val movieEntity = MovieEntity(
                requireArguments().getInt(ID),
                requireArguments().getString(TITLE),
                requireArguments().getString(POSTERPATH),
                requireArguments().getString(OVERVIEW),
                requireArguments().getFloat(RATING)
            )
            databaseMovie.movieDao().save(movieEntity)
                .subObserveComp()
                .subscribe({ databaseMovie.movieDao().save(movieEntity)
                }, {
                        error -> Timber.tag("TAGERROR").e(error.toString())
                })
        }
    }

}

//    private fun setViews(movie: MovieDetailsResponse) {
//        binding.tvMovieName.text = movie.title
//    }
    companion object{
    const val POSTERPATH = "posterPath"
    const val ID = "id"
    const val TITLE = "title"
    const val OVERVIEW = "overview"
    const val RATING = "rating"
    }
}

