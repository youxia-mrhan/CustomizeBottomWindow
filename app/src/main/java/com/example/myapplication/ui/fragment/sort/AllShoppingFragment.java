package com.example.myapplication.ui.fragment.sort;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.bean.SortDownBean;
import com.example.myapplication.databinding.FragmentAllShoppingBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 全部商区
 */
public class AllShoppingFragment extends Fragment {

    private FragmentAllShoppingBinding binding;

    private List<Object> selectedItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAllShoppingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        logSelected_0(selectedItems);
    }

    public void post(List<Object> params) {
        selectedItems = params;
        if (binding != null) {
            logSelected_0(selectedItems);
        }
    }

    private void logSelected_0(List<Object> selectedItems) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO) {
                SortDownBean.AllShoppingListDTO s = (SortDownBean.AllShoppingListDTO) selectedItems.get(i);
                str.append("---\n" + s.getAllShoppingId() + "---" + s.getAllShoppingTitle() + "---");
            } else if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO.AllShoppingChildDTO) {
                SortDownBean.AllShoppingListDTO.AllShoppingChildDTO s = (SortDownBean.AllShoppingListDTO.AllShoppingChildDTO) selectedItems.get(i);
                str.append("---\n" + s.getAllShoppingChildId() + "---" + s.getAllShoppingChildTitle() + "---");
            } else if (selectedItems.get(i) instanceof SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO) {
                SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO s = (SortDownBean.AllShoppingListDTO.AllShoppingChildDTO.AllShoppingChildDetailDTO) selectedItems.get(i);
                str.append("---\n" + s.getLocationId() + "---" + s.getLocationDistance() + "---");
            }
        }

        // Log.d("TAG", str.toString());
        binding.allShoppingTxt.setText(str.toString());
    }

}