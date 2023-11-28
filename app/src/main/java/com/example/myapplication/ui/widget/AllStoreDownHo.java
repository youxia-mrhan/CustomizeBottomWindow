package com.example.myapplication.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.base.DownBaseView;
import com.example.myapplication.bean.SortWindowBean;
import com.example.myapplication.util.DisplayUtils;
import com.example.myapplication.util.MAnimatorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部商区 下拉框内容
 */
public class AllStoreDownHo extends DownBaseView {

    private List<Object> selectedItems = new ArrayList<>();

    public AllStoreDownHo(@NonNull Context context) {
        super(context);
    }

    // 数据
    private List<SortWindowBean.AllStoreListDTO> allStoreList;

    private AllStoreDownSelectedCallback storeDownSelectedCallback;

    // 和之前的选择是否相同，如果相同不执行 callback回调
    private boolean isSame = false;

    public List<Object> getSelectedItems() {
        return selectedItems;
    }

    public void setStoreDownSelectedCallback(AllStoreDownSelectedCallback storeDownSelectedCallback) {
        this.storeDownSelectedCallback = storeDownSelectedCallback;
    }

    // 按钮组
    private LinearLayout allStoreSortBtnGroup;

    // 左侧的选项
    private LinearLayout allStoreSelectItemList;

    // 右侧的选项
    private LinearLayout allStoreSelectItemListDetail;

    // 三级联动：全部商区，点击右侧选项
    private void updateAllStoreRightState(int currentIndex, List<SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO> detail) {
        // 右侧分类详情
        TextView btn;
        for (int i = 0; i < allStoreSelectItemListDetail.getChildCount(); i++) {
            btn = (TextView) allStoreSelectItemListDetail.getChildAt(i);
            btn = noSelectedText(btn);
        }
        btn = (TextView) allStoreSelectItemListDetail.getChildAt(currentIndex);
        btn = selectedText(btn);
        selectedRightItem(detail.get(currentIndex));
        logSelected();
    }

    // 二级联动：全部商区，点击左侧选项
    private void updateAllStoreLeftState(int parentCurrentIndex, int currentIndex) {

        // 左侧分类item
        TextView btn;
        for (int i = 0; i < allStoreSelectItemList.getChildCount(); i++) {
            btn = (TextView) allStoreSelectItemList.getChildAt(i);
            btn = noSelectedText(btn);
        }
        btn = (TextView) allStoreSelectItemList.getChildAt(currentIndex);
        btn = selectedIconText(btn);


        // 右侧分类详情
        SortWindowBean.AllStoreListDTO dto = allStoreList.get(parentCurrentIndex);
        List<SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO> detail = dto.getAllStoreChild().get(currentIndex).getAllStoreChildDetail();

        allStoreSelectItemListDetail.removeAllViews();

        for (int i = 0; i < detail.size(); i++) {
            TextView detailText = new TextView(getContext());
            if (i == 0) {
                detailText = selectedText(detailText);
            } else {
                detailText = noSelectedText(detailText);
            }
            detailText.setText(detail.get(i).getLocationDistance());
            allStoreSelectItemListDetail.addView(detailText);

            int finalI = i;
            detailText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllStoreRightState(finalI, detail);
                }
            });
        }

        selectedLeftItem(dto.getAllStoreChild().get(currentIndex));
        logSelected();
    }

    // 一级联动：全部商区，点击按钮选项
    private void updateAllStoreBtnState(int currentIndex) {

        // 按钮组
        TextView btn;
        for (int i = 0; i < allStoreSortBtnGroup.getChildCount(); i++) {
            btn = (TextView) allStoreSortBtnGroup.getChildAt(i);
            btn = noSelectedBtn(btn);
        }
        btn = (TextView) allStoreSortBtnGroup.getChildAt(currentIndex);
        btn = selectedBtn(btn);


        // 左侧分类item
        SortWindowBean.AllStoreListDTO dto = allStoreList.get(currentIndex);
        List<SortWindowBean.AllStoreListDTO.AllStoreChildDTO> storeChildDTO = dto.getAllStoreChild();

        allStoreSelectItemList.removeAllViews();

        int finalLeftI = 0;
        for (int i = 0; i < storeChildDTO.size(); i++) {
            TextView sortDownText = new TextView(getContext());
            if (i == 0) {
                sortDownText = selectedIconText(sortDownText);
            } else {
                sortDownText = noSelectedText(sortDownText);
            }
            sortDownText.setText(storeChildDTO.get(i).getAllStoreChildTitle());
            allStoreSelectItemList.addView(sortDownText);

            finalLeftI = i;
            int finalLeftI1 = finalLeftI;
            sortDownText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllStoreLeftState(currentIndex, finalLeftI1);
                }
            });
        }

        // 右侧分类详情
        List<SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO> detail = dto.getAllStoreChild().get(0).getAllStoreChildDetail();

        allStoreSelectItemListDetail.removeAllViews();

        for (int i = 0; i < detail.size(); i++) {
            TextView detailText = new TextView(getContext());
            if (i == 0) {
                detailText = selectedText(detailText);
            } else {
                detailText = noSelectedText(detailText);
            }
            detailText.setText(detail.get(i).getLocationDistance());
            allStoreSelectItemListDetail.addView(detailText);

            int finalI = i;
            detailText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllStoreRightState(finalI, detail);
                }
            });
        }

        selectedBtnGroup(dto);
        logSelected();
    }

    // 悬浮框内容：全部商区
    public View getAllStoreFilter(SortWindowBean sortWindowBean) {
        View allStoreFilter = View.inflate(getContext(), R.layout.widget_all_store_filter_ho, null);

        allStoreSortBtnGroup = allStoreFilter.findViewById(R.id.sort_btn_group);
        allStoreSelectItemList = allStoreFilter.findViewById(R.id.select_item_list);
        allStoreSelectItemListDetail = allStoreFilter.findViewById(R.id.select_item_list_detail);

        allStoreSelectItemList.setLayoutTransition(MAnimatorUtils.mLayoutTransition(500));
        allStoreSelectItemListDetail.setLayoutTransition(MAnimatorUtils.mLayoutTransition(500));

        allStoreList = sortWindowBean.getAllStoreList();
        allStoreSortBtnGroup.setGravity(Gravity.CENTER_VERTICAL);

        initSelected();

        for (int i = 0; i < allStoreList.size(); i++) {

            // 按钮组
            TextView sortDownBtn = new TextView(getContext());

            if (i == 0) {
                sortDownBtn = selectedBtn(sortDownBtn);
            } else {
                sortDownBtn = noSelectedBtn(sortDownBtn);
            }

            sortDownBtn.setText(allStoreList.get(i).getAllStoreTitle());
            allStoreSortBtnGroup.addView(sortDownBtn);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sortDownBtn.getLayoutParams();
            params.setMarginEnd(DisplayUtils.dp2px(getContext(), 16));

            int finalI = i;
            sortDownBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllStoreBtnState(finalI);
                }
            });
        }

        // 左侧分类item，初始化
        SortWindowBean.AllStoreListDTO dto = allStoreList.get(0);
        List<SortWindowBean.AllStoreListDTO.AllStoreChildDTO> storeChildDTO = dto.getAllStoreChild();

        for (int i = 0; i < storeChildDTO.size(); i++) {
            TextView sortDownText = new TextView(getContext());

            if (i == 0) {
                sortDownText = selectedIconText(sortDownText);
            } else {
                sortDownText = noSelectedText(sortDownText);
            }
            sortDownText.setText(storeChildDTO.get(i).getAllStoreChildTitle());
            allStoreSelectItemList.addView(sortDownText);

            int finalI = i;
            sortDownText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllStoreLeftState(0, finalI);
                }
            });
        }

        // 右侧分类详情，初始化
        List<SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO> detail = dto.getAllStoreChild().get(0).getAllStoreChildDetail();
        for (int i = 0; i < detail.size(); i++) {
            TextView detailText = new TextView(getContext());

            if (i == 0) {
                detailText = selectedText(detailText);
            } else {
                detailText = noSelectedText(detailText);
            }
            detailText.setText(detail.get(i).getLocationDistance());
            allStoreSelectItemListDetail.addView(detailText);

            int finalI = i;
            detailText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllStoreRightState(finalI, detail);
                }
            });
        }

        logSelected();
        return allStoreFilter;
    }

    // 输出选中的日志
    private void logSelected() {
        if (storeDownSelectedCallback != null && !isSame) {
//            Log.d("TAG", str.toString());
            storeDownSelectedCallback.callback(selectedItems);
        }
        isSame = false;
    }

    // 选中右侧
    private void selectedRightItem(SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来加入的三级右侧选项，再加入三级右侧选项

        SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO s = selected;

        boolean isExits = false;
        int previousId = 0;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO) {
                previousId = i;
                SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO s2 = (SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO) selectedItems.get(i);
                if (s.getLocationId() == s2.getLocationId()) {
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
    private void selectedLeftItem(SortWindowBean.AllStoreListDTO.AllStoreChildDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来加入的二级左侧选项，再加入二级左侧选项
        // 清空三级选项，并赋予三级选项 索引0的对象（要先判空）

        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO) {
                selectedItems.remove(i);
            }
        }

        SortWindowBean.AllStoreListDTO.AllStoreChildDTO s = selected;

        boolean isExits = false;
        int previousId = 0;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortWindowBean.AllStoreListDTO.AllStoreChildDTO) {
                previousId = i;
                SortWindowBean.AllStoreListDTO.AllStoreChildDTO s2 = (SortWindowBean.AllStoreListDTO.AllStoreChildDTO) selectedItems.get(i);
                if (s.getAllStoreChildId() == s2.getAllStoreChildId()) {
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

        SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO rightSelected;
        if (s.getAllStoreChildDetail().size() > 0) {
            // 选中的右侧选项
            rightSelected = s.getAllStoreChildDetail().get(0);
            selectedItems.add(rightSelected);
        }
    }

    // 选中按钮
    private void selectedBtnGroup(SortWindowBean.AllStoreListDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来所有选项
        // 加入一级选项，并赋予二三级选项 索引0的对象（要先判空）

        SortWindowBean.AllStoreListDTO s = selected;
        boolean isExits = false;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortWindowBean.AllStoreListDTO) {
                SortWindowBean.AllStoreListDTO s2 = (SortWindowBean.AllStoreListDTO) selectedItems.get(i);
                if (s.getAllStoreId() == s2.getAllStoreId()) {
                    isExits = true;
                }
            }
        }
        if (isExits) {
            isSame = true;
            return;
        }
        selectedItems.clear();

        // 选中的按钮
        selectedItems.add(s);

        SortWindowBean.AllStoreListDTO.AllStoreChildDTO leftSelected;
        if (s.getAllStoreChild().size() > 0) {
            // 选中的左侧选择
            leftSelected = s.getAllStoreChild().get(0);
            selectedItems.add(leftSelected);
        } else {
            return;
        }

        SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO rightSelected;
        if (leftSelected.getAllStoreChildDetail().size() > 0) {
            // 选中的右侧选项
            rightSelected = leftSelected.getAllStoreChildDetail().get(0);
            selectedItems.add(rightSelected);
        }
    }

    // 初始化
    private void initSelected() {
        SortWindowBean.AllStoreListDTO selectedBtnGroup;
        if (allStoreList.size() > 0) {
            // 选中的按钮
            selectedBtnGroup = allStoreList.get(0);
            selectedItems.add(selectedBtnGroup);
        } else {
            return;
        }

        SortWindowBean.AllStoreListDTO.AllStoreChildDTO leftSelected;
        if (selectedBtnGroup.getAllStoreChild().size() > 0) {
            // 选中的左侧选择
            leftSelected = selectedBtnGroup.getAllStoreChild().get(0);
            selectedItems.add(leftSelected);
        } else {
            return;
        }

        SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO rightSelected;
        if (leftSelected.getAllStoreChildDetail().size() > 0) {
            // 选中的右侧选项
            rightSelected = leftSelected.getAllStoreChildDetail().get(0);
            selectedItems.add(rightSelected);
        }
    }

    public static abstract class AllStoreDownSelectedCallback {

        public abstract void callback(List<Object> selectedItems);

    }
}




















