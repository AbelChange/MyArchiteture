package com.ablec.myarchitecture.logic.pageslist

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ablec.module_base.util.convert
import com.ablec.module_base.util.fromJson
import com.ablec.module_base.util.toJson
import com.ablec.myarchitecture.data.Person
import com.ablec.myarchitecture.data.server.api.TestApiService
import com.ablec.myarchitecture.data.server.dto.BaseResp
import com.ablec.myarchitecture.data.server.dto.GetListReq
import com.ablec.myarchitecture.data.server.dto.ListItem
import com.ablec.myarchitecture.data.server.dto.PageData
import com.blankj.utilcode.util.LogUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

@HiltViewModel
class DataListModel @Inject constructor(
    private val apiService: TestApiService,
    val app: Application,
    val dataStore: DataStore<Preferences>,//App kv
    private val savedStateHandle: SavedStateHandle//进程销毁后，viewmodel也不复存在，但是savedStateHandle可以保存一些数据
) : ViewModel() {

    private val _list = MediatorLiveData<PageData<ListItem>?>()

    val listLive: LiveData<PageData<ListItem>?> = _list;

    init {
        getListTest()
        viewModelScope.launch {
            dataStore.edit { preferences ->
                Person("1", 20, true).apply {
                    preferences[stringPreferencesKey("key")] = toJson()
                }
            }
        }
    }

    fun getListTest() {
        viewModelScope.launch {
            dataStore.data
                .map { it[stringPreferencesKey("key")] }
                .collect {
                    Timber.tag("DataListModel").d("localDataStore: " + it)
                    LogUtils.d("DataListModel.localDataStore: $it")
                }
        }


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

    override fun onCleared() {
        super.onCleared()
    }

}