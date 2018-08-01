package cn.bidostar.ticserver.utils;

import de.greenrobot.event.EventBus;

/**
 * Created by wangg on 2017/4/23.
 */

public class AppBusService {
    private static EventBus eventBus;
    protected void init() {
        eventBus = EventBus.getDefault();
    }
    public void initBusService(){
        init();
    }
    private AppBusService() {
    }
    public static EventBus getBus() {
        return eventBus;
    }
    private static final AppBusService NBAPLUS_SERVICE = new AppBusService();
    public static AppBusService getInstance() {
        return NBAPLUS_SERVICE;
    }
}
