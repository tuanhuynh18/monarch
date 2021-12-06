//package com.example.usr_page_test.user_page;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.TypedValue;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.Switch;
//import android.widget.TextView;
//
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.NavController;
//import androidx.navigation.Navigation;
//import androidx.navigation.fragment.NavHostFragment;
//
//import com.baoyz.swipemenulistview.SwipeMenu;
//import com.baoyz.swipemenulistview.SwipeMenuCreator;
//import com.baoyz.swipemenulistview.SwipeMenuItem;
//import com.baoyz.swipemenulistview.SwipeMenuListView;
//import com.baoyz.swipemenulistview.SwipeMenuView;
//import com.example.usr_page_test.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QueryDocumentSnapshot;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.EventListener;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//public class BudgetListFragment extends Fragment implements EnterDialogFragment.InputListener {
//    private Switch btn;
//    private TextView te;
//    private Button addBtn;
////    private  OnOpenButtonClick onOpenButtonClick;
////    private OnCloseButtonClick onCloseButtonClick;
//    private int fragmentId;
//    private View listId;
//    private int addButtonId;
//    public SwipeMenuListView swipeMenuView;
//    public List<ActivityItem> activityItemList;
//    public UserEnterAdapter userEnterAdapter;
//    public int user_change_pos;
//    public String[] user_enter;
//    public Fragment pass_to_listener;
//    public Set<String> got;
//    private FirebaseFirestore db;
//
////    public void setComponents(int fragmentId, int listId) {
////        this.fragmentId = fragmentId;
////        this.listId = listId;
////        //this.addButtonId = addButtonId;
////    }
//
//    @Override
//    public void onInputComplete(String item, String cost) {
//        user_enter = new String[]{item, cost};
//        String tmpId = activityItemList.get(user_change_pos).getActivityName();
//        edit_item(tmpId, user_enter[0], user_enter[1]);
//        activityItemList.get(user_change_pos).setActivityName(user_enter[0]);
//        activityItemList.get(user_change_pos).setCost(user_enter[1]);
//        //list.set(user_change_pos,user_enter[0]);
//        userEnterAdapter.notifyDataSetChanged();
//        //TODO: send this to backend
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.my_fragment_layout, container, false);
//        swipeMenuView = view.findViewById(R.id.user_enter_list);
//        create_bean();
//        return  view;
//    }
//
//    public void create_bean(){
//        pass_to_listener = this;
//        activityItemList = new ArrayList<>();
//        init_list();
//        userEnterAdapter = new UserEnterAdapter();
//        swipeMenuView.setAdapter(userEnterAdapter);
//    }
//
//    @Override
//    public void onStart() {
//
//        super.onStart();
//        get_list();
//
//    }
//
//    private int dp2px(int dp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
//                getResources().getDisplayMetrics());
//    }
//    public void get_list() {
//        //activityItemList = new ArrayList<>();
//        got = new HashSet<>();
//        db = FirebaseFirestore.getInstance();
//        db.collection("OtherActivity")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                if (!got.contains(document.get("ID"))) {
//                                    ActivityItem ai = new ActivityItem((String)document.get("ID"), (String)document.get("cost"), (String)document.get("payer"));
//                                    activityItemList.add(ai);
//                                    got.add((String) document.get("ID"));
//                                }
//                                userEnterAdapter.notifyDataSetChanged();
//                                Log.d("fs", document.getId() + " => " + document.getData());
//                            }
//                        } else {
//                            Log.d("fs", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//
//    }
//
//    private void init_list() {
//        SwipeMenuCreator creator = new SwipeMenuCreator() {
//
//            @Override
//            public void create(SwipeMenu menu) {
//                // create "open" item
//                SwipeMenuItem openItem = new SwipeMenuItem(
//                        getContext());
//                // set item background
//                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                        0xCE)));
//                openItem.setWidth(dp2px(90));
//                openItem.setIcon(R.drawable.baseline_edit_note_red_100_24dp);
//
//                // set item width
////                openItem.setWidth(dp2px(90));
////                // set item title
////                openItem.setTitle("Edit");
////                // set item title fontsize
////                openItem.setTitleSize(15);
////                // set item title font color
////                openItem.setTitleColor(Color.WHITE);
//                // add to menu
//                menu.addMenuItem(openItem);
//
//                // create "delete" item
//                SwipeMenuItem deleteItem = new SwipeMenuItem(
//                        getContext());
//                // set item background
//                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
//                        0x3F, 0x25)));
//                // set item width
//                deleteItem.setWidth(dp2px(90));
//                // set a icon
//                deleteItem.setIcon(R.drawable.baseline_delete_red_100_18dp);
//                // add to menu
//                menu.addMenuItem(deleteItem);
//            }
//        };
//        swipeMenuView.setMenuCreator(creator);
//        swipeMenuView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
//                switch (index) {
//                    case 0:
//                        // open a fragment for collecting data
//                        // TODO : build the diaglog for editing
//                        Context context = getContext();
//                        EnterDialogFragment dialog = new EnterDialogFragment();
//                        dialog.setContext((EnterDialogFragment.InputListener) pass_to_listener);
//                        //what if the budgetfragment has not been sent before dialog onclick created
//                        dialog.show(getFragmentManager(), "enter item");
//                        user_change_pos = position;
//                        break;
//                    case 1:
//                        // delete
//                        //volleyUtils.post(list.get(position), String.class, res);
//                        remove_item(position);
//                        break;
//                }
//                // false : close the menu; true : not close the menu
//                return false;
//            }
//        });
//    }
//    public void edit_item(String id, String newId, String newCost) {
//        Query query = db.collection("OtherActivity").whereEqualTo("ID", id);
//        query.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("fs delete", document.getId() + " => " + document.getData());
//                                String idEdited = document.getId();
//                                db.collection("OtherActivity").document(idEdited)
//                                        .update("ID", newId,"cost", newCost)
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Log.d("fs edit", "DocumentSnapshot successfully edited!");
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.w("fs edit", "Error editing document", e);
//                                            }
//                                        });
//                            }
//                        } else {
//                            Log.d("fs edit", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//    }
//    public void remove_item(int position) {
//        Query query = db.collection("OtherActivity").whereEqualTo("ID", activityItemList.get(position).getActivityName());
//        query.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d("fs delete", document.getId() + " => " + document.getData());
//                                String idDelete = document.getId();
//                                db.collection("OtherActivity").document(idDelete)
//                                        .delete()
//                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                            @Override
//                                            public void onSuccess(Void aVoid) {
//                                                Log.d("fs delete", "DocumentSnapshot successfully deleted!");
//                                            }
//                                        })
//                                        .addOnFailureListener(new OnFailureListener() {
//                                            @Override
//                                            public void onFailure(@NonNull Exception e) {
//                                                Log.w("fs delete", "Error deleting document", e);
//                                            }
//                                        });
//                            }
//                        } else {
//                            Log.d("fs delete", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//        activityItemList.remove(position);
//        userEnterAdapter.notifyDataSetChanged();
//    }
//    public class UserEnterAdapter extends BaseSwipListAdapter  {
//
//        @Override
//        public int getCount() {
//            return activityItemList.size();
//        }
//
//        @Override
//        public ActivityItem getItem(int position) {
//            return activityItemList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                convertView = View.inflate(getContext(),
//                        R.layout.activity_desc_item, null);
//                new UserEnterAdapter.ViewHolder(convertView);
//            }
//            UserEnterAdapter.ViewHolder holder = (UserEnterAdapter.ViewHolder) convertView.getTag();
//            ActivityItem item = getItem(position);
//            //holder.iv_icon.setImageResource(R.drawable.ic_launcher);
//            holder.tv_name.setText(item.getActivityName());
//            holder.itemCost.setText(item.getCost());
//            holder.itemPayer.setText(item.getPayer());
////            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Toast.makeText(MyActivity.this, "点击了图标", Toast.LENGTH_SHORT).show();
////                }
////            });
//            holder.tv_name.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Toast.makeText(MyActivity.this, "点击了文字", Toast.LENGTH_SHORT).show();
//                }
//            });
//            return convertView;
//        }
//
//        class ViewHolder {
//            TextView itemCost;
//            TextView tv_name;
//            TextView itemPayer;
//
//            public ViewHolder(View view) {
//                //iv_icon = (TextView) view.findViewById(R.id.activi);
//                tv_name = (TextView) view.findViewById(R.id.item_id);
//                itemCost = view.findViewById(R.id.item_cost);
//                itemPayer = view.findViewById(R.id.item_payer);
//
//                view.setTag(this);
//            }
//        }
//
//        @Override
//        public boolean getSwipEnableByPosition(int position) {        //设置条目是否可以滑动
////            if (position % 2 == 0) {
////                return false;
////            }
//            return true;
//        }
//
//    }
//
//
//}
