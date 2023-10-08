package com.example.myapplication.ui.widget;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.base.DownBaseView;
import com.example.myapplication.bean.SortDownBean;
import com.example.myapplication.util.MAnimatorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部分类 下拉框内容
 */
public class AllSortDownHo extends DownBaseView {

    private List<Object> selectedItems = new ArrayList<>();

    public AllSortDownHo(@NonNull Context context) {
        super(context);
    }

    private List<SortDownBean.AllSortListDTO> allSortList;

    private LinearLayout allSortSelectItemList;

    private LinearLayout allSortSelectItemListDetail;

    private AllSortDownSelectedCallback allSortDownSelectedCallback;

    // 和之前的选择是否相同，如果相同不执行 callback回调
    private boolean isSame = false;

    public List<Object> getSelectedItems() {
        return selectedItems;
    }

    public void setAllSortDownSelectedCallback(AllSortDownSelectedCallback allSortDownSelectedCallback) {
        this.allSortDownSelectedCallback = allSortDownSelectedCallback;
    }

    // 二级联动：全部分类，点击右侧选项
    private void updateAllSortRightState(int currentIndex,List<SortDownBean.AllSortListDTO.AllSortChildDTO> detail) {
        // 右侧分类详情
        TextView btn;
        for (int i = 0; i < allSortSelectItemListDetail.getChildCount(); i++) {
            btn = (TextView) allSortSelectItemListDetail.getChildAt(i);
            btn = noSelectedText(btn);
        }
        btn = (TextView) allSortSelectItemListDetail.getChildAt(currentIndex);
        btn = selectedText(btn);

        selectedRightItem(detail.get(currentIndex));
        logSelected();
    }

    // 一级联动：全部分类，点击左侧选项
    private void updateAllSortLeftState(int currentIndex) {

        // 左侧分类item
        TextView btn;
        for (int i = 0; i < allSortSelectItemList.getChildCount(); i++) {
            btn = (TextView) allSortSelectItemList.getChildAt(i);
            btn = noSelectedText(btn);
        }
        btn = (TextView) allSortSelectItemList.getChildAt(currentIndex);
        btn = selectedIconText(btn);


        // 右侧分类详情
        List<SortDownBean.AllSortListDTO.AllSortChildDTO> detail = allSortList.get(currentIndex).getAllSortChild();

        allSortSelectItemListDetail.removeAllViews();

        for (int i = 0; i < detail.size(); i++) {
            TextView detailText = new TextView(getContext());
            if (i == 0) {
                detailText = selectedText(detailText);
            } else {
                detailText = noSelectedText(detailText);
            }
            detailText.setText(detail.get(i).getAllSortChildTitle());
            allSortSelectItemListDetail.addView(detailText);

            int finalI = i;
            detailText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllSortRightState(finalI,detail);
                }
            });
        }

        selectedLeftItem(allSortList.get(currentIndex));
        logSelected();
    }

    // 悬浮框内容：全部分类
    public View getAllSortFilter(SortDownBean sortDownBean) {

        View allSortFilter = View.inflate(getContext(), R.layout.widget_all_sort_filter_ho, null);

        allSortSelectItemList = allSortFilter.findViewById(R.id.select_item_list);
        allSortSelectItemListDetail = allSortFilter.findViewById(R.id.select_item_list_detail);

        allSortSelectItemListDetail.setLayoutTransition(MAnimatorUtils.mLayoutTransition(500));

        allSortList = sortDownBean.getAllSortList();

        initSelected();

        // 左侧分类item，初始化
        for (int i = 0; i < allSortList.size(); i++) {
            TextView sortDownText = new TextView(getContext());

            if (i == 0) {
                sortDownText = selectedIconText(sortDownText);
            } else {
                sortDownText = noSelectedText(sortDownText);
            }
            sortDownText.setText(allSortList.get(i).getAllSortTitle());
            allSortSelectItemList.addView(sortDownText);

            int finalI = i;
            sortDownText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllSortLeftState(finalI);
                }
            });
        }

        // 右侧分类详情，初始化
        List<SortDownBean.AllSortListDTO.AllSortChildDTO> detail = allSortList.get(0).getAllSortChild();
        for (int i = 0; i < detail.size(); i++) {
            TextView detailText = new TextView(getContext());

            if (i == 0) {
                detailText = selectedText(detailText);
            } else {
                detailText = noSelectedText(detailText);
            }
            detailText.setText(detail.get(i).getAllSortChildTitle());
            allSortSelectItemListDetail.addView(detailText);

            int finalI = i;
            detailText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllSortRightState(finalI,detail);
                }
            });
        }

        logSelected();
        return allSortFilter;
    }


    // 输出选中的日志
    private void logSelected() {
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < selectedItems.size(); i++) {
//            if (selectedItems.get(i) instanceof SortDownBean.AllSortListDTO) {
//                SortDownBean.AllSortListDTO s = (SortDownBean.AllSortListDTO) selectedItems.get(i);
//                str.append("---" + s.getAllSortId() + "---" + s.getAllSortTitle() + "---");
//            } else if (selectedItems.get(i) instanceof SortDownBean.AllSortListDTO.AllSortChildDTO ) {
//                SortDownBean.AllSortListDTO.AllSortChildDTO s = (SortDownBean.AllSortListDTO.AllSortChildDTO) selectedItems.get(i);
//                str.append("---" + s.getAllSortChildId() + "---" + s.getAllSortChildTitle() + "---");
//            }
//        }

        if (allSortDownSelectedCallback != null && !isSame) {
//            Log.d("TAG", str.toString());
            allSortDownSelectedCallback.callback(selectedItems);
        }
        isSame = false;
    }

    // 选中右侧
    private void selectedRightItem(SortDownBean.AllSortListDTO.AllSortChildDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来加入的二级右侧选项，再加入二级右侧选项

        SortDownBean.AllSortListDTO.AllSortChildDTO s = selected;

        boolean isExits = false;
        int previousId = 0;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.AllSortListDTO.AllSortChildDTO) {
                previousId = i;
                SortDownBean.AllSortListDTO.AllSortChildDTO s2 = (SortDownBean.AllSortListDTO.AllSortChildDTO) selectedItems.get(i);
                if (s.getAllSortChildId() == s2.getAllSortChildId()) {
                    isExits = true;
                }
            }
        }
        if (isExits) {
            isSame = true;
            return;
        }

        if (selectedItems.size() > 0) {
            selectedItems.remove(previousId);
        }

        // 选中的左侧选择
        selectedItems.add(s);
    }

    // 选中左侧
    private void selectedLeftItem(SortDownBean.AllSortListDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来所有选项
        // 加入一级选项，并赋予二级选项 索引0的对象（要先判空）

        SortDownBean.AllSortListDTO s = selected;
        boolean isExits = false;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.AllSortListDTO) {
                SortDownBean.AllSortListDTO s2 = (SortDownBean.AllSortListDTO) selectedItems.get(i);
                if (s.getAllSortId() == s2.getAllSortId()) {
                    isExits = true;
                }
            }
        }
        if (isExits) {
            isSame = true;
            return;
        }
        selectedItems.clear();

        // 选中的左侧选择
        selectedItems.add(s);

        SortDownBean.AllSortListDTO.AllSortChildDTO rightSelected;
        if (s.getAllSortChild().size() > 0) {
            // 选中的右侧选项
            rightSelected = s.getAllSortChild().get(0);
            selectedItems.add(rightSelected);
        }
    }

    // 初始化
    private void initSelected() {

        SortDownBean.AllSortListDTO leftSelected;
        if (allSortList.size() > 0) {
            // 选中的左侧选择
            leftSelected = allSortList.get(0);
            selectedItems.add(leftSelected);
        } else {
            return;
        }

        SortDownBean.AllSortListDTO.AllSortChildDTO rightSelected;
        if (leftSelected.getAllSortChild().size() > 0) {
            // 选中的右侧选项
            rightSelected = leftSelected.getAllSortChild().get(0);
            selectedItems.add(rightSelected);
        }
    }

    public static abstract class AllSortDownSelectedCallback {

        public abstract void callback(List<Object> selectedItems);

    }
}
