package  com.ablechange.learn.design.interceptor


interface Interceptor {
    fun intercept(chain: Chain): String
}

class Chain(
    private val interceptors: List<Interceptor>,
    private val index: Int,
    val requestDay: Int
) {
    fun proceed(): String {
        //拦截器拦截Chain
        return interceptors[index].intercept(Chain(interceptors, index + 1, requestDay))
    }
}

class Programmer {
    fun requestHoliday(): String {
        val interceptors = ArrayList<Interceptor>()
        interceptors.add(Major())
        interceptors.add(Ceo())
        interceptors.add(Boss())
        val chain = Chain(interceptors, 0, 7)
        return chain.proceed()
    }
}

/**
 * @Description:
 * @Author: haoshuaihui
 * @CreateDate: 2021/4/15 13:59
 */
class Boss : Interceptor {
    override fun intercept(chain: Chain): String {
        return if (chain.requestDay <= 10) {
            "boss:负责"
        } else {
            "boss:滚吧"
        }
    }
}

class Ceo : Interceptor {
    override fun intercept(chain: Chain): String {
        return if (chain.requestDay <= 5) {
            "Ceo:负责"
        } else {
            println("Ceo:上递")
            chain.proceed()
        }
    }
}

class Major : Interceptor {
    override fun intercept(chain: Chain): String {
        return if (chain.requestDay <= 3) {
            "Major:负责"
        } else {
            println("Major上递")
            chain.proceed()
        }
    }
}