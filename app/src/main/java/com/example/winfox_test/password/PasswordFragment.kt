package com.example.winfox_test.password

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.winfox_test.MainActivity
import com.example.winfox_test.R
import com.example.winfox_test.movies.MoviesFragment
import com.example.winfox_test.databinding.FragmentPasswordBinding
import kotlinx.coroutines.launch

class PasswordFragment : Fragment(R.layout.fragment_password) {
    private val viewModel: PasswordViewModel by viewModel()
    private val binding by viewBinding<FragmentPasswordBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.event.collect { event ->
                if (event is PasswordViewModel.Event.SuccessAuth) {
                    (activity as? MainActivity)?.replaceFragment(MoviesFragment())
                }
            }
        }
    }
}
