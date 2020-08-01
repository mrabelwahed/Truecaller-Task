package com.ramadan.ui

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ramadan.R
import com.ramadan.ResultState
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        fetchDataView.setOnClickListener {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.getTenthCharRequest()
        viewModel.tenthCharData.observe(this, Observer {
            when (it) {
                is ResultState.Success<*> -> {
                    val data = it.value as String
                    textView1.text = data
                }
                is ResultState.Error -> {
                    if (it.failure is Failure.NetworkConnection)
                        textView1.text = "No Internet Connection"
                    else
                        textView1.text = " Error happens while getting 10th Char"
                }
            }
        })

        viewModel.getEveryTenthCharRequest()
        viewModel.everyTenthCharData.observe(this, Observer {
            when (it) {
                is ResultState.Success<*> -> {
                    val data = it.value as String
                    textView2.text = data
                }
                is ResultState.Error -> {
                    if (it.failure is Failure.NetworkConnection)
                        textView2.text = "No Internet Connection"
                    else
                        textView2.text = " Error happens while getting 10th Char"
                }
            }
        })

        viewModel.getWordCountRequest()
        viewModel.wordCountData.observe(this, Observer {
            when (it) {
                is ResultState.Success<*> -> {
                    val data = it.value as String
                    textView3.text = data
                }
                is ResultState.Error -> {
                    if (it.failure is Failure.NetworkConnection)
                        textView3.text = "No Internet Connection"
                    else
                        textView3.text = " Error happens while getting 10th Char"
                }
            }
        })

    }
}
