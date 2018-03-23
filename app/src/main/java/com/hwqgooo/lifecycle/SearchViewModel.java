package com.hwqgooo.lifecycle;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

/**
 * Created by hpq on 2018/3/22.
 */

public class SearchViewModel extends ViewModel {
    public MutableLiveData<String> showString;
    public MutableLiveData<String> inputString;

    public SearchViewModel(String show) {
        showString = new MutableLiveData<>();
        inputString = new MutableLiveData<>();
        showString.setValue(show);
    }

    public void setShow(CharSequence show) {
        showString.setValue(show.toString());
    }
}
