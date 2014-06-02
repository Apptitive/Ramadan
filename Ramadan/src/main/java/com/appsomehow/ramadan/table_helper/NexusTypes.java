package com.appsomehow.ramadan.table_helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rayhan on 6/1/2014.
 */
public class NexusTypes {
    public final String name;
    public final List<Nexus> list;

    NexusTypes(String name) {
        this.name = name;
        list = new ArrayList<Nexus>();
    }

    public int size() {
        return list.size();
    }

    public Nexus get(int i) {
        return list.get(i);
    }
}
