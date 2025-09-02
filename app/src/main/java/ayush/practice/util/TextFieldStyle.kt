package ayush.practice.util

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun searchTextFieldColors() = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color(0xFFF2F7FD),
        unfocusedContainerColor = Color(0xFFF2F7FD),
        focusedBorderColor = Color.Transparent,
        unfocusedBorderColor = Color.Transparent,
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        cursorColor = Color(0xFF6200EE),
        focusedLeadingIconColor = Color.Black,
        unfocusedLeadingIconColor = Color.Black,
        focusedPlaceholderColor = Color(0xFF607080),
        unfocusedPlaceholderColor = Color(0xFF607080)
    )