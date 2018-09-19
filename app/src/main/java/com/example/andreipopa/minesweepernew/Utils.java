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

    public static int patternValueToValue(int value_in_pattern){

        int value= ValueType.UNDEFINED_VALUETYPE;

        switch (value_in_pattern){
            case 0:
                value=ValueType.EMPTY;
                break;
            case 1:
                value=ValueType.ONE;
                break;
            case 2:
                value=ValueType.TWO;
                break;
            case 3:
                value=ValueType.THREE;
                break;
            case 4:
                value=ValueType.FOUR;
                break;
            case 5:
                value=ValueType.FIVE;
                break;
            case 6:
                value=ValueType.SIX;
                break;
            case 7:
                value=ValueType.SEVEN;
                break;
            case 8:
                value=ValueType.EIGHT;
                break;
            case -1:
                value=ValueType.BOMB;
                break;
            default:
                throw new RuntimeException("Attempt to convert invalid pattern value to valueType ");
        }

        return value;

    }

    public static int[] xDirAccordingToGameMode(int gameMode){

        int[] xDir=new int[]{0,0,0,0,0,0,0,0};

        switch (gameMode){
            case GameMode.CLASSICAL:
                xDir= new int[]{-1,-1,0,1,1,1,0,-1};
                break;
            case GameMode.KNIGHTPATHS:
                xDir= new int[]{-2,-1,1,2,2,1,-1,-2};
                break;
            default:
                throw new RuntimeException("Invalid gameMode passed to xDirAccording function");
        }

        return xDir;
    }

    public static int[] yDirAccordingToGameMode(int gameMode){

        int[] yDir=new int[]{0,0,0,0,0,0,0,0};

        switch (gameMode){
            case GameMode.CLASSICAL:
                yDir= new int[]{0,1,1,1,0,-1,-1,-1};
                break;
            case GameMode.KNIGHTPATHS:
                yDir= new int[]{1,2,2,1,-1,-2,-2,-1};
                break;
            default:
                throw new RuntimeException("Invalid gameMode passed to yDirAccording function");

        }

        return yDir;
    }

    public static int valueAccordingToIcon(int icon){

        int value= ValueType.UNDEFINED_VALUETYPE;

        switch (icon){
            case IconType.ONE:
                value=ValueType.ONE;
                break;
            case IconType.TWO:
                value=ValueType.TWO;
                break;
            case IconType.THREE:
                value=ValueType.THREE;
                break;
            case IconType.FOUR:
                value=ValueType.FOUR;
                break;
            case IconType.FIVE:
                value=ValueType.FIVE;
                break;
            case IconType.SIX:
                value=ValueType.SIX;
                break;
            case IconType.SEVEN:
                value=ValueType.SEVEN;
                break;
            case IconType.EIGHT:
                value=ValueType.EIGHT;
                break;
            default:
                throw new RuntimeException("Why did you not send a number tile: "+String.valueOf(icon));
        }
        return value;
    }

    public static int iconAccordingToValue(int value){

        int icon= IconType.UNDEFINED_ICON;
        switch(value){
            case ValueType.ONE:
                icon=IconType.ONE;
                break;
            case ValueType.TWO:
                icon=IconType.TWO;
                break;
            case ValueType.THREE:
                icon=IconType.THREE;
                break;
            case ValueType.FOUR:
                icon=IconType.FOUR;
                break;
            case ValueType.FIVE:
                icon=IconType.FIVE;
                break;
            case ValueType.SIX:
                icon=IconType.SIX;
                break;
            case ValueType.SEVEN:
                icon=IconType.SEVEN;
                break;
            case ValueType.EIGHT:
                icon= IconType.EIGHT;
                break;
            case ValueType.EMPTY:
                icon=IconType.EMPTY;
                break;
            default:
                throw new RuntimeException("WHAT Number did you give me? ");
        }

        return icon;

    }
}
