package com.ldz.util.bean;

import java.util.List;

public class NewTrackPointReturn {

    private int status;

    private String message;

    private int total;

    private double distance;

    private double toll_distance;

    private List<Point> points;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getToll_distance() {
        return toll_distance;
    }

    public void setToll_distance(double toll_distance) {
        this.toll_distance = toll_distance;
    }

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

   public class Point{

        private double longitude;

        private double latitude;

        private long loc_time;

        private double direction;

        private double height;

        private double speed;

        private double radius;

        private String road_grade;

        private String car_limit_speed;

        private String coord_type;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public long getLoc_time() {
            return loc_time;
        }

        public void setLoc_time(long loc_time) {
            this.loc_time = loc_time;
        }

        public double getDirection() {
            return direction;
        }

        public void setDirection(double direction) {
            this.direction = direction;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public double getRadius() {
            return radius;
        }

        public void setRadius(double radius) {
            this.radius = radius;
        }

        public String getRoad_grade() {
            return road_grade;
        }

        public void setRoad_grade(String road_grade) {
            this.road_grade = road_grade;
        }

        public String getCar_limit_speed() {
            return car_limit_speed;
        }

        public void setCar_limit_speed(String car_limit_speed) {
            this.car_limit_speed = car_limit_speed;
        }

        public String getCoord_type() {
            return coord_type;
        }

        public void setCoord_type(String coord_type) {
            this.coord_type = coord_type;
        }
    }


}
