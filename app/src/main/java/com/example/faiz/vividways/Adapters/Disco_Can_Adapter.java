package com.example.faiz.vividways.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.faiz.vividways.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AST on 8/28/2017.
 */

public class Disco_Can_Adapter extends BaseAdapter implements SectionIndexer,Filterable{

    private ArrayList<String> arrayList;
    private Context mContext;
    private LayoutInflater layoutInflater;
    public int postition = 222222222;
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private List<String> data= new ArrayList<String>();
    View view;
    TextView textView;
    private boolean flag;
    private ArrayList<Boolean> selectedList;


    public Disco_Can_Adapter(ArrayList<String> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
        this.layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        view = layoutInflater.inflate(R.layout.custom_dis_btn_view, null);

        textView = (TextView) view.findViewById(R.id.cont_name);


//        if(this.postition==position && !flag){
//            view = layoutInflater.inflate(R.layout.custom_dis_btn_view_r,null);
//            textView = (TextView)view.findViewById(R.id.cont_name);
//        }else if (this.postition==position && flag){
//            view = layoutInflater.inflate(R.layout.custom_dis_btn_view,null);
//            textView = (TextView)view.findViewById(R.id.cont_name);
//        }
        if (selectedList != null) {
            if (selectedList.size() > 0) {
                if (selectedList.get(position)) {
                    view = layoutInflater.inflate(R.layout.custom_dis_btn_view_r, null);
                    textView = (TextView) view.findViewById(R.id.cont_name);
                }else{
                    view = layoutInflater.inflate(R.layout.custom_dis_btn_view, null);
                    textView = (TextView) view.findViewById(R.id.cont_name);
                }
            }
        }


        textView.setText(arrayList.get(position));






        return view;
    }

    public void setSelect(ArrayList<Boolean> selectedList) {
//        int pos, boolean flag,
//        this.postition = pos;
//        this.flag = flag;
        this.selectedList = selectedList;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (match(String.valueOf(arrayList.get(k).charAt(0)), String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (match(String.valueOf(arrayList.get(j).charAt(0)), String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    private class MangaNameFilter extends Filter{

        final List<String> list = new ArrayList<String>();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();

            if(constraint != null && constraint.toString().length() > 0){

                ArrayList<String> allItems = new ArrayList<String>();
                synchronized (this)
                {
                    allItems.addAll(arrayList);
                }

                list.clear();

                for(int i=0;i<allItems.size();i++){
                    String item = allItems.get(i).toLowerCase();
                    if(item.startsWith(constraint+"")){
                        list.add(allItems.get(i));
                    }
                }

                result.count = list.size();
                result.values = list;

            }
            else{
                synchronized(this){
                    list.clear();
                    for(int i=0;i<arrayList.size();i++){
                        list.add(arrayList.get(i));
                    }

                    result.values = list;
                    result.count = list.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data = (ArrayList<String>)results.values;
            notifyDataSetChanged();
        }
    }

    public boolean match(String value, String keyword) {
        if (value == null || keyword == null)
            return false;
        if (keyword.length() > value.length())
            return false;

        int i = 0, j = 0;
        do {
            if (keyword.charAt(j) == value.charAt(i)) {
                i++;
                j++;
            } else if (j > 0)
                break;
            else
                i++;
        } while (i < value.length() && j < keyword.length());

        return (j == keyword.length())? true : false;
    }

}
