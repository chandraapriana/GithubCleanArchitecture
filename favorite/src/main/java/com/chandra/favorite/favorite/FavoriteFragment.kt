package com.chandra.favorite.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandra.core.domain.model.Users
import com.chandra.core.ui.ListAdapter
import com.chandra.favorite.databinding.FragmentFavoriteBinding

import com.chandra.favorite.di.favoriteModule
import com.chandra.github.detail.DetailFragment
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules


class FavoriteFragment : Fragment() {


    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnFavoriteBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }




        observeFavorite()
    }

    private fun passingData(data: Users) {
        val bundle = Bundle()
        bundle.putString(DetailFragment.LOGIN, data.login)
        val detailFragment = DetailFragment()
        detailFragment.arguments = bundle
        parentFragmentManager
            .beginTransaction()
            .replace(com.chandra.github.R.id.main_container, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeFavorite() {

        favoriteViewModel.favoriteUsers.observe(viewLifecycleOwner, {
            val listAdapter = ListAdapter(arrayListOf())

            it.let {
                if (!it.isNullOrEmpty()) {
                    binding.tvNoFavorite.visibility = View.GONE
                    listAdapter.setData(it)
                } else {
                    binding.tvNoFavorite.visibility = View.VISIBLE
                }

            }
            listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Users) {
                    passingData(data)
                }
            })
            binding.rvFavorite.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = listAdapter
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(favoriteModule)
    }

}