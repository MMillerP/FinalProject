package com.example.finalproject.rDB;

public class DefaultContentResource { // contains default content for resources database
    public static String[] TAG = {
            "LMB","IRN","COA","STR","STE","WOO","CEM","CLO","CLT","CAR","FOO","PAP"
    };

    public static int[] aiOneProduce = {// focus on Iron and Coal
            30,  // Lumber
            200, // Iron
            300, // Coal
            50,  // String
            200, // Steel
            20,  // Wood
            100, // Cement
            70,  // Cloth
            120, // Clothes
            72,  // Furniture
            400, // Food
            80  // Paper
    };
    public static int[] aiOneConsume = { // 20,000 people
            70,  // Lumber
            113, // Iron
            95, // Coal
            55,  // String
            20, // Steel
            38,  // Wood
            40, // Cement
            80,  // Cloth
            60, // Clothes
            60, // Furniture
            100, // Food
            40 // Paper
    };
    public static int[] aiTwoProduce = { // focus on lumber and string products
            200,  // Lumber
            60, // Iron
            50, // Coal
            50,  // String
            100, // Steel
            300,  // Wood
            100, // Cement
            120,  // Cloth
            250, // Clothes
            140, // Furniture
            400, // Food
            130 // Paper
    };
    public static int[] aiTwoConsume = { //50,000
            265,  // Lumber
            135, // Iron
            100, // Coal
            110,  // String
            50, // Steel
            85,  // Wood
            100, // Cement
            175,  // Cloth
            150, // Clothes
            150, // Furniture
            250, // Food
            100 // Paper
    };
    public static int[] aiThreeProduce = { // focus on steel and cement
            40,  // Lumber
            70, // Iron
            100, // Coal
            50,  // String
            400, // Steel
            20,  // Wood
            300, // Cement
            80,  // Cloth
            110, // Clothes
            80, // Furniture
            300, // Food
            60 // Paper
    };
    public static int[] aiThreeConsume = {//10,000
            50,  // Lumber
            205, // Iron
            185, // Coal
            50,  // String
            10, // Steel
            30,  // Wood
            20, // Cement
            65,  // Cloth
            30, // Clothes
            30, // Furniture
            50, // Food
            20 // Paper
    };

    public static int[] startPrices = {
            2,//Lumber
            3,//Iron
            2,//Coal
            1,//String
            4,//Steel
            3,//wood
            3,//cement
            2,//cloth
            4,//clothes
            5,//furniture
            1,//food
            3//paper
    };

    public static int[] popConsume = { // per 1000 pop
            1,
            1,
            1,
            1,
            1,
            1,
            2,
            1,
            3,
            3,
            5,
            2
    };

}
