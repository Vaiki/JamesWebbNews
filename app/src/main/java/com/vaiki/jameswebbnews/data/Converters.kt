package com.vaiki.jameswebbnews.data

import androidx.room.TypeConverter
import com.vaiki.jameswebbnews.models.Source

class Converters {


    @TypeConverter
    fun fromSource(source: Source):String{
        return source.name
    }
    @TypeConverter
    fun toSource(name:String): Source {
        return Source(name,name)
    }
}