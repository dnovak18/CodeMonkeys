package com.example.pung.codemonkeys;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by pung on 1/30/2018.
 */

public class CustomSwipe extends PagerAdapter  {
    private int[] tutorial_image = {R.drawable.tutorial1,R.drawable.tutorial2,R.drawable.tutorial3,R.drawable.tutorial4,R.drawable.tutorial5};
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipe(Context c) {

        context = c;
    }

    @Override
    public int getCount() {

        return tutorial_image.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = layoutInflater.inflate(R.layout.fragment_welcome,container,false);
        ImageView imageView= (ImageView) itemView.findViewById(R.id.tutorial_view);
        //TextView textView = (TextView) itemView.findViewById(R.id.viewPager);
        imageView.setImageResource(tutorial_image[position]);
        //textView.setText();
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==object);
    }
}
