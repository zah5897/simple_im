package com.mobile.im.service;

import com.mobile.im.dao.LocationDao;
import com.mobile.im.dao.MsgDao;
import com.mobile.im.entity.Location;
import com.mobile.im.entity.Msg;
import com.mobile.im.enums.ChatType;
import com.mobile.im.enums.MsgType;
import com.mobile.im.start.event.MsgEvent;
import com.mobile.im.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zah on 2018/6/22.
 */
@SuppressWarnings("ALL")
@Service
public class LocationService {
    @Autowired
    private LocationDao locationDao;

    public int save(Location location) {
        if (locationDao.getLocationCount(location) > 0) {
            return locationDao.updateLocation(location);
        }
        return locationDao.insertObj(location);
    }

    public List<Location> loadPublish(String username, Integer last_id, Integer pageSize) {
        return locationDao.loadPublish(username, last_id == null ? Integer.MAX_VALUE : last_id, pageSize == null ? 20 : pageSize);
    }
}
