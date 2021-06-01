package com.chandra.github.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandra.core.data.source.Resource
import com.chandra.core.domain.model.Users
import com.chandra.core.ui.ListAdapter
import com.chandra.github.R
import com.chandra.github.databinding.FragmentUsersBinding
import com.chandra.github.detail.DetailFragment
import org.koin.android.viewmodel.ext.android.viewModel


class UsersFragment : Fragment() {

    private lateinit var binding: FragmentUsersBinding
    private val usersViewModel: UsersViewModel by viewModel()
    private lateinit var userAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = ListAdapter(arrayListOf())
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = userAdapter
        }
        userAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                val bundle = Bundle()
                bundle.putString(DetailFragment.LOGIN, data.login)
                val detailFragment = DetailFragment()
                detailFragment.arguments = bundle
                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, detailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        observeUser()

    }

    private fun observeUser() {
        usersViewModel.getUsers().observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Success -> {
                        it.data.let { data -> userAdapter.setData(data) }
                        binding.progressBarUsers.visibility = View.GONE
                    }
                    is Resource.Loading -> {
                        binding.progressBarUsers.visibility = View.VISIBLE
                    }

                    is Resource.Error ->{
                        binding.progressBarUsers.visibility = View.GONE
                        Toast.makeText(context, "error ${it.message}", Toast.LENGTH_SHORT).show()
                    }



                }
            }
        }
        )


    }


}