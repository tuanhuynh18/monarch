package com.example.usr_page_test.user_page;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usr_page_test.R;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.ArrayList;

public class segmentedlist {
//    private void initView() {
//        mRecyclerView = (RecyclerView) findViewById(R.id.budget_list_view);
//        mAdapter = new HotelEntityAdapter(this);
//        //GridLayoutManager manager = new GridLayoutManager(this,4);
//        //设置header
//        //manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,manager));
//        LinearLayoutManager manager = new LinearLayoutManager(this);
//        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setAdapter(mAdapter);
//        //String st = "{\"allTagsList\":[{\"tagsName\":\"hotel\",\"activityItemList\":[{\"activityName\":\"modera\"},{\"activityName\":\"here\"}]},{\"tagsName\":\"food\",\"activityItemList\":[{\"activityName\":\"taco\"},{\"activityName\":\"McDonald\"}]}]}";
//        String st = "{\"allTagsList\":[{\"tagsName\":\"hotel\",\"activityItemsList\":[{\"activityName\":\"modera\"},{\"activityName\":\"here\"}]},{\"tagsName\":\"food\",\"activityItemsList\":[{\"activityName\":\"taco\"},{\"activityName\":\"McDonald\"}]}]}";
//        allActivity entity = JsonUtils.analysisJsonFile(this, st);
//        mAdapter.setData(entity.allTagsList);
//    }


//    public class HotelEntityAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DescHolder, RecyclerView.ViewHolder> {
//
//        public ArrayList<allActivity.TagsEntity> allTagList;
//        private Context mContext;
//        private LayoutInflater mInflater;
//        private SparseBooleanArray mBooleanMap;//记录下哪个section是被打开的
//
//        public HotelEntityAdapter(Context context) {
//            mContext = context;
//            mInflater = LayoutInflater.from(context);
//            mBooleanMap = new SparseBooleanArray();
//        }
//
//        public void setData(ArrayList<allActivity.TagsEntity> allTagList) {
//            this.allTagList = allTagList;
//            notifyDataSetChanged();
//        }
//
//        @Override
//        protected int getSectionCount() {
//            return HotelUtils.isEmpty(allTagList) ? 0 : allTagList.size();
//        }
//
//        @Override
//        protected int getItemCountForSection(int section) {
//            int count = allTagList.get(section).activityItemsList.size();
////            if (count >= 8 && !mBooleanMap.get(section)) {
////                count = 8;
////            }
//            if (mBooleanMap.get(section)) {
//                count = 0;
//            }
//
//            return HotelUtils.isEmpty(allTagList.get(section).activityItemsList) ? 0 : count;
//        }
//
//        //是否有footer布局
//        @Override
//        protected boolean hasFooterInSection(int section) {
//            return false;
//        }
//
//        @Override
//        protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
//            return new HeaderHolder(mInflater.inflate(R.layout.activity_title_item, parent, false));
//        }
//
//        @Override
//        protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
//            return null;
//        }
//
//        @Override
//        protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
//            return new DescHolder(mInflater.inflate(R.layout.activity_desc_item, parent, false));
//        }
//
//
//        @Override
//        protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
//            holder.openView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean isOpen = mBooleanMap.get(section);
//                    String text = isOpen ? "展开" : "关闭";
//                    mBooleanMap.put(section, !isOpen);
//                    holder.openView.setText(text);
//                    notifyDataSetChanged();
//
//                }
//            });
//
//            holder.titleView.setText(allTagList.get(section).tagsName);
//            holder.openView.setText(mBooleanMap.get(section) ? "关闭" : "展开");
//
//        }
//
//        @Override
//        protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {
//
//        }
//
//        @Override
//        protected void onBindItemViewHolder(DescHolder holder, int section, int position) {
//            holder.activity.setText(allTagList.get(section).activityItemsList.get(position).getActivityName());
//
//        }
//
//
//    }
//
//    public class HeaderHolder extends RecyclerView.ViewHolder {
//        TextView openView;
//        TextView titleView;
//
//        public HeaderHolder(View itemView) {
//            super(itemView);
//            initView();
//        }
//
//        private void initView() {
//            titleView = (TextView) itemView.findViewById(R.id.tv_title);
//            openView = (TextView) itemView.findViewById(R.id.tv_open);
//        }
//
//    }
//
//    public class DescHolder extends RecyclerView.ViewHolder {
//        //activityItem
//        TextView activity;
//        TextView cost;
//
//        public DescHolder(View itemView) {
//            super(itemView);
//            initView();
//        }
//
//        private void initView() {
//            activity = (TextView) itemView.findViewById(R.id.item_id);
//        }
//    }
//

}
