package model;

/**
 * The FilmBuilder class provides a flexible way to construct a Film object.
 * This builder pattern allows for the step-by-step construction of Film instances.
 */


public class FilmBuilder {
    int id;
    String title;
    int year;
    String director;
    String stars;
    String review;
    FilmBuilder setID(int id){
        this.id = id;
        return this;
    }
    FilmBuilder setTitle(String title){
        this.title = title;
        return this;
    }
    FilmBuilder setYear(int year){
        this.year = year;
        return this;
    }
    FilmBuilder setDirector(String director){
        this.director = director;
        return this;
    }
    FilmBuilder setStars(String stars){
        this.stars = stars;
        return this;
    }
    FilmBuilder setReview(String review){
        this.review = review;
        return this;
    }
    Film build(){
        return new Film(this);
    }
}