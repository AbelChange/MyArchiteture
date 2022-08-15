package com.ablec.myarchitecture.logic.transformation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.ablec.myarchitecture.data.Person

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/2/2 15:52
 */
 class TransformationViewModel(app: Application,savedStateHandle: SavedStateHandle) : AndroidViewModel(app) {


    private val rawLiveData = MutableLiveData<Person>()

    fun start() {
        //person是data,data 1 equal data 2,如果使用distinctUntilChanged，只会拿到两次变化
        rawLiveData.value = Person("男", 27, false)
        rawLiveData.value = Person("男", 27, false)
        rawLiveData.value = Person("男", 28, false)
    }

    val allPerson:LiveData<Person> get() {
         return rawLiveData
    }
    /**
     * 关注联系使用map
     */
    fun mapAgeOnly(): LiveData<Int> {
        return rawLiveData.map {
            it.age
        }
    }

    /**
     * 关注switch使用switchMap,比map更加灵活
     */
    fun switchMapAgeOnly(): LiveData<Int> {
        return rawLiveData.switchMap {
            MutableLiveData(it.age)
        }
    }
    /**
     *
     */
    fun distinctPerson(): LiveData<Person> {
        return rawLiveData.distinctUntilChanged()
    }

}