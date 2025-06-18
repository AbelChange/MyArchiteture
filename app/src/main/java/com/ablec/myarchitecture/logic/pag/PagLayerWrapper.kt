package com.ablec.myarchitecture.logic.pag

import org.libpag.PAGLayer

enum class PlayEndStrategy {
    REPEAT,//循环
    ONCE
}
data class PagLayerWrapper(val pagLayer:PAGLayer,val strategy:PlayEndStrategy)
