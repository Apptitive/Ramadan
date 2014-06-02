package com.appsomehow.ramadan.table.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayhan on 6/1/2014.
 */
public class InfoTypes {
    public final String name;
    public final List<Info> list;

    InfoTypes(String name) {
        this.name = name;
        list = new ArrayList<Info>();
    }

    public int size() {
        return list.size();
    }

    public Info get(int i) {
        return list.get(i);
    }
}
