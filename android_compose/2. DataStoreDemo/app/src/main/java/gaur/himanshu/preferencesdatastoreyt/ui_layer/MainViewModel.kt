package gaur.himanshu.preferencesdatastoreyt.ui_layer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gaur.himanshu.preferencesdatastoreyt.pref.UserPref
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val userPref: UserPref) : ViewModel() {

    val userName = userPref.getName().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = ""
    )

    fun saveData(name: String) {
        viewModelScope.launch {
            userPref.saveName(name)
        }
    }


}