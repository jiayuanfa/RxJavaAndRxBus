package com.example.rxjavademo

import com.google.gson.Gson
import com.orhanobut.logger.Logger

object Logger {

        /**
         * 对象转Json打印
         */
        fun json(tag: String, result: Any) {
            Logger.t(tag).json(Gson().toJson(result))
        }

        /**
         * 打印日志
         */
        fun d(tag: String, result: Any) {
            Logger.t(tag).d(result)
        }
}