package ru.androidschool.intensiv.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.androidschool.intensiv.BuildConfig
import ru.androidschool.intensiv.R
import ru.androidschool.intensiv.data.MovieDto
import ru.androidschool.intensiv.databinding.FeedFragmentBinding
import ru.androidschool.intensiv.databinding.FeedHeaderBinding
import ru.androidschool.intensiv.network.MovieApiClient
import ru.androidschool.intensiv.ui.afterTextChanged
import timber.log.Timber
import java.util.concurrent.TimeUnit

class FeedFragment : Fragment(R.layout.feed_fragment) {

    private var _binding: FeedFragmentBinding? = null
    private var _searchBinding: FeedHeaderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchBinding get() = _searchBinding!!

    private val adapter by lazy {
        GroupAdapter<GroupieViewHolder>()
    }


    private val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSearchEditText()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FeedFragmentBinding.inflate(inflater, container, false)
        _searchBinding = FeedHeaderBinding.bind(binding.root)
        onSearchEditText()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBinding.searchToolbar.onTextChangedObservable
            .map { it.trim() }
            .debounce(500, TimeUnit.MILLISECONDS)
            .filter {it.toString().length > MIN_LENGTH}
            .doOnNext { Log.d("THR# 42", Thread.currentThread().name) }
            .observeOn(Schedulers.io())
            .doOnNext { }
            .flatMapSingle {
                MovieApiClient.apiClient.searchByQuery(
                   API_KEY,
                    "ru",
                    it
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                setMovies(it.results)
                openSearch(it.results)
                Timber.tag(TAG).d(it.toString())
            }, {
                Timber.tag(TAG).e(it.toString())
            })

        val getNowPlayingMovie = MovieApiClient.apiClient.getNowPlayingMovie(API_KEY, LANGUAGE)

        getNowPlayingMovie.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { it ->
                val movie = it.results
                val movieList = listOf(
                    MainCardContainer(
                        R.string.recommended,
                        movie.map {
                            MovieItem(it) { movie ->
                                openMovieDetails(movie)
                            }
                        }.toList()
                    )
                )
                binding.moviesRecyclerView.adapter = adapter.apply { addAll(movieList) }
            }, { error ->
                    // Логируем ошибку
                    Timber.tag(TAG).e(error.toString())
                }
            )

        val getUpcomingMovie = MovieApiClient.apiClient.getUpcomingMovies(API_KEY, LANGUAGE, 3)
        getUpcomingMovie.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it ->
                val movies = it.results
                val movieList = listOf(MainCardContainer(
                    R.string.upcoming,
                    movies.map {
                        MovieItem(it) {
                                movie ->
                            openMovieDetails(movie)
                        }
                    }.toList()))

                binding.moviesRecyclerView.adapter = adapter.apply { addAll(movieList) }
            }, { error ->
                // Логируем ошибку
                Timber.tag(TAG).e(error.toString())
            })
    }

    private fun setMovies(results: List<MovieDto>) {

        val movieList = listOf(
            MainCardContainer(
                R.string.recommended,
                results.map {
                    MovieItem(it) { movie ->
                        openMovieDetails(movie)
                    }
                }.toList()
            )
        )
        binding.moviesRecyclerView.adapter = adapter.apply { addAll(movieList) }
    }

    private fun openMovieDetails(movie: MovieDto) {
        val bundle = Bundle()
        bundle.putString(KEY_TITLE, movie.title)
        findNavController().navigate(R.id.movie_details_fragment, bundle, options)
    }

    private fun openSearch(searchText: List<MovieDto>) {
        val bundle = Bundle()
        bundle.putParcelableArrayList("list",ArrayList(searchText))

        findNavController().navigate(R.id.search_dest, bundle, options)
    }

    override fun onStop() {
        super.onStop()
        searchBinding.searchToolbar.clear()
        adapter.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _searchBinding = null
    }

    private fun onSearchEditText(): Observable<String> {
        return Observable.create{e ->
            searchBinding.searchToolbar.binding.searchEditText.afterTextChanged {
                e.onNext(it.toString().filter { ((!it.isWhitespace()) && (it.toString().length > 3))})
                e.setCancellable{ searchBinding.searchToolbar.binding.searchEditText.setOnTouchListener(null)
                    e.onComplete()}
            }

        }
    }
    companion object {
        const val MIN_LENGTH = 3
        const val KEY_TITLE = "title"
        const val KEY_SEARCH = "search"
        private val API_KEY = BuildConfig.THE_MOVIE_DATABASE_API
        const val TAG = "FeedFragment"
        const val MOVIE_ID = "movie_id"
        const val LANGUAGE = "ru"
        fun newInstance(myList : ArrayList<MovieDto>): FeedFragment {
            val args = Bundle()
            args.putParcelableArrayList("list",ArrayList(myList))
            val fragment = FeedFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
