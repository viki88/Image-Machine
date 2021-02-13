package com.vikination.imagemachine.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

//    @TypeConverter
//    public static String imageToString(Image image){return image == null ? null : new Gson().toJson(image); }
//
//    @TypeConverter
//    public static Image stringToImage(String valueImage) {return valueImage == null ? null : new Gson().fromJson(valueImage, Image.class); }
}
