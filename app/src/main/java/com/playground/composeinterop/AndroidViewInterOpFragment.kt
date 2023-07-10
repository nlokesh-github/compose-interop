package com.playground.composeinterop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.AndroidViewBinding
import androidx.navigation.fragment.findNavController
import com.playground.composeinterop.databinding.FragmentAndroidViewInteropBinding
import com.playground.composeinterop.databinding.ViewLayoutInComposeBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AndroidViewInterOpFragment : Fragment() {

    private var _binding: FragmentAndroidViewInteropBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /* _binding = FragmentAndroidViewInteropBinding.inflate(inflater, container, false)
         return binding.root*/
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {

                FocusAndroidView()

            }
        }

    }

    @Composable
    fun FocusAndroidView() {
        var statusText by remember { mutableStateOf("Change focus or click") }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                statusText,
                modifier = Modifier.focusable(true)
            )

            // Swap true/false to easily try with and without the AndroidView focusable modifier
            val setAndroidViewFocusable = true
            val androidViewModifier = if (setAndroidViewFocusable) {
                Modifier
                    .onFocusChanged {
                        if (it.hasFocus) {
                            statusText = "AndroidView has focus"
                        }
                    }
                    .focusable()
            } else {
                Modifier
            }
            //
            AndroidView(
                modifier = androidViewModifier,
                factory = {
                    android.widget.Button(it)
                },
                update = {
                    it.text = "View Button"
                    it.setOnClickListener { statusText = "View Button clicked" }
                    it.setOnFocusChangeListener { _: View, hasFocus: Boolean ->
                        if (hasFocus) {
                            statusText = "View button has focus"
                        }
                    }
                }
            )

            AndroidViewBinding(
                ViewLayoutInComposeBinding::inflate,
                modifier = androidViewModifier
            ) {

            }

            Text(
                "Composable Text",
                modifier = Modifier.focusable()
            )

            Button(
                modifier = Modifier.onFocusChanged {
                    if (it.hasFocus) {
                        statusText = "Composable Button has focus"
                    }
                },
                onClick = {
                    statusText = "Composable Button clicked"
                }) {
                Text("Composable Button")
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}