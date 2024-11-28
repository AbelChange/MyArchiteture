package com.ablec.myarchitecture.logic.pageslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ablec.module_base.util.convert
import com.ablec.myarchitecture.data.server.api.TestApiService
import com.ablec.myarchitecture.data.server.dto.BaseResp
import com.ablec.myarchitecture.data.server.dto.GetListReq
import com.ablec.myarchitecture.data.server.dto.ListItem
import com.ablec.myarchitecture.data.server.dto.PageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DataListModel @Inject constructor(
    private val apiService: TestApiService,
    val app: Application
) : AndroidViewModel(app) {

    private val _list = MediatorLiveData<PageData<ListItem>?>()

    val listLive: LiveData<PageData<ListItem>?> = _list;

    init {
        getListTest()
    }

    fun getListTest() {
        _list.value = PageData<ListItem>().apply {
            list = listOf(
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
                ListItem("", "", "", "", "", ""),
            )
        }
//        viewModelScope.launch() {
//            handleApiCall { apiService.getListData(GetListReq(1, 10).convert()) }.onSuccess {
//                _list.value = it
//            }.onFailure {
//                LogUtils.e(it)
//            }
//        }
    }

    // standard repo
    suspend fun getListFlow(): Flow<BaseResp<PageData<ListItem>>> {
        val resp = apiService.getListData(GetListReq(1, 10).convert());
        return flow { emit(resp) }
    }

}