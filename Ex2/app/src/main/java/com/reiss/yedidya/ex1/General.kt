package com.reiss.yedidya.ex1

import android.widget.Toast

/**
 * Created by yedidya on 05/04/2018.
 */




fun toast(msg:String) {
    Toast.makeText(App.context, msg, Toast.LENGTH_SHORT).show();
}