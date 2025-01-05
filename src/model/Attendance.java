package model;

import java.time.LocalDate;

public class Attendance {
    private int id;
    private int memberId;
    private int classId;
    private LocalDate attendanceDate;

    // No-argument constructor
    public Attendance() {
    }

    // Constructor with parameters
    public Attendance(int memberId, int classId, LocalDate attendanceDate) {
        this.memberId = memberId;
        this.classId = classId;
        this.attendanceDate = attendanceDate;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(LocalDate attendanceDate) {
        this.attendanceDate = attendanceDate;
    }
}
