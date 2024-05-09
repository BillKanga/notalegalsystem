package net.ezra.ui.calendar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.util.*



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CalendarScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Calendar App") }) },
        content = { CalendarContent() }
    )
}

@Composable
fun CalendarContent() {
    val selectedDate = remember { mutableStateOf(0) }
    val reminders = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Calendar(selectedDate = selectedDate)
        if (selectedDate.value != 0) {
            ReminderInput(
                selectedDate = selectedDate.value,
                onAddReminder = { reminder ->
                    reminders.add("Date $selectedDate.value: $reminder")
                }
            )
        }
        RemindersList(reminders = reminders)
    }
}

@Composable
fun Calendar(selectedDate: MutableState<Int>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(months) { index, month ->
            MonthItem(month = month, index = index + 1, selectedDate = selectedDate)
        }
    }
}

@Composable
fun MonthItem(month: String, index: Int, selectedDate: MutableState<Int>) {
    val daysInMonth = daysInMonth(index)
    val firstDay = firstDayOfMonth(index)
    val weekDays = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")

    val weekList = mutableListOf<List<String>>()
    val firstWeek = mutableListOf<String>()

    // Add empty cells for the days before the first day of the month
    for (i in 1 until firstDay) {
        firstWeek.add("")
    }

    // Add days of the month
    for (i in 1..daysInMonth) {
        firstWeek.add(i.toString())
    }

    // Pad the list with empty strings to ensure each row has exactly 7 elements
    while (firstWeek.size % 7 != 0) {
        firstWeek.add("")
    }

    // Split weeks
    var start = 1
    while (start < firstWeek.size) {
        val end = minOf(start + 7, firstWeek.size)
        weekList.add(firstWeek.subList(start, end))
        start += 7
    }

    Card(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)

        ) {
            Text(text = month, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(8.dp))
            // Display week days
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                weekDays.forEach { day ->
                    Text(
                        text = day,
                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(4.dp)
                    )
                }
            }
            // Display dates
            weekList.forEach { week ->
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    week.forEach { date ->
                        Box(
                            modifier = Modifier
                                .background(if (date == selectedDate.value.toString()) Color.LightGray else Color.White)
                                .clickable { if (date.isNotBlank()) selectedDate.value = date.toInt() }
                                .padding(4.dp)
                                .size(40.dp, 40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = date,
                                fontSize = 10.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}





fun daysInMonth(month: Int): Int {
    return when (month) {
        2 -> 29 // February
        4, 6, 9, 11 -> 30 // April, June, September, November
        else -> 31 // January, March, May, July, August, October, December
    }
}

fun firstDayOfMonth(month: Int): Int {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, month - 1) // Calendar.MONTH is zero-based
        set(Calendar.DAY_OF_MONTH, 1)
    }
    return calendar.get(Calendar.DAY_OF_WEEK)
}

@Composable
fun ReminderInput(selectedDate: Int, onAddReminder: (String) -> Unit) {
    var reminderText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = reminderText,
            onValueChange = { reminderText = it },
            placeholder = { Text("Enter reminder...") },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                if (reminderText.isNotEmpty()) {
                    onAddReminder("Date $selectedDate: $reminderText")
                    reminderText = ""
                }
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text("Add Reminder")
        }
    }
}

@Composable
fun RemindersList(reminders: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(reminders) { index, reminder ->
            ReminderItem(reminder = reminder)
        }
    }
}

@Composable
fun ReminderItem(reminder: String) {
    Text(
        text = reminder,
        fontSize = 16.sp,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        color = Color.DarkGray
    )
}

val months = listOf(
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

}
