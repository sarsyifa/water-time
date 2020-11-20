package id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.dao.EventDao;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.dao.UserDao;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.database.WaterConsumptionDatabase;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.Event;
import id.ac.ui.cs.mobileprogramming.sitiauliarahmatussyifa.watertime.entity.User;

public class EventRepository {
    private EventDao eventDao;
    private List<Event> allEvents;
    public EventRepository(Application application) {
        WaterConsumptionDatabase db = WaterConsumptionDatabase.getDatabase(application);
        eventDao = db.getEventDao();
        allEvents = eventDao.getAll();
    }

    public List<Event> getAllEvents() {
        return allEvents;
    }

    public void addEvent(Event e) {

        new EventRepository.InsertEventAsyncTask(eventDao).execute(e);
    }

    public void updateEvent(Event e) {

        new EventRepository.UpdateEventAsyncTask(eventDao).execute(e);
    }

    public void deleteEvent(Event e) {

        new EventRepository.DeleteEventAsyncTask(eventDao).execute(e);
    }

    public void deleteAllUsers() {
        new EventRepository.DeleteAllEventsAsyncTask(eventDao).execute();
    }

    private static class InsertEventAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao eventDao;

        InsertEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... params) {
            eventDao.insert(params[0]);
            return null;
        }

    }

    private static class UpdateEventAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao eventDao;

        UpdateEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... params) {
            eventDao.update(params[0]);
            return null;
        }

    }

    private static class DeleteEventAsyncTask extends AsyncTask<Event, Void, Void> {

        private EventDao eventDao;

        DeleteEventAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }

        @Override
        protected Void doInBackground(Event... params) {
            eventDao.delete(params[0]);
            return null;
        }

    }
    private static class DeleteAllEventsAsyncTask extends AsyncTask<Void, Void, Void> {
        private EventDao eventDao;
        DeleteAllEventsAsyncTask(EventDao eventDao) {
            this.eventDao = eventDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            eventDao.deleteAllEvents();
            return null;
        }
    }
}
