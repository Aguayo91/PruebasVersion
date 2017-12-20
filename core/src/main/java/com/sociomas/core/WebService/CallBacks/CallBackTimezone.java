package com.sociomas.core.WebService.CallBacks;

import java.util.Calendar;

/**
 * Created by oemy9 on 25/08/2017.
 */

public interface CallBackTimezone {
    void OnSuccess(Calendar calendarApi);
    void OnError(Throwable error);
}
