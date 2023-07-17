package com.example.omapp.presentation.model

import com.example.omapp.common.Mapper
import com.example.omapp.domain.model.GenereShows

const val RAIL_TITLE= "Rail: "
class MapperUI : Mapper<List<GenereShowsUI>, List<GenereShows>> {

    companion object{
        const val UI_MAPPER_NAME = "MapperUI"
    }

    override fun map(input: List<GenereShows>): List<GenereShowsUI>  = input.map {
        GenereShowsUI(
            title = "$RAIL_TITLE${it.id}",
            shows = it.shows
        )
    }

}