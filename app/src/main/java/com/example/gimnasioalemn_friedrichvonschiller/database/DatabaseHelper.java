package com.example.gimnasioalemn_friedrichvonschiller.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper {
    private DatabaseReference database;
    public DatabaseHelper() {
        database = FirebaseDatabase.getInstance().getReference();
    }

    // Metodo para obtener datos desde cualquier referencia de la base de datos
    public void getData(String path, final DataCallback callback) {
        database.child(path)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            callback.onSuccess(dataSnapshot);
                        } else {
                            callback.onFailure("Datos no encontrados");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        callback.onFailure("Error al obtener los datos");
                    }
                });
    }

    // Metodo para obtener los datos de un usuario específico (por ID)
    public void getUserData(String userId, final UserDataCallback callback) {
        getData("users/" + userId, new DataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                String fullName = dataSnapshot.child("full_name").getValue(String.class);
                String role = dataSnapshot.child("role").getValue(String.class);
                String storedPassword = dataSnapshot.child("password").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String phoneNumber = dataSnapshot.child("phone_number").getValue(String.class);

                // Pasar todos los datos al callback
                callback.onSuccess(fullName, role, storedPassword, email, phoneNumber);
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    // Metodo para obtener las tareas de un estudiante
    public void getStudentTasks(String userId, final TaskDataCallback callback) {
        getData("users/" + userId + "/tasks", new DataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    String taskTitle = taskSnapshot.child("task_title").getValue(String.class);
                    String subject = taskSnapshot.child("subject").getValue(String.class);
                    String dueDate = taskSnapshot.child("due_date").getValue(String.class);
                    String status = taskSnapshot.child("status").getValue(String.class);
                    callback.onTaskReceived(taskTitle, subject, dueDate, status);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    // Metodo para obtener todos los eventos de un grado específico
    public void getGradeEvents(final EventDataCallback callback) {
        getData("events/" , new DataCallback() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    String title = eventSnapshot.child("title").getValue(String.class);
                    String time = eventSnapshot.child("time").getValue(String.class);
                    String date = eventSnapshot.child("date").getValue(String.class);
                    String description = eventSnapshot.child("description").getValue(String.class);
                    callback.onEventReceived(title, date, time, description);
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                callback.onFailure(errorMessage);
            }
        });
    }

    // Metodo para crear un evento
    public void createEvent(String userId, String title, String date, String description) {
            String eventId = database.child("events").push().getKey();

            // Usamos un Map para almacenar los detalles del evento
            Map<String, Object> event = new HashMap<>();
            event.put("title", title);
            event.put("date", date);
            event.put("description", description);

            // Guardamos el evento en Firebase Realtime Database
            database.child("events").child(eventId).setValue(event);
    }

    // Interfaces de callback para los diferentes tipos de datos
    public interface DataCallback {
        void onSuccess(DataSnapshot dataSnapshot);
        void onFailure(String errorMessage);
    }

    public interface UserDataCallback {
        void onSuccess(String fullName, String role, String password, String email, String phoneNumber);
        void onFailure(String errorMessage);
    }


    public interface TaskDataCallback {
        void onTaskReceived(String taskTitle, String subject, String dueDate, String status);
        void onFailure(String errorMessage);
    }

    public interface EventDataCallback {
        void onEventReceived(String title, String date, String time, String description);
        void onFailure(String errorMessage);
    }

    public interface ScheduleDataCallback {
        void onScheduleReceived(String time, String subject, String teacher);
        void onFailure(String errorMessage);
    }
}