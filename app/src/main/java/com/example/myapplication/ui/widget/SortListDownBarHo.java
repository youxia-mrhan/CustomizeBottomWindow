package com.example.myapplication.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.myapplication.R;
import com.example.myapplication.bean.SortDownBean;
import com.example.myapplication.common.AffiliatedBottomWindow;
import com.example.myapplication.ext.CommonNavigatorEx;
import com.example.myapplication.ext.SimplePagerTitleViewIconHo;
import com.example.myapplication.util.DisplayUtils;
import com.example.myapplication.util.MAnimatorUtils;
import com.example.myapplication.widget.BaseView;
import com.example.myapplication.widget.GradientLinePagerIndicator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;

import java.util.List;

/**
 * 分类列表Bar
 */
public class SortListDownBarHo extends BaseView {

    // tab库对象
    private MagicIndicator sortTabLayout;

    // 标题
    private List<String> tabs;

    // 下拉框的数据
    private SortDownBean sortDownBean;

    // 传给外部回调
    private SortBarItemOnClick sortBarItemOnClick;

    private CommonNavigatorEx commonNavigator;

    // 下拉悬浮框
    private AffiliatedBottomWindow bottomWindow;

    // 下拉框内容：全部商区
    private AllShoppingDownHo allShoppingDownHo;
    private View allShoppingFilter;


    // 下拉框内容：全部分类
    private AllSortDownHo allSortDownHo;
    private View allSortFilter;


    // 下拉框内容：推荐排序
    private RecommendSortDownHo recommendSortDownHo;
    private View recommendSortFilter;

    public SortListDownBarHo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MagicIndicator getSortTabLayout() {
        return sortTabLayout;
    }

    public void setTabs(List<String> tabs) {
        this.tabs = tabs;
        initView();
    }

    public void setSortBarItemOnClick(SortBarItemOnClick sortBarItemOnClick) {
        this.sortBarItemOnClick = sortBarItemOnClick;
    }

    public void setSortDownBean(SortDownBean sortDownBean) {
        this.sortDownBean = sortDownBean;
    }

    @Override
    public void initData() {
        ConstraintLayout filterContainerRoot = (ConstraintLayout) View.inflate(getContext(), R.layout.widget_sort_filter_container_ho, null);
        FrameLayout filterContainer = filterContainerRoot.findViewById(R.id.filter_container);
        filterContainer.setLayoutTransition(MAnimatorUtils.mLayoutTransition(500));

        bottomWindow = AffiliatedBottomWindow.createInstance(
                getContext(),
                filterContainerRoot,
                SortListDownBarHo.this,
                filterContainer,
                true);

        View mask = filterContainerRoot.findViewById(R.id.mask);
        mask.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomWindowDismiss();
            }
        });

        // 悬浮框内容：全部商区
        allShoppingDownHo = new AllShoppingDownHo(getContext());
        allShoppingFilter = allShoppingDownHo.getAllShoppingFilter(sortDownBean);
        allShoppingDownHo.setShoppingDownSelectedCallback(new AllShoppingDownHo.AllShoppingDownSelectedCallback() {
            @Override
            public void callback(List<Object> selectedItems) {
                sortBarItemOnClick.selectedItemCallback(allShoppingDownHo.getSelectedItems(), 0);
            }
        });

        // 悬浮框内容：全部分类
        allSortDownHo = new AllSortDownHo(getContext());
        allSortFilter = allSortDownHo.getAllSortFilter(sortDownBean);
        allSortDownHo.setAllSortDownSelectedCallback(new AllSortDownHo.AllSortDownSelectedCallback() {
            @Override
            public void callback(List<Object> selectedItems) {
                sortBarItemOnClick.selectedItemCallback(allSortDownHo.getSelectedItems(), 1);
            }
        });

        // 悬浮框内容：推荐排序
        recommendSortDownHo = new RecommendSortDownHo(getContext());
        recommendSortFilter = recommendSortDownHo.getRecommendSortFilter(sortDownBean);
        recommendSortDownHo.setRecommendSortSelectedCallback(new RecommendSortDownHo.RecommendSortSelectedCallback() {
            @Override
            public void callback(List<Object> selectedItems) {
                sortBarItemOnClick.selectedItemCallback(recommendSortDownHo.getSelectedItems(), 2);
            }
        });
    }

    private void initView() {
        sortTabLayout = (MagicIndicator) View.inflate(getContext(), R.layout.widget_sort_list_down_bar_ho, null).getRootView();
        addView(sortTabLayout);

        SimplePagerTitleViewIconHo.SimpleParameter.CallbackOnClick barOnClick = new SimplePagerTitleViewIconHo.SimpleParameter.CallbackOnClick() {
            @Override
            public void iconClick(Object tag, boolean clickIcon) {
                if (clickIcon) {
                    AffiliatedBottomWindow.dismiss();
                } else {
                    useFilter(tag);
                }
            }

            @Override
            public void titleClick(Object tag, boolean clickIcon) {
                if (clickIcon) {
                    useFilter(tag);
                }
            }

            private void useFilter(Object tag) {
                switch (tag.toString()) {
                    case "0":
                        bottomWindow.insertViewLayout(allShoppingFilter);
                        break;
                    case "1":
                        bottomWindow.insertViewLayout(allSortFilter);
                        break;
                    case "2":
                        bottomWindow.insertViewLayout(recommendSortFilter);
                        break;
                }
            }
        };
        sortTabLayout.setBackgroundColor(getResources().getColor(R.color.white, null));

        commonNavigator = new CommonNavigatorEx(getContext());
        commonNavigator.setAdjustMode(true); // 是否充满，每个tab平均分布，默认false
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabs.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleViewIconHo.SimpleParameter parameter = new SimplePagerTitleViewIconHo.SimpleParameter(
                        tabs.get(index),
                        index,
                        R.style.Font_303133_15_bold,
                        R.style.Font_909399_15,
                        R.mipmap.drop_down_black,
                        R.mipmap.drop_down_gray,
                        R.mipmap.pack_up_black,
                        barOnClick
                );
                SimplePagerTitleViewIconHo simplePagerTitleView = new SimplePagerTitleViewIconHo(context, parameter);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sortBarItemOnClick != null) {
                            sortBarItemOnClick.barItemOnClick(index);
                        }
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                GradientLinePagerIndicator indicator = new GradientLinePagerIndicator(context,
                        getResources().getColor(R.color.color_E32227, null),
                        getResources().getColor(R.color.white, null));

                // 模式
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(DisplayUtils.dp2px(context, 40));
                indicator.setLineHeight(DisplayUtils.dp2px(context, 4));

                // 圆角
                // indicator.setRoundRadius(DisplayUtils.dp2px(context, 3));

                // 拉伸动画
                // indicator.setStartInterpolator(new AccelerateInterpolator());
                // indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));

                return indicator;
            }

        });
        sortTabLayout.setNavigator(commonNavigator);
    }

    // 获取当前选中的TitleView
    private SimplePagerTitleViewIconHo getCurrentTitleView() {
        return (SimplePagerTitleViewIconHo) commonNavigator.getPagerTitleView(commonNavigator.getCurrentPosition());
    }

    // 隐藏悬浮框 + 获取当前选中的TitleView图标还原
    public void bottomWindowDismiss() {
        AffiliatedBottomWindow.dismiss();
        getCurrentTitleView().reset();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        bottomWindow.clear();
    }

    public static abstract class SortBarItemOnClick {

        // 传回外部，用于在外部 切换ViewPage2 回调
        public abstract void barItemOnClick(int index);

        // 传回外部，用于在外部 获取过滤选项
        public abstract void selectedItemCallback(List<Object> selectedItems, int index);

    }

}
