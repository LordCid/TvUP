package com.example.omapp

const val BASE_URL = "https://mediasync.tvup.cloud/media/carrier/"
const val MOVIE_COUNT = "5"
//const val GET_MOVIES_PATH = "GetUnifiedList?client=json&statuses=published&definitions=SD;HD;4K&external_category_id=SED_3880&filter_empty_categories=true&count=$MOVIE_COUNT"
const val GET_MOVIES_PATH = "GetUnifiedList?client=json&external_category_id=U7D_14039&statuses=published&definitions=SD;HD&order=asc&order_by=tree&count=$MOVIE_COUNT"
const val RAIL_ID = "railId"
const val GET_GENERES_SHOWS = "{$RAIL_ID}/last7d.cine.json "
const val GET_MOVIE_PATH = "GetVideo?client=json"

const val IMAGES_BASE_PATH = "https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images"

const val IMAGES_BASE_GENERE_SHOWS_PATH = "https://mediasync.tvup.cloud"

// DATABASE
const val DATABASE_VERSION = 1
const val DATABASE_NAME = "interbus_database"


const val EMPTY_STRING = ""

//ERRORS MESSAGES
const val ERROR_DATABASE_GENERIC_MESSAGE = "Error DataBase"
const val ERROR_NETWORK_GENERIC_MESSAGE = "Error Network"
const val ERROR_GENERIC_MESSAGE = "Error"