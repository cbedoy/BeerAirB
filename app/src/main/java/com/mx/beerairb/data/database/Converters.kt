package com.mx.beerairb.data.database

import androidx.room.TypeConverter
import com.mx.beerairb.data.model.BeerAmenity
import org.json.JSONArray
import org.json.JSONObject

class Converters {
    @TypeConverter
    fun fromAmenities(value: List<BeerAmenity>): String {
        val arr = JSONArray()
        value.forEach { a ->
            val obj = JSONObject()
            obj.put("iconDescription", a.iconDescription)
            obj.put("label", a.label)
            obj.put("badgeColorIndex", a.badgeColorIndex)
            arr.put(obj)
        }
        return arr.toString()
    }

    @TypeConverter
    fun toAmenities(value: String): List<BeerAmenity> {
        val arr = JSONArray(value)
        val list = mutableListOf<BeerAmenity>()
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            list.add(
                BeerAmenity(
                    iconDescription = obj.getString("iconDescription"),
                    label = obj.getString("label"),
                    badgeColorIndex = obj.getInt("badgeColorIndex")
                )
            )
        }
        return list
    }
}
