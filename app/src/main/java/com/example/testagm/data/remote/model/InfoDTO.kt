package com.example.testagm.data.remote.model

import com.example.testagm.domain.model.Info

data class InfoDTO(
    val count: Long,
    val pages: Long,
    val next: String?,
    val prev: String?
) {
    fun toDomain(): Info {
        val nextPage: Int? = this.next?.split("page=")?.last()?.toInt()
        val prevPage: Int? = this.prev?.split("page=")?.last()?.toInt()
        return Info(
            next = nextPage,
            prev = prevPage
        )
    }
}