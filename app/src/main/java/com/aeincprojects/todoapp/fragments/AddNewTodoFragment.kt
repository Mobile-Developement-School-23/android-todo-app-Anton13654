package com.aeincprojects.todoapp.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.aeincprojects.todoapp.R
import com.aeincprojects.todoapp.databinding.FragmentAddNewTodoBinding
import com.aeincprojects.todoapp.models.TodoItem
import com.aeincprojects.todoapp.util.Importance
import java.time.LocalDateTime
import java.util.*


class AddNewTodoFragment : Fragment(R.layout.fragment_add_new_todo), AdapterView.OnItemSelectedListener {

    private val binding:  FragmentAddNewTodoBinding by viewBinding(FragmentAddNewTodoBinding::bind)
    private val viewModel: AddNewTodoViewModel by viewModels()
    private val args by navArgs<AddNewTodoFragmentArgs>()

    override fun onStart() {
        super.onStart()
        viewModel.takeId(args.id)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.spinnerImportance.onItemSelectedListener = this
        binding.dataText.visibility = View.GONE
        binding.deleteLayout.setOnClickListener{
            viewModel.deleteElement()
            findNavController().navigateUp()
        }
        binding.closeButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.switchData.setOnClickListener {
            if(binding.switchData.isChecked){
                showDatePicker()
            }else{
                binding.dataText.visibility = View.GONE
            }

        }
        binding.saveButton.setOnClickListener {
            saveNewTodoItem()
        }
    }

    fun saveNewTodoItem(){
        val text = binding.dataText.text.toString()
        val importance = when(binding.spinnerImportance.selectedItem){
            "Нет" -> Importance.Normal
            "Низкий" -> Importance.Low
            else -> Importance.Urgent
        }
        val dateFinish = binding.dataText.text.toString()
        viewModel.saveNewTodo(text, importance, dateFinish)
        findNavController().navigateUp()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.selectImportance(p0?.selectedItem.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendar.get(Calendar.MONTH)
        val year: Int = calendar.get(Calendar.YEAR)
        val picker = DatePickerDialog(
            requireContext(),
            { _, currentYear, monthOfYear, dayOfMonth ->
                var dayString: String = dayOfMonth.toString()
                var mountString :String = monthOfYear.toString()
                if(dayOfMonth<10) {dayString = "0$day" }
                if(monthOfYear<10) {mountString = "0$mountString" }
                binding.dataText.visibility = View.VISIBLE
                binding.dataText.text = "$dayString/$mountString/$currentYear"
            }, year, month, day
        )
        picker.show()
    }

}

