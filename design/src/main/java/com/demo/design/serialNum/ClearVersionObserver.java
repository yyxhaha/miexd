package com.demo.design.serialNum;

import java.util.Observable;
import java.util.Observer;

public class ClearVersionObserver implements Observer {
    public void update(Observable o, Object arg) {
        Version v = (Version) o;
        VersionProvider.clearVersion(v.getBtype());
    }

}
