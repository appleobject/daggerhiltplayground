package com.appleobject.daggerhiltplayground.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.appleobject.daggerhiltplayground.ERROR_MESSAGE
import com.appleobject.daggerhiltplayground.R
import com.appleobject.daggerhiltplayground.model.Blog
import com.appleobject.daggerhiltplayground.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.lang.StringBuilder


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "AppDebug"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateEvent(MainStateEvent.GetBlogEvents)
    }

    private fun subscribeObservers(){
        viewModel.dataState.observe(this, Observer { dataState ->

            when(dataState){

                is DataState.Success<List<Blog>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(dataState.data)
                }

                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(dataState.exception.message)
                }

                is DataState.Loading -> {
                    displayProgressBar(isDisplayed = true)
                }
            }

        })
    }


    private fun displayError(message: String?){
        if(message != null){
            text.text = message
        }else{
            text.text = ERROR_MESSAGE
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean){
         progress_bar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun appendBlogTitles(blogs: List<Blog>){
        val sb = StringBuilder()
        for (blog in blogs){
            sb.append(blog.title + "\n")
        }

        text.text = sb.toString()
    }
}