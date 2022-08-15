package com.ablec.myarchitecture.logic.pageslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ablec.myarchitecture.data.server.api.TestApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DataListModel @Inject constructor(apiService: TestApiService, app: Application) :
    AndroidViewModel(app) {







}