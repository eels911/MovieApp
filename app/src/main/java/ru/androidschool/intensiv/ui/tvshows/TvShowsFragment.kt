package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.TvShow
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

        val getPopularSeries = MovieApiClient.apiClient.getPopularSeries(API_KEY, LANGUAGE, 7)
       getPopularSeries.observeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .subscribe({it->
               val tvShow = it.results
               val seriesList =
                   tvShow.map {
                        SeriesItem(it) {
                                series ->
                            openMovieDetails(series)
                       }
                    }.toList()
                binding.seriesRecyclerView.adapter = adapter.apply { addAll(seriesList) }
           },{ error ->
               // Логируем ошибку
               Timber.tag(TAG).e(error.toString())
           })
    }

    override fun onStop() {
        super.onStop()
        adapter.clear()
    }

    private fun openMovieDetails(movie: TvShow) {
        val bundle = Bundle()
        bundle.putString(FeedFragment.KEY_TITLE, movie.name)
        findNavController().navigate(R.id.movie_details_fragment, bundle)
    }

    companion object {
        private const val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        const val TAG = "TvShowsFragment"
        const val LANGUAGE = "ru"
    }
}
