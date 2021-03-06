/* OpenTrail is licensed under the GNU General Public License v2.
(c) Nick Whitelegg, 2012-18 */

package freemap.opentrail;

/*** Created by nick on 09/01/16.
 */


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import freemap.andromaps.ConfigChangeSafeTask;
import freemap.andromaps.DataCallbackTask;
import freemap.andromaps.HTTPCommunicationTask;

public class SavedData
{

    private DataCallbackTask<?,?> dataCallbackTask;
    private HTTPCommunicationTask httpTask;
    private OpenTrail.AddToWalkrouteCacheTask addToWalkrouteTask;

    private static SavedData instance;

    private SavedData() { }

    static SavedData getInstance()
    {
        if(instance==null)
            instance = new SavedData();
        return instance;
    }

    void reconnect(Context ctx, HTTPCommunicationTask.Callback callback)
    {
        if(dataCallbackTask !=null)
        {
               dataCallbackTask.reconnect(ctx, callback);
        }
        if(httpTask!=null)
        {

            httpTask.reconnect(ctx, callback);
        }
        reconnectWalkrouteSaveTask(ctx);
    }

    void setHTTPCommunicationTask (HTTPCommunicationTask dfTask) {
        disconnectHTTPCommunicationTask();
        this.httpTask = dfTask;
    }

     void setHTTPCommunicationTask (HTTPCommunicationTask dfTask, String dialogTitle, String dialogText)
    {
        dfTask.setDialogDetails(dialogTitle, dialogText);
        setHTTPCommunicationTask(dfTask);




    }
    void executeHTTPCommunicationTask (HTTPCommunicationTask dfTask, String dialogTitle, String dialogText)
    {
        setHTTPCommunicationTask(dfTask, dialogTitle, dialogText);
        dfTask.confirmAndExecute();
    }

    // TODO just have one getter for the one dataCallbackTask
     HTTPCommunicationTask getHTTPCommunicationTask()
    {

        return httpTask; // (HTTPCommunicationTask)dataCallbackTask;
    }

     void setDataCallbackTask (DataCallbackTask<?,?> dataTask) {
        disconnectDataCallbackTask();
        this.dataCallbackTask = dataTask;
    }

     DataCallbackTask<?,?> getDataCallbackTask()
    {

        return dataCallbackTask;
    }

    void disconnect() {
        disconnectDataCallbackTask();
        disconnectHTTPCommunicationTask();
    }

    private void disconnectDataCallbackTask() {

        if (dataCallbackTask != null && dataCallbackTask.getStatus() == AsyncTask.Status.RUNNING) {

            dataCallbackTask.disconnect();
        } else
            dataCallbackTask = null;
    }

    private void disconnectHTTPCommunicationTask() {
        if(httpTask!=null && httpTask.getStatus()==AsyncTask.Status.RUNNING)
        {

            httpTask.disconnect();
        }
        else
            httpTask = null;
    }

    public void disconnectWalkrouteSaveTask(OpenTrail.AddToWalkrouteCacheTask task) {
        if(task!=null && task.getStatus()==AsyncTask.Status.RUNNING) {
            addToWalkrouteTask = task;
            task.disconnect();
        } else {
            addToWalkrouteTask = null;
        }
    }

    private void reconnectWalkrouteSaveTask(Context ctx) {
        if(addToWalkrouteTask != null) {
            addToWalkrouteTask.reconnect((OpenTrail)ctx);
        }
    }
}
