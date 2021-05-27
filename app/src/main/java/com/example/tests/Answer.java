package com.example.tests;

import java.io.Serializable;

public class Answer implements Serializable {

    private Long id;
    private String answer;
    private Integer score;

    public Answer(Long id, String answer){
        this.id = id;
        this.answer=answer;
        this.score=score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }



    public void setScore(Integer score) {
        this.score = score;
    }

}
