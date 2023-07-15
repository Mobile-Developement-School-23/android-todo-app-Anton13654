package com.aeincprojects.todoapp.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aeincprojects.todoapp.R
import com.aeincprojects.todoapp.databinding.FragmentSettingsBinding
import com.aeincprojects.todoapp.util.Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private val binding: FragmentSettingsBinding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.theme.collect{
                if(it!=null){
                    Toast.makeText(requireContext(), it.name, Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.buttonLightTheme.setOnClickListener{
            viewModel.updateTheme(Theme.Light)
        }
        binding.buttonNightTheme.setOnClickListener{
            viewModel.updateTheme(Theme.Night)
        }
        binding.buttonSystemTheme.setOnClickListener{
            viewModel.updateTheme(Theme.System)
        }


    }

}