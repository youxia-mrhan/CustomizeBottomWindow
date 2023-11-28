package com.example.myapplication.ui.fragment.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.bean.SortWindowBean;
import com.example.myapplication.databinding.FragmentAllStoreBinding;
import com.example.myapplication.ui.activity.SortListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部商区
 */
public class AllStoreFragment extends Fragment implements SortListActivity.OptionCallback {

    private FragmentAllStoreBinding binding;

    private List<Object> selectedItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllStoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void updateList(List<Object> params) {
        this.selectedItems = params;
        logSelected_0(params);
    }

    private void logSelected_0(List<Object> selectedItems) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortWindowBean.AllStoreListDTO) {
                SortWindowBean.AllStoreListDTO s = (SortWindowBean.AllStoreListDTO) selectedItems.get(i);
                str.append("---\n" + s.getAllStoreId() + "---" + s.getAllStoreTitle() + "---");
            } else if (selectedItems.get(i) instanceof SortWindowBean.AllStoreListDTO.AllStoreChildDTO) {
                SortWindowBean.AllStoreListDTO.AllStoreChildDTO s = (SortWindowBean.AllStoreListDTO.AllStoreChildDTO) selectedItems.get(i);
                str.append("---\n" + s.getAllStoreChildId() + "---" + s.getAllStoreChildTitle() + "---");
            } else if (selectedItems.get(i) instanceof SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO) {
                SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO s = (SortWindowBean.AllStoreListDTO.AllStoreChildDTO.AllStoreChildDetailDTO) selectedItems.get(i);
                str.append("---\n" + s.getLocationId() + "---" + s.getLocationDistance() + "---");
            }
        }

        // Log.d("TAG", str.toString());
        binding.allShoppingTxt.setText(str.toString());
    }

}