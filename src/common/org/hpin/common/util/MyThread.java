package org.hpin.common.util;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by daimian on 17-3-8.
 */
public class MyThread implements Callable<String>{

    private Integer count;

    public MyThread(Integer count){
       this.count = count;
    }

    @Override
    public String call() throws Exception {
        Integer ycj = PropsUtils.getInt("status", "statusYm.ycj");
        System.out.println("thread1 ycj:["+count+"]: "+ycj);
        return ""+ycj;
    }

}
