/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ablec.module_base.db

import com.ablec.module_base.db.entity.ProductEntity
import java.util.*

/**
 * Generates data to pre-populate the database
 */
object DataGenerator {
    private val FIRST = arrayOf(
        "Special edition", "New", "Cheap", "Quality", "Used"
    )
    private val SECOND = arrayOf(
        "Three-headed Monkey", "Rubber Chicken", "Pint of Grog", "Monocle"
    )
    private val DESCRIPTION = arrayOf(
        "is finally here", "is recommended by Stan S. Stanman",
        "is the best sold product on Mêlée Island", "is \uD83D\uDCAF", "is ❤️", "is fine"
    )
    private val COMMENTS = arrayOf(
        "Comment 1", "Comment 2", "Comment 3", "Comment 4", "Comment 5", "Comment 6"
    )

    fun generateProducts(): List<ProductEntity> {
        val products: MutableList<ProductEntity> =
            ArrayList<ProductEntity>(FIRST.size * SECOND.size)
        val rnd = Random()
        for (i in FIRST.indices) {
            for (j in SECOND.indices) {
                val product = ProductEntity(description = "sdad",price = 100)
                products.add(product)
            }
        }
        return products
    }

}