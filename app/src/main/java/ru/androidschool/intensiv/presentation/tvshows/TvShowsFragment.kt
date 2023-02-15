package ru.androidschool.intensiv.presentation.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.repository.TvShowRemoteRepository
import ru.androidschool.intensiv.data.vo.TvShow
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.domain.usecase.TvShowUseCase
import ru.androidschool.intensiv.presentation.feed.FeedFragment

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment), TvShowsPresenter.ShowView {
    private var _binding: TvShowsFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    private val tvShowsPresenter: TvShowsPresenter by lazy {
        TvShowsPresenter(TvShowUseCase(TvShowRemoteRepository()))
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

        tvShowsPresenter.attachView(this)

//        val getPopularSeries = MovieApiClient.apiClient.getPopularSeries(API_KEY, LANGUAGE, 7)
//        getPopularSeries.observeOn(Schedulers.io())
//           .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({ it ->
//               val tvShow = it.results
//               val seriesList =
//                   tvShow.map {
//                       SeriesItem(it) { series ->
//                           openMovieDetails(series)
//                       }
//                   }.toList()
//               binding.seriesRecyclerView.adapter = adapter.apply { addAll(seriesList) }
//           }, { error ->
//               // Логируем ошибку
//               Timber.tag(TAG).e(error.toString())
//           })

        tvShowsPresenter.getShows()
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
        const val TAG = "TvShowsFragment"
    }

    override fun showShows(shows: List<TvShow>) {
        val tvShow = shows
        val seriesList =
            tvShow.map {
                SeriesItem(it) { series ->
                    openMovieDetails(series)
                }
            }.toList()
        binding.seriesRecyclerView.adapter = adapter.apply { addAll(seriesList) }

    }


}

