package com.miaomi.fenbei.room.ui.callback

import android.database.Observable

class RecordVolumeObservable : Observable<RecordVolumeObservable.RecordVolumeObserver>() {

    fun changeValue(volume: Int) {
        for (i in mObservers.indices.reversed()) {
            mObservers[i].onChange(volume)
        }
    }

    interface RecordVolumeObserver  {
        fun onChange(volume: Int)
    }
}