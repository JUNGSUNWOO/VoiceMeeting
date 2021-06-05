package com.id.socketio;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class activity_list extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        private ListView mListView = null;
        private ListViewAdapter mAdapter = null;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sub1);

            final ImageView iv = (ImageView)findViewById(R.id.imageView2);
            mListView = (ListView)findViewById(R.id.listview);
            mAdapter = new ListViewAdapter(this);
            mListView.setAdapter(mAdapter);

            mAdapter.addItem(getResources().getDrawable(R.drawable.man),
                    "500m",
                    "취미는 코딩입니다");
            mAdapter.addItem(getResources().getDrawable(R.drawable.man),
                    "800m",
                    "대학생이에요");
            mAdapter.addItem(getResources().getDrawable(R.drawable.man),
                    "1000m",
                    "저는 목소리가 허스키해요");
            mAdapter.addItem(getResources().getDrawable(R.drawable.man),
                    "1200m",
                    "평범한 회사원입니다");
            mAdapter.addItem(getResources().getDrawable(R.drawable.man),
                    "1700m",
                    "취미가 비슷한 사람이 좋아요");
            mAdapter.addItem(getResources().getDrawable(R.drawable.man),
                    "2000m",
                    "키큰 사람이 좋아요");
            mAdapter.addItem(getResources().getDrawable(R.drawable.man),
                    "2400m",
                    "돈 많은 사람은 별루");
            mAdapter.addItem(getResources().getDrawable(R.drawable.man),
                    "2900m",
                    "무쌍이 좋아여");


            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                    ListData mData = mAdapter.mListData.get(position);
                    Toast.makeText(activity_sub1.this, mData.mTitle, Toast.LENGTH_SHORT).show();
                }
            });
            Animation anim = AnimationUtils
                    .loadAnimation
                            (getApplicationContext(),
                                    R.anim.animation);
            iv.startAnimation(anim);



        }
        private class ViewHolder {
            public ImageView mIcon;

            public TextView mText;

            public TextView mDate;
        }

        private class ListViewAdapter extends BaseAdapter {
            private Context mContext = null;
            private ArrayList<ListData> mListData = new ArrayList<ListData>();

            public ListViewAdapter(Context mContext) {
                super();
                this.mContext = mContext;
            }

            @Override
            public int getCount() {
                return mListData.size();
            }

            @Override
            public Object getItem(int position) {
                return mListData.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            public void addItem(Drawable icon, String mTitle, String mDate){
                ListData addInfo = null;
                addInfo = new ListData();
                addInfo.mIcon = icon;
                addInfo.mTitle = mTitle;
                addInfo.mDate = mDate;

                mListData.add(addInfo);
            }

            public void remove(int position){
                mListData.remove(position);
                dataChange();
            }

            public void sort(){
                Collections.sort(mListData, ListData.ALPHA_COMPARATOR);
                dataChange();
            }

            public void dataChange(){
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                ImageView image = null;
                if (convertView == null) {
                    holder = new ViewHolder();
                    final ImageView iv = (ImageView)findViewById(R.id.imageView2);

                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = inflater.inflate(R.layout.listview_custom, null);
                    holder.mIcon = (ImageView) convertView.findViewById(R.id.mImage);
                    holder.mText = (TextView) convertView.findViewById(R.id.mText);
                    holder.mDate = (TextView) convertView.findViewById(R.id.mDate);


                    convertView.setTag(holder);
                }else{
                    holder = (ViewHolder) convertView.getTag();
                }

                ListData mData = mListData.get(position);

                if (mData.mIcon != null) {
                    holder.mIcon.setVisibility(View.VISIBLE);
                    holder.mIcon.setImageDrawable(mData.mIcon);
                }else{
                    holder.mIcon.setVisibility(View.GONE);
                }

                holder.mText.setText(mData.mTitle);
                holder.mDate.setText(mData.mDate);
//
//            if (position % 2 == 0) {  //Place the condition where you want to change the item color.
//                image.setColorFilter(Color.GRAY);
//            } else {
//                //Setting to default color.
//                image.setColorFilter(Color.WHITE);
//            }


                return convertView;
            }
        }
    }