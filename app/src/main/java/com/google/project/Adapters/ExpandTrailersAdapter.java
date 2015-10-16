package com.google.project.Adapters;

/**
 * Created by OmarAli on 07/10/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.project.R;
import com.google.project.Utilites.Child;
import com.google.project.Utilites.Group;

import java.util.ArrayList;

public class ExpandTrailersAdapter extends BaseExpandableListAdapter {

        private Context context;
        private ArrayList<Group> groups;

        public ExpandTrailersAdapter(Context context, ArrayList<Group> groups) {
            this.context = context;
            this.groups = groups;
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            ArrayList<Child> chList = groups.get(groupPosition).getItems();
            return chList.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {

            Child child = (Child) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) context
                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.trailer_item, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.trailer_name);
            ImageView iv = (ImageView) convertView.findViewById(R.id.flag);

            tv.setText(child.getTrailerName().toString());
            iv.setImageResource(child.getTrailerImage());

            return convertView;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            ArrayList<Child> chList = groups.get(groupPosition).getItems();
            return chList.size();
        }

        @Override
        public Object getGroup(int groupPosition) {
            return groups.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return groups.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            Group group = (Group) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) context
                        .getSystemService(context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.list_group, null);
            }
            TextView tv = (TextView) convertView.findViewById(R.id.group_name);
            tv.setText(group.getName());
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }