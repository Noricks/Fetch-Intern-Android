package com.example.fetchintern

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.fetchintern.databinding.FragmentFirstBinding
import com.example.fetchintern.databinding.ItemListBinding
import com.example.fetchintern.databinding.RightItemsBinding
import org.json.JSONArray
import org.json.JSONException


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
    }

    private fun loadData() {
        val queue = Volley.newRequestQueue(binding.root.context)
        val listItemArray = ArrayList<ListItem>()
        val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
        val request: StringRequest =
            @SuppressLint("SetTextI18n")
            object : StringRequest(Method.GET, url,
                Response.Listener { response ->
                    try {
                        val jsonArray = JSONArray(response)
                        if (jsonArray.length() > 0) {
                            for (i in 0 until jsonArray.length()) {
                                val jsonObject = jsonArray.getJSONObject(i)
                                val listItem = ListItem(
                                    jsonObject.getInt("id"),
                                    jsonObject.getInt("listId"),
                                    jsonObject.getString("name")
                                )
                                listItemArray.add(listItem)
                            }
                        }
                        val listItemArrayComparator = Comparator<ListItem> { o1, o2 ->
                            if (o1.listId == o2.listId) {
                                o1.name.compareTo(o2.name)
                            } else {
                                o1.listId.compareTo(o2.listId)
                            }
                        }
                        val filteredArray = listItemArray.filterNot { it.name == "null" || it.name == "" }.sortedWith(listItemArrayComparator)
                        var prevListId = filteredArray[0].listId
                        var block:ItemListBinding = ItemListBinding.inflate(layoutInflater)
                        block.listId.text = prevListId.toString()
                        for (element in filteredArray) {
                            if (prevListId != element.listId) {
                                binding.table.addView(block.root)
                                block = ItemListBinding.inflate(layoutInflater)
                                block.listId.text = element.listId.toString()
                                prevListId = element.listId
                            }
                            val row = RightItemsBinding.inflate(layoutInflater)
                            row.id.text = element.id.toString()
                            row.name.text = element.name
                            block.right.addView(row.root)
                        }
                        binding.table.addView(block.root)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error -> // displaying toast message on response failure.
                    Log.e("tag", "error is " + error!!.message)
                    Toast.makeText(binding.root.context, "Fail to load data..", Toast.LENGTH_SHORT)
                        .show()
                }) {}
        queue.add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}