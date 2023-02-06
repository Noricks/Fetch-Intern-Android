package com.example.fetchintern

import junit.framework.TestCase
import org.json.JSONArray
import org.junit.Test

class UtilInstrumentedTest {
    @Test
    fun testParseJSONArray() {
        val jsonString = """
            [
                {"id": 755, "listId": 2, "name": ""},
                {"id": 684, "listId": 1, "name": "Item 684"},
                {"id": 736, "listId": 3, "name": null}
            ]
        """.trimIndent()
        val jsonArray = JSONArray(jsonString)
        val listItemArray = Util.parseJSONArray(jsonArray)
        // Check that the array is not empty
        TestCase.assertEquals(3, listItemArray.size)
        // Check the array ids
        TestCase.assertEquals(755, listItemArray[0].id)
        TestCase.assertEquals(684, listItemArray[1].id)
        TestCase.assertEquals(736, listItemArray[2].id)
        // Check the array listIds
        TestCase.assertEquals(2, listItemArray[0].listId)
        TestCase.assertEquals(1, listItemArray[1].listId)
        TestCase.assertEquals(3, listItemArray[2].listId)
        // Check the array names
        TestCase.assertEquals("", listItemArray[0].name)
        TestCase.assertEquals("Item 684", listItemArray[1].name)
        TestCase.assertEquals("null", listItemArray[2].name)
    }
}