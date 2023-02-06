package com.example.fetchintern

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
import com.example.fetchintern.Util.Companion.parseJSONArray
import com.example.fetchintern.Util.Companion.processArray
import com.example.fetchintern.databinding.FragmentFirstBinding
import com.example.fetchintern.databinding.ItemListBinding
import com.example.fetchintern.databinding.RightItemsBinding
import org.json.JSONArray
import org.json.JSONException

/**
 * A simple [Fragment] subclass which displays the data from the API.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    // URL for the API
    private val fetchURL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
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

    /**
     * This function is called when the data is loaded from the API.
     * It parses the data and calls the function to render the table.
     *
     * @param filteredArray The array of data to be rendered.
     */
    private fun renderTable(filteredArray: List<ListItem>) {
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
    }

    /**
     * This function is called when the data is loaded from the API.
     * It parses the data and calls the function to render the table.
     *
     * @param url The URL to load the data from.
     */
    private fun loadData(url: String = fetchURL) {
        val queue = Volley.newRequestQueue(binding.root.context)

        val request: StringRequest =
            object : StringRequest(Method.GET, url,
                Response.Listener { response ->
                    try {
                        val jsonArray = JSONArray(response)
                        val listItemArray = parseJSONArray(jsonArray)
                        val filteredArray = processArray(listItemArray)
                        renderTable(filteredArray)
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