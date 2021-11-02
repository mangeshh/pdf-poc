package com;

import java.util.ArrayList;
import java.util.List;

class RedactBox {
    int startX;
    int startY;
    int endX;
    int endY;
    int w;
    int h;
    int offsetLeft;
    int offsetTop;

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getOffsetLeft() {
        return offsetLeft;
    }

    public void setOffsetLeft(int offsetLeft) {
        this.offsetLeft = offsetLeft;
    }

    public int getOffsetTop() {
        return offsetTop;
    }

    public void setOffsetTop(int offsetTop) {
        this.offsetTop = offsetTop;
    }

    @Override
    public String toString() {
        return "RedactBox{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", w=" + w +
                ", h=" + h +
                ", offsetLeft=" + offsetLeft +
                ", offsetTop=" + offsetTop +
                '}';
    }
}

class RedactBoxSet {
    List<RedactBox> redactBoxSet = new ArrayList<>();

    public List<RedactBox> getRedactBoxSet() {
        return redactBoxSet;
    }

    public void setRedactBoxSet(List<RedactBox> redactBoxSet) {
        this.redactBoxSet = redactBoxSet;
    }
}
