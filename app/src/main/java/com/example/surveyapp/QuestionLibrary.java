package com.example.surveyapp;

public class QuestionLibrary
{
    private String mQuestions [] = {
            "How much water do you consume for washing ",
            "What percentage is used",
            "How can we save water",
            "Which need of yours consume more water"
    };
    private String mChoices [][] = {
            {"10","20","30","40"},
            {"25","50","60","90"},
            {"method 1","method 2","method 3","method 4"},
            {"washing utensils","bathing","washing clothes","house cleaning"}
    };

    public String getQuestion(int a)
    {
        String question = mQuestions[a];
        return question;
    }
    public String getChoice1(int a)
    {
        String choice0 = mChoices[a][0];
        return choice0;
    }
    public String getChoice2(int a)
    {
        String choice1 = mChoices[a][1];
        return choice1;
    }
    public String getChoice3(int a)
    {
        String choice2 = mChoices[a][2];
        return choice2;
    }
    public String getChoice4(int a)
    {
        String choice3 = mChoices[a][3];
        return choice3;
    }
}
