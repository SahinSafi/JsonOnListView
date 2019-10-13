package com.cyberwith.jsononlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<StudentModel> value;

    public CustomAdapter(Context context, int layout, List<StudentModel> value) {
        this.context = context;
        this.layout = layout;
        this.value = value;
    }

    @Override
    public int getCount() {
        return value.size();
    }

    @Override
    public Object getItem(int position) {
        return value.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, parent, false);
        }

        TextView nameText = convertView.findViewById(R.id.nameTextID);
        TextView departmentText = convertView.findViewById(R.id.departmentTextID);
        TextView countryText = convertView.findViewById(R.id.countryTextID);

        nameText.setText(value.get(position).getName());
        departmentText.setText(value.get(position).getDepartment());
        countryText.setText(value.get(position).getCountry());


        return convertView;
    }
}
