package ru.androidschool.intensiv.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MockRepository
import ru.androidschool.intensiv.data.Movie
import ru.androidschool.intensiv.databinding.TvShowsFragmentBinding
import ru.androidschool.intensiv.ui.feed.FeedFragment

class TvShowsFragment : Fragment(R.layout.tv_shows_fragment){
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
        val moviesList = listOf(
            SerialsCardContainer(
                MockRepository.getMovies().map {
                    SeriesItem(it) { movie ->
                    openMovieDetails(movie)
                    }
                }.toList()
            )
        )
        binding.seriesRecyclerView.adapter = adapter.apply { addAll(moviesList) }
    }
    private fun openMovieDetails(movie: Movie) {
        val bundle = Bundle()
        bundle.putString(FeedFragment.KEY_TITLE, movie.title)
        findNavController().navigate(R.id.movie_details_fragment, bundle)
    }
}
