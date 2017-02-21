package bluepanther.jiljungjuk.Contacts_Grid;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bluepanther.jiljungjuk.R;
import bluepanther.jiljungjuk.RowItem2;

/**
 * Created by Hariharsudan on 11/3/2016.
 */

public class CustomAdapter2 extends BaseAdapter {

    Context context;
    List<RowItem2> rowItems;

    public CustomAdapter2(Context context, List<RowItem2> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    private class ViewHolder {
        ImageView profile_pic;
        TextView member_name;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        holder = new ViewHolder();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item2, null);

            holder.member_name = (TextView) convertView.findViewById(R.id.member_name);
            holder.profile_pic = (ImageView) convertView.findViewById(R.id.profile_pic);


            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        RowItem2 row_pos = rowItems.get(position);

        holder.profile_pic.setImageResource(row_pos.getProfile_pic_id());
        holder.member_name.setText(row_pos.getMember_name());

        //convertView.setBackgroundColor(Color.parseColor("#FFF05A4D"));

        return convertView;
    }


}
