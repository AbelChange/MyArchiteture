package com.ablec.myarchitecture.logic.pageslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.ablec.module_base.http.handleApiCall
import com.ablec.module_base.util.convert
import com.ablec.myarchitecture.data.server.api.TestApiService
import com.ablec.myarchitecture.data.server.dto.GetListReq
import com.ablec.myarchitecture.data.server.dto.ListItem
import com.ablec.myarchitecture.data.server.dto.PageData
import com.blankj.utilcode.util.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

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
        viewModelScope.launch() {
            handleApiCall { apiService.getListData(GetListReq(1, 10).convert()) }.onSuccess {
                _list.value = it
            }.onFailure {
                LogUtils.e(it)
            }
        }
    }

}