package com.example.gimnasioalemn_friedrichvonschiller.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

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

    // Traer datos específicos del estudiante, por ejemplo el "grade"
    public void getStudentData(String userId, final StudentDataCallback callback) {
        database.child("users").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String role = snapshot.child("role").getValue(String.class);
                            if ("student".equals(role)) {
                                String grade = snapshot.child("grade").getValue(String.class);
                                callback.onSuccess(grade);
                            } else {
                                callback.onFailure("El usuario no es un estudiante");
                            }
                        } else {
                            callback.onFailure("Usuario no encontrado");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        callback.onFailure("Error al obtener datos: " + error.getMessage());
                    }
                });
    }

    // Traer datos específicos del teacher
    public void getTeacherData(String userId, final TeacherDataCallback callback) {
        database.child("users").child(userId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String role = snapshot.child("role").getValue(String.class);
                            if ("teacher".equals(role)) {
                                List<String> subjectsList = new ArrayList<>();
                                for (DataSnapshot subjectSnapshot : snapshot.child("subjects").getChildren()) {
                                    String subject = subjectSnapshot.getValue(String.class);
                                    subjectsList.add(subject);
                                }
                                callback.onSuccess(subjectsList);
                            } else {
                                callback.onFailure("El usuario no es un profesor");
                            }
                        } else {
                            callback.onFailure("Usuario no encontrado");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        callback.onFailure("Error al obtener datos: " + error.getMessage());
                    }
                });
    }

    public interface DataCallback {
        void onSuccess(DataSnapshot dataSnapshot);
        void onFailure(String errorMessage);
    }

    public interface UserDataCallback {
        void onSuccess(String fullName, String role, String password, String email, String phoneNumber);
        void onFailure(String errorMessage);
    }

    public interface StudentDataCallback {
        void onSuccess(String grade);
        void onFailure(String errorMessage);
    }

    public interface TeacherDataCallback {
        void onSuccess(List<String> subjects);
        void onFailure(String errorMessage);
    }
}