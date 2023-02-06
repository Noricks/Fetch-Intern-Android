package com.example.fetchintern

import org.json.JSONArray

class Util {
    companion object {
        fun parseJSONArray(jsonArray: JSONArray): ArrayList<ListItem> {
            val listItemArray = ArrayList<ListItem>()
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
            return listItemArray
        }

        fun processArray(listItemArray: ArrayList<ListItem>): List<ListItem> {
            val listItemArrayComparator = Comparator<ListItem> { o1, o2 ->
                if (o1.listId == o2.listId) {
                    o1.name.compareTo(o2.name)
                } else {
                    o1.listId.compareTo(o2.listId)
                }
            }
            return listItemArray.filterNot { it.name == "null" || it.name == "" }.sortedWith(listItemArrayComparator)
        }
    }
}