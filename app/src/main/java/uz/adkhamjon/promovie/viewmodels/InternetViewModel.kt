package uz.adkhamjon.promovie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InternetViewModel:ViewModel() {
    private val internetConnection = MutableLiveData<String>()
    fun setInternet(internet:String){
        internetConnection.value=internet
    }
    fun getInternet(): LiveData<String> {
        return internetConnection
    }
}