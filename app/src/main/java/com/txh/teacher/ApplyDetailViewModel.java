package com.txh.teacher;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.txh.json.apply.ApplyDetail;

import java.util.List;

public class ApplyDetailViewModel extends ViewModel {
    //存储耗材申购记录的列表
    private List<MutableLiveData<ApplyDetail>> applyDetailList;

    public List<MutableLiveData<ApplyDetail>> getApplyDetailList() {
        return applyDetailList;
    }

    public void setApplyDetailList(List<MutableLiveData<ApplyDetail>> applyDetailList) {
        this.applyDetailList = applyDetailList;
    }
}
