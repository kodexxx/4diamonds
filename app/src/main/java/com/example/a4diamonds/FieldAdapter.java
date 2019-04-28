package com.example.a4diamonds;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.a4diamonds.engine.Engine;

import java.util.ArrayList;
import java.util.List;

public class FieldAdapter extends BaseAdapter {
    private Context mContext;

    private int counter = -1;
    private List<ArrayList<Integer>> field;

    public FieldAdapter(Context ctx, List<ArrayList<Integer>> field) {
        this.mContext = ctx;
        this.field = field;
    }

    @Override
    public int getCount() {
        return field.size() * field.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void updateResults(List<ArrayList<Integer>> results) {
        this.field = results;
        notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(int position) {
        int x = position / 10;
        int y = position % 10;
        return field.get(x).get(y) == Engine.FREE_DIAMOND;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ImageView iv = new ImageView(this.mContext);
        int x = position / 10;
        int y = position % 10;

        switch (field.get(x).get(y)) {
            case Engine.FREE_DIAMOND:
                iv.setImageResource(R.drawable.hole);
                break;
            case Engine.BLUE_DIAMOND:
                iv.setEnabled(false);
                iv.setImageResource(R.drawable.blue_diamond);
                break;
            case Engine.RED_DIAMOND:
                iv.setEnabled(false);
                iv.setImageResource(R.drawable.red_diamond);
                break;
        }

        iv.setSoundEffectsEnabled(false);
        iv.setPadding(10, 20, 10, 20);

        return iv;
    }
}
