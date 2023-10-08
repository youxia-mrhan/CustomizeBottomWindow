package com.example.myapplication.ui.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.base.DownBaseView;
import com.example.myapplication.bean.SortDownBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 推荐排序 下拉框内容
 */
public class RecommendSortDownHo extends DownBaseView {

    private List<Object> selectedItems = new ArrayList<>();

    public RecommendSortDownHo(@NonNull Context context) {
        super(context);
    }

    private List<SortDownBean.RecommendSortListDTO> recommendSortList;

    private LinearLayout recommendSortSelectItemList;

    private RecommendSortSelectedCallback recommendSortSelectedCallback;

    // 和之前的选择是否相同，如果相同不执行 callback回调
    private boolean isSame = false;

    public List<Object> getSelectedItems() {
        return selectedItems;
    }

    public void setRecommendSortSelectedCallback(RecommendSortSelectedCallback recommendSortSelectedCallback) {
        this.recommendSortSelectedCallback = recommendSortSelectedCallback;
    }

    // 联动：全部分类，点击选项
    private void updateRecommendSortState(int currentIndex) {
        // 右侧分类详情
        TextView btn;
        for (int i = 0; i < recommendSortSelectItemList.getChildCount(); i++) {
            btn = (TextView) recommendSortSelectItemList.getChildAt(i);
            btn = noSelectedText(btn);
        }
        btn = (TextView) recommendSortSelectItemList.getChildAt(currentIndex);
        btn = selectedText(btn);

        selectedItem(recommendSortList.get(currentIndex));
        logSelected();
    }

    // 悬浮框内容：推荐排序
    public View getRecommendSortFilter(SortDownBean sortDownBean) {

        View recommendSortFilter = View.inflate(getContext(), R.layout.widget_recommend_sort_filter_ho, null);

        recommendSortSelectItemList = recommendSortFilter.findViewById(R.id.select_item_list);
        recommendSortList = sortDownBean.getRecommendSortList();

        initSelected();

        // 分类item，初始化
        for (int i = 0; i < recommendSortList.size(); i++) {
            TextView sortDownText = new TextView(getContext());

            if (i == 0) {
                sortDownText = selectedText(sortDownText);
            } else {
                sortDownText = noSelectedText(sortDownText);
            }

            sortDownText.setText(recommendSortList.get(i).getRecommendSortTitle());
            recommendSortSelectItemList.addView(sortDownText);

            int finalI = i;
            sortDownText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateRecommendSortState(finalI);
                }
            });
        }

        logSelected();
        return recommendSortFilter;
    }


    // 输出选中的日志
    private void logSelected() {
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < selectedItems.size(); i++) {
//            if (selectedItems.get(i) instanceof SortDownBean.RecommendSortListDTO) {
//                SortDownBean.RecommendSortListDTO s = (SortDownBean.RecommendSortListDTO) selectedItems.get(i);
//                str.append("---" + s.getRecommendSortId() + "---" + s.getRecommendSortTitle() + "---");
//            }
//        }

        if (recommendSortSelectedCallback != null && !isSame) {
//            Log.d("TAG", str.toString());
            recommendSortSelectedCallback.callback(selectedItems);
        }
        isSame = false;
    }

    // 选中
    private void selectedItem(SortDownBean.RecommendSortListDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来所有选项
        // 加入一级选项

        SortDownBean.RecommendSortListDTO s = selected;

        boolean isExits = false;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.RecommendSortListDTO) {
                SortDownBean.RecommendSortListDTO s2 = (SortDownBean.RecommendSortListDTO) selectedItems.get(i);
                if (s.getRecommendSortId() == s2.getRecommendSortId()) {
                    isExits = true;
                }
            }
        }
        if (isExits) {
            isSame = true;
            return;
        }

        selectedItems.clear();

        // 选中的选择
        selectedItems.add(selected);

    }

    // 初始化
    private void initSelected() {

        SortDownBean.RecommendSortListDTO selected;
        if (recommendSortList.size() > 0) {
            // 选中的选择
            selected = recommendSortList.get(0);
            selectedItems.add(selected);
        }

    }

    public static abstract class RecommendSortSelectedCallback {

        public abstract void callback(List<Object> selectedItems);

    }
}
