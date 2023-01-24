/*
 * This file is a part of the Configured library
 * Copyright (C) 2023 Deftu (https://deftu.xyz)
 *
 * DO NOT remove or alter copyright notices, or remove this file header.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package xyz.deftu.configured.options

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import xyz.deftu.configured.Config
import java.awt.Color
import java.io.File

class OptionSerializer {
    private lateinit var config: Config
    private lateinit var collector: OptionCollector
    private lateinit var file: File
    private val gson = GsonBuilder()
        .registerTypeAdapter(Color::class.java, ColorTypeAdapter())
        .registerTypeAdapter(File::class.java, FileTypeAdapter())
        .setPrettyPrinting()
        .create()
    val initialized: Boolean
        get() = this::config.isInitialized && this::collector.isInitialized
    var dirty = false
    //#if MC<=11701
    private val jsonParser = JsonParser()
    //#endif

    fun initialize(config: Config, collector: OptionCollector) {
        this.config = config
        this.collector = collector
        val directory = config.directory
        if (!directory.exists() && !directory.mkdirs()) throw IllegalStateException("Could not create directory ${directory.absolutePath}")
        this.file = File(directory, "config.json")
        if (!file.exists()) {
            file.createNewFile()
            file.writeText(gson.toJson(JsonObject()))
        }
    }

    fun markDirty() {
        dirty = true
    }

    fun serialize() {
        if (!initialized) throw IllegalStateException("OptionSerializer not initialized!")
        if (!dirty) return
        val json = parseJson(file.readText())?.asJsonObject ?: JsonObject()
        for (option in collector.get()) {
            if (!option.type.serializable) continue
            val name = option.name.lowercase().replace(" ", "_")
            val category = option.category.lowercase().replace(" ", "_")
            val categoryJson = json[category]?.asJsonObject ?: JsonObject()
            val value = parseJson(gson.toJson(option.invoke()))
            categoryJson.add(name, value)
            json.add(category, categoryJson)
        }
        file.writeText(gson.toJson(json))
        dirty = false
    }

    fun deserialize() {
        if (!initialized) throw IllegalStateException("OptionSerializer not initialized!")
        val json = parseJson(file.readText())?.asJsonObject ?: JsonObject()
        for (option in collector.get()) {
            if (!option.type.serializable) continue
            val name = option.name.lowercase().replace(" ", "_")
            val category = option.category.lowercase().replace(" ", "_")
            val categoryJson = json[category]?.asJsonObject ?: JsonObject()
            val jsonValue = categoryJson[name] ?: continue
            val value = gson.fromJson(jsonValue, option.type.type)
            option.set(value)
        }
    }

    fun parseJson(str: String) =
        //#if MC<=11701
        jsonParser.parse(str)
        //#else
        //$$ JsonParser.parseString(str)
        //#endif
}

private class ColorTypeAdapter : TypeAdapter<Color>() {
    override fun write(out: JsonWriter, value: Color) {
        out.beginObject()
        out.name("red").value(value.red)
        out.name("green").value(value.green)
        out.name("blue").value(value.blue)
        out.name("alpha").value(value.alpha)
        out.endObject()
    }

    override fun read(`in`: JsonReader): Color {
        `in`.beginObject()
        var red = 0
        var green = 0
        var blue = 0
        var alpha = 0
        while (`in`.hasNext()) {
            val next = `in`.nextName()
            when (next) {
                "red" -> red = `in`.nextInt()
                "green" -> green = `in`.nextInt()
                "blue" -> blue = `in`.nextInt()
                "alpha" -> alpha = `in`.nextInt()
            }
        }
        `in`.endObject()
        return Color(red, green, blue, alpha)
    }
}

private class FileTypeAdapter : TypeAdapter<File>() {
    override fun write(out: JsonWriter, value: File) {
        out.value(value.absolutePath)
    }

    override fun read(`in`: JsonReader) =
        File(`in`.nextString())
}
