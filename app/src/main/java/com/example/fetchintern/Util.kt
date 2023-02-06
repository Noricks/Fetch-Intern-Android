package com.example.fetchintern

import org.json.JSONArray

class Util {
    companion object {
        /**
         * Parses a JSONArray into an ArrayList of ListItems.
         * @param jsonArray The JSONArray to parse.
         * @return An ArrayList of ListItems.
         */
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

        /**
         * Processes an ArrayList of ListItems.
         * @param listItemArray The ArrayList of ListItems to process.
         * @return A processed ArrayList of ListItems.
         */
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