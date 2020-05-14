package com.example.savegram.Fragments.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.savegram.R;

import java.util.List;

public class ClipAdapter extends BaseAdapter {
    private List<Clip> cliplist;
    private Context ctx;

    public ClipAdapter(Context ctx,List<Clip> cliplist) {
        this.cliplist = cliplist;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return cliplist.size();
    }

    @Override
    public Object getItem(int i) {
        return cliplist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null){
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            v = inflater.inflate(R.layout.layout_clip,null);
        }
        /* Get Views from inflated view */
        TextView clip = v.findViewById(R.id.clip_txt);
        TextView time = v.findViewById(R.id.clip_time);

        /* Set values to Views */
        clip.setText(cliplist.get(i).getClip());
        time.setText(cliplist.get(i).getTimeStamp());
        return v;
    }
}
