package com.mobiowin.paalansocial.payload.response;

import java.util.Arrays;

/**
 * Created on 5/1/17.
 * Author Dharmendra
 * Company CmssPhyder
 */

public class OrgResCreateEvent {

    private String message;

    private String status;

    private Data[] data;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public Data[] getData ()
    {
        return data;
    }

    public void setData (Data[] data)
    {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrgResCreateEvent{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }

    public class Data
    {
        private String eventid;

        public String getEventid ()
        {
            return eventid;
        }

        public void setEventid (String eventid)
        {
            this.eventid = eventid;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "eventid='" + eventid + '\'' +
                    '}';
        }
    }

}
