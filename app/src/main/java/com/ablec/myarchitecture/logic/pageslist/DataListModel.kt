package com.ablec.myarchitecture.logic.pageslist

import android.app.Application
import androidx.lifecycle.*
import com.ablec.lib.ext.showToast
import com.ablec.module_base.http.PageData
import com.ablec.module_base.util.convert
import com.ablec.module_base.util.toJson
import com.ablec.myarchitecture.data.server.api.TestApiService
import com.ablec.myarchitecture.data.server.dto.GetListReq
import com.ablec.myarchitecture.data.server.dto.ListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataListModel @Inject constructor(
    private val apiService: TestApiService,
    val app: Application
) : AndroidViewModel(app) {

    private val _list = MediatorLiveData<PageData<ListItem>?>()

    val listLive: LiveData<PageData<ListItem>?> = _list;

    init {
//        getListTest()
    }

    fun getListTest() {
        viewModelScope.launch {
            val resp = apiService.getListData(GetListReq(1, 10).convert())
            if (resp.isSuccess) {
                _list.value = resp.data
            }
        }
    }

}