package com.appsomehow.ramadan.table_helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.appsomehow.ramadan.R;
import com.appsomehow.ramadan.model.TimeTable;
import com.appsomehow.ramadan.utilities.Utilities;
import com.inqbarna.tablefixheaders.adapters.BaseTableAdapter;
import java.util.List;

/**
 * Created by rayhan on 6/1/2014.
 */
public class FamilyNexusAdapter extends BaseTableAdapter {


    private Context context;
    private LayoutInflater inflater;
    private List<TimeTable> timeTables;
    private final NexusTypes familys[];
    private final String headers[] = {
            "তারিখ",
            "সেহরী",
            "ইফতার",
            "রমজান",
    };

    private final int[] widths = {
            120,
            100,
            140,
            60,
    };
    private final float density;

    public FamilyNexusAdapter(Context context, List<TimeTable> timeTables) {
        this.context = context;
        this.timeTables = timeTables;
        familys = new NexusTypes[]{
                new NexusTypes(""),
        };

        density = context.getResources().getDisplayMetrics().density;
        for (TimeTable timeTable : timeTables) {
            familys[0].list.add(new Nexus(timeTable.getDateInBangla(), timeTable.getSehriTime(), timeTable.getIfterTime(), timeTable.getRojaCount()));
        }

        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
    }

    public void setUpdatedListItems(List<TimeTable> ttList) {
        familys[0].list.clear();
        for (TimeTable tt : ttList) {
            familys[0].list.add(new Nexus(tt.getDateInBangla(), tt.getSehriTime(), tt.getIfterTime(), tt.getRojaCount()));
        }
        update();
    }

    @Override
    public int getRowCount() {
        return timeTables.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public View getView(int row, int column, View convertView, ViewGroup parent) {
        final View view;
        switch (getItemViewType(row, column)) {
            case 0:
                view = getFirstHeader(row, column, convertView, parent);
                break;
            case 1:
                view = getHeader(row, column, convertView, parent);
                break;
            case 2:
                view = getFirstBody(row, column, convertView, parent);
                break;
            case 3:
                view = getBody(row, column, convertView, parent);
                break;
            case 4:
                view = getFamilyView(row, column, convertView, parent);
                break;
            default:
                throw new RuntimeException("wtf?");
        }
        return view;
    }

    private View getFirstHeader(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_table_header_first, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(Utilities.setBanglaText(headers[0],context));
        return convertView;
    }

    private View getHeader(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_table_header, parent, false);
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(Utilities.setBanglaText(headers[column + 1],context));
        return convertView;
    }

    private View getFirstBody(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_table_first, parent, false);
        }
        convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(Utilities.setBanglaText(getDevice(row).data[column + 1],context));
        return convertView;
    }

    private View getBody(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_table, parent, false);
        }
        convertView.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1 : R.drawable.bg_table_color2);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(Utilities.setBanglaText(getDevice(row).data[column + 1],context));
        return convertView;
    }

    private View getFamilyView(int row, int column, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_table_family, parent, false);
        }
        final String string;
        if (column == -1) {
            string = getFamily(row).name;
        } else {
            string = "";
        }
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(Utilities.setBanglaText(string,context));
        return convertView;
    }

    @Override
    public int getWidth(int column) {
        return Math.round(widths[column + 1] * density);
    }

    @Override
    public int getHeight(int row) {
        final int height;
        if (row == -1) {
            height = 35;
        } else if (isFamily(row)) {
            height = 25;
        } else {
            height = 45;
        }
        return Math.round(height * density);
    }

    @Override
    public int getItemViewType(int row, int column) {
        final int itemViewType;
        if (row == -1 && column == -1) {
            itemViewType = 0;
        } else if (row == -1) {
            itemViewType = 1;
        } else if (isFamily(row)) {
            itemViewType = 4;
        } else if (column == -1) {
            itemViewType = 2;
        } else {
            itemViewType = 3;
        }
        return itemViewType;
    }

    private boolean isFamily(int row) {
        int family = 0;
        while (row > 0) {
            row -= familys[family].size() + 1;
            family++;
        }
        return row == 0;
    }

    private NexusTypes getFamily(int row) {
        int family = 0;
        while (row >= 0) {
            row -= familys[family].size() + 1;
            family++;
        }
        return familys[family - 1];
    }

    private Nexus getDevice(int row) {
        int family = 0;
        while (row >= 0) {
            row -= familys[family].size() + 1;
            family++;
        }
        family--;
        return familys[family].get(row + familys[family].size());
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    public void update() {
        notifyDataSetChanged();
    }
}
