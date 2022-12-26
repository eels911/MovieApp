package ru.androidschool.intensiv.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.data.MovieDetailsResponse
import ru.androidschool.intensiv.databinding.MovieDetailsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.feed.FeedFragment
import timber.log.Timber

class MovieDetailsFragment : Fragment(R.layout.movie_details_fragment) {

private var _binding: MovieDetailsFragmentBinding? = null

    private val binding get() = _binding!!

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

    // Используя Мок-репозиторий получаем фэйковый список фильмов
    val actorList =
        MockRepository.getActors().map {
            ActorItem(it)
        }

    val getMovieDetails = MovieApiClient.apiClient.getMovieDetails(id)

    getMovieDetails.enqueue(object : Callback<MovieDetailsResponse> {
        override fun onResponse(
            call: Call<MovieDetailsResponse>,
            response: Response<MovieDetailsResponse>
        ) {

//            val movieList =  listOf( MainCardContainer(
//                R.string.recommended,
//                movies.map {
//                    MovieItem(it){
//                            movie ->
//                        openMovieDetails(movie)
//                    }
//                }.toList())
//            )
        }

        override fun onFailure(call: Call<MovieDetailsResponse>, error: Throwable) {
            // Логируем ошибку
            Timber.tag(FeedFragment.TAG).e(error.toString())
        }
    }
    )

    binding.listActor.adapter = adapter.apply { addAll(actorList) }
}

    private fun setViews(movie: MovieDetailsResponse) {
        binding.tvMovieName.text = movie.title
    }
}
