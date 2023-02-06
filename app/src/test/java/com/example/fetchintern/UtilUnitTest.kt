package com.example.fetchintern

import junit.framework.TestCase.assertEquals
import org.json.JSONArray
import org.json.JSONException
import org.junit.Before
import org.junit.Test

class UtilUnitTest {

    @Test
    fun testProcessArrayFilter() {
        val listItemArray = ArrayList<ListItem>()
        listItemArray.add(ListItem(755, 2, ""))
        listItemArray.add(ListItem(684, 1, "Item 684"))
        listItemArray.add(ListItem(736, 3, "null"))
        val processedArray = Util.processArray(listItemArray)
        // Check that the array is not empty
        assertEquals(1, processedArray.size)
        // Check the array ids
        assertEquals(684, processedArray[0].id)
        // Check the array listIds
        assertEquals(1, processedArray[0].listId)
        // Check the array names
        assertEquals("Item 684", processedArray[0].name)
    }

    @Test
    fun testProcessArrayOrder() {
        val listItemArray = ArrayList<ListItem>()
        listItemArray.add(ListItem(755, 2, "Item 755"))
        listItemArray.add(ListItem(684, 1, "Item 684"))
        listItemArray.add(ListItem(736, 3, "Item 736"))
        listItemArray.add(ListItem(737, 4, "null"))
        listItemArray.add(ListItem(75, 5, ""))
        val processedArray = Util.processArray(listItemArray)
        // Check that the array is not empty
        assertEquals(3, processedArray.size)
        // Check the array ids
        assertEquals(684, processedArray[0].id)
        assertEquals(755, processedArray[1].id)
        assertEquals(736, processedArray[2].id)
        // Check the array listIds
        assertEquals(1, processedArray[0].listId)
        assertEquals(2, processedArray[1].listId)
        assertEquals(3, processedArray[2].listId)
        // Check the array names
        assertEquals("Item 684", processedArray[0].name)
        assertEquals("Item 755", processedArray[1].name)
        assertEquals("Item 736", processedArray[2].name)
    }
}