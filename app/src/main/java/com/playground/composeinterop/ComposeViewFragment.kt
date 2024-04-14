package com.playground.composeinterop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.playground.composeinterop.databinding.FragmentComposeViewBinding


class ComposeViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentComposeViewBinding.inflate(inflater, container, false)
        with(binding) {
            composeView.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    FocusComposeView()
                }
            }
        }
        return binding.root
    }

    @Composable
    fun FocusComposeView() {
        var statusText by remember { mutableStateOf("Composable Text 0") }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .focusable(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                statusText,
                modifier = Modifier.onFocusChanged {
                    if (it.hasFocus) {
                        statusText = "Composable statusText has focus"
                    }
                }.focusable()
            )

            Text(
                "Composable Text1",
                modifier = Modifier.onFocusChanged {
                    if (it.hasFocus) {
                        statusText = "Composable Text1 has focus"
                    }
                }.focusable()
            )

            TextButton(
                modifier = Modifier.onFocusChanged {
                    if (it.hasFocus) {
                        statusText = "Composable Button has focus"
                    }
                }.focusable(),
                onClick = {
                    statusText = "Composable Button clicked"
                }) {
                Text("Composable Button")
            }

            Text(
                "Composable Text2",
                modifier = Modifier.onFocusChanged {
                    if (it.hasFocus) {
                        statusText = "Composable Text2 has focus"
                    }
                }.focusable()
            )
        }
    }
}