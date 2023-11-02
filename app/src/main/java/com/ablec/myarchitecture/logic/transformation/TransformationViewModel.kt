package com.ablec.myarchitecture.logic.transformation

import android.app.Application
import androidx.lifecycle.*
import com.ablec.myarchitecture.data.Person
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/2/2 15:52
 */
class TransformationViewModel(app: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(app) {


    private val rawLiveData = MutableLiveData<Person>()

    fun start() {
        //person是data,data 1 equal data 2,如果使用distinctUntilChanged，只会拿到两次变化
        rawLiveData.value = Person("男", 27, false)
        rawLiveData.value = Person("男", 27, false)
        rawLiveData.value = Person("男", 28, false)
    }

    val allPerson: LiveData<Person>
        get() {
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

    /**
     * 下游停止收集时取消流,2000是为了防止配置修改引起的意外取消
     * 如果downStream  downStream2 都不再收集 才 取消该流
     * 可避免重复创建上游带来的资源浪费
     */
    private val _whileSubscribed: Flow<Int> = flow<Int> {
        emit(1)
        emit(2)
        delay(3000)
        emit(3)
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(2000))
        .distinctUntilChanged { old, new ->
            old - new == 0
        }

    val downStream: Flow<String> = _whileSubscribed.map { it.toString() }

    val downStream2: Flow<String> = _whileSubscribed.map { it.toString() }


}