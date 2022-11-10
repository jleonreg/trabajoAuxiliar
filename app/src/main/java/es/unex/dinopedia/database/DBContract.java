package es.unex.dinopedia.database;

import android.provider.BaseColumns;

public final class DBContract {

    private DBContract() {}

    public static class Dinosaurio implements BaseColumns {
        public static final String TABLE_NAME = "Dinosaurio";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DIET = "diet";
        public static final String COLUMN_NAME_LIVE_IN = "live_in";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_SPECIES = "species";
        public static final String COLUMN_NAME_PERIOD_NAME = "period";
        public static final String COLUMN_NAME_HEIGHT = "height";
    }

}
