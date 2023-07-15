package com.aeincprojects.todoapp.presentation.newTodo

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aeincprojects.todoapp.R
import com.aeincprojects.todoapp.data.models.TodoFromServer
import com.aeincprojects.todoapp.util.ExtendedTheme
import com.aeincprojects.todoapp.util.Importance
import com.aeincprojects.todoapp.util.TodoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddNewTodoFragment : Fragment()/*, AdapterView.OnItemSelectedListener*/ {

    private val viewModel: AddNewTodoViewModel by viewModels()
    private val args by navArgs<AddNewTodoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
               // TodoAppTheme {
                    makeScreen()
              //  }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.takeId(args.id)
    }

    private fun saveNewTodoItem(text: String, importance: Importance, data: String) {
        var dateFinish: Long = 0
        dateFinish = if (data.isEmpty()) {
            0
        } else {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            dateFormat.parse(data).time
        }
        viewModel.saveTodo(text, importance, dateFinish)
        findNavController().navigateUp()
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePicker(onDataChanged:(String)->Unit) {
        val calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
        val month: Int = calendar.get(Calendar.MONTH)
        val year: Int = calendar.get(Calendar.YEAR)
        var textData = ""
        val picker = DatePickerDialog(
            requireContext(),
            { _, currentYear, monthOfYear, dayOfMonth ->
                var dayString: String = dayOfMonth.toString()
                var mountString: String = monthOfYear.toString()
                if (dayOfMonth < 10) {
                    dayString = "0$day"
                }
                if (monthOfYear < 10) {
                    mountString = "0$mountString"
                }
                onDataChanged("$dayString/$mountString/$currentYear")

            }, year, month, day
        )
        picker.show()
    }


    @Preview
    @Composable
    fun makeScreen() {

        val todo by viewModel.item.collectAsState()

        val textState = remember { mutableStateOf("") }
        if(todo!=null){
            textState.value = todo!!.text
        }
        val checkedState = remember { mutableStateOf(false) }
        val data = remember { mutableStateOf("")}
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = { findNavController().navigateUp() },
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.button_close),
                        contentDescription = "Image"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp, 0.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    TextButton(onClick = { saveNewTodoItem(textState.value, Importance.Low, data.value) }) {
                        Text(text = "Сохранить", color = Color.Black)
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(7.dp, 20.dp),
                shape = RoundedCornerShape(9.dp),
                elevation = 5.dp
            ) {
                TextField(
                    value = textState.value,
                    onValueChange = { newText -> textState.value = newText },
                    label = {
                        Text(text =
                        if(todo!=null){
                            if(todo?.text!!.isNotEmpty()){
                                ""
                            }else{"Нужно сделать..."}
                        }else{"Нужно сделать..."})
                    },
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = ExtendedTheme.colors.backSecondary)
                )
            }
            TextButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(12.dp, 0.dp)) {
                Text(text = "Важность")
            }
            Text(text = "Нет", modifier = Modifier.padding(20.dp, 3.dp))
            Divider(
                color = Color.Black, thickness = 1.dp, modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 12.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Сделать до", modifier = Modifier.padding(20.dp, 12.dp))
                Text(text = data.value)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp, 0.dp), contentAlignment = Alignment.CenterEnd
                ) {
                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it
                            if(it){
                                showDatePicker(){it -> data.value = it}}
                            else{
                                data.value = ""
                            }
                        }
                        )
                }
            }
            Divider(
                color = Color.Black, thickness = 1.dp, modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 12.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { viewModel.deleteElement() }) {
                    Image(
                        painter = painterResource(id = R.drawable.button_delete),
                        contentDescription = "",
                        Modifier.padding(20.dp, 27.dp)
                    )
                }
                Text(text = "Удалить")
            }
        }


    }

}

