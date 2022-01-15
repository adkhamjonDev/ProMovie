package uz.adkhamjon.promovie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GridTypeViewModel:ViewModel() {
    private val data = MutableLiveData<String>()
    fun setType(number:String){
        data.value=number
    }
    fun getType(): LiveData<String> {
        return data
    }
}