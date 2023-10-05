package com.example.hiltunittest.util.state

/**
 * @author hafizdwp
 * 29/09/2023
 **/
sealed class ViewState {
    object Loading : ViewState()

    object Success : ViewState()

    class Empty(val emptyMsg: String? = null) : ViewState()

    class Failed(val errorMsg: String?) : ViewState()
}