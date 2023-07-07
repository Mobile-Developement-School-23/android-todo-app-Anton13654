package com.aeincprojects.todoapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aeincprojects.todoapp.R
import com.aeincprojects.todoapp.databinding.FragmentMainBinding
import com.aeincprojects.todoapp.data.models.TodoItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainFragmentViewModel by viewModels()
    

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.sendItems()

        val adapter = ListTodoAdapter(){
           // viewModel.updateStatusTodo(it)
        }
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ) = true

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                    val position = viewHolder.bindingAdapterPosition
                 //   viewModel.deleteElement(position)
                }
            }
        ).attachToRecyclerView(binding.recyclerView)

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.items.collect{
                adapter.items = it
            }
        }

        binding.addNewTodo.setOnClickListener{
            //viewModel.getListTodo()
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToAddNewTodoFragment(""))
        }
    }


}