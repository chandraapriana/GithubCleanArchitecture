package com.chandra.github.home

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.chandra.github.R
import com.chandra.github.databinding.ActivityMainBinding
import com.chandra.github.users.UsersFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadUsers(UsersFragment())
        moveFavorite()

    }

    private fun moveFavorite() {
        val className = "com.chandra.favorite.favorite.FavoriteFragment"
        binding.toolbarMain.btnFavorite.setOnClickListener {
            val fragment = instantiateFragment(className)
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun loadUsers(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.home_container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit()
    }

    private fun instantiateFragment(className: String): Fragment? {
        return try {
            Class.forName(className).newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            null
        }
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q &&
            isTaskRoot &&
            supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount ?: 0 == 0 &&
            supportFragmentManager.backStackEntryCount == 0
        ) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}