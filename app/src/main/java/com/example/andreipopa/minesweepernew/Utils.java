package com.example.andreipopa.minesweepernew;

public class Utils {

    public static int valueToDrawable(int value){
        int drawableCode= 0;
        switch (value){
            case ValueType.EMPTY:
                drawableCode=R.drawable.new_empty_tile;
                break;
            case ValueType.ONE:
                drawableCode=R.drawable.new_one_tile;
                break;
            case ValueType.TWO:
                drawableCode=R.drawable.new_two_tile;
                break;
            case ValueType.THREE:
                drawableCode=R.drawable.new_three_tile;
                break;
            case ValueType.FOUR:
                drawableCode=R.drawable.new_four_tile;
                break;
            case ValueType.FIVE:
                drawableCode=R.drawable.new_five_tile;
                break;
            case ValueType.SIX:
                drawableCode=R.drawable.new_six_tile;
                break;
            case ValueType.SEVEN:
                drawableCode=R.drawable.new_seven_tile;
                break;
            case ValueType.EIGHT:
                drawableCode=R.drawable.new_eight_tile;
                break;
            case ValueType.BOMB:
                drawableCode=R.drawable.bomb;
                break;
            default:
                throw new RuntimeException("Not a proper code for selecting drawable");
        }

        return drawableCode;
    }

    public static int iconToDrawable(int icon){
        int drawableCode=0;

        switch (icon){
            case IconType.ONE:
                 drawableCode=R.drawable.new_one_tile;
                 break;
            case IconType.TWO:
                drawableCode=R.drawable.new_two_tile;
                break;
            case IconType.THREE:
                drawableCode= R.drawable.new_three_tile;
                break;
            case IconType.FOUR:
                drawableCode=R.drawable.new_four_tile;
                break;
            case IconType.FIVE:
                drawableCode=R.drawable.new_five_tile;
                break;
            case IconType.SIX:
                drawableCode=R.drawable.new_six_tile;
                break;
            case IconType.SEVEN:
                drawableCode=R.drawable.new_seven_tile;
                break;
            case IconType.EIGHT:
                drawableCode=R.drawable.new_eight_tile;
                break;
            case IconType.EMPTY:
                drawableCode=R.drawable.new_empty_tile;
                break;
            case IconType.BOMB:
                drawableCode=R.drawable.bomb;
                break;
            case IconType.FLAG:
                drawableCode=R.drawable.new_flagged_tile;
                break;
            case IconType.RED_BOMB:
                drawableCode=R.drawable.new_red_bomb;
                break;
            case IconType.HIDDEN:
                drawableCode=R.drawable.new_hidden;
                break;
            case IconType.WRONG_FLAG:
                drawableCode=R.drawable.new_wrong_flagged_tile;
                break;
            case IconType.UNDEFINED_ICON:
                throw new RuntimeException("Passed an invalid icon value to the converter");


        }

        return drawableCode;
    }
}
