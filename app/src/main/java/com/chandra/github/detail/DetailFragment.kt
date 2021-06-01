package com.chandra.github.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chandra.core.data.source.Resource
import com.chandra.core.domain.model.Users
import com.chandra.github.R
import com.chandra.github.databinding.FragmentDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel


class DetailFragment : Fragment(),View.OnClickListener {
    private var isFavorite = false
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModel()
    private var users: Users? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.get(LOGIN)
        binding.toolbarDetail.btnBack.setOnClickListener(this)
        checkFavorite(username.toString())
        observerDetail(username.toString())
    }

    private fun checkFavorite(username: String) {
        detailViewModel.getFavoriteUsersByUname(username)
            ?.observe(viewLifecycleOwner, { user ->
                isFavorite = user.isFavorite == true
                stateFavorite(isFavorite)
            })
    }


    private fun observerDetail(username: String) {
        detailViewModel.detailUsers(username).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    binding.fabAdd.visibility = View.VISIBLE
                    users = it.data!!
                    setDetailUI(it)
                    binding.progressBarDetail.visibility = View.GONE
                }
                is Resource.Loading -> {
                    binding.fabAdd.visibility = View.GONE
                    binding.progressBarDetail.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.fabAdd.visibility = View.GONE
                    binding.progressBarDetail.visibility = View.GONE
                    Toast.makeText(context, "There is something wrong", Toast.LENGTH_SHORT).show()
                }
            }
            stateFavorite(isFavorite)
            binding.fabAdd.setOnClickListener(this)

        })
    }

    private fun setDetailUI(it: Resource<Users>) {
        binding.apply {
            toolbarDetail.tvUsernameDetail.text = it.data!!.login
            tvNameDetail.text = it.data?.name
            tvLocationDetail.text = it.data?.location
            tvCompanyDetail.text = it.data?.company
            tvFollowersDetail.text = it.data?.followers.toString()
            tvFollowingDetail.text = it.data?.following.toString()
            tvRepositoryDetail.text = it.data?.publicRepos.toString()
            Glide.with(this@DetailFragment).load(it.data?.avatarUrl)
                .error(R.drawable.ic_image_broken)
                .apply(RequestOptions().override(120, 120)).into(civImageDetail)

        }
    }

    private fun insertOrDeleteFavorite() {
        if (!isFavorite) {
            users?.isFavorite = !isFavorite
            users?.let { detailViewModel.insertFavorite(it) }
            Toast.makeText(context?.applicationContext, "success add", Toast.LENGTH_SHORT).show()
            isFavorite = !isFavorite
        } else {
            users?.isFavorite = !isFavorite
            users?.let { detailViewModel.deleteFavorite(it) }
            Toast.makeText(context?.applicationContext, "success delete", Toast.LENGTH_SHORT).show()
            isFavorite = !isFavorite
        }
    }

    private fun stateFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabAdd.setImageResource(R.drawable.ic_favorite_fill)
        } else {
            binding.fabAdd.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    companion object {
        const val LOGIN = ""
    }

    override fun onClick(view: View?) {

        when(view?.id){
            R.id.btn_back -> parentFragmentManager.popBackStack()
            R.id.fab_add ->{
                insertOrDeleteFavorite()
                stateFavorite(isFavorite)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        users = null
    }

}