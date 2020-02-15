package com.example.hotfix;

import android.content.Context;
import android.widget.Toast;

/**
 * @ClassName: Calculator
 * @Description:
 * @CreateDate: 2020/2/15
 */
public class Calculator {
    public void calculate(Context context){
        int a = 666;
        int b = 0;
        Toast.makeText(context, ""+a/b, Toast.LENGTH_SHORT).show();
    }
}
