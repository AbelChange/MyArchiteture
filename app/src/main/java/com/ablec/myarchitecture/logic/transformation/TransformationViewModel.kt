package com.ablec.myarchitecture.logic.transformation

import android.app.Application
import androidx.lifecycle.*
import com.ablec.myarchitecture.data.Person
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random

/**
 * @Description:
 * @Author:         haoshuaihui
 * @CreateDate:     2021/2/2 15:52
 */
class TransformationViewModel(app: Application, savedStateHandle: SavedStateHandle) : AndroidViewModel(app) {


    private val rawLiveData = MutableLiveData<Person>()

    private val persons =
        listOf<Person>(Person("男", 27, false), Person("男", 27, false), Person("女", 27, false))

    fun start() {
        //person是data,data 1 equal data 2,如果使用distinctUntilChanged，只会拿到两次变化
        rawLiveData.value = Person("男", 27, false)
        rawLiveData.value = Person("男", 27, false)
        rawLiveData.value = Person("男", 27, false)
        rawLiveData.value = Person("女", 27, false)
    }

    val allPerson: LiveData<Person>
        get() {
            return rawLiveData
        }

    /**
     * 简单的映射转换
     */
    fun mapAgeOnly(): LiveData<Int> {
        return rawLiveData.map {
            it.age
        }
    }

    /**
     * switchMap 用于处理动态改变上游
     */
    fun switchMapAgeOnly(): LiveData<List<Person>> {
        return rawLiveData.switchMap {person->
            val searchResultsLiveData = MutableLiveData<List<Person>>()
            //模拟查询年龄一致的
            val mockSearchResults = persons.filter { it.age == person.age }
            searchResultsLiveData.value = mockSearchResults
            return@switchMap searchResultsLiveData
        }
    }

    /**
     *
     */
    fun distinctPerson(): LiveData<Person> {
        return rawLiveData.distinctUntilChanged()
    }


    private val _whileSubscribed: Flow<Int> = flow<Int> {
        //init gps
        while (true) {
            //模拟gps.getLocation()
            emit(Random(3).nextInt())
            delay(2000)
        }
        //Sharing is started when the first subscriber appears,
        // stops when the last subscriber disappears after timeoutsMillis(by default 0)
        // keeping the replay cache forever (by default).
        //1.该策略可延迟热流开始时间 （1stFragment 不需要渲染位置信息, 2ndFragment 需要渲染位置信息）
        //2.2000ms可避免因AC配置变更导致的意外stop,从而再次订阅时 flow重新开始
    }.shareIn(viewModelScope, SharingStarted.WhileSubscribed(2000))
        .distinctUntilChanged { old, new ->
            old - new == 0
        }

    val downStream: Flow<String> = _whileSubscribed.map { it.toString() }

    val downStream2: Flow<String> = _whileSubscribed.map { it.toString() }


}