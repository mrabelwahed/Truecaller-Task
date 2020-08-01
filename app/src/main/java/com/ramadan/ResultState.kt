package com.ramadan

import Failure


sealed class ResultState {
    data class Error(val failure: Failure) : ResultState()
    data class Success<T>(val value: T) : ResultState()
}