package com.cwsn.mobileapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.cwsn.mobileapp.R
import com.cwsn.mobileapp.databinding.ActivityMainBinding
import com.cwsn.mobileapp.network.Status
import com.cwsn.mobileapp.viewmodel.hello.HelloViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("MoveLambdaOutsideParentheses")
class MainActivity : AppCompatActivity()
{

    private val helloViewModel by viewModel<HelloViewModel>()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        helloViewModel.getApplicationMessage().observe(this, { resource->
            when (resource.status) {
                Status.SUCCESS -> {
                    Toast.makeText(this@MainActivity,"result ${resource.data?.value}",Toast.LENGTH_LONG).show()
                    binding.tvMessage.text=resource.data?.value
                }
                Status.ERROR -> {
                    Toast.makeText(this@MainActivity,"error",Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    Toast.makeText(this@MainActivity,"loading",Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}