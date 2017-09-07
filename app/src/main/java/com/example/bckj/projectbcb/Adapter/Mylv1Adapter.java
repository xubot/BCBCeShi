package com.example.bckj.projectbcb.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bckj.projectbcb.Activity.TaxiImActivity;
import com.example.bckj.projectbcb.Bean.ListViewBean_1;
import com.example.bckj.projectbcb.R;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */

public class Mylv1Adapter extends BaseAdapter {
    private final TaxiImActivity taxiImActivity;
    private final List<ListViewBean_1> lvb_1;

    public Mylv1Adapter(TaxiImActivity taxiImActivity, List<ListViewBean_1> lvb_1) {
        this.taxiImActivity = taxiImActivity;
        this.lvb_1 = lvb_1;
    }

    @Override
    public int getCount() {
        return lvb_1.size();
    }

    @Override
    public Object getItem(int position) {
        return lvb_1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //找到listView条目的布局文件
        convertView= View.inflate(taxiImActivity, R.layout.taxi_itme2,null);
        //从布局里找到对应的控件
        TextView lv_twe_xinxi = (TextView) convertView.findViewById(R.id.lv_twe_xinxi);
        TextView lv_twe_time = (TextView) convertView.findViewById(R.id.lv_twe_time);
        ImageView lv_twe_img = (ImageView) convertView.findViewById(R.id.lv_twe_img);
        //得到对应位置的对象
        ListViewBean_1 listViewBean_1 = lvb_1.get(position);
        //给控件赋值
        lv_twe_xinxi.setText(listViewBean_1.getTitle());
        lv_twe_time.setText(listViewBean_1.getTime());
        lv_twe_img.setImageResource(listViewBean_1.getImg());
        return convertView;
    }
}
