package com.egco428.firebasedatabase

/**
 * Created by 6272user on 11/30/2017 AD.
 */

class Message (val id: String, val message: String, val rating: Int){
    constructor(): this("","",0)
}