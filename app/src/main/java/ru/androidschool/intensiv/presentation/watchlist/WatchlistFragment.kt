package ru.androidschool.intensiv.presentation.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.database.MovieDatabase
import ru.androidschool.intensiv.data.database.MovieEntity
import ru.androidschool.intensiv.databinding.FragmentWatchlistBinding
import ru.androidschool.intensiv.domain.subObserve
import timber.log.Timber

class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRecyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(listOf()) }

        val databaseMovie = MovieDatabase.get(requireContext())

        var movieList: ArrayList<MoviePreviewItem> = ArrayList()

        databaseMovie.movieDao().getMovies()
            .subObserve()
            .subscribe({ movie ->
                movieList.clear()
                movieList = movie.map {
                    MoviePreviewItem(it) { v ->
                        openMovieDetails(v)
                    }
                } as ArrayList<MoviePreviewItem>
                binding.moviesRecyclerView.adapter = adapter.apply { addAll(movieList) }
            }, {
                    error -> Timber.tag("TAGERROR").e(error.toString())
            })

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        adapter.clear()
    }

    private fun openMovieDetails(movie: MovieEntity) {
        val bundle = Bundle()
        bundle.putString(KEY_TITLE, movie.title)
        bundle.putFloat(RATING, movie.rating ?: 5F)
        bundle.putString(OVERVIEW, movie.overview)
        bundle.putInt(ID, movie.id)
        bundle.putString(POSTERPATH, movie.posterPath)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = WatchlistFragment()
        const val RATING = "rating"
        const val KEY_TITLE = "title"
        const val OVERVIEW = "overview"
        const val POSTERPATH = "posterPath"
        const val ID = "id"
    }
}
