package uz.adkhamjon.promovie.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TypeViewModel:ViewModel() {
    private val gridType = MutableLiveData<String>()
    private val dialogType = MutableLiveData<String>()
    fun setGridType(number:String){
        gridType.value=number
    }
    fun getGridType(): LiveData<String> {
        return gridType
    }
    fun setDialogType(str:String){
        dialogType.value=str
    }
    fun getDialogType(): LiveData<String> {
        return dialogType
    }
}