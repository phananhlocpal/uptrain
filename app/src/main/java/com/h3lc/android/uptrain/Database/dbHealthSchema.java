package com.h3lc.android.uptrain.Database;

public class dbHealthSchema {
    public static final class UserTable{
        public static final String TABLE_NAME = "user";
        public static final String Id = "id";
        public static final String NAME = "name";
        public static final String AGE = "age";
        public static final String GENDER = "gender";
        public static final String EMAIL = "email";
        public static final String PHONE = "phone";
    }
    public static final class HeightTable{
        public static final String TABLE_NAME = "height";
        public static final String Id = "id";
        public static final String Value = "value";
        public static final String Date = "date";
    }
    public static final class WeightTable{
        public static final String TABLE_NAME = "weight";
        public static final String Id = "id";
        public static final String Value = "value";
        public static final String Date = "date";
    }
    public static final class JourneyTable{
        public static final String TABLE_NAME = "journey";
        public static final String JourneyId = "journeyID";
        public static final String Duration = "duration";
        public static final String Distance = "distance";
        public static final String Date = "date";
        public static final String Name = "name";
        public static final String Rating = "rating";
        public static final String Comment = "comment";
        public static final String Image = "image";
    }
    public static final class LocationTable{
        public static final String TABLE_NAME = "location";
        public static final String LocationID = "id";
        public static final String JourneyID = "journeyID";
        public static final String Altitude = "altitude";
        public static final String Longitude = "longitude";
        public static final String Latitude = "latitude";
    }
}
