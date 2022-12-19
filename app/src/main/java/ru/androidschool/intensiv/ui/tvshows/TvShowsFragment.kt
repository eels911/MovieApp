package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShow
import ru.androidschool.intensiv.data.TvShowsResponse
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.feed.FeedFragment
import timber.log.Timber

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment) {
    private var _binding: TvShowsFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TvShowsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Используя Мок-репозиторий получаем фэйковый список фильмов
//        val moviesList =
//            MockRepository.getMovies().map {
//                SeriesItem(it) { movie ->
//                    openMovieDetails(movie)
//                }
//            }
//
//        binding.seriesRecyclerView.adapter = adapter.apply { addAll(moviesList) }
        val getPopularSeries = MovieApiClient.apiClient.getPopularSeries(API_KEY,"ru",7)
        getPopularSeries.enqueue(object: Callback<TvShowsResponse> {
            override fun onResponse(
                call: Call<TvShowsResponse>,
                response: Response<TvShowsResponse>
            ) {
                val series = response.body()?.results!!

                val seriesList =
                    series.map {
                        SeriesItem(it){
                                series ->
                            openMovieDetails(series)
                        }
                    }.toList()

                binding.seriesRecyclerView.adapter = adapter.apply { addAll(seriesList) }
                adapter.updateAsync(seriesList)
            }

            override fun onFailure(call: Call<TvShowsResponse>, error: Throwable) {
                Timber.tag(FeedFragment.TAG).e(error.toString())
            }

        })
    }

    private fun openMovieDetails(movie: TvShow) {
        val bundle = Bundle()
        bundle.putString(FeedFragment.KEY_TITLE, movie.name)
        findNavController().navigate(R.id.movie_details_fragment, bundle)
    }

    companion object {
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        const val TAG = "TvShowsFragment"
    }
}
