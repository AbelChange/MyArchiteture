package com.temption.myarchitecture.logic.websocket

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.temption.myarchitecture.databinding.WebSocketFragmentBinding

class WebSocketFragment : Fragment() {

    private lateinit var binding: WebSocketFragmentBinding

    private val viewModel by viewModels<WebSocketViewModel>()

    companion object {
        fun newInstance() = WebSocketFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WebSocketFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStartConnect.setOnClickListener {
            val socket = WebSocketManager.connect("10.191.84.172", 39541);
        }
    }




}