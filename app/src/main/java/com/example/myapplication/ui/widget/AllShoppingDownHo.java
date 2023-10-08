package com.example.myapplication.ui.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.myapplication.R;
import com.example.myapplication.base.DownBaseView;
import com.example.myapplication.bean.SortDownBean;
import com.example.myapplication.util.DisplayUtils;
import com.example.myapplication.util.MAnimatorUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部商区 下拉框内容
 */
public class AllShoppingDownHo extends DownBaseView {

    private List<Object> selectedItems = new ArrayList<>();

    private List<Object> originalSelectedItems = new ArrayList<>();

    public AllShoppingDownHo(@NonNull Context context) {
        super(context);
    }

    // 数据
    private List<SortDownBean.AllShoppingListDTO> allShoppingList;

    private AllShoppingDownSelectedCallback shoppingDownSelectedCallback;

    // 和之前的选择是否相同，如果相同不执行 callback回调
    private boolean isSame = false;

    public List<Object> getSelectedItems() {
        return selectedItems;
    }

    public void setShoppingDownSelectedCallback(AllShoppingDownSelectedCallback shoppingDownSelectedCallback) {
        this.shoppingDownSelectedCallback = shoppingDownSelectedCallback;
    }

    // 按钮组
    private LinearLayout allShoppingSortBtnGroup;

    // 左侧的选项
    private LinearLayout allShoppingSelectItemList;

    // 右侧的选项
    private LinearLayout allShoppingSelectItemListDetail;

    // 三级联动：全部商区，点击右侧选项
    private void updateAllShoppingRightState(int currentIndex, List<SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO> detail) {
        // 右侧分类详情
        TextView btn;
        for (int i = 0; i < allShoppingSelectItemListDetail.getChildCount(); i++) {
            btn = (TextView) allShoppingSelectItemListDetail.getChildAt(i);
            btn = noSelectedText(btn);
        }
        btn = (TextView) allShoppingSelectItemListDetail.getChildAt(currentIndex);
        btn = selectedText(btn);
        selectedRightItem(detail.get(currentIndex));
        logSelected();
    }

    // 二级联动：全部商区，点击左侧选项
    private void updateAllShoppingLeftState(int parentCurrentIndex, int currentIndex) {

        // 左侧分类item
        TextView btn;
        for (int i = 0; i < allShoppingSelectItemList.getChildCount(); i++) {
            btn = (TextView) allShoppingSelectItemList.getChildAt(i);
            btn = noSelectedText(btn);
        }
        btn = (TextView) allShoppingSelectItemList.getChildAt(currentIndex);
        btn = selectedIconText(btn);


        // 右侧分类详情
        SortDownBean.AllShoppingListDTO dto = allShoppingList.get(parentCurrentIndex);
        List<SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO> detail = dto.getAllShoppingChild().get(currentIndex).getAllShoppingChildDetail();

        allShoppingSelectItemListDetail.removeAllViews();

        for (int i = 0; i < detail.size(); i++) {
            TextView detailText = new TextView(getContext());
            if (i == 0) {
                detailText = selectedText(detailText);
            } else {
                detailText = noSelectedText(detailText);
            }
            detailText.setText(detail.get(i).getLocationDistance());
            allShoppingSelectItemListDetail.addView(detailText);

            int finalI = i;
            detailText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllShoppingRightState(finalI, detail);
                }
            });
        }

        selectedLeftItem(dto.getAllShoppingChild().get(currentIndex));
        logSelected();
    }

    // 一级联动：全部商区，点击按钮选项
    private void updateAllShoppingBtnState(int currentIndex) {

        // 按钮组
        TextView btn;
        for (int i = 0; i < allShoppingSortBtnGroup.getChildCount(); i++) {
            btn = (TextView) allShoppingSortBtnGroup.getChildAt(i);
            btn = noSelectedBtn(btn);
        }
        btn = (TextView) allShoppingSortBtnGroup.getChildAt(currentIndex);
        btn = selectedBtn(btn);


        // 左侧分类item
        SortDownBean.AllShoppingListDTO dto = allShoppingList.get(currentIndex);
        List<SortDownBean.AllShoppingListDTO.AllShoppingChildDTO> shoppingChildDTO = dto.getAllShoppingChild();

        allShoppingSelectItemList.removeAllViews();

        int finalLeftI = 0;
        for (int i = 0; i < shoppingChildDTO.size(); i++) {
            TextView sortDownText = new TextView(getContext());
            if (i == 0) {
                sortDownText = selectedIconText(sortDownText);
            } else {
                sortDownText = noSelectedText(sortDownText);
            }
            sortDownText.setText(shoppingChildDTO.get(i).getAllShoppingChildTitle());
            allShoppingSelectItemList.addView(sortDownText);

            finalLeftI = i;
            int finalLeftI1 = finalLeftI;
            sortDownText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllShoppingLeftState(currentIndex, finalLeftI1);
                }
            });
        }

        // 右侧分类详情
        List<SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO> detail = dto.getAllShoppingChild().get(0).getAllShoppingChildDetail();

        allShoppingSelectItemListDetail.removeAllViews();

        for (int i = 0; i < detail.size(); i++) {
            TextView detailText = new TextView(getContext());
            if (i == 0) {
                detailText = selectedText(detailText);
            } else {
                detailText = noSelectedText(detailText);
            }
            detailText.setText(detail.get(i).getLocationDistance());
            allShoppingSelectItemListDetail.addView(detailText);

            int finalI = i;
            detailText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllShoppingRightState(finalI, detail);
                }
            });
        }

        selectedBtnGroup(dto);
        logSelected();
    }

    // 悬浮框内容：全部商区
    public View getAllShoppingFilter(SortDownBean sortDownBean) {
        View allShoppingFilter = View.inflate(getContext(), R.layout.widget_all_shopping_filter_ho, null);

        allShoppingSortBtnGroup = allShoppingFilter.findViewById(R.id.sort_btn_group);
        allShoppingSelectItemList = allShoppingFilter.findViewById(R.id.select_item_list);
        allShoppingSelectItemListDetail = allShoppingFilter.findViewById(R.id.select_item_list_detail);

        allShoppingSelectItemList.setLayoutTransition(MAnimatorUtils.mLayoutTransition(500));
        allShoppingSelectItemListDetail.setLayoutTransition(MAnimatorUtils.mLayoutTransition(500));

        allShoppingList = sortDownBean.getAllShoppingList();
        allShoppingSortBtnGroup.setGravity(Gravity.CENTER_VERTICAL);

        initSelected();

        for (int i = 0; i < allShoppingList.size(); i++) {

            // 按钮组
            TextView sortDownBtn = new TextView(getContext());

            if (i == 0) {
                sortDownBtn = selectedBtn(sortDownBtn);
            } else {
                sortDownBtn = noSelectedBtn(sortDownBtn);
            }

            sortDownBtn.setText(allShoppingList.get(i).getAllShoppingTitle());
            allShoppingSortBtnGroup.addView(sortDownBtn);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) sortDownBtn.getLayoutParams();
            params.setMarginEnd(DisplayUtils.dp2px(getContext(), 16));

            int finalI = i;
            sortDownBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllShoppingBtnState(finalI);
                }
            });
        }

        // 左侧分类item，初始化
        SortDownBean.AllShoppingListDTO dto = allShoppingList.get(0);
        List<SortDownBean.AllShoppingListDTO.AllShoppingChildDTO> shoppingChildDTO = dto.getAllShoppingChild();

        for (int i = 0; i < shoppingChildDTO.size(); i++) {
            TextView sortDownText = new TextView(getContext());

            if (i == 0) {
                sortDownText = selectedIconText(sortDownText);
            } else {
                sortDownText = noSelectedText(sortDownText);
            }
            sortDownText.setText(shoppingChildDTO.get(i).getAllShoppingChildTitle());
            allShoppingSelectItemList.addView(sortDownText);

            int finalI = i;
            sortDownText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllShoppingLeftState(0, finalI);
                }
            });
        }

        // 右侧分类详情，初始化
        List<SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO> detail = dto.getAllShoppingChild().get(0).getAllShoppingChildDetail();
        for (int i = 0; i < detail.size(); i++) {
            TextView detailText = new TextView(getContext());

            if (i == 0) {
                detailText = selectedText(detailText);
            } else {
                detailText = noSelectedText(detailText);
            }
            detailText.setText(detail.get(i).getLocationDistance());
            allShoppingSelectItemListDetail.addView(detailText);

            int finalI = i;
            detailText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateAllShoppingRightState(finalI, detail);
                }
            });
        }

        logSelected();
        return allShoppingFilter;
    }

    // 输出选中的日志
    private void logSelected() {
//        StringBuilder str = new StringBuilder();
//        for (int i = 0; i < selectedItems.size(); i++) {
//            if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO) {
//                SortDownBean.AllShoppingListDTO s = (SortDownBean.AllShoppingListDTO) selectedItems.get(i);
//                str.append("---" + s.getAllShoppingId() + "---" + s.getAllShoppingTitle() + "---");
//            } else if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO.AllShoppingChildDTO) {
//                SortDownBean.AllShoppingListDTO.AllShoppingChildDTO s = (SortDownBean.AllShoppingListDTO.AllShoppingChildDTO) selectedItems.get(i);
//                str.append("---" + s.getAllShoppingChildId() + "---" + s.getAllShoppingChildTitle() + "---");
//            } else if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO) {
//                SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO s = (SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO) selectedItems.get(i);
//                str.append("---" + s.getLocationId() + "---" + s.getLocationDistance() + "---");
//            }
//        }

        if (shoppingDownSelectedCallback != null && !isSame) {
//            Log.d("TAG", str.toString());
            shoppingDownSelectedCallback.callback(selectedItems);
        }
        isSame = false;
    }

    // 选中右侧
    private void selectedRightItem(SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来加入的三级右侧选项，再加入三级右侧选项

        SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO s = selected;

        boolean isExits = false;
        int previousId = 0;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO) {
                previousId = i;
                SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO s2 = (SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO) selectedItems.get(i);
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
    private void selectedLeftItem(SortDownBean.AllShoppingListDTO.AllShoppingChildDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来加入的二级左侧选项，再加入二级左侧选项
        // 清空三级选项，并赋予三级选项 索引0的对象（要先判空）

        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO) {
                selectedItems.remove(i);
            }
        }

        SortDownBean.AllShoppingListDTO.AllShoppingChildDTO s = selected;

        boolean isExits = false;
        int previousId = 0;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO.AllShoppingChildDTO) {
                previousId = i;
                SortDownBean.AllShoppingListDTO.AllShoppingChildDTO s2 = (SortDownBean.AllShoppingListDTO.AllShoppingChildDTO) selectedItems.get(i);
                if (s.getAllShoppingChildId() == s2.getAllShoppingChildId()) {
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

        SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO rightSelected;
        if (s.getAllShoppingChildDetail().size() > 0) {
            // 选中的右侧选项
            rightSelected = s.getAllShoppingChildDetail().get(0);
            selectedItems.add(rightSelected);
        }
    }

    // 选中按钮
    private void selectedBtnGroup(SortDownBean.AllShoppingListDTO selected) {
        // 循环查找是否存在，如果存在不执行，不存在 清除原来所有选项
        // 加入一级选项，并赋予二三级选项 索引0的对象（要先判空）

        SortDownBean.AllShoppingListDTO s = selected;
        boolean isExits = false;
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO) {
                SortDownBean.AllShoppingListDTO s2 = (SortDownBean.AllShoppingListDTO) selectedItems.get(i);
                if (s.getAllShoppingId() == s2.getAllShoppingId()) {
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

        SortDownBean.AllShoppingListDTO.AllShoppingChildDTO leftSelected;
        if (s.getAllShoppingChild().size() > 0) {
            // 选中的左侧选择
            leftSelected = s.getAllShoppingChild().get(0);
            selectedItems.add(leftSelected);
        } else {
            return;
        }

        SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO rightSelected;
        if (leftSelected.getAllShoppingChildDetail().size() > 0) {
            // 选中的右侧选项
            rightSelected = leftSelected.getAllShoppingChildDetail().get(0);
            selectedItems.add(rightSelected);
        }
    }

    // 初始化
    private void initSelected() {
        SortDownBean.AllShoppingListDTO selectedBtnGroup;
        if (allShoppingList.size() > 0) {
            // 选中的按钮
            selectedBtnGroup = allShoppingList.get(0);
            selectedItems.add(selectedBtnGroup);
        } else {
            return;
        }

        SortDownBean.AllShoppingListDTO.AllShoppingChildDTO leftSelected;
        if (selectedBtnGroup.getAllShoppingChild().size() > 0) {
            // 选中的左侧选择
            leftSelected = selectedBtnGroup.getAllShoppingChild().get(0);
            selectedItems.add(leftSelected);
        } else {
            return;
        }

        SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO rightSelected;
        if (leftSelected.getAllShoppingChildDetail().size() > 0) {
            // 选中的右侧选项
            rightSelected = leftSelected.getAllShoppingChildDetail().get(0);
            selectedItems.add(rightSelected);
        }
    }

    public static abstract class AllShoppingDownSelectedCallback {

        public abstract void callback(List<Object> selectedItems);

    }
}




















