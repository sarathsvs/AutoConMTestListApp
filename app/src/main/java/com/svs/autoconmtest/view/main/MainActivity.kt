package com.svs.autoconmtest.view.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.svs.autoconmtest.R
import com.svs.autoconmtest.network.APIExecutor
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var numberAdapter: NumberListAdapter
    var numbersList : ArrayList<String> = ArrayList()
    var isLoading=false
    var page=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        initializeListView()
        getNumbers(1)
    }

    private fun initializeListView(){
        try{
            val layoutManager = LinearLayoutManager(applicationContext)
            rcViewNumbers.layoutManager = layoutManager
            rcViewNumbers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if(dy>0){
                        if(!isLoading){
                            if(layoutManager.findLastVisibleItemPosition()==(numbersList.size-10)){
                                //Loading More Data When list reaches 40th pos
                                page++
                                getNumbers(page)
                            } } } }
            })

            numberAdapter = NumberListAdapter(numbersList, applicationContext) { item: String, position: Int ->
                Unit
                onNumberClicked(item, position)
            }
            rcViewNumbers.adapter = numberAdapter

            isLoading=false
        }catch (e: Exception){

        }

    }

    private fun onNumberClicked(item: String, position: Int) {
        Toast.makeText(applicationContext,"CLicked on :$item",Toast.LENGTH_SHORT).show()
    }



    private fun getNumbers(page:Int){
        showLoading(true)
        if(!isLoading){
            isLoading=true
            val instStartNum=(page-1)*50 //1-1*50 = 50
            val intEndNum = (page*50)

            GlobalScope.launch(Dispatchers.Main) {
                val response = APIExecutor.getApiService().getNumbers(instStartNum.toString(), intEndNum.toString())
                if(response.isSuccessful){
                    val apiNumberList = ArrayList<String>(response.body()!!)
                    if(page==1){
                        numbersList.clear()
                    }else{
                        //TO REMOVE DUPLICATE FROM THE LIST
                        numbersList.remove(numbersList[numbersList.size-1])
                    }

                    for(item in apiNumberList){ numbersList.add(item) }

                    numberAdapter.updateList(numbersList)
                    isLoading=false
                    showLoading(false)

                }
            }
        }

    }

    private fun showLoading(stat:Boolean){
        if(page==1){
            if(stat){
                loadingLayout.visibility= View.VISIBLE
                contentView.visibility=View.GONE
            }else{
                loadingLayout.visibility= View.GONE
                contentView.visibility=View.VISIBLE
            }
        }

    }

}