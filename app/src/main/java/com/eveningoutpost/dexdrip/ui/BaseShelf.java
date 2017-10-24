package com.eveningoutpost.dexdrip.ui;

import android.databinding.ObservableArrayMap;

import com.eveningoutpost.dexdrip.Home;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jamorham on 21/10/2017.
 *
 * Base class for ViewShelf implementations
 * uses observable map
 *
 */

public abstract class BaseShelf implements ViewShelf {

    public ObservableArrayMap<String, Boolean> included = new ObservableArrayMap<>();

    final HashMap<String, String> map = new HashMap<>();
    String PREFS_PREFIX = null;


    // live read an item
    public boolean get(String id) {
        return included.get(id);
    }

    // live set an item
    public void set(String id, boolean value) {
        included.put(id, value);
    }

    // persistently set an item
    public void pset(String id, boolean value) {
        set(id, value);
        spb(id, value);
    }

    // toggle an item
    public void ptoggle(String id) {
        if (!included.containsKey(id)) {
            throw new NullPointerException("we don't know key " + id);
        }
        pset(id, !included.get(id));
    }

    // load everything from persistent store
    void populate() {
        for (Map.Entry<String, String> es : map.entrySet()) {
            set(es.getKey(), gpb(es.getKey()));
        }
    }

    // implementation for saving to persistent store
    private void spb(String id, boolean value) {
        Home.setPreferencesBoolean(PREFS_PREFIX + id, value);
    }

    // implementation for loading from persistent store
    private boolean gpb(String id) {
        return Home.getPreferencesBooleanDefaultFalse(PREFS_PREFIX + id);
    }


}
